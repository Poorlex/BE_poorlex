package com.project.poorlex.dto.goal;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.poorlex.domain.goal.Goal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GoalListResponse {

    private Long id;

    String name;

    String message;

    private int amount;

    private int currentAmount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private boolean isCompleted;

    public static List<GoalListResponse> of(List<Goal> goals) {

        List<GoalListResponse> goalListResponseList = new ArrayList<>();

        if (goals != null) {
            for (Goal goal : goals) {
                goalListResponseList.add(GoalListResponse.builder()
                        .id(goal.getId())
                        .name(goal.getName())
                        .amount(goal.getAmount())
                        .currentAmount(goal.getCurrentAmount())
                        .startDate(goal.getStartDate())
                        .endDate(goal.getEndDate())
                        .isCompleted(goal.isCompleted())
                        .message("리스트가 조회되었습니다.")
                        .build());
            }
        }
        return goalListResponseList;
    }
}
