package com.project.mt.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.mt.member.domain.Member;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findMemberByMemberIdx(Long memberIdx);
	Optional<Member> findMemberByEmail(String email);

	@Modifying
	@Transactional
	@Query("update Member m " +
			"set m.refreshToken = null " +
			"where m.memberIdx = :memberIdx")
	void logoutByMemberIdx(@Param("memberIdx") Long memberIdx);

	@Modifying
	@Transactional
	@Query("update Member m " +
			"set m.refreshToken = :refreshToken " +
			"where m.memberIdx = :memberIdx")
	void saveRefreshToken(@Param("refreshToken") String refreshToken, @Param("memberIdx") Long memberIdx);
}
