package com.project.poorlex.domain.friend;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_user_id")
	private Member member;

	private Long fromUserId;

	private String name;

	private String description;

	private int point;

	private boolean areWeFriend;

	@Builder
	private Friend(Member member, Long fromUserId, String name, String description, int point) {
		this.member = member;
		this.fromUserId = fromUserId;
		this.name = name;
		this.description = description;
		this.point = point;
		this.areWeFriend = false;
	}
}
