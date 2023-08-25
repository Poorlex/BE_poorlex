package com.project.poorlex.api.service;

import static com.project.poorlex.domain.member.MemberRole.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.project.poorlex.IntegrationTestSupport;
import com.project.poorlex.domain.beggar.Beggar;
import com.project.poorlex.domain.beggar.BeggarRepository;
import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.dto.member.MemberCreateRequest;
import com.project.poorlex.dto.member.MemberLoginResponse;
import com.project.poorlex.dto.member.MemberResponse;
import com.project.poorlex.dto.member.MemberUpdateRequest;
import com.project.poorlex.dto.member.MemberUpdateResponse;
import com.project.poorlex.jwt.Token;

@Transactional
class MemberServiceTest extends IntegrationTestSupport {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BeggarRepository beggarRepository;

	@DisplayName("소셜로그인에 필요한 정보들로 회원가입을 요청하면 토큰 정보를 반환한다.")
	@Test
	void createMember() {
		// given
		MemberCreateRequest request = MemberCreateRequest.builder()
			.oauthId("123456")
			.email("test@test.com")
			.name("홍길동")
			.build();
		Token resultToken = new Token("accessToken", "refreshToken");
		given(jwtTokenProvider.createAccessToken(any(String.class)))
			.willReturn(resultToken);

		// when
		MemberLoginResponse response = memberService.createOrLogin(request);
		List<Member> members = memberRepository.findAll();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getAccessToken()).isEqualTo(resultToken.getAccessToken());
		assertThat(members).hasSize(1)
			.extracting("oauthId", "email", "name")
			.containsExactlyInAnyOrder(tuple("123456", "test@test.com", "홍길동"));
	}

	@DisplayName("DB에 고유번호가 있다면 저장 없이 토큰을 반환한다.")
	@Test
	void login() {
		// given
		String target = "123456";
		String email = "test@test.com";
		String name = "홍길동";
		MemberCreateRequest request = MemberCreateRequest.builder()
			.oauthId(target)
			.email(email)
			.name(name)
			.build();
		Member member = createMember(target, email, name);
		memberRepository.save(member);
		Token resultToken = new Token("accessToken", "refreshToken");
		given(jwtTokenProvider.createAccessToken(any(String.class)))
			.willReturn(resultToken);
		// when
		MemberLoginResponse response = memberService.createOrLogin(request);
		List<Member> members = memberRepository.findAll();
		for (Member member1 : members) {
			System.out.println("member1 = " + member1.getOauthId());
		}

		// then
		assertThat(response).isNotNull();
		assertThat(response.getAccessToken()).isEqualTo(resultToken.getAccessToken());
		assertThat(members).hasSize(1)
			.extracting("oauthId", "email", "name")
			.containsExactlyInAnyOrder(tuple(target, email, name));
	}

	@DisplayName("닉네임과 상태메세지를 받아 회원정보를 수정한다.")
	@Test
	void updateMember() {
		// given
		MemberUpdateRequest request = MemberUpdateRequest.builder()
			.name("박효신")
			.description("소개글")
			.build();
		Member member1 = createMember("1", "test@test.com", "김범수");
		Member member2 = createMember("2", "test2@test.com", "나얼");
		memberRepository.saveAll(List.of(member1, member2));

		Member findMember = memberService.findMemberById(1L);
		given(authService.findMemberFromToken()).willReturn(findMember);

		// when
		MemberUpdateResponse response = memberService.updateMember(request);
		List<Member> result = memberRepository.findAll();

		// then
		assertThat(response.getDescription()).isEqualTo(request.getDescription());
		assertThat(result).hasSize(2)
			.extracting("name", "description")
			.containsExactlyInAnyOrder(
				tuple("박효신", "소개글"),
				tuple("나얼", null)
			);
	}

	@DisplayName("회원 정보를 조회한다.")
	@Test
	void getMemberInfo() {
		// given
		Member member1 = createMember("1", "test@test.com", "김범수", 10);
		memberRepository.save(member1);
		Beggar beggar1 = createBeggar(1, 0, 19);
		Beggar beggar2 = createBeggar(2, 20, 49);
		beggarRepository.saveAll(List.of(beggar1, beggar2));

		Member findMember = memberService.findMemberById(1L);
		given(authService.findMemberFromToken()).willReturn(findMember);
		int sum = findMember.getGold() + findMember.getSilver() + findMember.getBronze();
		int targetPoint = beggar2.getMinPoint() - findMember.getPoint();

		// when
		MemberResponse response = memberService.getMemberInfo();

		// then
		assertThat(response.getName()).isEqualTo(findMember.getName());
		assertThat(response.getSuccess()).isEqualTo(sum);
		assertThat(response.getLevel()).isEqualTo(beggar1.getLevel());
		assertThat(response.getNeedPoint()).isEqualTo(targetPoint);
	}

	private Beggar createBeggar(int level, int minPoint, int maxPoint) {
		return Beggar.builder()
			.level(level)
			.minPoint(minPoint)
			.maxPoint(maxPoint)
			.photoName("-")
			.photoUrl("")
			.build();
	}

	private Member createMember(String oauthId, String email, String name) {
		return Member.builder()
			.oauthId(oauthId)
			.email(email)
			.name(name)
			.memberRole(USER)
			.gold(1)
			.silver(2)
			.bronze(3)
			.build();
	}

	private Member createMember(String oauthId, String email, String name, int point) {
		return Member.builder()
			.oauthId(oauthId)
			.email(email)
			.name(name)
			.memberRole(USER)
			.point(point)
			.gold(1)
			.silver(2)
			.bronze(3)
			.build();
	}
}
