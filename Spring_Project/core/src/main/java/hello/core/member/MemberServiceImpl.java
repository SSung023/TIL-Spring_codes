package hello.core.member;

public class MemberServiceImpl implements MemberService{

    // member 정보를 저장하기 위한 memberRepository 변수 선언 (다형성)
    private final MemberRepository memberRepository = new MemoryMemberRepository();


    @Override
    public void join(Member member) {
        // 전달받은 member를 저장
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        // 전달받은 memberId를 통해 member를 찾아서 return
        return memberRepository.findById(memberId);
    }
}
