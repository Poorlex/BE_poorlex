package com.project.poorlex.dto.member;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberUpdateRequest {

	@Size(min = 2, max = 15, message = "닉네임은 2자 이상 15자 이하여야 합니다.")
	private String name;

	@Size(max = 100, message = "상태 메세지는 100자 내로 입력해야 합니다.")
	private String description;

	@Builder
	private MemberUpdateRequest(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
