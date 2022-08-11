package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    /**
     * Order-Member 다대일 관계
     * 연관관계 주인: 어떤 값이 변경되었을 때 FK를 바꿀거야!
     * member_id(FK)를 연관관계 주인에 매핑 -> Order의 member를 연관관계의 주인으로 설정
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // FK의 이름이 member_id
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // hibernate가 지원

    @Enumerated(EnumType.STRING) // 반드시 STRING으로 할 것
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]


    //== 연관관계 편의 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
