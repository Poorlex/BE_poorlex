package com.project.poorlex.domain.vote;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.battleuser.BattleUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class VoteComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private BattleUser battleUser;

    @Builder
    public VoteComment(Long id, String comments, Vote vote, BattleUser battleUser) {
        this.id = id;
        this.comments = comments;
        this.vote = vote;
        this.battleUser = battleUser;
    }
}

