package com.project.poorlex.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentCreateRequest {

  Long voteId;

  String comments;

  Long battleUserId;

}
