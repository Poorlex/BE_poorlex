package com.project.poorlex.dto.vote;

import com.project.poorlex.domain.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class VoteCreateResponse {

    Long id;
    String message;

    public static VoteCreateResponse of(Vote vote) {

        return VoteCreateResponse.builder()
                .id(vote.getId())
                .message("투표가 생성되었습니다.")
                .build();
    }
}