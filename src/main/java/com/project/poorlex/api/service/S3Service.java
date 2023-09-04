package com.project.poorlex.api.service;

import com.project.poorlex.exception.payment.PaymentCustomException;
import com.project.poorlex.exception.payment.PaymentErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final Region region = Region.AP_NORTHEAST_2;

    private final S3Client s3Client = S3Client.builder().region(region).build();

    public void uploadToS3(MultipartFile file, String bucketName, String key) {
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

    public String generateS3Url(String bucketName, String key) {
        return "https://" + bucketName + ".s3." + region.id() + ".amazonaws.com/" + key;
    }
}
