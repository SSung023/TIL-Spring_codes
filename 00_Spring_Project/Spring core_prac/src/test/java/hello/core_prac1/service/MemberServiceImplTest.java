package hello.core_prac1.service;

import hello.core_prac1.AppConfig;
import hello.core_prac1.member.Grade;
import hello.core_prac1.member.Member;
import hello.core_prac1.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceImplTest {

    @Test
    @DisplayName("회원 가입 테스트")
    void join() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        MemberRepository memberRepository = ac.getBean(MemberRepository.class);
        Member userA = new Member(1L, "userA", Grade.VIP);

        memberService.join(userA);
        Member foundMember = memberRepository.findById(userA.getId());

        Assertions.assertThat(userA).isEqualTo(foundMember);
    }


}