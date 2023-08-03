package com.project.poorlex.domain.battleuser;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.battle.Battle;
import com.project.poorlex.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BattleUser extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	private Battle battle;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	private BattleUser(Member member, Battle battle, Role role) {
		this.member = member;
		this.battle = battle;
		this.role = role;
	}
}
