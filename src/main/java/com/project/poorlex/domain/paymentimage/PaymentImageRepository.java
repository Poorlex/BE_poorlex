package com.project.poorlex.domain.paymentimage;

import com.project.poorlex.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentImageRepository extends JpaRepository<PaymentImage, Long> {

    List<PaymentImage> findAllByPaymentIn(List<Payment> payments);

    List<PaymentImage> findAllByPaymentId(Long paymentId);

}
