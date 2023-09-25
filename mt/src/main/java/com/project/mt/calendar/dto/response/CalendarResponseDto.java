package com.project.mt.calendar.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CalendarResponseDto {
	private int month;
	private int day;
	private Long memoIdx;
	private Long meditationIdx;
}
