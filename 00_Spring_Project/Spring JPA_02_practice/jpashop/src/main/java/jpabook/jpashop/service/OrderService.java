package jpabook.jpashop.service;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    /**
     * 주문 상품(OrderItem)을 통해 해당 상품 객체 생성
     * createOrder를 통해 OrdetItem을 통합한 Order 객체 생성
     *
     */
    public Long order(Long memberId, Long orderId, OrderItem ...orderItem){
        return 0L;
    }

    public void cancelOrder(){

    }
}
