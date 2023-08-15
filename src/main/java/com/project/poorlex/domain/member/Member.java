package com.project.poorlex.domain.member;

import static com.project.poorlex.domain.member.MemberRole.*;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.dto.member.MemberCreateRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String oauthId;

	private String email;

	private String name;

	private String description;

	private int point;

	@Enumerated(EnumType.STRING)
	private MemberRole memberRole;

	@Builder
	private Member(String oauthId, String email, String name, String description, MemberRole memberRole, int point) {
		this.oauthId = oauthId;
		this.email = email;
		this.name = name;
		this.description = description;
		this.point = point;
		this.memberRole = memberRole;
	}

	public static Member create(MemberCreateRequest request) {
		return Member.builder()
			.oauthId(request.getOauthId())
			.email(request.getEmail())
			.name(request.getName())
			.memberRole(USER)
			.build();
	}
}
