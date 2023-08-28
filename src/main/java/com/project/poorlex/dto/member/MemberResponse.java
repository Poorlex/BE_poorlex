package com.project.poorlex.dto.member;

import java.util.List;

import com.project.poorlex.domain.beggar.Beggar;
import com.project.poorlex.domain.friend.Friend;
import com.project.poorlex.domain.goal.Goal;
import com.project.poorlex.domain.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {

	private String name;
	private String description;
	private String photoName;
	private int level;
	private int point;
	private int needPoint;
	private int success;
	private int gold;
	private int silver;
	private int bronze;
	private List<Friend> friends;
	private List<Goal> goals;

	@Builder
	private MemberResponse(String name, String description, String photoName, int level, int point, int needPoint, int success, int gold,
		int silver, int bronze, List<Friend> friends, List<Goal> goals) {
		this.name = name;
		this.description = description;
		this.photoName = photoName;
		this.level = level;
		this.point = point;
		this.needPoint = needPoint;
		this.success = success;
		this.gold = gold;
		this.silver = silver;
		this.bronze = bronze;
		this.friends = friends;
		this.goals = goals;
	}

	public static MemberResponse of(int success, int needPoint, Member member, Beggar beggar) {
		return MemberResponse.builder()
			.name(member.getName())
			.description(member.getName())
			.photoName(beggar.getPhotoName())
			.level(beggar.getLevel())
			.point(member.getPoint())
			.needPoint(needPoint)
			.success(success)
			.gold(member.getGold())
			.silver(member.getSilver())
			.bronze(member.getBronze())
			.build();
	}
}
