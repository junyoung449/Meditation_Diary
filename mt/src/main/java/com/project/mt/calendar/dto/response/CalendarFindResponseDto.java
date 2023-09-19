package com.project.mt.calendar.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CalendarFindResponseDto {
	private int day;
	private Long memoIdx;
	private Long meditationIdx;
}
