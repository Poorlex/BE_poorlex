package com.project.poorlex.api.controller;

import com.project.poorlex.api.service.PaymentService;
import com.project.poorlex.dto.payment.PaymentCreateRequest;
import com.project.poorlex.dto.payment.PaymentCreateResponse;
import com.project.poorlex.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<PaymentCreateResponse> createPayment(
            @RequestPart("request")PaymentCreateRequest request,
            @RequestPart("images") List<MultipartFile> images){
        return ApiResponse.ok(paymentService.createPayment(request, images));
    }

}
