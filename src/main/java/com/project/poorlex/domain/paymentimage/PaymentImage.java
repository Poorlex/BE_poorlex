package com.project.poorlex.domain.paymentimage;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.payment.Payment;

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
public class PaymentImage extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Payment payment;

	private String url;

	@Builder
	private PaymentImage(Payment payment, String url) {
		this.payment = payment;
		this.url = url;
	}
}
