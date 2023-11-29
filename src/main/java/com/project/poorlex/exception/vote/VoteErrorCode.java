package com.project.poorlex.exception.vote;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCode {

    VOTE_NOT_FOUND("투표 정보를 찾을 수 없습니다."),
    TIME_NOT_VALID("투표 종료 시간을 잘못 선택했습니다."),
    TIME_EXPIRED("투표 시간이 종료되었습니다."),
    VOTE_NOT_VALID("투표 개설자는 참여할 수 없습니다."),
    BEFORE_END_TIME("투표 종료 시간 이전에는 개표할 수 없습니다.");

    private final String description;

}
