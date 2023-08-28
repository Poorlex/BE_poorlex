package com.project.poorlex.domain.beggar;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeggarRepository extends JpaRepository<Beggar, Long> {
	Optional<Beggar> findFirstByMinPointLessThanEqualOrderByLevelDesc(int point);
}
