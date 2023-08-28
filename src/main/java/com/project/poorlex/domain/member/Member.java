package com.project.poorlex.domain.member;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.dto.member.MemberUpdateRequest;

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

	private int gold;

	private int silver;

	private int bronze;

	@Enumerated(EnumType.STRING)
	private MemberRole memberRole;

	@Builder
	private Member(String oauthId, String email, String name, String description, int point, int gold, int silver,
		int bronze, MemberRole memberRole) {
		this.oauthId = oauthId;
		this.email = email;
		this.name = name;
		this.description = description;
		this.point = point;
		this.gold = gold;
		this.silver = silver;
		this.bronze = bronze;
		this.memberRole = memberRole;
	}

	public void update(MemberUpdateRequest request) {
		this.name = request.getName();
		this.description = request.getDescription();
	}
}
