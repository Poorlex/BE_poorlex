package com.project.poorlex.domain.battle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum BattleFilter {

    ALL("전체", null, null),
    TO_80K("1~8만원", 1, 80000),
    TO_140K("9~14만원", 90000, 140000),
    TO_200K("15~20만원", 150000, 200000);

    private final String text;
    private final Integer minBudget;
    private final Integer maxBudget;

}
