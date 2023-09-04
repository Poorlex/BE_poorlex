package com.project.poorlex.exception.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PaymentCustomException extends RuntimeException{

    private final int statusCode;
    private final PaymentErrorCode errorCode;
    private static final ObjectMapper mapper = new ObjectMapper();

    public PaymentCustomException(PaymentErrorCode errorCode){
        super(errorCode.getDescription());
        this.statusCode = HttpStatus.BAD_REQUEST.value();
        this.errorCode = errorCode;
    }

    public PaymentCustomException(HttpStatus status, PaymentErrorCode errorCode){
        super(errorCode.getDescription());
        this.statusCode = status.value();
        this.errorCode = errorCode;
    }
}
