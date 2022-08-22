package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;


    /**
     * Entity를 직접 노출하는 방법
     * 단점: Entity가 변하면 API spec도 변함 + 양방향 연관관계 문제(@JsonIgnore 추가 필요)
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }


    /**
     * v2의 문제점
     * 1. DTO 클래스 안에는 Entity가 있으면 안된다 -> OrderItems를 List로 가지고 있음 -> OrderItem 또한 DTO로 변환해야함.
     * 2. DTO 클래스 내에 Entity가 있는 경우, 해당 Entity가 변하는 경우 API spec 또한 변경될 가능성이 있음.
     * 3. OrderItemDTO를 생성하여 사용
     *
     * 쿼리문은 얼마나 나갈까?
     * Order 2개 -> Member -> Delivery -> Item 여러개 -> ...
     * Collection을 사용하게 되면쿼리가 엄청나게 많이 나가게 된다.
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDTO> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        return orders.stream()
                .map(o ->new OrderDTO(o))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDTO> ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream()
                .map(o ->new OrderDTO(o))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDTO> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit){

        // Member와 Delivery를 fetch-join: ToOne 관계이기 때문에 row 수에 영향을 미치지 않는다.
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        return orders.stream()
                .map(o ->new OrderDTO(o))
                .collect(Collectors.toList());
    }


    @Data
    static class OrderDTO{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDTO> orderItems;

        public OrderDTO(Order order){
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();

            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDTO(orderItem))
                    .collect(Collectors.toList());
        }
    }
    @Data
    static class OrderItemDTO{
        private String itemName; // 상품명
        private int orderPrice; // 주문 가격
        private int count; // 주문 수량

        public OrderItemDTO(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
