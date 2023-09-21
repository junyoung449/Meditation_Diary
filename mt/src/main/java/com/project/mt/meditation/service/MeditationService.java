package com.project.mt.meditation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.project.mt.calendar.dto.response.CalendarResponseDto;
import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import com.project.mt.meditation.dto.response.MeditationListResponseDto;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;
import com.project.mt.memo.domain.Memo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.mt.calendar.dto.request.CalendarRequestDto;
import com.project.mt.meditation.repository.MeditationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.project.mt.fileupload.util.UseJson;
import com.project.mt.meditation.domain.Meditation;
import com.project.mt.meditation.repository.MeditationRepository;

@Service
@RequiredArgsConstructor
public class MeditationService {

	private final MeditationRepository meditationRepository;
	private final MemberRepository memberRepository;

	@Autowired
	private UseJson useJson;

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

	public void save(String[] imageFileNames, String[] audioFileNames) {
		for (int i = 0; i < imageFileNames.length; i++) {
			// meditationRepository.save(Meditation.builder()
			// 	.meditationImage(imageFileNames[i])
			// 	.meditationAudio(audioFileNames[i])
			// 	.build());
		}
	}

	public Map<String, Object> getText(String[] imageFileNames) {
		JSONObject requestBody = useJson.createRequestBody(imageFileNames);
		Map<String, Object> response = useJson.callConversionApi(requestBody);
		return response;
	}
}
