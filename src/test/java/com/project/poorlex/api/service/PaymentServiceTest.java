package com.project.poorlex.api.service;

import com.project.poorlex.IntegrationTestSupport;
import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.domain.payment.Payment;
import com.project.poorlex.domain.payment.PaymentRepository;
import com.project.poorlex.dto.payment.PaymentCreateRequest;
import com.project.poorlex.dto.payment.PaymentSearchResponse;
import com.project.poorlex.exception.payment.PaymentCustomException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Transactional
public class PaymentServiceTest extends IntegrationTestSupport {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void clearRepository(){
        paymentRepository.deleteAll();
    }

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

    @Test
    @DisplayName("지출 아이디 없이 내역 조회 시 해당 사용자의 지출 내역 전체가 조회된다.")
    void searchPaymentAll(){

        // given
        Member member = Member.builder()
                .name("테스트 유저")
                .build();

        Member savedMember = memberRepository.save(member);
        BDDMockito.given(authService.findMemberFromToken()).willReturn(member);

        for(int i = 1 ; i <= 10 ; i++){
            Payment payment = Payment.builder()
                    .amount(2000 * i)
                    .member(savedMember)
                    .memo("지출등록"+i)
                    .build();

            paymentRepository.save(payment);
        }

        // when
        PaymentSearchResponse paymentSearchResponse = paymentService.searchAllPaymentForUser();
        List<Payment> paymentList = paymentSearchResponse.getPaymentList();

        // then
        assertThat(paymentList).hasSize(10);
    }

    @Test
    @DisplayName("지출 아이디 포함 내역 조회 시 해당 지출 내역이 조회된다.")
    void searchPaymentByPaymentId(){
        // given
        Member member = Member.builder()
                .name("테스트 유저")
                .build();

        Member savedMember = memberRepository.save(member);
        BDDMockito.given(authService.findMemberFromToken()).willReturn(member);

        for(int i = 1 ; i <= 10 ; i++){
            Payment payment = Payment.builder()
                    .amount(2000 * i)
                    .member(savedMember)
                    .memo("지출등록"+i)
                    .build();

            paymentRepository.save(payment);
        }
        
        // when
        PaymentSearchResponse firstPaymentSearchResponse = paymentService.searchPaymentByPaymentId(paymentRepository.findTopByOrderByIdAsc().getId());
        PaymentSearchResponse lastPaymentSearchResponse = paymentService.searchPaymentByPaymentId(paymentRepository.findTopByOrderByIdDesc().getId());

        assertThat(firstPaymentSearchResponse.getPayment().getAmount()).isEqualTo(2000);
        assertThat(lastPaymentSearchResponse.getPayment().getAmount()).isEqualTo(2000 * 10);
        assertThat(firstPaymentSearchResponse.getPayment().getMemo()).isEqualTo("지출등록1");
        assertThat(lastPaymentSearchResponse.getPayment().getMemo()).isEqualTo("지출등록10");
    }

    @Test
    @DisplayName("존재하지 않은 지출 아이디 조회 시 Exception이 발생한다.")
    void searchPaymentByWrongPaymentId(){
        // given
        Member member = Member.builder()
                .name("테스트 유저")
                .build();

        Member savedMember = memberRepository.save(member);
        BDDMockito.given(authService.findMemberFromToken()).willReturn(member);

        for(int i = 1 ; i <= 10 ; i++){
            Payment payment = Payment.builder()
                    .amount(2000 * i)
                    .member(savedMember)
                    .memo("지출등록"+i)
                    .build();

            paymentRepository.save(payment);
        }

        // when
        // then
        assertThatThrownBy(() -> paymentService.searchPaymentByPaymentId(-1L))
                .isInstanceOf(PaymentCustomException.class)
                .hasMessage("지출 내역을 찾을 수 없습니다.");
    }
}
