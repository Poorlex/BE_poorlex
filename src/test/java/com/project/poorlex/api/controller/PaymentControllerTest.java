package com.project.poorlex.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.poorlex.api.service.PaymentService;
import com.project.poorlex.config.TestSecurityConfig;
import com.project.poorlex.dto.payment.PaymentCreateRequest;
import com.project.poorlex.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = {PaymentController.class})
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PaymentService paymentService;

    @DisplayName("지출 내역 등록 시 지출 내역 목록에 담긴다.")
    @Test
    void createPayment() throws Exception {
        // given
        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .amount(200000)
                .paymentDate(LocalDate.of(2023, 9, 3))
                .memo("테스트 메모")
                .build();

        MockMultipartFile requestPart =
                new MockMultipartFile("request", "", "application/json", objectMapper.writeValueAsBytes(request));

        // when
        // then
        mockMvc.perform(
                        multipart("/payment")
                                .file(requestPart)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }
}
