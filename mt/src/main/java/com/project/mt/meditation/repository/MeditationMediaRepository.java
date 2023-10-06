package com.project.mt.meditation.repository;

import com.project.mt.meditation.domain.MeditationMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeditationMediaRepository extends JpaRepository<MeditationMedia, Long> {

}
