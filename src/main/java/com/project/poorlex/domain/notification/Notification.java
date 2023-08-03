package com.project.poorlex.domain.notification;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	private String title;

	private String link;

	private String message;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

	private boolean isChecked;

	@Builder
	private Notification(Member member, String title, String link, String message, NotificationType notificationType,
		boolean isChecked) {
		this.member = member;
		this.title = title;
		this.link = link;
		this.message = message;
		this.notificationType = notificationType;
		this.isChecked = isChecked;
	}
}
