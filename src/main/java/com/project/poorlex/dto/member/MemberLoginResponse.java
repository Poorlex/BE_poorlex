package com.project.poorlex.dto.member;

import com.project.poorlex.jwt.Token;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginResponse {
	private String grantType;
	private String accessToken;
	private String refreshToken;

	@Builder
	private MemberLoginResponse(String grantType, String accessToken, String refreshToken) {
		this.grantType = grantType;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static MemberLoginResponse of(Token token) {
		return MemberLoginResponse.builder()
			.grantType("Bearer")
			.accessToken(token.getAccessToken())
			.refreshToken(token.getRefreshToken())
			.build();
	}
}
