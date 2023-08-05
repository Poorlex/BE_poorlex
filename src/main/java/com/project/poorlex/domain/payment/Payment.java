package com.project.poorlex.domain.payment;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.battleuser.BattleUser;
import com.project.poorlex.domain.budget.Budget;

import jakarta.persistence.Entity;
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
public class Payment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private BattleUser battleUser;

	@ManyToOne(fetch = FetchType.LAZY)
	private Budget budget;

	private String content;

	private String category;

	private int amount;

	@Builder
	private Payment(BattleUser battleUser, Budget budget, String content, String category, int amount) {
		this.battleUser = battleUser;
		this.budget = budget;
		this.content = content;
		this.category = category;
		this.amount = amount;
	}
}
