package com.project.poorlex.api.service;

import com.project.poorlex.domain.goal.Goal;
import com.project.poorlex.domain.goal.GoalRepository;
import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.dto.goal.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    private final MemberRepository memberRepository;

    private final AuthService authService;

    public GoalCreateResponse createGoal(GoalCreateRequest request) {

//        Optional<Member> optionalMember = memberRepository.findByOauthId(request.getOauthId());
//        if (!optionalMember.isPresent()) {
//            throw new RuntimeException("id not found.");
//        }
//
//        Member member = optionalMember.get();
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

//        Member member = memberRepository.findByOauthId(oauthId).orElseThrow(() -> new RuntimeException("Id not found"));
        Member  member = authService.findMemberFromToken();
        Long goalId = goalRepository.findById(id).orElseThrow(() -> new RuntimeException("Goal not found"))
                .getId();

        Goal updatedGoal = Goal.builder()
                .id(goalId)
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

        Long goalId = goalRepository.findById(id)
            .orElseThrow(() -> new GoalCustomException(GoalErrorCode.GOAL_NOT_FOUND))
                .getId();
        Goal completedGoal = Goal.builder()
                .id(goalId)
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

        goalRepository.deleteById(id);

        return true;
    }
}