package com.project.poorlex.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.project.poorlex.domain.member.MemberRole;

public class MemberAuthentication extends AbstractAuthenticationToken {

	private final Long memberId;

	public MemberAuthentication(String memberId) {
		super(authorities());
		this.memberId = Long.parseLong(memberId);
	}

	@Override
	public Object getPrincipal() {
		return memberId;
	}

	@Override
	public Object getCredentials() {
		return memberId;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	private static List<GrantedAuthority> authorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(MemberRole.USER.name()));
		return authorities;
	}
}
