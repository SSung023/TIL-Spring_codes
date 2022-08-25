package jpabook.jpashop.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    
    
    @Test
    public void 상품주문() throws Exception{
        // given
        
        // when
        
        //then
        
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