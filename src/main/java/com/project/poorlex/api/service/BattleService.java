package com.project.poorlex.api.service;

import com.project.poorlex.domain.battle.*;
import com.project.poorlex.domain.battleuser.BattleUser;
import com.project.poorlex.domain.battleuser.BattleUserRepository;
import com.project.poorlex.domain.battleuser.Role;
import com.project.poorlex.domain.member.Member;
import com.project.poorlex.dto.battle.BattleCreateRequest;
import com.project.poorlex.dto.battle.BattleCreateResponse;
import com.project.poorlex.dto.battle.BattleSearchResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;

    private final BattleUserRepository battleUserRepository;

    private final AuthService authService;

    // Battle 생성 및 생성 요청을 한 유저의 정보를 담은 BattleUser 생성
    @Transactional
    public BattleCreateResponse createBattleAndBattleUser(BattleCreateRequest request) {

        // 1-1. 배틀 방 생성
        Battle battle = Battle.builder()
                .name(request.getName())
                .total(request.getTotal())
                .description(request.getDescription())
                .endDate(request.getEndDate())
                .budget(request.getBudget())
                .battleStatus(BattleStatus.WAITING)
                .build();

        // 1-2. 배틀 방 생성 정보 저장
        battleRepository.save(battle);

        // 2. 배틀 방 생성 요청하는 유저의 정보 가져오기
        Member member = authService.findMemberFromToken();

        // 3-1. 배틀유저 생성
        BattleUser battleUser = BattleUser.builder()
                .member(member)
                .battle(battle)
                .role(Role.ADMIN)
                .build();

        // 3-2. 배틀유저 정보 저장
        battleUserRepository.save(battleUser);

        return BattleCreateResponse.from(battleUser);
    }

    // 배틀 방 조회
    public BattleSearchResponse searchBattle(BattleFilter filter) {

        List<Battle> battleList;

        if(filter == BattleFilter.ALL){
            // 모든 방 조회
            // TODO : Pageable
            battleList = battleRepository.findAll();
        }
        else{
            // 예산 범위에 따른 조회
            battleList = battleRepository.findByBudgetBetween(filter.getMinBudget(), filter.getMaxBudget());
        }

        return new BattleSearchResponse(battleList);
    }
}
