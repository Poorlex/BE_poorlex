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
public class GoalUpdateResponse {

    Long id;
    String message;

    public static GoalUpdateResponse of(Goal goal) {

        return GoalUpdateResponse.builder()
                .id(goal.getId())
                .message("목표가 수정되었습니다.")
                .build();
    }
}
