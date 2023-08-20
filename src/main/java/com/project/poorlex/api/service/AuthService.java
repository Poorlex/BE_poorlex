package com.project.poorlex.api.service;

import static com.project.poorlex.exception.member.MemberErrorCode.*;

import org.springframework.stereotype.Service;

import com.project.poorlex.dto.member.MemberLoginResponse;
import com.project.poorlex.exception.member.MemberCustomException;
import com.project.poorlex.jwt.JwtTokenProvider;
import com.project.poorlex.jwt.Token;
import com.project.poorlex.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final RedisUtil redisUtil;

	public MemberLoginResponse accessTokenByRefreshToken(
		String accessToken,
		String refreshToken
	) {
		validateRefreshToken(refreshToken);
		String id = jwtTokenProvider.getPayload(accessToken);
		String data = redisUtil.getData(id);
		if (!data.equals(refreshToken)) {
			throw new MemberCustomException(INVALID_TOKEN);
		}
		Token newAccessToken = jwtTokenProvider.createAccessToken(id);
		return MemberLoginResponse
			.builder()
			.accessToken(newAccessToken.getAccessToken())
			.refreshToken(newAccessToken.getRefreshToken())
			.build();
	}

	private void validateAccessToken(String accessToken) {
		if (!jwtTokenProvider.validateToken(accessToken)) {
			throw new MemberCustomException(UNAUTHORIZED_ACCESS_TOKEN);
		}
	}

	private void validateRefreshToken(String refreshToken) {
		if (!jwtTokenProvider.validateToken(refreshToken)) {
			throw new MemberCustomException(UNAUTHORIZED_REFRESH_TOKEN);
		}
	}
}
