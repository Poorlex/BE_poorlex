package com.project.poorlex.dto.battle;

import com.project.poorlex.domain.member.Member;
import com.project.poorlex.dto.member.MemberLoginResponse;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
public class BattleDetailResponse {

    private Long id;

    private String name;

    private String description;

    private int budget;

    private int total;

    private List<BattleMemberResponse> battleMembers;

    @Builder
    private BattleDetailResponse(Long id, String name, String description, int budget, int total){
        this.id = id;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.total = total;
    }

}
