package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    @Test
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("userA");

        // when
        Long id = memberService.join(member);
        Member findMember = memberRepository.findById(id);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void 중복회원_예외() throws Exception{
        // given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("userA");
        member2.setName("userA");

        // when
        memberService.join(member1);

        //then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(exception.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


    }

}