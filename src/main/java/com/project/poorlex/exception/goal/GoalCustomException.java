package com.project.poorlex.exception.goal;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class GoalCustomException extends RuntimeException {

    private final GoalErrorCode goalErrorCode;
    public GoalCustomException(GoalErrorCode goalErrorCode) {
        super(goalErrorCode.getDescription());
        this.goalErrorCode = goalErrorCode;
    }
}
