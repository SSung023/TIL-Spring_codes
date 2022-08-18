package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // readOnly=true 로 설정하면 성능을 좀 더 올릴 수 있다.
@RequiredArgsConstructor // final 필드만 가지고 생성자를 생성
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원 가입 + 중복 회원 검증
     * 쓰기 기능에는 @Transactional을 추가하여 쓰기가 가능하도록(자세한 것이 우선순위가 높다)
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // DB에서 unique 제약 조건을 거는 것을 권장
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){ // EXCEPTION
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회 - readOnly
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 회원 단건 조회 - readOnly
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
    
    /**
     * 변경 감지 기능을 이용하여 회원 이름 필드의 값 변경
     * update 메서드의 경우 Member를 반환하면, 커맨드와 쿼리가 같이 있는 꼴이 된다.
     * 따라서 void or Long id 정도만 반환하도록 하자.
     */
    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
