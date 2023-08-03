package com.project.poorlex.domain.goalphoto;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.goal.Goal;

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
public class GoalPhoto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Goal goal;

	private String url;

	@Builder
	private GoalPhoto(Goal goal, String url) {
		this.goal = goal;
		this.url = url;
	}
}
