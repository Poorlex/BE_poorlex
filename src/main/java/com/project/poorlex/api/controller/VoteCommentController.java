package com.project.poorlex.api.controller;

import com.project.poorlex.api.service.VoteCommentService;
import com.project.poorlex.domain.vote.VoteComment;
import com.project.poorlex.dto.vote.CommentCreateRequest;
import com.project.poorlex.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class VoteCommentController {

    private final VoteCommentService voteCommentService;

    @PostMapping("/save")
    public ApiResponse<List<VoteComment>> save(@RequestBody CommentCreateRequest request) {

        Long saveResult = voteCommentService.save(request);
        if (saveResult != null) {
            List<VoteComment> voteCommentList = voteCommentService.findAll(request.getVoteId());
            return ApiResponse.ok(voteCommentList);
        } else {
            return ApiResponse.ok(Collections.emptyList());
        }
    }
}
