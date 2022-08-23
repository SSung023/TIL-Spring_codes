package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Transcational .persist()를 하면 바로 DB에 insert문이 나갈까? NO
 * Transaction이 commit 될 때에 flush 가 되면서 DB에 쿼리가 쭉 나간다.
 * 쿼리문을 보고 싶다면 em.flush()를 통해 확인할 수 있다.
 */
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("userA");

        //when
        Long savedId = memberService.join(member);

        //then
        //assertEquals(member, memberRepository.findOne(savedId));
        Assertions.assertThat(member.getId()).isEqualTo(savedId);
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("user");

        Member member2 = new Member();
        member2.setName("user");

        //when
        memberService.join(member1);

        //then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
        //fail("예외가 발생해야 한다."); // 코드가 여기까지 오면 잘못됐다는 것
    }

}