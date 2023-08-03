package com.project.poorlex.domain.feedback;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Reaction {

	DEFAULT("기본값");

	private final String text;
}
