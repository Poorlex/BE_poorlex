package com.project.poorlex.exception.battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BattleCustomException extends RuntimeException{

    private final int statusCode;
    private final BattleErrorCode errorCode;
    private static final ObjectMapper mapper = new ObjectMapper();

    public BattleCustomException(BattleErrorCode errorCode){
        super(errorCode.getDescription());
        this.statusCode = HttpStatus.BAD_REQUEST.value();
        this.errorCode = errorCode;
    }

    public BattleCustomException(HttpStatus status, BattleErrorCode errorCode){
        super(errorCode.getDescription());
        this.statusCode = status.value();
        this.errorCode = errorCode;
    }
}
