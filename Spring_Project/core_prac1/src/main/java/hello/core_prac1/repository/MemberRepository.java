package hello.core_prac1.repository;

import hello.core_prac1.member.Member;

public interface MemberRepository {

    void save(Member member);
    Member findById(Long memberId);
}
