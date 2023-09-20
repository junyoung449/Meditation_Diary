package com.project.mt.fileupload.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class AwsS3Uploader {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	public String bucket;

	@Value("${cloud.aws.s3.url}")
	public String s3Url;

	public String[] upload(MultipartFile[] multipartFile, String dirName) throws IOException {
		File[] uploadFiles = new File[multipartFile.length];

		for (int i = 0 ; i < multipartFile.length ; i++) {
			uploadFiles[i] = convert(multipartFile[i])
				.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert fail")); // 파일 생성
		}

		return upload(uploadFiles, dirName);
	}

	private String[] upload(File[] uploadFiles, String dirName) {
		String[] uploadUrls = new String[uploadFiles.length];

		for (int i = 0 ; i < uploadFiles.length ; i++) {
			String fileName = dirName + "/" + UUID.randomUUID() + uploadFiles[i].getName();
			uploadUrls[i] = putS3(uploadFiles[i], fileName);    // s3로 업로드
			removeNewFile(uploadFiles[i]);
		}

		return uploadUrls;
	}

	// 1. 로컬에 파일생성
	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());
		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			return Optional.of(convertFile);
		}

		return Optional.empty();
	}

	// 2. S3에 파일업로드
	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
			CannedAccessControlList.PublicRead));
		log.info("File Upload : " + fileName);
		return s3Url + fileName;
	}

	// 3. 로컬에 생성된 파일삭제
	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("File delete success");
			return;
		}
		log.info("File delete fail");
	}


	public void delete(String fileName) {
		log.info("File Delete : " + fileName);
		amazonS3Client.deleteObject(bucket, fileName);
	}
}