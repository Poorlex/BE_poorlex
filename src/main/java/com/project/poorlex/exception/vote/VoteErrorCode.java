package com.project.poorlex.exception.vote;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCode {

    VOTE_NOT_FOUND("투표가 존재하지 않습니다."),
    TIME_NOT_VALID("투표 종료 시간을 잘못 선택했습니다.");

    private final String description;

}
