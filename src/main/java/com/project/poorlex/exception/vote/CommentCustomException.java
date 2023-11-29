package com.project.poorlex.exception.vote;

import lombok.Getter;

@Getter
public class CommentCustomException extends RuntimeException {

    private final CommentErrorCode commentErrorCode;

    public CommentCustomException(CommentErrorCode commentErrorCode) {
        super(commentErrorCode.getDescription());
        this.commentErrorCode = commentErrorCode;
    }

}
