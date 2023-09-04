package com.project.poorlex.dto.payment;

import com.project.poorlex.domain.payment.Payment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PaymentSearchResponse {

    private final List<Payment> paymentList;

    private final Payment payment;

    public static PaymentSearchResponse from(List<Payment> paymentList) {
        return PaymentSearchResponse.builder()
                .paymentList(paymentList)
                .build();
    }

    public static PaymentSearchResponse from(Payment payment){
        return PaymentSearchResponse.builder()
                .payment(payment)
                .build();
    }

}
