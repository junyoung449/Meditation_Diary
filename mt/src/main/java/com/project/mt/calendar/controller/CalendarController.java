package com.project.mt.calendar.controller;

import com.project.mt.calendar.dto.request.CalendarRequestDto;
import com.project.mt.calendar.dto.response.CalendarResponseDto;
import com.project.mt.calendar.service.CalendarService;
import com.project.mt.meditation.service.MeditationService;
import com.project.mt.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/{memberIdx}")
    public ResponseEntity<?> findCalendarByMemberIdx(@PathVariable("memberIdx") Long memberIdx) {
        Map<String, List<CalendarResponseDto>> response = new HashMap<>();

        response.put("calendarList", calendarService.findMeditationsAndMemoByMember(memberIdx));

        return ResponseEntity.ok(response);
    }
}