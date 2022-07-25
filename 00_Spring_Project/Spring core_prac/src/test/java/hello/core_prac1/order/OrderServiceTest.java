package hello.core_prac1.order;

import hello.core_prac1.AppConfig;
import hello.core_prac1.AutoAppConfig;
import hello.core_prac1.discount.DiscountPolicy;
import hello.core_prac1.discount.RateDiscountPolicy;
import hello.core_prac1.member.Grade;
import hello.core_prac1.member.Member;
import hello.core_prac1.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    @DisplayName("vip는 고정 할인을 받을 수 있다.")
    void fixOrder_vip() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        OrderService orderService = ac.getBean(OrderService.class);

        Member vip = new Member(1L, "userA", Grade.VIP);
        memberService.join(vip);
        Order item1 = orderService.createOrder(1L, "item1", 20000, "fixDiscountPolicy");

        // vip이기 때문에 고정 할인 정책에 의해 1000원을 할인받을 것이다.
        Assertions.assertThat(item1.getDiscountPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("vip는 정률 할인을 받을 수 있다.")
    void rateOrder_vip(){
        DiscountPolicy discountPolicy = new RateDiscountPolicy();

        Member vip = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountPolicy.discount(vip, 20000);

        // VIP 등급이기 때문에 10%인 2000원을 할인 받아야 한다.
        Assertions.assertThat(discountPrice).isEqualTo(2000);
    }


    @Test
    @DisplayName("vip가 아닌 고객은 할인을 받을 수 없다.")
    void order_basic(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        OrderService orderService = ac.getBean(OrderService.class);

        Member basic = new Member(2L, "userB", Grade.BASIC);
        memberService.join(basic);
        Order item2 = orderService.createOrder(2L, "item1", 10000, "rateDiscountPolicy");

        // basic 회원이기 때문에 할인을 받을 수 없다.
        Assertions.assertThat(item2.getDiscountPrice()).isEqualTo(0);
    }
}