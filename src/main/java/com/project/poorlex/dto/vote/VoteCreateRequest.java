package com.project.poorlex.dto.vote;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.poorlex.exception.vote.VoteErrorCode;
import com.project.poorlex.exception.vote.VoteTimeCustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VoteCreateRequest {

    private Long battleUserId;

    private String name;

    private int price;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private int time;

    public LocalDateTime calculateEndDate() throws VoteTimeCustomException {

        if (time == 5) {
            return LocalDateTime.now().plusMinutes(5);
        } else if (time == 10) {
            return LocalDateTime.now().plusMinutes(10);
        } else if (time == 30) {
            return LocalDateTime.now().plusMinutes(30);
        } else if (time == 60) {
            return LocalDateTime.now().plusHours(1);
        } else throw new VoteTimeCustomException(VoteErrorCode.TIME_NOT_VALID);
    }
}
