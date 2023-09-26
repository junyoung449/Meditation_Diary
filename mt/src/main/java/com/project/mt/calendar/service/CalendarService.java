package com.project.mt.calendar.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.project.mt.calendar.dto.request.CalendarRequestDto;
import com.project.mt.calendar.dto.response.CalendarResponseDto;
import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import com.project.mt.meditation.domain.Meditation;
import com.project.mt.member.domain.Member;
import com.project.mt.memo.domain.Memo;
import com.project.mt.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

import com.project.mt.meditation.repository.MeditationRepository;
import com.project.mt.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final MeditationRepository meditationRepository;
	private final MemoRepository memoRepository;
	private final MemberRepository memberRepository;

    public List<CalendarResponseDto> findMeditationsAndMemoByMember(Long memberIdx) {
        Member member = memberRepository.findMemberByMemberIdx(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));

		List<Meditation> meditationResponse =  meditationRepository.findMeditationsByMember(member);
		if (meditationResponse == null || meditationResponse.isEmpty())
			meditationResponse = new ArrayList<>();

		List<Memo> memoResponse = memoRepository.findMemosByMember(member);
		if (memoResponse == null || memoResponse.isEmpty())
			memoResponse = new ArrayList<>();

		return mapMeditationAndMemoToResponseDto(meditationResponse, memoResponse);
    }

	// Meditation 과 Memo 를 CalendarResponseDto 로 변환하는 매핑 메서드
	private List<CalendarResponseDto> mapMeditationAndMemoToResponseDto(List<Meditation> meditationResponse, List<Memo> memoResponse) {
		List<CalendarResponseDto> responseDto = new ArrayList<>();

		for (int i = 0 ; i < memoResponse.size() ; i++) {
			Date date = new Date(meditationResponse.get(i).getDate().getTime());

			int year = date.getYear();
			int month = date.getMonth() + 1;
			int day = date.getDate();
			CalendarResponseDto calendarResponseDto = new CalendarResponseDto(year, month, day, memoResponse.get(i).getMemoIdx(), meditationResponse.get(i).getMeditationIdx());
			responseDto.add(calendarResponseDto);
		}

		return responseDto;
	}
}
