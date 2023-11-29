package com.project.poorlex.api.service;

import com.project.poorlex.domain.battleuser.BattleUser;
import com.project.poorlex.domain.battleuser.BattleUserRepository;
import com.project.poorlex.domain.vote.VoteComment;
import com.project.poorlex.domain.vote.VoteCommentRepository;
import com.project.poorlex.domain.vote.Vote;
import com.project.poorlex.domain.vote.VoteRepository;
import com.project.poorlex.dto.vote.CommentCreateRequest;
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
@Transactional
@RequiredArgsConstructor
public class VoteCommentService {

    private final VoteCommentRepository voteCommentRepository;
    private final VoteRepository voteRepository;
    private final BattleUserRepository battleUserRepository;

    public Long save(CommentCreateRequest request) {

        Vote vote = voteRepository.findById(request.getVoteId()).get();
        if (vote == null) {
            throw new VoteCustomException(VoteErrorCode.VOTE_NOT_FOUND);
        }

        BattleUser battleUser = battleUserRepository.findById(request.getBattleUserId()).orElse(null);
        if (battleUser == null) {
            throw new BattleUserCustomException(BattleUserErrorCode.MEMBER_NOT_FOUND);
        }
        LocalDateTime voteTime = LocalDateTime.now();
        if (vote.getEndDate().isBefore(voteTime)) {
            throw new VoteCustomException(VoteErrorCode.TIME_EXPIRED);
        }

        VoteComment voteComment = VoteComment.builder()
                .vote(vote)
                .comments(request.getComments())
                .battleUser(battleUser)
                .build();
        return voteCommentRepository.save(voteComment).getId();
    }

    public List<VoteComment> findAll(Long voteId) {

        Vote vote = voteRepository.findById(voteId).get();
        List<VoteComment> voteCommentList = voteCommentRepository.findAllByVoteOrderByIdDesc(vote);

        return voteCommentList;
    }
}
