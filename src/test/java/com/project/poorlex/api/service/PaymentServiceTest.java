package com.project.poorlex.api.service;

import com.project.poorlex.IntegrationTestSupport;
import com.project.poorlex.domain.payment.Payment;
import com.project.poorlex.domain.payment.PaymentRepository;
import com.project.poorlex.dto.payment.PaymentCreateRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
public class PaymentServiceTest extends IntegrationTestSupport {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    @DisplayName("지출 내역 등록 시 지출 내역 목록에 담긴다.")
    void createPayment(){
        // given
        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .amount(200000)
                .paymentDate(LocalDate.now())
                .memo("테스트 메모")
                .build();

        List<MultipartFile> emptyImageList = Collections.emptyList();

        // when
        paymentService.createPayment(request, emptyImageList);
        Payment savedPayment = paymentRepository.findTopByOrderByIdDesc();

        // then
        assertThat(savedPayment).isNotNull();
        assertThat(request.getAmount()).isEqualTo(savedPayment.getAmount());
        assertThat(request.getPaymentDate()).isEqualTo(savedPayment.getCreatedDate().toLocalDate());
        assertThat(request.getMemo()).isEqualTo(savedPayment.getMemo());
    }
}
