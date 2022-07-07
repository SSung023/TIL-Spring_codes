package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
//@Primary
//@Qualifier("mainDiscountPolicy") // DiscountPolicy 타입이 두 개일 때, Qualifier는 추가적인 방법을 제공.
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;


    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent / 100;
        }
        else{
            return 0;
        }
    }
}
