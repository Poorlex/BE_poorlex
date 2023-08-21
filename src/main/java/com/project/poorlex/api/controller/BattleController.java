package com.project.poorlex.api.controller;

import com.project.poorlex.api.service.BattleService;
import com.project.poorlex.domain.battle.*;
import com.project.poorlex.dto.battle.BattleCreateRequest;
import com.project.poorlex.dto.battle.BattleCreateResponse;
import com.project.poorlex.dto.battle.BattleSearchResponse;
import com.project.poorlex.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/battle")
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @PostMapping
    public ApiResponse<BattleCreateResponse> createBattle(
        @RequestBody BattleCreateRequest request) {
        return ApiResponse.ok(battleService.createBattleAndBattleUser(request));
    }

    @GetMapping
    public ApiResponse<BattleSearchResponse> searchBattle(
        @RequestParam BattleFilter filter) {
        return ApiResponse.ok(battleService.searchBattle(filter));
    }
}
