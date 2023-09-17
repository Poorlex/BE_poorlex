package com.project.poorlex.dto.goal;

import com.project.poorlex.domain.goal.Goal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GoalCreateResponse {

    Long id;

    String message;

    public static GoalCreateResponse of(Goal goal) {

        return GoalCreateResponse.builder()
                .id(goal.getId())
                .message("목표가 생성되었습니다.")
                .build();
    }
}
