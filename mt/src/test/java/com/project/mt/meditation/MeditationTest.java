// package com.project.mt.meditation;
//
// import com.project.mt.meditation.dto.response.MeditationListResponseDto;
// import com.project.mt.meditation.service.MeditationService;
// import com.project.mt.member.domain.Member;
// import com.project.mt.member.repository.MemberRepository;
// import com.project.mt.member.service.MemberService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;
// import org.assertj.core.api.Assertions;
//
// import java.util.List;
//
// @SpringBootTest
// public class MeditationTest {
//
//     @Autowired
//     MeditationService meditationService;
//
//     @Autowired
//     MemberService memberService;
//
//     @Autowired
//     MemberRepository memberRepository;
//
//     @Test
//     @Transactional
//     public void 명상글_리스트_조회_테스트() {
//         List<MeditationListResponseDto> list = meditationService.findMeditationByMemberIdx(1L);
//
//         Assertions.assertThat(list.size()).isEqualTo(2);
//     }
// }
