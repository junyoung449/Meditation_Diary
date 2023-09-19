package com.project.mt.fileupload.service;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mt.fileupload.util.UseJson;
import com.project.mt.meditation.domain.Meditation;
import com.project.mt.meditation.repository.MeditationRepository;

@Service
public class FileUploadService {

	@Autowired
	private MeditationRepository meditationRepository;

	@Autowired
	private UseJson useJson;

	public void save(String[] imageFileNames, String[] audioFileNames) {
		for (int i = 0; i < imageFileNames.length; i++) {
			// meditationRepository.save(Meditation.builder()
				// .image(imageFileNames[i])
				// .audio(audioFileNames[i])
				// .build());
		}
	}

	public Map<String, Object> getText(String[] imageFileNames) {
		JSONObject requestBody = useJson.createRequestBody(imageFileNames);

		Map<String, Object> response = useJson.callConversionApi(requestBody);
		return response;
	}
}
