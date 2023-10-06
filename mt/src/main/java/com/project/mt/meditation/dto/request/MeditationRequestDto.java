package com.project.mt.meditation.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MeditationRequestDto {

	private Long memberIdx;
	private List<MultipartFile> images;

}
