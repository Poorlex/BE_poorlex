package com.project.poorlex.dto.payment;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PaymentCreateRequest {

    private int amount;

    private LocalDate paymentDate;

    private String memo;

    private List<MultipartFile> images;

}
