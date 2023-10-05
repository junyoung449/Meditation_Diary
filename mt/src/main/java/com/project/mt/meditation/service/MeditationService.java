package com.project.mt.meditation.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import com.project.mt.meditation.domain.MeditationMedia;
import com.project.mt.meditation.dto.response.MeditationListResponseDto;
import com.project.mt.meditation.dto.response.MeditationMediaResponseDto;
import com.project.mt.meditation.dto.response.MeditationResponseDto;
import com.project.mt.meditation.repository.MeditationMediaRepository;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;
import com.project.mt.voice.domain.Voice;
import com.project.mt.voice.repository.VoiceRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.mt.meditation.repository.MeditationRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.mt.fileupload.util.UseJson;
import com.project.mt.meditation.domain.Meditation;

@Service
@RequiredArgsConstructor
public class MeditationService {

    private final MeditationRepository meditationRepository;
    private final MeditationMediaRepository meditationMediaRepository;
    private final MemberRepository memberRepository;
    private final VoiceRepository voiceRepository;

    @Autowired
    private UseJson useJson;

    @Value("${default.voice}")
    public String defaultVoice;

    public Long getMedia(Long memberIdx, String voiceUrl, String[] imageUrl) {
        Member member = memberRepository.findMemberByMemberIdx(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));

        JSONObject requestBody = useJson.createRequestBody(memberIdx, voiceUrl == null ? null : voiceUrl, imageUrl);

        Map<String, List<String>> response = useJson.callConversionApi(requestBody);

        String[] audioUrl = new String[imageUrl.length];

        List<String> responseAudio = response.get("audios");

        Meditation meditation = meditationRepository.save(Meditation.builder()
            .member(member)
            .build());

        List<MeditationMedia> meditationMediaList = new ArrayList<>();

        for (int i = 0; i < audioUrl.length; i++) {
            audioUrl[i] = responseAudio.get(i);
            meditationMediaList.add(MeditationMedia.builder()
                .meditation(meditation)
                .audioUrl(audioUrl[i])
                .imageUrl(imageUrl[i])
                .build());
        }

        meditationMediaRepository.saveAll(meditationMediaList);

        return meditation.getMeditationIdx();
    }

    public MeditationResponseDto findMeditationByMeditationIdx(Long meditationIdx) {
        Meditation meditation = meditationRepository.findByMeditationIdx(meditationIdx).orElseThrow(() -> new RestApiException(ErrorCode.MEDITATION_NOT_FOUND));

        List<MeditationMediaResponseDto> meditationMediaResponseDto = meditation.getMeditationMedia()
            .stream()
            .map(this::mapMeditationMediaToResponseDto)
            .collect(Collectors.toList());

        return new MeditationResponseDto(meditationIdx, meditation.getMember().getMemberIdx(), meditation.getDate(), meditationMediaResponseDto);
    }

    // Meditation 에서 MeditationMedia 를 MeditationMediaResponseDto 로 변환하는 매핑 메서드
    private MeditationMediaResponseDto mapMeditationMediaToResponseDto(MeditationMedia meditationMedia) {
        MeditationMediaResponseDto meditationMediaResponseDto
            = new MeditationMediaResponseDto(meditationMedia.getImageUrl(), meditationMedia.getAudioUrl());

        return meditationMediaResponseDto;
    }

    public List<MeditationListResponseDto> findMeditationByMemberIdx(Long memberIdx) {
        Member member = memberRepository.findMemberByMemberIdx(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));

        List<MeditationListResponseDto> meditationListResponseDtoList = meditationRepository.findMeditationsByMember(member)
                .stream()
                .map(this::mapMeditationToResponseDto)
                .collect(Collectors.toList());

        return meditationListResponseDtoList;
    }

    // Meditation 과 Memo 를 CalendarResponseDto 로 변환하는 매핑 메서드
    private MeditationListResponseDto mapMeditationToResponseDto(Meditation meditation) {
        MeditationListResponseDto meditationListResponseDto = new MeditationListResponseDto(meditation.getMeditationIdx()
                , meditation.getMember().getMemberIdx(), meditation.getDate(), meditation.getMeditationMedia().get(0).getImageUrl());

        return meditationListResponseDto;
    }

    public boolean deleteMeditationByMeditationIdx(Long meditationIdx) {
        Meditation meditation = meditationRepository.findByMeditationIdx(meditationIdx)
            .orElseThrow(() -> new RestApiException(ErrorCode.MEDITATION_NOT_FOUND));

        meditationRepository.delete(meditation);

        return true;
    }
}
