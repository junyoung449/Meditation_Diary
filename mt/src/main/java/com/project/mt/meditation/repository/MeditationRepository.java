package com.project.mt.meditation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mt.meditation.domain.Meditation;

@Repository
public interface MeditationRepository extends JpaRepository<Meditation, Long> {
}
