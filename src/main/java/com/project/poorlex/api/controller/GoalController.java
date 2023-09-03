package com.project.poorlex.api.controller;

import com.project.poorlex.api.service.GoalService;
import com.project.poorlex.dto.goal.*;
import com.project.poorlex.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/create")
    public ApiResponse<GoalCreateResponse> createGoal(
            @RequestBody GoalCreateRequest request) {

        return ApiResponse.ok(goalService.createGoal(request));
    }

    @GetMapping("/list")
    public ApiResponse<List<GoalListResponse>> getAllGoals(@RequestParam String oauthId) {

        return ApiResponse.ok(goalService.getAllGoals(oauthId));
    }

    @GetMapping("/completedList")
    public ApiResponse<List<GoalListResponse>> getAllCompletedGoals(@RequestParam String oauthId) {

        return ApiResponse.ok(goalService.getAllCompletedGoals(oauthId));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<GoalUpdateResponse> updateGoal(
             @PathVariable Long id,
            @RequestBody GoalUpdateRequest request) {

        return ApiResponse.ok(goalService.updateGoal(request, id));
    }

    @PutMapping("/complete/{id}")
    public ApiResponse<GoalUpdateResponse> completeGoal(@PathVariable Long id, @RequestBody GoalUpdateRequest request) {

        return ApiResponse.ok(goalService.goalCompleted(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteGoal(@PathVariable Long id) {

        return ApiResponse.ok(goalService.deleteGoal(id));
    }
}
