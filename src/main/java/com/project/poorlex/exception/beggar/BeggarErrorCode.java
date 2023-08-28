package com.project.poorlex.exception.beggar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BeggarErrorCode {

	BEGGAR_NOT_FOUND("일치하는 거지정보가 없습니다.");

	private final String description;
}
