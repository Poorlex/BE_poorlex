package com.project.poorlex.domain.vote;

import com.project.poorlex.domain.BaseEntity;
import com.project.poorlex.domain.battleuser.BattleUser;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BattleUser battleUser;

    private String name;

    private int price;

    //투표 종료 시간 추가
    private LocalDateTime endDate;

    //투표 결과 추가
    private boolean result;

    private double prosRatio;

    private double consRatio;

    @Builder
    private Vote(BattleUser battleUser, String name, int price, LocalDateTime endDate, boolean result, double prosRatio, double consRatio) {
        this.battleUser = battleUser;
        this.name = name;
        this.price = price;
        this.endDate = endDate;
        this.result = result;
        this.prosRatio = prosRatio;
        this.consRatio = consRatio;
    }
}