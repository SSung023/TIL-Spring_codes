package hello.core_prac1.service;

import hello.core_prac1.member.Member;
import hello.core_prac1.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        Member foundMember = memberRepository.findById(memberId);
        return foundMember;
    }
}
