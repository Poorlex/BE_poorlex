package com.project.poorlex.dto.payment;

import com.project.poorlex.domain.payment.Payment;
import com.project.poorlex.domain.paymentimage.PaymentImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
public class PaymentSearchResponse {

    private final List<PaymentDetail> paymentDetails;

    private final PaymentDetail paymentDetail;

    public static PaymentSearchResponse from(List<Payment> paymentList, List<PaymentImage> paymentImages) {

        Map<Long, List<String>> paymentImagesMap = paymentImages.stream()
                .collect(Collectors.groupingBy(img -> img.getPayment().getId(),
                        Collectors.mapping(PaymentImage::getUrl, Collectors.toList())));

        List<PaymentDetail> paymentDetails = paymentList.stream()
                .map(payment -> new PaymentDetail(payment, paymentImagesMap.getOrDefault(payment.getId(), Collections.emptyList())))
                .collect(Collectors.toList());

        return PaymentSearchResponse.builder()
                .paymentDetails(paymentDetails)
                .build();
    }

    public static PaymentSearchResponse from(Payment payment, List<PaymentImage> paymentImages) {

        List<String> imageUrls = paymentImages.stream()
                                        .map(PaymentImage::getUrl)
                                        .collect(Collectors.toList());

        PaymentDetail paymentDetail = new PaymentDetail(payment, imageUrls);

        return PaymentSearchResponse.builder()
                .paymentDetail(paymentDetail)
                .build();
    }

}
