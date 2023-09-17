package com.project.poorlex.exception.goal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GoalErrorCode {

    GOAL_NOT_FOUND("목표명이 존재하지 않습니다.");

    private final String description;
}
