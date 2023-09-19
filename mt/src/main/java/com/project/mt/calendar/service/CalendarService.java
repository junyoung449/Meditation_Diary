package com.project.mt.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mt.calendar.dto.request.CalendarFindRequestDto;
import com.project.mt.calendar.dto.response.CalendarFindResponseDto;
import com.project.mt.meditation.repository.MeditationRepository;
import com.project.mt.member.dto.response.MemberResponseDto;
import com.project.mt.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {

	private final MeditationRepository meditationRepository;


	// public List<CalendarFindResponseDto> findCalendarByMemberIdxAndMonth(CalendarFindRequestDto calendarFindRequestDto) {
	// 	meditationRepository.findByMemberAndDate_MonthOrderByDate();
	// }

}
