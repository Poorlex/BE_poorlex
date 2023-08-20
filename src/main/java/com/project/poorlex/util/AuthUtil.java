package com.project.poorlex.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuthUtil {

	public static final String AUTHORIZATION = "Authorization";
	public static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";
	public static String BEARER_TYPE = "Bearer";

	public static String resolveAccessTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if (StringUtils.hasText(token) && token.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
			return token.substring(BEARER_TYPE.length()).trim();
		}
		return null;
	}

	public static String resolveRefreshTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader(REFRESH_TOKEN_HEADER);
		if (StringUtils.hasText(token)) {
			return token;
		}
		return null;
	}

	public static Long getCurrentUserId() {
		return (Long)(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
	}
}
