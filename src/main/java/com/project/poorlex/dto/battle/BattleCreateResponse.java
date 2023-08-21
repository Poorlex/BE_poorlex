package com.project.poorlex.dto.battle;

import com.project.poorlex.domain.battleuser.BattleUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BattleCreateResponse {

    private String oauthId;

    public static BattleCreateResponse from(BattleUser battleUser){
        // TODO : 방 생성후 돌려줘야 할 값 정하기
        return BattleCreateResponse.builder()
                .build();
    }
}
