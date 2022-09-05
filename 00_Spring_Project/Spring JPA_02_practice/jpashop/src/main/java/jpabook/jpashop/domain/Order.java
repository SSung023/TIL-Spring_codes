package jpabook.jpashop.domain;

import jpabook.jpashop.domain.status.DeliveryStatus;
import jpabook.jpashop.domain.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY /*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "member_id") // FK
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // FK
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;


    //== 연관관계 편의 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    //== Order 생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem ...orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //== 비지니스 로직 ==//
    /**
     * DeliveryStatus COMP 인 경우 취소 불가능
     * increase stock, setOrderStatus CANCEL
     */
    public void cancel(){
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("미리 배송완료된 상품은 취소가 불가능합니다.");
        }
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
        this.setStatus(OrderStatus.CANCEL);
    }

    /**
     * 모든 OrderItems의 TotalPrice
     * @return
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
