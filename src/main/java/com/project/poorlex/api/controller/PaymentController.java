package com.project.poorlex.api.controller;

import com.project.poorlex.api.service.PaymentService;
import com.project.poorlex.dto.payment.PaymentCreateRequest;
import com.project.poorlex.dto.payment.PaymentCreateResponse;
import com.project.poorlex.dto.payment.PaymentSearchResponse;
import com.project.poorlex.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ApiResponse<PaymentSearchResponse> searchPaymentAll(){
        return ApiResponse.ok(paymentService.searchAllPaymentForUser());
    }

    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentSearchResponse> searchPaymentByPaymentId(@PathVariable Long paymentId){
        return ApiResponse.ok(paymentService.searchPaymentByPaymentId(paymentId));
    }


    @PostMapping
    public ApiResponse<PaymentCreateResponse> createPayment(
            @RequestPart("request")PaymentCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images){
        return ApiResponse.ok(paymentService.createPayment(request, images));
    }

}
