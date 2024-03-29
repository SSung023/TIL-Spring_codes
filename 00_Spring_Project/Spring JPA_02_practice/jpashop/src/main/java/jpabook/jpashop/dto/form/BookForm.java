package jpabook.jpashop.dto.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
