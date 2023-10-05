package com.project.mt.meditation.controller;

import java.io.IOException;
import java.util.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.mt.fileupload.config.AwsS3Uploader;
import com.project.mt.meditation.dto.response.MeditationListResponseDto;
import com.project.mt.meditation.dto.response.MeditationResponseDto;
import com.project.mt.meditation.service.MeditationService;
import com.project.mt.voice.dto.request.VoiceRequestDto;
import com.project.mt.voice.service.VoiceService;

import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/meditation")
@RequiredArgsConstructor
public class MeditationController {

	private final MeditationService meditationService;
	private final VoiceService voiceService;
	private final AwsS3Uploader awsS3Uploader;

	// @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@PostMapping
	public ResponseEntity<?> save(@RequestParam List<MultipartFile> images,
								  @RequestParam Long memberIdx, @RequestParam(required = false) MultipartFile voice) throws IOException {
		Map<String, Object> response = new HashMap<>();

		List<MultipartFile> voiceList = new ArrayList<>();
		String[] voiceUrl = new String[1];

		if (voice != null) {
			voiceList.add(voice);
			voiceUrl = awsS3Uploader.upload(voiceList, "voice");
		}

		String[] imageUrl = awsS3Uploader.upload(images, "image");

		Long meditationIdx = meditationService.getMedia(memberIdx, voiceUrl[0] == null ? null : voiceUrl[0], imageUrl);

		// response.put("imageUrl", imageUrl[0]);
		// response.put("voiceUrl", voiceUrl[0]);

		response.put("meditationIdx", meditationIdx);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{meditationIdx}")
	public ResponseEntity<?> findMeditationByMeditationIdx(@PathVariable("meditationIdx") Long meditationIdx) {
		return ResponseEntity.ok(meditationService.findMeditationByMeditationIdx(meditationIdx));
	}

	@GetMapping("/list/{memberIdx}")
	public ResponseEntity<?> findMeditationByMemberIdx(@PathVariable("memberIdx") Long memberIdx) {
		Map<String, List<MeditationListResponseDto>> response = new HashMap<>();
		response.put("meditationList", meditationService.findMeditationByMemberIdx(memberIdx));
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{meditationIdx}")
	public ResponseEntity<?> deleteMeditationByMeditationIdx(@PathVariable("meditationIdx") Long meditationIdx) {
		Map<String, String> response = new HashMap<>();

		if (meditationService.deleteMeditationByMeditationIdx(meditationIdx))
			response.put("resmsg", "삭제 완료");

		return ResponseEntity.ok(response);
	}
}
