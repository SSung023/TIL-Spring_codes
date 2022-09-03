package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.status.OrderStatus;
import jpabook.jpashop.domain.valueObject.Address;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;


    /**
     * Member와 Book 객체를 생성한 후, orderService를 통해 상품을 주문한다.
     * orderRepository에서 order를 찾아, orderService로 주문한 내역과 같은지 확인한다.
     */
    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("JPA1", 10000, 100);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(orderId);

        Assertions.assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        Assertions.assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        Assertions.assertThat(findOrder.getTotalPrice()).isEqualTo(book.getPrice() * orderCount);
        Assertions.assertThat(book.getStockQuantity()).isEqualTo(98);
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("userA");
        member.setAddress(new Address("Seoul", "street1", "15673"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    /**
     * Member, Book 객체 생성하여 orderService를 통해 order()
     * 이후, orderService의 cancelOrder()를 통해 주문 취소
     * OrderStatus가 CANCEL인지 확인, 재고가 다시 늘어났는지 확인
     */
    @Test
    public void 주문취소() throws Exception{
        // given
        Member member = createMember();
        Book item = createBook("JPA 1", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        Assertions.assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    /**
     * Member, Book 객체 생성하여 orderService를 통해 order()
     * order 시, item stockQuantity 보다 더 큰 수량 주문
     * NotEnoughStockException 발생
     */
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = createMember();
        Book item = createBook("JPA 1", 20000, 10);
        int orderCount = 12;

        // when
        //Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

}