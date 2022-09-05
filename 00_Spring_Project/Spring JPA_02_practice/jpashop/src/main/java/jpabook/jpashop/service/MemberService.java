package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.valueObject.Address;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> members = memberRepository.findByName(member.getName());
        if (!members.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    public Member findById(Long id){
        return memberRepository.findById(id);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Transactional
    public void update(Long id, String name, Address address){
        Member member = memberRepository.findById(id);
        member.setName(name);
        member.setAddress(address);
    }
}
