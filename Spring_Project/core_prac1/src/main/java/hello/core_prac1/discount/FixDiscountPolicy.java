package hello.core_prac1.discount;

import hello.core_prac1.member.Grade;
import hello.core_prac1.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{

    private final int discountAmt = 1000;

    @Override
    public int discount(Member member, int itemPrice) {
        if(member.getGrade() == Grade.VIP){
            return discountAmt;
        }
        else{
            return 0;
        }
    }
}
