package hello.core_prac1.discount;

import hello.core_prac1.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int itemPrice);
}
