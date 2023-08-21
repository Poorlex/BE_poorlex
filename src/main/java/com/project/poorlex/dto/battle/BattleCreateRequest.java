package com.project.poorlex.dto.battle;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BattleCreateRequest {

    private String name;

    private int budget;

    private int total;

    private String description;

    private LocalDateTime endDate;

}
