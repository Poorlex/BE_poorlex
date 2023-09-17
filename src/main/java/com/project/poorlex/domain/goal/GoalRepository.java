package com.project.poorlex.domain.goal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByMemberId_OauthIdAndIsCompletedFalse(String oauthId);

    List<Goal> findAllByMemberId_OauthIdAndIsCompletedTrue(String oauthId);
}
