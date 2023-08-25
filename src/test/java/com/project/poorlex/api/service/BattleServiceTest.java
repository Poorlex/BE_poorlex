package com.project.poorlex.api.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.poorlex.IntegrationTestSupport;
import com.project.poorlex.domain.battle.Battle;
import com.project.poorlex.domain.battle.BattleFilter;
import com.project.poorlex.domain.battle.BattleRepository;
import com.project.poorlex.domain.battle.BattleStatus;
import com.project.poorlex.domain.battleuser.BattleUser;
import com.project.poorlex.domain.battleuser.BattleUserRepository;
import com.project.poorlex.domain.battleuser.Role;
import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.dto.battle.BattleCreateRequest;
import com.project.poorlex.dto.battle.BattleSearchResponse;

import jakarta.transaction.Transactional;

@Transactional
class BattleServiceTest extends IntegrationTestSupport {

	@Autowired
	BattleRepository battleRepository;

	@Autowired
	BattleUserRepository battleUserRepository;

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
	void searchSuccess() {
		for (int i = 1; i <= 10; i++) {
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
	void filterSearchSuccess() {
		for (int i = 1; i <= 10; i++) {
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
}
