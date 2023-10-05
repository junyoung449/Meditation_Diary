package com.project.mt.calendar.controller;

import com.project.mt.calendar.dto.request.CalendarRequestDto;
import com.project.mt.meditation.service.MeditationService;
import com.project.mt.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/calendar")
// @CrossOrigin(origins = "*")
// @RequiredArgsConstructor
// @Slf4j
// public class CalendarController {
//
//     private final MeditationService meditationService;
//
//    @PostMapping
//    public ResponseEntity<?> findCalendarByMemberIdx(@RequestBody CalendarRequestDto calendarRequestDto) {
//        meditationService.findCalendarByMemberIdx(calendarRequestDto);
//    }
//
// }
