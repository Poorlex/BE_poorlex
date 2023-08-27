package com.project.poorlex.api.service;

import com.project.poorlex.domain.battle.*;
import com.project.poorlex.domain.battleuser.BattleUser;
import com.project.poorlex.domain.battleuser.BattleUserRepository;
import com.project.poorlex.domain.battleuser.Role;
import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.dto.battle.*;
import com.project.poorlex.exception.battle.BattleCustomException;
import com.project.poorlex.exception.battle.BattleErrorCode;
import com.project.poorlex.exception.battleuesr.BattleUserCustomException;
import com.project.poorlex.exception.battleuesr.BattleUserErrorCode;
import com.project.poorlex.exception.member.MemberCustomException;
import com.project.poorlex.exception.member.MemberErrorCode;
import io.jsonwebtoken.lang.Collections;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;

    private final BattleUserRepository battleUserRepository;

    private final MemberRepository memberRepository;

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

        return BattleSearchResponse.from(battleList);
    }

    // 배틀 방 상세 조회
    public BattleDetailResponse searchBattleDetail(Long battleRoomId){

        Optional<Battle> battleOptional = battleRepository.findById(battleRoomId);

        List<BattleUser> battleUsers = battleUserRepository.findByBattleId(battleRoomId);

        if(battleOptional.isEmpty()){
            throw new BattleCustomException(BattleErrorCode.BATTLE_NOT_FOUND);
        }

        Battle battle = battleOptional.get();

        BattleDetailResponse response = new BattleDetailResponse();
        response.setId(battle.getId());
        response.setName(battle.getName());
        response.setBudget(battle.getBudget());
        response.setTotal(battle.getTotal());

        List<Long> memberIds = battleUsers.stream()
                .map(battleUser -> battleUser.getMember().getId())
                .collect(Collectors.toList());

        List<Member> members = memberRepository.findAllById(memberIds);

        List<BattleMemberResponse> battleMembers = members.stream()
                .map(member -> {
                    BattleMemberResponse battleMemberResponse = new BattleMemberResponse();
                    battleMemberResponse.setId(member.getId());
                    battleMemberResponse.setName(member.getName());
                    return battleMemberResponse;
                }).collect(Collectors.toList());

        // 진행 전인 방인 경우
        if(battle.getBattleStatus() == BattleStatus.WAITING){
            response.setDescription(battle.getDescription());
            response.setBattleMembers(battleMembers);
        }
        else if(battle.getBattleStatus() == BattleStatus.PROGRESS){
            // TODO : 지출 작업 후 진행
        }

        return response;
    }

    // 배틀 참여하기
    @Transactional
    public BattleJoinResponse joinBattle(Long battleRoomId){

        Member member = authService.findMemberFromToken();

        Optional<Battle> battleOptional = battleRepository.findById(battleRoomId);

        if(battleOptional.isPresent()){

            Battle battle = battleOptional.get();

            if(battle.getBattleStatus() == BattleStatus.PROGRESS){
                throw new BattleCustomException(BattleErrorCode.BATTLE_IS_ALREADY_IN_PROGRESS);
            }
            else if(battle.getBattleStatus() == BattleStatus.FINISHED){
                throw new BattleCustomException(BattleErrorCode.BATTLE_IS_ALREADY_FINISH);
            }

            BattleUser battleUser = BattleUser.builder()
                    .member(member)
                    .battle(battleOptional.get())
                    .role(Role.USER)
                    .build();

            battleUserRepository.save(battleUser);

            BattleJoinResponse response = new BattleJoinResponse();
            response.setBattleId(battle.getId());
            return response;
        }
        else{
            throw new BattleCustomException(BattleErrorCode.BATTLE_NOT_FOUND);
        }
    }
}
