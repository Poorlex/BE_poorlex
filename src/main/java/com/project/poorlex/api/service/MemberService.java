package com.project.poorlex.api.service;

import static com.project.poorlex.exception.member.MemberErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.dto.member.MemberCreateRequest;
import com.project.poorlex.dto.member.MemberLoginResponse;
import com.project.poorlex.dto.member.MemberUpdateRequest;
import com.project.poorlex.dto.member.MemberUpdateResponse;
import com.project.poorlex.exception.member.MemberCustomException;
import com.project.poorlex.jwt.JwtTokenProvider;
import com.project.poorlex.jwt.Token;
import com.project.poorlex.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisUtil redisUtil;

	@Transactional
	public MemberLoginResponse createOrLogin(MemberCreateRequest request) {
		Member member;
		if (memberRepository.existsByOauthId(request.getOauthId())) {
			member = memberRepository.findByOauthId(request.getOauthId())
				.orElseThrow(() -> new MemberCustomException(MEMBER_NOT_FOUND));
		} else {
			member = memberRepository.save(request.toEntity());
		}
		Token token = jwtTokenProvider.createAccessToken(member.getId().toString());
		redisUtil.setData(String.valueOf(member.getId()), token.getRefreshToken());
		return MemberLoginResponse.of(token);
	}

	@Transactional
	public MemberUpdateResponse updateMember(MemberUpdateRequest request) {
		Member member = authService.findMemberFromToken();
		member.update(request);
		return MemberUpdateResponse.of(member);
	}

	public Member findMemberById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new MemberCustomException(MEMBER_NOT_FOUND));
	}
}
