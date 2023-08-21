package com.project.poorlex.dto.battle;

import com.project.poorlex.domain.battle.Battle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BattleSearchResponse {

    private final List<Battle> battleList;

}
