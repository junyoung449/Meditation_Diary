package com.project.mt.meditation.service;

import java.util.Map;

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

	@Autowired
	private UseJson useJson;

	public void findMeditationByMemberIdx(Long memberIdx) {

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
		System.out.println(requestBody.toJSONString());
		Map<String, Object> response = useJson.callConversionApi(requestBody);
		return response;
	}
}
