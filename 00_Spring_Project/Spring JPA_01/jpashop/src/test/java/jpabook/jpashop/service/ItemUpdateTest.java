package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception{
        Book book = em.find(Book.class, 1L);

        // Transaction
        book.setName("springBook");

        /**
         * 변경 감지 == dirty checking
         * 트랜젝션 안에서 값을 바꾸고, Transaction commit을 하면 JPA가 변경분을 확인하고 쿼리를 만든 후에, DB에 반영한다.
         */

    }

}
