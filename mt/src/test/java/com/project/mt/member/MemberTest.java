package com.project.mt.member;

import com.project.mt.member.domain.Member;
import com.project.mt.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("회원 고유 idx를 사용해 회원정보 리턴 테스트")
    public void 회원정보_리턴_테스트() {
        Member member = memberService.findMemberByMemberIdx(1L);

        Assertions.assertThat(member.getName()).isEqualTo("이형석");
    }
}
