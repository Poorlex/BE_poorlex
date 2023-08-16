package com.project.poorlex.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

	USER("사용자"),
	ADMIN("관리자");

	private final String text;
}
