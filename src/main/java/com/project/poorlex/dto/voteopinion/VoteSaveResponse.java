package com.project.poorlex.dto.voteopinion;

import com.project.poorlex.domain.voteopinion.VoteOpinion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VoteSaveResponse {

    Long id;
    String message;

    public static VoteSaveResponse of(VoteOpinion voteOpinion) {

        return VoteSaveResponse.builder()
                .id(voteOpinion.getId())
                .message("투표 의견이 저장 되었습니다.")
                .build();
    }
}
