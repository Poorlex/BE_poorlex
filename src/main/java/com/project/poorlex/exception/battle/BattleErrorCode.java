package com.project.poorlex.exception.battle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BattleErrorCode {

    BATTLE_NOT_FOUND("일치하는 배틀 방이 없습니다."),
    BATTLE_IS_ALREADY_IN_PROGRESS("이미 진행중인 방입니다."),
    BATTLE_IS_ALREADY_FINISH("이미 종료된 방입니다.")
    ;

    private final String description;
}
