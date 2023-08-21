package com.project.poorlex.domain.battle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {

    List<Battle> findByBudgetBetween(Integer minBudget, Integer maxBudget);

}
