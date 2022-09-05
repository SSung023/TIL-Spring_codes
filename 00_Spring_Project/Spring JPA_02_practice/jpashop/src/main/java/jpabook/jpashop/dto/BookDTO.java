package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDTO {

    private String name;
    private Long id;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

}
