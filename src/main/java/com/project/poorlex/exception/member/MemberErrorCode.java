package com.project.poorlex.exception.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

	MEMBER_NOT_FOUND("일치하는 회원이 없습니다."),
	UNAUTHORIZED_ACCESS_TOKEN("인가되지 않은 ACCESS 토큰입니다."),
	UNAUTHORIZED_REFRESH_TOKEN("인가되지 않은 REFRESH 토큰입니다."),
	INVALID_TOKEN("유효하지 않는 액세스 토큰입니다.");

	private final String description;
}
