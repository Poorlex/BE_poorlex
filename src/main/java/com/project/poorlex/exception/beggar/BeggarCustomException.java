package com.project.poorlex.exception.beggar;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class BeggarCustomException extends RuntimeException {

	private final int statusCode;
	private final BeggarErrorCode errorCode;
	private static final ObjectMapper mapper = new ObjectMapper();

	public BeggarCustomException(BeggarErrorCode errorCode) {
		super(errorCode.getDescription());
		this.statusCode = HttpStatus.BAD_REQUEST.value();
		this.errorCode = errorCode;
	}

	public BeggarCustomException(HttpStatus status, BeggarErrorCode errorCode) {
		super(errorCode.getDescription());
		this.statusCode = status.value();
		this.errorCode = errorCode;
	}
}
