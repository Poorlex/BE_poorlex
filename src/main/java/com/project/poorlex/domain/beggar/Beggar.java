package com.project.poorlex.domain.beggar;

import com.project.poorlex.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Beggar extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int level;

	private int minPoint;

	private int maxPoint;

	private String photoName;

	private String photoUrl;

	@Builder
	private Beggar(int level, int minPoint, int maxPoint, String photoName, String photoUrl) {
		this.level = level;
		this.minPoint = minPoint;
		this.maxPoint = maxPoint;
		this.photoName = photoName;
		this.photoUrl = photoUrl;
	}
}
