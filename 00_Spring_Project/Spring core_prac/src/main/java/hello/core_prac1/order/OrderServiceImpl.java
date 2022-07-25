package hello.core_prac1.order;

import hello.core_prac1.AppConfig;
import hello.core_prac1.discount.DiscountPolicy;
import hello.core_prac1.member.Member;
import hello.core_prac1.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderServiceImpl implements OrderService{

    private final Map<String, DiscountPolicy> policyMap;
    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(Map<String, DiscountPolicy> policyMap, MemberRepository memberRepository) {
        this.policyMap = policyMap;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice, String discountCode) {
        DiscountPolicy discountPolicy = policyMap.get(discountCode);
        Member member = memberRepository.findById(memberId);

        int discountPrice = discountPolicy.discount(member, itemPrice);
        Order newOrder = new Order(memberId, itemName, itemPrice, discountPrice);
        return newOrder;
    }
}
