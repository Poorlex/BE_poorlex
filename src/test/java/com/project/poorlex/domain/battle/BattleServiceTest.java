package com.project.poorlex.domain.battle;

import com.project.poorlex.api.service.AuthService;
import com.project.poorlex.api.service.BattleService;
import com.project.poorlex.domain.battleuser.BattleUser;
import com.project.poorlex.domain.battleuser.BattleUserRepository;
import com.project.poorlex.domain.battleuser.Role;
import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.dto.battle.BattleCreateRequest;
import com.project.poorlex.dto.battle.BattleDetailResponse;
import com.project.poorlex.dto.battle.BattleSearchResponse;
import com.project.poorlex.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class BattleServiceTest {

    @Autowired
    BattleRepository battleRepository;

    @Autowired
    BattleUserRepository battleUserRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthService authService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BattleService battleService;

    @Test
    @DisplayName("방을 성공적으로 생성 후 방을 생성한 유저의 정보를 배틀유저에 넣음")
    void createBattleAndBattleUserSuccess() {
        // 방 생성 정보를 받음
        BattleCreateRequest battleCreateRequest = BattleCreateRequest.builder()
                .name("방 생성하기")
                .total(10)
                .description("방 생성 테스트입니다")
                .endDate(LocalDateTime.now())
                .budget(200000)
                .build();

        // 방 생성 정보를 통해 새로운 배틀을 만듦
        Battle newBattle = Battle.builder()
                .name(battleCreateRequest.getName())
                .total(battleCreateRequest.getTotal())
                .description(battleCreateRequest.getDescription())
                .endDate(battleCreateRequest.getEndDate())
                .budget(battleCreateRequest.getBudget())
                .battleStatus(BattleStatus.WAITING)
                .build();

        Battle savedBattle = battleRepository.save(newBattle);

        // 해당 방을 만든 유저의 정보를 만듦(테스트용)
        Member member = Member.builder()
                .name("테스트 유저")
                .build();

        Member savedMember = memberRepository.save(member);

        BDDMockito.given(authService.findMemberFromToken()).willReturn(member);


        BattleUser battleUser = BattleUser.builder()
                .member(savedMember)
                .battle(savedBattle)
                .role(Role.ADMIN)
                .build();

        BattleUser savedBattleUser = battleUserRepository.save(battleUser);

        assertThat(newBattle.getName()).isEqualTo(savedBattle.getName());
        assertThat(battleUser.getMember().getId()).isEqualTo(savedBattleUser.getMember().getId());
    }

    @Test
    @DisplayName("방 조회하기 - 전체 조회")
    void searchSuccess(){
        for(int i = 1 ; i <= 10 ; i++){
            // 방 생성 정보를 통해 새로운 배틀을 만듦
            Battle newBattle = Battle.builder()
                    .name("방 " + i)
                    .total(20)
                    .description("설명 " + i)
                    .endDate(LocalDateTime.now())
                    .budget(20000 * i)
                    .battleStatus(BattleStatus.WAITING)
                    .build();

            battleRepository.save(newBattle);
        }

        BattleSearchResponse battleSearchResponse = battleService.searchBattle(BattleFilter.ALL);

        assertEquals(10, battleSearchResponse.getBattleList().size());
        assertEquals("방 1", battleSearchResponse.getBattleList().get(0).getName());
    }



    @Test
    @DisplayName("방 조회하기 - 조건")
    void filterSearchSuccess(){
        for(int i = 1 ; i <= 10 ; i++){
            // 방 생성 정보를 통해 새로운 배틀을 만듦
            Battle newBattle = Battle.builder()
                    .name("방 " + i)
                    .total(20)
                    .description("설명 " + i)
                    .endDate(LocalDateTime.now())
                    .budget(20000 * i)
                    .battleStatus(BattleStatus.WAITING)
                    .build();

            battleRepository.save(newBattle);
        }

        BattleSearchResponse battleSearchResponse = battleService.searchBattle(BattleFilter.TO_80K);
        assertEquals(4, battleSearchResponse.getBattleList().size());

        battleSearchResponse = battleService.searchBattle(BattleFilter.TO_140K);
        assertEquals(3, battleSearchResponse.getBattleList().size());

        battleSearchResponse = battleService.searchBattle(BattleFilter.TO_200K);
        assertEquals(3, battleSearchResponse.getBattleList().size());
    }

    @Test
    @DisplayName("방 상세정보 보기 - 진행 전 ")
    void searchWaitingRoomDetail(){
        // 방 생성 정보를 받음
        BattleCreateRequest battleCreateRequest = BattleCreateRequest.builder()
                .name("방 생성하기")
                .total(10)
                .description("방 생성 테스트입니다")
                .endDate(LocalDateTime.now())
                .budget(200000)
                .build();

        // 방 생성 정보를 통해 새로운 배틀을 만듦
        Battle newBattle = Battle.builder()
                .name(battleCreateRequest.getName())
                .total(battleCreateRequest.getTotal())
                .description(battleCreateRequest.getDescription())
                .endDate(battleCreateRequest.getEndDate())
                .budget(battleCreateRequest.getBudget())
                .battleStatus(BattleStatus.WAITING)
                .build();

        Battle savedBattle = battleRepository.save(newBattle);

        // 해당 방을 만든 유저의 정보를 만듦(테스트용)
        Member member = Member.builder()
                .name("테스트 유저")
                .build();

        Member member2 = Member.builder()
                .name("테스트 유저2")
                .build();

        Member savedMember = memberRepository.save(member);

        Member savedMember2 = memberRepository.save(member2);

        BDDMockito.given(authService.findMemberFromToken()).willReturn(member);


        BattleUser battleUser = BattleUser.builder()
                .member(savedMember)
                .battle(savedBattle)
                .role(Role.ADMIN)
                .build();

        battleUserRepository.save(battleUser);

        BattleUser battleUser2 = BattleUser.builder()
                .member(savedMember2)
                .battle(savedBattle)
                .role(Role.USER)
                .build();

        battleUserRepository.save(battleUser2);

        BattleDetailResponse battleDetailResponse = battleService.searchBattleDetail(1L);

        assertEquals(2, battleDetailResponse.getBattleMembers().size());
        assertEquals("방 생성 테스트입니다", battleDetailResponse.getDescription());
    }
}