package com.project.poorlex.domain.battle;

import java.time.LocalDateTime;

import com.project.poorlex.domain.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Battle extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private int budget;

	private int total;

	private LocalDateTime endDate;

	@Enumerated(EnumType.STRING)
	private BattleStatus battleStatus;

	@Builder
	private Battle(String name, String description, int budget, int total, LocalDateTime endDate, BattleStatus battleStatus) {
		this.name = name;
		this.description = description;
		this.budget = budget;
		this.total = total;
		this.endDate = endDate;
		this.battleStatus = battleStatus;
	}
}
