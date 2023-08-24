package com.project.poorlex.dto.member;

import com.project.poorlex.domain.member.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateResponse {

	private String name;

	private String description;

	@Builder
	private MemberUpdateResponse(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public static MemberUpdateResponse of(Member member) {
		return MemberUpdateResponse.builder()
			.name(member.getName())
			.description(member.getDescription())
			.build();
	}
}
