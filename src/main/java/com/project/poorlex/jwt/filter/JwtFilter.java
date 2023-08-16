package com.project.poorlex.jwt.filter;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.poorlex.jwt.JwtTokenProvider;
import com.project.poorlex.jwt.MemberAuthentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

	private static final String NO_CHECK_URL = "/api/v1/members/login";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER_TYPE = "Bearer ";

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		if (request.getRequestURI().equals(NO_CHECK_URL)) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = resolveToken(request);
		String requestURI = request.getRequestURI();
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			MemberAuthentication authentication = new MemberAuthentication(jwtTokenProvider.getPayload(token));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
		} else {
			log.info("유효한 JWT 토큰이 없습니다., uri: {}", requestURI);
		}
		filterChain.doFilter(request, response);

	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
			return bearerToken.substring(BEARER_TYPE.length());
		}
		return null;
	}
}
