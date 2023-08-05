package com.project.poorlex.domain.battleuser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	ADMIN("관리자"),
	USER("사용자");

	private final String text;
}
