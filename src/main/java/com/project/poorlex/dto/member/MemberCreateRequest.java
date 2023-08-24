package com.project.poorlex.dto.member;

import static com.project.poorlex.domain.member.MemberRole.*;

import com.project.poorlex.domain.member.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberCreateRequest {

	@NotBlank(message = "고유번호는 필수입니다.")
	private String oauthId;
	@NotBlank(message = "이메일은 필수입니다.")
	private String email;
	@NotBlank(message = "닉네임은 필수입니다.")
	private String name;

	public Member toEntity() {
		return Member.builder()
			.oauthId(oauthId)
			.email(email)
			.name(name)
			.memberRole(USER)
			.build();
	}
}


