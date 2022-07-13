package hello.core_prac1.repository;

import hello.core_prac1.member.Member;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MemoryMemberRepository implements MemberRepository {

    private static HashMap<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        Member member = store.get(memberId);
        return member;
    }
}
