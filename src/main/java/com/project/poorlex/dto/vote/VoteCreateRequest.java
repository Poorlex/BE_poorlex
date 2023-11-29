package com.project.poorlex.dto.vote;

import com.project.poorlex.domain.vote.VoteTime;
import com.project.poorlex.exception.vote.VoteCustomException;
import com.project.poorlex.exception.vote.VoteErrorCode;
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

  private VoteTime voteTime;

  public LocalDateTime calculateEndDate() throws VoteCustomException {

    switch (voteTime) {

      case FIVE -> {
        return LocalDateTime.now().plusMinutes(5);
      }
      case TEN -> {
        return LocalDateTime.now().plusMinutes(10);
      }
      case THIRTY -> {
        return LocalDateTime.now().plusMinutes(30);
      }
      case SIXTY -> {
        return LocalDateTime.now().plusMinutes(60);
      }
      default -> throw new VoteCustomException(VoteErrorCode.TIME_NOT_VALID);
    }
  }
}
