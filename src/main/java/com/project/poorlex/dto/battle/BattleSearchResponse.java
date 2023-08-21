package com.project.poorlex.dto.battle;

import com.project.poorlex.domain.battle.Battle;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class BattleSearchResponse {

    private final List<Battle> battleList;

    public static BattleSearchResponse from(List<Battle> battleList) {
        return BattleSearchResponse.builder()
                .battleList(battleList)
                .build();
    }
}
