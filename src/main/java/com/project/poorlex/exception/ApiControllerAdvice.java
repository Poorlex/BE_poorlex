package com.project.poorlex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.poorlex.exception.member.MemberCustomException;

@RestControllerAdvice
public class ApiControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiResponse<Object> bindException(BindException e) {
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
		);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MemberCustomException.class)
	public ApiResponse<Object> memberCustomException(MemberCustomException e) {
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getMessage()
		);
	}
}
