package com.project.poorlex.domain.goal;

import java.time.LocalDateTime;

import com.project.poorlex.domain.BaseEntity;
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
public class Goal extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	private String name;

	private String photoUrl;

	private int amount;

	private int currentAmount;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private boolean isCompleted;

	@Builder
	private Goal(Long id, Member member, String name, String photoUrl, int amount, int currentAmount,
		LocalDateTime startDate, LocalDateTime endDate, boolean isCompleted) {
		this.id = id;
		this.member = member;
		this.name = name;
		this.photoUrl = photoUrl;
		this.amount = amount;
		this.currentAmount = currentAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isCompleted = isCompleted;
	}
}

