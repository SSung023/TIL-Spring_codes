package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY /*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY /*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int orderCount;
}
