package com.project.mt.voice.repository;

import com.project.mt.member.domain.Member;
import com.project.mt.voice.domain.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoiceRepository extends JpaRepository<Voice, Long> {
    Optional<Voice> findVoiceByVoiceIdx(Long voiceIdx);
    List<Voice> findVoicesByMember(Member member);
}
