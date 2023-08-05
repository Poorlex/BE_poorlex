package com.project.poorlex.domain.member;

import com.project.poorlex.domain.BaseEntity;

import jakarta.persistence.Entity;
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

	@Builder
	private Member(String oauthId, String email, String name, String description, int point) {
		this.oauthId = oauthId;
		this.email = email;
		this.name = name;
		this.description = description;
		this.point = point;
	}
}
