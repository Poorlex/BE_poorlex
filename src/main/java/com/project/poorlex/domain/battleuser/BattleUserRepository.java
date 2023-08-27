package com.project.poorlex.domain.battleuser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BattleUserRepository extends JpaRepository<BattleUser, Long> {
    List<BattleUser> findByBattleId(Long battleId);

}
