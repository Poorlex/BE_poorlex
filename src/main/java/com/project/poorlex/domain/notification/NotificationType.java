package com.project.poorlex.domain.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

	CONTENT("내역"),
	FRIEND("친구초대"),
	ROOM("방초대");

	private final String text;
}
