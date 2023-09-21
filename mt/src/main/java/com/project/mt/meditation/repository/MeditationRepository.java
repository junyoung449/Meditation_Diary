package com.project.mt.meditation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mt.meditation.domain.Meditation;
import com.project.mt.member.domain.Member;

@Repository
public interface MeditationRepository extends JpaRepository<Meditation, Long> {
	 List<Meditation> findMeditationsByMember(Member member);
}
