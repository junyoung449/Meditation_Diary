package com.project.mt.meditation.service;


import com.project.mt.calendar.dto.request.CalendarRequestDto;
import com.project.mt.meditation.repository.MeditationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeditationService {

    private final MeditationRepository meditationRepository;

    public void findCalendarByMemberIdx(CalendarRequestDto calendarRequestDto) {

    }
}
