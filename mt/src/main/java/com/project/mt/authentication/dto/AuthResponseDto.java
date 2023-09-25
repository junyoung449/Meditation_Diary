package com.project.mt.authentication.dto;

import com.project.mt.authentication.domain.AuthTokens;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class AuthResponseDto {
	private Long memberIdx;
	private String accessToken;
}
