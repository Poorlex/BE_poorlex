package com.project.poorlex.dto.battle;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BattleCreateRequest {

    @Size(min=2, max=12)
    private String name;

    private int budget;

    private int total;

    private String description;

    private LocalDateTime endDate;

}
