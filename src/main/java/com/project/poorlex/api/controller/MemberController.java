package com.project.poorlex.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.poorlex.api.service.MemberService;
import com.project.poorlex.dto.member.MemberCreateRequest;
import com.project.poorlex.dto.member.MemberLoginResponse;
import com.project.poorlex.dto.member.MemberResponse;
import com.project.poorlex.dto.member.MemberUpdateRequest;
import com.project.poorlex.dto.member.MemberUpdateResponse;
import com.project.poorlex.exception.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/login")
	public ApiResponse<MemberLoginResponse> login(
		@RequestBody @Valid MemberCreateRequest request) {
		return ApiResponse.ok(memberService.createOrLogin(request));
	}

	@PutMapping
	public ApiResponse<MemberUpdateResponse> update(
		@RequestBody @Valid MemberUpdateRequest request) {
		return ApiResponse.ok(memberService.updateMember(request));
	}

	@GetMapping
	public ApiResponse<MemberResponse> myPage() {
		return ApiResponse.ok(memberService.getMemberInfo());
	}
}
