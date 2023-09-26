package com.project.mt.calendar.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
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

import lombok.Data;
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

		// 메모와 명상글을 날짜순으로 정렬
		meditationResponse.sort(Comparator.comparing(Meditation::getDate));
		memoResponse.sort(Comparator.comparing(Memo::getDate));

		return mapMeditationAndMemoToResponseDto(meditationResponse, memoResponse);
    }

	// Meditation 과 Memo 를 CalendarResponseDto 로 변환하는 매핑 메서드
	private List<CalendarResponseDto> mapMeditationAndMemoToResponseDto(List<Meditation> meditationResponse, List<Memo> memoResponse) {
		List<CalendarResponseDto> responseDto = new ArrayList<>();

		int meditationIndex = 0;
		int memoIndex = 0;

		while (meditationIndex < meditationResponse.size() || memoIndex < memoResponse.size()) {
			Date meditationDate = meditationIndex < meditationResponse.size() ?
				new Date(meditationResponse.get(meditationIndex).getDate().getTime()) : null;
			Date memoDate = memoIndex < memoResponse.size() ?
				new Date(memoResponse.get(memoIndex).getDate().getTime()) : null;

			if (meditationDate == null) {
				// 날짜가 없는 경우 메모만 있음
				int month = memoDate.getMonth() + 1;
				int day = memoDate.getDate();
				CalendarResponseDto calendarResponseDto = new CalendarResponseDto(month, day, memoResponse.get(memoIndex).getMemoIdx(), null);
				responseDto.add(calendarResponseDto);
				memoIndex++;
			} else if (memoDate == null) {
				// 날짜가 없는 경우 명상글만 있음
				int month = meditationDate.getMonth() + 1;
				int day = meditationDate.getDate();
				CalendarResponseDto calendarResponseDto = new CalendarResponseDto(month, day, null, meditationResponse.get(meditationIndex).getMeditationIdx());
				responseDto.add(calendarResponseDto);
				meditationIndex++;
			} else {
				// 날짜가 모두 있는 경우
				int meditationMonth = meditationDate.getMonth() + 1;
				int meditationDay = meditationDate.getDate();
				int memoMonth = memoDate.getMonth() + 1;
				int memoDay = memoDate.getDate();

				if (meditationDate.before(memoDate)) {
					// 명상글이 먼저 작성된 경우
					CalendarResponseDto calendarResponseDto = new CalendarResponseDto(meditationMonth, meditationDay, null, meditationResponse.get(meditationIndex).getMeditationIdx());
					responseDto.add(calendarResponseDto);
					meditationIndex++;
				} else {
					// 메모가 먼저 작성된 경우
					CalendarResponseDto calendarResponseDto = new CalendarResponseDto(memoMonth, memoDay, memoResponse.get(memoIndex).getMemoIdx(), null);
					responseDto.add(calendarResponseDto);
					memoIndex++;
				}
			}
		}

		return responseDto;
	}
}
