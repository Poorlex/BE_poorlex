package com.project.poorlex.api.controller;

import com.project.poorlex.api.service.BattleService;
import com.project.poorlex.domain.battle.*;
import com.project.poorlex.dto.battle.*;
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

    @GetMapping("/join")
    public ApiResponse<BattleJoinResponse> joinBattle(
            @RequestParam Long battleId) {
        return ApiResponse.ok(battleService.joinBattle(battleId));
    }

    @GetMapping("/detail")
    public ApiResponse<BattleDetailResponse> searchDetailBattle(
            @RequestParam Long battleId) {
        return ApiResponse.ok(battleService.searchBattleDetail(battleId));
    }
}
