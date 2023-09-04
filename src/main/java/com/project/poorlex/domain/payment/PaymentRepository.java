package com.project.poorlex.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findTopByOrderByIdDesc();

    @Query("SELECT p FROM Payment p WHERE p.member.id = :memberId")
    List<Payment> findPaymentsByMemberId(@Param("memberId")Long memberId);

    Payment findTopByOrderByIdAsc();

}
