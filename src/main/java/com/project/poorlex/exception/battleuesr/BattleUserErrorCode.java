package com.project.poorlex.exception.battleuesr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BattleUserErrorCode {

    MEMBER_NOT_FOUND("해당 방에 참여중인 회원을 찾을 수 없습니다.");

    private final String description;
}
