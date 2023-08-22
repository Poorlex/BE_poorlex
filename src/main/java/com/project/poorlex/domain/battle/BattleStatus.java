package com.project.poorlex.domain.battle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BattleStatus {

    WAITING("대기중"),
    PROGRESS("진행중"),
    FINISHED("종료");

    private final String text;
}
