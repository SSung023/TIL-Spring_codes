package hello.core_prac1;

import hello.core_prac1.discount.DiscountPolicy;
import hello.core_prac1.discount.FixDiscountPolicy;
import hello.core_prac1.order.OrderService;
import hello.core_prac1.order.OrderServiceImpl;
import hello.core_prac1.repository.MemberRepository;
import hello.core_prac1.repository.MemoryMemberRepository;
import hello.core_prac1.service.MemberService;
import hello.core_prac1.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class AppConfig {

//    @Bean
//    public MemberService memberService(){
//        return new MemberServiceImpl(memberRepository());
//    }
//
//    @Bean
//    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }
//
//    @Bean
//    public OrderService orderService(){
//        return new OrderServiceImpl(memberRepository(), discountPolicy());
//    }
//
//    @Bean
//    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
//    }
    // 수동 등록을 사용하는 경우에 policyMap을 어떻게 넣을 것인가?
}
