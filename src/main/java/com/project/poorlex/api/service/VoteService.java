package com.project.poorlex.api.service;

import com.project.poorlex.domain.battle.Battle;
import com.project.poorlex.domain.battle.BattleRepository;
import com.project.poorlex.domain.battleuser.BattleUser;
import com.project.poorlex.domain.battleuser.BattleUserRepository;
import com.project.poorlex.domain.vote.Vote;
import com.project.poorlex.domain.vote.VoteRepository;
import com.project.poorlex.domain.voteopinion.VoteOpinion;
import com.project.poorlex.domain.voteopinion.VoteOpinionRepository;
import com.project.poorlex.dto.vote.VoteCreateRequest;
import com.project.poorlex.dto.vote.VoteCreateResponse;
import com.project.poorlex.dto.voteopinion.VoteSaveRequest;
import com.project.poorlex.exception.battle.BattleCustomException;
import com.project.poorlex.exception.battle.BattleErrorCode;
import com.project.poorlex.exception.battleuesr.BattleUserCustomException;
import com.project.poorlex.exception.battleuesr.BattleUserErrorCode;
import com.project.poorlex.exception.vote.VoteCustomException;
import com.project.poorlex.exception.vote.VoteErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final VoteOpinionRepository voteOpinionRepository;

    private final BattleUserRepository battleUserRepository;

    private final BattleRepository battleRepository;

    public VoteCreateResponse createVote(VoteCreateRequest request) throws VoteCustomException {

        Long battleUserId = request.getBattleUserId();

        BattleUser battleUser = battleUserRepository.findById(battleUserId)
                .orElseThrow(() -> new BattleUserCustomException(BattleUserErrorCode.MEMBER_NOT_FOUND));

        Battle battleExist = battleRepository.findById(battleUserId)
                .orElseThrow(() -> new BattleCustomException(BattleErrorCode.BATTLE_NOT_FOUND));

        LocalDateTime endDate = request.calculateEndDate();

        Vote vote = Vote.builder()
                .battleUser(battleUser)
                .name(request.getName())
                .price(request.getPrice())
                .endDate(endDate)
                .build();

        voteRepository.save(vote);

        return VoteCreateResponse.of(vote);

    }

    public Vote getVote(Long voteId) {

        return voteRepository.findById(voteId)
                .orElseThrow(() -> new VoteCustomException(VoteErrorCode.VOTE_NOT_FOUND));
    }

    @Transactional
    public VoteOpinion saveVoteOpinion(VoteSaveRequest request) {

        Vote vote = voteRepository.findById(request.getVoteId())
                .orElseThrow(() -> new VoteCustomException(VoteErrorCode.VOTE_NOT_FOUND));

        BattleUser battleUser = battleUserRepository.findById(request.getBattleUserId())
                .orElseThrow(() -> new BattleUserCustomException(BattleUserErrorCode.MEMBER_NOT_FOUND));

        LocalDateTime currentTime = LocalDateTime.now();
        if (vote.getEndDate().isBefore(currentTime)) {
            throw new VoteCustomException(VoteErrorCode.TIME_EXPIRED);
        }

        if (battleUser.equals(vote.getBattleUser())) {
            throw new VoteCustomException(VoteErrorCode.VOTE_NOT_VALID);
        }

        List<VoteOpinion> userOpinions = voteOpinionRepository.findIdByBattleUserIdAndVoteId(
                request.getBattleUserId(),
                request.getVoteId());

        if (userOpinions.isEmpty()) {
            VoteOpinion voteOpinion = VoteOpinion.builder()
                    .agreeYn(request.isAgreeYn())
                    .vote(vote)
                    .battleUser(battleUser)
                    .build();
            voteOpinionRepository.save(voteOpinion);
            return voteOpinion;

        } else {
            VoteOpinion existingOpinion = userOpinions.get(0);

            VoteOpinion updatedOpinion = VoteOpinion.builder()
                    .id(existingOpinion.getId())
                    .agreeYn(request.isAgreeYn())
                    .vote(existingOpinion.getVote())
                    .battleUser(existingOpinion.getBattleUser())
                    .build();
            return voteOpinionRepository.save(updatedOpinion);
        }
    }

    public Vote voteResult(Long voteId) {

        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new VoteCustomException(VoteErrorCode.VOTE_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();

        if (vote.getEndDate().isAfter(now)) {
            throw new VoteCustomException(VoteErrorCode.BEFORE_END_TIME);
        }

        Long countUsers = voteOpinionRepository.countAllByVoteId(voteId);
        Long countPros = voteOpinionRepository.countByAgreeYnTrueAndVoteId(voteId);

        String result = prosCons(countUsers, countPros);

        vote = Vote.builder()
                .id(voteId)
                .battleUser(vote.getBattleUser())
                .name(vote.getName())
                .price(vote.getPrice())
                .endDate(vote.getEndDate())
                .result(result)
                .build();
        return voteRepository.save(vote);
    }

    private String prosCons(Long countUsers, Long countPros) {

        double prosPercentage = (double) countPros / countUsers * 100.0;
        double consPercentage = 100.0 - prosPercentage;

        if (prosPercentage > consPercentage) {
            return "찬성";
        } else if (prosPercentage < consPercentage) {
            return "반대";
        } else {
            return "동점";
        }
    }

    public Vote voteResultConfirm(Long voteId) {

        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new VoteCustomException(VoteErrorCode.VOTE_NOT_FOUND));

        if (!vote.getEndDate().isBefore(LocalDateTime.now())) {
            throw new VoteCustomException(VoteErrorCode.BEFORE_END_TIME);
        }
        return vote;
    }
}