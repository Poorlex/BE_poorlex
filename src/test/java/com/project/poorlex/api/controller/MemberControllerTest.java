package com.project.poorlex.api.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.poorlex.api.service.AuthService;
import com.project.poorlex.api.service.MemberService;
import com.project.poorlex.config.TestSecurityConfig;
import com.project.poorlex.dto.member.MemberCreateRequest;
import com.project.poorlex.dto.member.MemberResponse;
import com.project.poorlex.dto.member.MemberUpdateRequest;
import com.project.poorlex.jwt.JwtTokenProvider;

@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = {MemberController.class})
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private MemberService memberService;

	@DisplayName("소셜로그인을 통해 로그인한다.")
	@Test
	void socialLogin() throws Exception {
		// given
		MemberCreateRequest request = MemberCreateRequest.builder()
			.oauthId("123456")
			.email("test@test.com")
			.name("홍길동")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/members/login")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("200"))
			.andExpect(jsonPath("$.status").value("OK"))
			.andExpect(jsonPath("$.message").value("OK"));
	}

	@DisplayName("신규 회원 등록시 고유번호는 필수값이다.")
	@Test
	void socialLoginWithoutOAuthId() throws Exception {
		// given
		MemberCreateRequest request = MemberCreateRequest.builder()
			.email("test@test.com")
			.name("홍길동")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/members/login")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("고유번호는 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("신규 회원 등록시 이메일은 필수값이다.")
	@Test
	void socialLoginWithoutEmail() throws Exception {
		// given
		MemberCreateRequest request = MemberCreateRequest.builder()
			.oauthId("123456")
			.name("홍길동")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/members/login")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("이메일은 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("신규 회원 등록시 닉네임은 필수값이다.")
	@Test
	void socialLoginWithoutName() throws Exception {
		// given
		MemberCreateRequest request = MemberCreateRequest.builder()
			.oauthId("123456")
			.email("test@test.com")
			.build();

		// when // then
		mockMvc.perform(
				post("/api/v1/members/login")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("닉네임은 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("회원정보를 수정한다.")
	@Test
	void updateMember() throws Exception {
		// given
		MemberUpdateRequest request = MemberUpdateRequest.builder()
			.name("어우동")
			.description("소개글입니다.")
			.build();

		// when // then
		mockMvc.perform(
				put("/api/v1/members")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("200"))
			.andExpect(jsonPath("$.status").value("OK"))
			.andExpect(jsonPath("$.message").value("OK"));
	}

	@DisplayName("회원정보 수정시 닉네임은 2자 이상 30자 이하여야한다.")
	@Test
	void updateMemberWithTooLongName() throws Exception {
		// given
		MemberUpdateRequest request = MemberUpdateRequest.builder()
			.name("안녕하세요반갑습니다다시만나요하하")
			.description("소개글입니다.")
			.build();

		// when // then
		mockMvc.perform(
				put("/api/v1/members")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("닉네임은 2자 이상 15자 이하여야 합니다."))
			.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("회원정보 수정시 상태 메세지는 100자 이하여야한다.")
	@Test
	void updateMemberWithTooLongDescription() throws Exception {
		// given
		String target = "안녕하세요반갑습니다";
		for (int i = 0; i < 5; i++) {
			target += target;
		}
		MemberUpdateRequest request = MemberUpdateRequest.builder()
			.name("홍길동")
			.description(target)
			.build();

		// when // then
		mockMvc.perform(
				put("/api/v1/members")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상태 메세지는 100자 내로 입력해야 합니다."))
			.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("메인페이지를 조회힌다.")
	@Test
	void myPage() throws Exception {
		// given
		MemberResponse response = MemberResponse.builder().build();
		given(memberService.getMemberInfo()).willReturn(response);

		// when // then
		mockMvc.perform(
				get("/api/v1/members")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("200"))
			.andExpect(jsonPath("$.status").value("OK"))
			.andExpect(jsonPath("$.message").value("OK"))
			.andExpect(jsonPath("$.data").exists());
	}
}
