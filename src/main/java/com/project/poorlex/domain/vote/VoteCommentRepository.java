package com.project.poorlex.domain.vote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteCommentRepository extends JpaRepository<VoteComment, Long> {

    List<VoteComment> findAllByVoteOrderByIdDesc(Vote vote);
}
