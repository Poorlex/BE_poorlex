package com.project.poorlex.domain.voteopinion;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.battleuser.BattleUser;
import com.project.poorlex.domain.vote.Vote;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteOpinion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vote vote;

    //투표 결과 집계를 위한 battleUser 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private BattleUser battleUser;

    //투표 결과 추가
    private boolean agreeYn;

    @Builder
    private VoteOpinion(Long id, Vote vote, boolean agreeYn, BattleUser battleUser) {
        this.id = id;
        this.vote = vote;
        this.agreeYn = agreeYn;
        this.battleUser = battleUser;
    }
}