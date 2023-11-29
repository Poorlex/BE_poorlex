package com.project.poorlex.dto.voteopinion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VoteSaveRequest {

    private Long voteId;

    private Long battleUserId;

    private boolean agreeYn;
}
