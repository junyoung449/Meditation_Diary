package com.project.mt.fileupload.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.mt.fileupload.config.AwsS3Uploader;
import com.project.mt.fileupload.service.FileUploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FileController {

	private final AwsS3Uploader awsS3Uploader;

	private final FileUploadService fileUploadService;

	// @PostMapping("/upload")
	// public Map<String, Object> upload(@RequestParam("image") MultipartFile[] imageMultipartFile) throws IOException {
	// 	String[] imageFileNames = awsS3Uploader.upload(imageMultipartFile, "image");
	// 	// String[] audioFileNames = awsS3Uploader.upload(audioMultipartFile, "audio");
	// 	String[] audioFileNames = new String[3];
	//
	// 	fileUploadService.save(imageFileNames, audioFileNames);
	//
	// 	Map<String, Object> result = fileUploadService.getText(imageFileNames);
	//
	// 	// result.add(imageFileNames);
	// 	// result.add(audioFileNames);
	//
	// 	return result;
	// }
}