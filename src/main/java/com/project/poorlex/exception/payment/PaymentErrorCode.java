package com.project.poorlex.exception.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode {

    FAIL_UPLOAD_IMAGE("이미지 업로드에 실패하였습니다.");

    private final String description;

}
