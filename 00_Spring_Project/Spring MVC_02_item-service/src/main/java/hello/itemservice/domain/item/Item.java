package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

// @Data는 핵심 도메인 객체에 사용하기엔 위험하다. 왠만하면 Getter, Setter를 기본으로 사용하고 필요할 때에 따로 추가하자
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // Integer를 쓴 이유는 null이 들어갈 수 있기 때문
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
