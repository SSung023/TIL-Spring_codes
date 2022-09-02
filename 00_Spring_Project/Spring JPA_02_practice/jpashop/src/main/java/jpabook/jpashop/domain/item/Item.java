package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;
    private int stockQuantity;

//    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
//    List<OrderItem> orderItems = new ArrayList<>();


    //== stock 관련 ==//
    public void addStock(int count){
        this.stockQuantity += count;
    }
    public void removeStock(int count){
        this.stockQuantity -= count;
    }

}