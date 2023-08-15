package com.project.poorlex.domain.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByOauthId(String oauthId);

	Optional<Member> findByOauthId(String oauthId);
}
