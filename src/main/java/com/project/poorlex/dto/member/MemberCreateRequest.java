package com.project.poorlex.dto.member;

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

	@NotBlank
	private String oauthId;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
}


