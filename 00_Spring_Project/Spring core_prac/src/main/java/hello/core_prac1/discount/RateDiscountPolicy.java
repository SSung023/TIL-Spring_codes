package hello.core_prac1.discount;

import hello.core_prac1.member.Grade;
import hello.core_prac1.member.Member;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    private final int discountRate = 10;

    @Override
    public int discount(Member member, int itemPrice) {
        if(member.getGrade() == Grade.VIP){
            return itemPrice * discountRate / 100;
        }
        else{
            return 0;
        }
    }
}
