package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.valueObject.Address;
import jpabook.jpashop.repository.OrderRepository;
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


    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("JPA1", 10000, 100);
        int orderCount = 2;

        // when


        
        //then
        
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
        return book;
    }

    @Test
    public void 주문취소() throws Exception{
        // given
        
        // when
        
        //then
        
    }
    
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        // given
        
        // when
        
        //then
        
    }

}