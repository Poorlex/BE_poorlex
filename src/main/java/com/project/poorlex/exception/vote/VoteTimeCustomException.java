package com.project.poorlex.exception.vote;

import lombok.Getter;

@Getter
public class VoteTimeCustomException extends Exception {

    private final VoteErrorCode voteErrorCode;

    public VoteTimeCustomException(VoteErrorCode voteErrorCode) {
        super(voteErrorCode.getDescription());
        this.voteErrorCode = voteErrorCode;
    }
}
