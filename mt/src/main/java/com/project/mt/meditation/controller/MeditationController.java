package com.project.mt.meditation.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mt.fileupload.config.AwsS3Uploader;
import com.project.mt.meditation.service.MeditationService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController("/api/meditation")
@RequiredArgsConstructor
public class MeditationController {

	private final MeditationService meditationService;
	private final AwsS3Uploader awsS3Uploader;

	@PostMapping
	public ResponseEntity<?> save(@RequestParam("image") MultipartFile[] requestDto) throws IOException {
		Map<String, String> response = new HashMap<>();

		System.out.println("들어옴");
		// System.out.println(requestDto.getName());
		// String[] imageFileNames = awsS3Uploader.upload(requestDto.getImages(), "image");
		System.out.println("여기까지 옴");
		// meditationService.getText(imageFileNames);

		return ResponseEntity.ok(response);
	}
}
