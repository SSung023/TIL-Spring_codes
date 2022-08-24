package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY /*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "order_id") // FK
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY /*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "item_id") // FK
    private Item item;

    private int orderPrice;
    private int orderCount;
}
