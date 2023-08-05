package com.project.poorlex.domain.voteopinion;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.vote.Vote;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteOpinion extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Vote vote;

	private boolean isAgreed;

	@Builder
	private VoteOpinion(Vote vote, boolean isAgreed) {
		this.vote = vote;
		this.isAgreed = isAgreed;
	}
}
