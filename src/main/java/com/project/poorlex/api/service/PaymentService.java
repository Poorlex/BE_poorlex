package com.project.poorlex.api.service;

import com.project.poorlex.domain.member.Member;
import com.project.poorlex.domain.payment.Payment;
import com.project.poorlex.domain.payment.PaymentRepository;
import com.project.poorlex.domain.paymentimage.PaymentImage;
import com.project.poorlex.domain.paymentimage.PaymentImageRepository;
import com.project.poorlex.dto.payment.PaymentCreateRequest;
import com.project.poorlex.dto.payment.PaymentCreateResponse;
import com.project.poorlex.exception.payment.PaymentCustomException;
import com.project.poorlex.exception.payment.PaymentErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AuthService authService;

    private final PaymentRepository paymentRepository;

    private final PaymentImageRepository paymentImageRepository;

    private final S3Client s3Client = S3Client.builder()
            .region(Region.AP_NORTHEAST_2)
            .build();

    @Transactional
    public PaymentCreateResponse createPayment(PaymentCreateRequest request, List<MultipartFile> images) {

        Member member = authService.findMemberFromToken();

        Payment payment = Payment.builder()
                .member(member)
                .memo(request.getMemo())
                .amount(request.getAmount())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        images.forEach(file -> {
            String key = "payments/" + file.getOriginalFilename(); // S3 내의 저장 경로
            uploadToS3(file, "poorlex", key);

            String imageUrl = generateS3Url("poorlex", key);
            savePaymentImages(savedPayment, imageUrl);
        });

        PaymentCreateResponse response = new PaymentCreateResponse();

        return response;
    }

    private void savePaymentImages(Payment payment, String imageUrl) {
        PaymentImage paymentImage = PaymentImage.builder()
                .payment(payment)
                .url(imageUrl)
                .build();

        paymentImageRepository.save(paymentImage);
    }

    private String generateS3Url(String bucketName, String key) {
        return "https://" + bucketName + ".s3." + Region.AP_NORTHEAST_2.id() + ".amazonaws.com/" + key;
    }


    private void uploadToS3(MultipartFile file, String bucketName, String key) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (Exception e) {
            throw new PaymentCustomException(PaymentErrorCode.FAIL_UPLOAD_IMAGE);
        }
    }

}
