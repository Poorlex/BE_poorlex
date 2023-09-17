package com.project.poorlex.api.service;

import com.project.poorlex.domain.goal.Goal;
import com.project.poorlex.domain.goal.GoalRepository;
import com.project.poorlex.domain.member.Member;
import com.project.poorlex.dto.goal.*;
import com.project.poorlex.exception.goal.GoalCustomException;
import com.project.poorlex.exception.goal.GoalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    private final AuthService authService;

    public GoalCreateResponse createGoal(GoalCreateRequest request) {

        Member member = authService.findMemberFromToken();

        Goal goal = Goal.builder()
                .member(member)
                .name(request.getName())
                .amount(request.getAmount())
                .currentAmount(request.getCurrentAmount())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isCompleted(request.isCompleted())
                .build();

        goalRepository.save(goal);

        return GoalCreateResponse.of(goal);
    }

    public List<GoalListResponse> getAllGoals(String oauthId) {


        List<Goal> goalList = goalRepository.findAllByMemberId_OauthIdAndIsCompletedFalse(oauthId);

        return GoalListResponse.of(goalList);
    }

    public List<GoalListResponse> getAllCompletedGoals(String oauthId) {

        List<Goal> goalList = goalRepository.findAllByMemberId_OauthIdAndIsCompletedTrue(oauthId);

        return GoalListResponse.of(goalList);
    }

    public GoalUpdateResponse updateGoal(GoalUpdateRequest request, Long id) {

        Member member = authService.findMemberFromToken();
        Goal existingGoalId = goalRepository.findById(id)
                .orElseThrow(() -> new GoalCustomException(GoalErrorCode.GOAL_NOT_FOUND));

        Goal updatedGoal = Goal.builder()
                .id(existingGoalId.getId())
                .member(member)
                .amount(request.getAmount())
                .currentAmount(request.getCurrentAmount())
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isCompleted(false)
                .build();

        goalRepository.save(updatedGoal);

        return GoalUpdateResponse.of(updatedGoal);
    }

    public GoalUpdateResponse goalCompleted(Long id, GoalUpdateRequest request) {

        Goal existingGoalId = goalRepository.findById(id)
                .orElseThrow(() -> new GoalCustomException(GoalErrorCode.GOAL_NOT_FOUND));
        Goal completedGoal = Goal.builder()
                .id(existingGoalId.getId())
                .amount(request.getAmount())
                .currentAmount(request.getCurrentAmount())
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isCompleted(true)
                .build();

        goalRepository.save(completedGoal);

        return GoalUpdateResponse.of(completedGoal);
    }

    public boolean deleteGoal(Long id) {

        Goal existingGoalId = goalRepository.findById(id)
                .orElseThrow(() -> new GoalCustomException(GoalErrorCode.GOAL_NOT_FOUND));

        goalRepository.deleteById(existingGoalId.getId());

        return true;
    }
}