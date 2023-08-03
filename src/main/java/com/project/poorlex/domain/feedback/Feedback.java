package com.project.poorlex.domain.feedback;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.payment.Payment;

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
public class Feedback extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Payment payment;

	private String message;

	@Enumerated(EnumType.STRING)
	private Reaction reaction;

	@Builder
	private Feedback(Payment payment, String message, Reaction reaction) {
		this.payment = payment;
		this.message = message;
		this.reaction = reaction;
	}
}
