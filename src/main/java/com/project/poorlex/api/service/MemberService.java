package com.project.poorlex.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.dto.member.MemberCreateRequest;
import com.project.poorlex.dto.member.MemberLoginResponse;
import com.project.poorlex.exception.member.MemberCustomException;
import com.project.poorlex.exception.member.MemberErrorCode;
import com.project.poorlex.jwt.JwtTokenProvider;
import com.project.poorlex.jwt.Token;
import com.project.poorlex.util.AuthUtil;
import com.project.poorlex.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisUtil redisUtil;

	@Transactional
	public MemberLoginResponse createOrLogin(MemberCreateRequest request) {
		Member member;
		if (memberRepository.existsByOauthId(request.getOauthId())) {
			member = memberRepository.findByOauthId(request.getOauthId())
				.orElseThrow(() -> new MemberCustomException(MemberErrorCode.MEMBER_NOT_FOUND));
		} else {
			member = memberRepository.save(Member.create(request));
		}
		Token token = jwtTokenProvider.createAccessToken(member.getId().toString());
		redisUtil.setData(String.valueOf(member.getId()), token.getRefreshToken());
		return MemberLoginResponse.of(token);
	}

	public Member findMemberFromToken() {
		return memberRepository.findById(AuthUtil.getCurrentUserId())
			.orElseThrow(() -> new MemberCustomException(MemberErrorCode.INVALID_TOKEN));
	}

}
