package com.project.poorlex.jwt;

import static com.project.poorlex.exception.member.MemberErrorCode.*;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.poorlex.exception.member.MemberCustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {

	@Value("${jwt.token.secret-key}")
	private String secretKey;
	@Value("${jwt.token.access-expire-length}")
	private long accessTokenValidInMilliSeconds;
	@Value("${jwt.token.refresh-expire-length}")
	private long refreshTokenValidInMilliSeconds;
	private Key key;

	@Override
	public void afterPropertiesSet() {
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public Token createAccessToken(String payload) {
		String accessToken = createToken(payload, accessTokenValidInMilliSeconds);
		String refreshToken = createToken(UUID.randomUUID().toString(), refreshTokenValidInMilliSeconds);
		return new Token(accessToken, refreshToken);
	}

	private String createToken(String payload, long expiredLength) {
		Claims claims = Jwts.claims().setSubject(payload);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + expiredLength))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	public String getPayload(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		} catch (ExpiredJwtException e) {
			return e.getClaims().getSubject();
		} catch (JwtException e) {
			throw new MemberCustomException(INVALID_TOKEN);
		}
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.info("지원하지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}
}
