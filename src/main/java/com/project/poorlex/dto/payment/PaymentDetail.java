package com.project.poorlex.dto.payment;

import com.project.poorlex.domain.payment.Payment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PaymentDetail {

    private Payment payment;
    private List<String> imageUrls;
}
