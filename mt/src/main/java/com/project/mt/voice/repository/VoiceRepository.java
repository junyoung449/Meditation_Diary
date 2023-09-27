package com.project.mt.voice.repository;

import com.project.mt.voice.domain.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoiceRepository extends JpaRepository<Voice, Long> {
//    List<>
}
