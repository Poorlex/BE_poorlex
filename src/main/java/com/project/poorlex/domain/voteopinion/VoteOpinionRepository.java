package com.project.poorlex.domain.voteopinion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteOpinionRepository extends JpaRepository<VoteOpinion, Long> {
    Long countByAgreeYnTrueAndVoteId(Long voteId);

    Long countAllByVoteId(Long voteId);

    Long countByAgreeYnFalseAndVoteId(Long voteId);

    List<VoteOpinion> findIdByBattleUserIdAndVoteId(Long battleUserId, Long voteId);

}
