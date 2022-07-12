package hello.core_prac1.order;

import hello.core_prac1.discount.DiscountPolicy;
import hello.core_prac1.member.Member;
import hello.core_prac1.repository.MemberRepository;
import hello.core_prac1.service.MemberService;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);

        int discountPrice = discountPolicy.discount(member, itemPrice);
        Order newOrder = new Order(memberId, itemName, itemPrice, discountPrice);
        return newOrder;
    }
}
