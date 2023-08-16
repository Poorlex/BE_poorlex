package com.project.poorlex.exception.member;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class MemberCustomException extends RuntimeException {

	private final int statusCode;
	private final MemberErrorCode errorCode;
	private static final ObjectMapper mapper = new ObjectMapper();

	public MemberCustomException(MemberErrorCode errorCode) {
		super(errorCode.getDescription());
		this.statusCode = HttpStatus.BAD_REQUEST.value();
		this.errorCode = errorCode;
	}

	public MemberCustomException(HttpStatus status, MemberErrorCode errorCode) {
		super(errorCode.getDescription());
		this.statusCode = status.value();
		this.errorCode = errorCode;
	}
}
