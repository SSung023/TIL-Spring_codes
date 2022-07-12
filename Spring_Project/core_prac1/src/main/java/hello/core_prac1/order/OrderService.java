package hello.core_prac1.order;

import hello.core_prac1.member.Member;

public interface OrderService {

    Order createOrder(Long memberId, String itemName, int itemPrice);
}
