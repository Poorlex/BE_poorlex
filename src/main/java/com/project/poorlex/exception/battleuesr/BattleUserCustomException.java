package com.project.poorlex.exception.battleuesr;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BattleUserCustomException extends RuntimeException {

    private final int statusCode;
    private final BattleUserErrorCode errorCode;
    private static final ObjectMapper mapper = new ObjectMapper();

    public BattleUserCustomException(BattleUserErrorCode errorCode){
        super(errorCode.getDescription());
        this.statusCode = HttpStatus.BAD_REQUEST.value();
        this.errorCode = errorCode;
    }

    public BattleUserCustomException(HttpStatus status, BattleUserErrorCode errorCode){
        super(errorCode.getDescription());
        this.statusCode = status.value();
        this.errorCode = errorCode;
    }
}
