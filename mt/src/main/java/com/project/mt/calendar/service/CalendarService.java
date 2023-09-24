package com.project.mt.calendar.service;

import java.util.List;
import java.util.stream.Collectors;

import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import com.project.mt.meditation.domain.Meditation;
import com.project.mt.member.domain.Member;
import com.project.mt.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

import com.project.mt.calendar.dto.request.CalendarFindRequestDto;
import com.project.mt.calendar.dto.response.CalendarFindResponseDto;
import com.project.mt.meditation.repository.MeditationRepository;
import com.project.mt.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final MeditationRepository meditationRepository;
	private final MemoRepository memoRepository;
	private final MemberRepository memberRepository;

//    public List<CalendarFindResponseDto> findMeditationsByMember(CalendarFindRequestDto calendarFindRequestDto) {
//        Member member = memberRepository.findMemberByMemberIdx(calendarFindRequestDto.getMemberIdx()).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));
//
//		return meditationRepository.findMeditationsByMember(member)
//				.stream()
//				.map(this::mapMeditationToResponseDto)
//				.collect(Collectors.toList());
//    }
//
//	// Meditation을 CalendarFindResponseDto로 변환하는 매핑 메서드
//	private CalendarFindResponseDto mapMeditationToResponseDto(Meditation meditation) {
//
//		CalendarFindResponseDto responseDto = new CalendarFindResponseDto(meditation.getDate(),  meditation.getMeditationIdx(), );
//
//
//		return responseDto;
//	}
}
