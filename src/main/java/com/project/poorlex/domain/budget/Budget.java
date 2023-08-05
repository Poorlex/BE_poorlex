package com.project.poorlex.domain.budget;

import java.time.LocalDateTime;

import com.project.poorlex.domain.member.Member;

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
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	private int amount;

	private int totalPayment;

	private LocalDateTime endDate;

	@Builder
	private Budget(Member member, int amount, int totalPayment, LocalDateTime endDate) {
		this.member = member;
		this.amount = amount;
		this.totalPayment = totalPayment;
		this.endDate = endDate;
	}
}
