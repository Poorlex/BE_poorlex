package com.project.poorlex.exception.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode {

    FAIL_UPLOAD_IMAGE("이미지 업로드에 실패하였습니다."),
    PAYMENT_NOT_FOUND("지출 내역을 찾을 수 없습니다.");

    private final String description;

}
