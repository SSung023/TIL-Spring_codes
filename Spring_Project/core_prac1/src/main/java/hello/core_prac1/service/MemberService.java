package hello.core_prac1.service;

import hello.core_prac1.member.Member;

public interface MemberService {

    void join(Member member);
    Member findMember(Long memberId);
}
