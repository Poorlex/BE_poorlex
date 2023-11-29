package com.project.poorlex.api.controller;


import com.project.poorlex.api.service.VoteService;
import com.project.poorlex.domain.vote.Vote;
import com.project.poorlex.domain.voteopinion.VoteOpinion;
import com.project.poorlex.dto.vote.VoteCreateRequest;
import com.project.poorlex.dto.vote.VoteCreateResponse;
import com.project.poorlex.dto.voteopinion.VoteSaveRequest;
import com.project.poorlex.exception.ApiResponse;
import com.project.poorlex.exception.vote.VoteCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ApiResponse<VoteCreateResponse> createVote(
            @RequestBody VoteCreateRequest request) throws VoteCustomException {

        return ApiResponse.ok(voteService.createVote(request));
    }

    @GetMapping("/{voteId}")
    public ApiResponse<Vote> getVote(@PathVariable Long voteId) {

        return ApiResponse.ok(voteService.getVote(voteId));
    }

    @PostMapping("/saveOpinion")
    public ApiResponse<VoteOpinion> saveVoteOpinion(@RequestBody VoteSaveRequest request) {

        return ApiResponse.ok(voteService.saveVoteOpinion(request));
    }

    @PostMapping("/result/{voteId}")
    public ApiResponse<Vote> getVoteResult(@PathVariable Long voteId) {

        return ApiResponse.ok(voteService.voteResult(voteId));
    }

    @GetMapping("resultConfirm/{voteId}")
    public ApiResponse<Vote> getVoteResultConfirm(@PathVariable Long voteId) {

        return ApiResponse.ok(voteService.voteResultConfirm(voteId));
    }
}