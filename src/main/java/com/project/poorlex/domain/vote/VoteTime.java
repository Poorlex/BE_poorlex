package com.project.poorlex.domain.vote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum VoteTime {

    FIVE(5),
    TEN(10),
    THIRTY(30),
    SIXTY(60);

    private final int value;

    VoteTime(int value) {
        this.value = value;
    }

    @JsonCreator
    public static VoteTime from(int value) {
        for (VoteTime voteTime : VoteTime.values()) {
            if (voteTime.getValue() == value) {
                return voteTime;
            }
        }
        return null;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
