package com.project.mt.voice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import com.project.mt.fileupload.util.UseJson;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;
import com.project.mt.voice.domain.Voice;
import com.project.mt.voice.dto.request.VoiceRequestDto;
import com.project.mt.voice.dto.response.VoiceListResponseDto;
import com.project.mt.voice.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoiceService {

    private final VoiceRepository voiceRepository;
    private final MemberRepository memberRepository;

    @Autowired
    private UseJson useJson;

    @Value("${elevenlabs.api.key}")
    public String elevenlabs_api_key;

    public Long saveVoice(VoiceRequestDto voiceRequestDto, MultipartFile voice) throws IOException {
        Member member = memberRepository.findMemberByMemberIdx(voiceRequestDto.getMemberIdx()).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));

        ByteArrayResource fileResource = new ByteArrayResource(voice.getBytes()) {
            // 기존 ByteArrayResource의 getFilename 메서드 override
            @Override
            public String getFilename() {
                return "requestFile.wav";
            }
        };

        // HTTP 요청을 보낼 URL
        String apiUrl = "https://api.elevenlabs.io/v1/voices/add";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("xi-api-key", elevenlabs_api_key);

        // 요청 바디 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", voiceRequestDto.getVoiceName());
        body.add("files", fileResource);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // 응답 처리
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // 응답 성공 시 처리
            String responseBody = responseEntity.getBody();

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String voiceId = jsonNode.get("voice_id").asText();

            // 결과 반환
            return voiceRepository.save(Voice.builder()
                    .member(member)
                    .voiceName(voiceRequestDto.getVoiceName())
                    .modelId(voiceId)
                    .build()).getVoiceIdx();
        } else {
            // 응답 실패 시 예외 처리 또는 다른 처리 수행
            throw new RuntimeException("Failed to upload voice.");
        }
    }

    public List<VoiceListResponseDto> findVoiceList(Long memberIdx) {
        Member member = memberRepository.findMemberByMemberIdx(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));

        return voiceRepository.findVoicesByMember(member).stream().map(this::mapVoiceToVoiceDetailResponseDto).collect(Collectors.toList());
    }

    // voice 에서 voiceIdx 와 voiceName 을 VoiceListResponseDto 로 변환하는 매핑 메서드
    private VoiceListResponseDto mapVoiceToVoiceDetailResponseDto(Voice voice) {
        return new VoiceListResponseDto(voice.getVoiceIdx(), voice.getVoiceName(), voice.getModelId());
    }
}
