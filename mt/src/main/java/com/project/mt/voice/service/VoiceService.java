package com.project.mt.voice.service;

import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import com.project.mt.fileupload.util.UseJson;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;
import com.project.mt.voice.domain.Voice;
import com.project.mt.voice.dto.request.VoiceRequest;
import com.project.mt.voice.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoiceService {

    private final VoiceRepository voiceRepository;
    private final MemberRepository memberRepository;

    @Autowired
    private UseJson useJson;

    public Long saveVoice(VoiceRequest voiceRequest, String voiceUrl) {
        Member member = memberRepository.findMemberByMemberIdx(voiceRequest.getMemberIdx()).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));

        return voiceRepository.save(Voice.builder()
                        .member(member)
                        .voiceName(voiceRequest.getVoiceName())
                        .voiceUrl(voiceUrl)
                .build()).getVoiceIdx();

    }
}
