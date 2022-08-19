package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simpleQuery.OrderSimpleQueryRepository;
import jpabook.jpashop.repository.order.simpleQuery.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XToOne(ManyToOne, OneToOne) 에서의 성능 최적화
 * Order 조회
 * Order -> Member : ManyToOne
 * Order -> Delivery : OneToOne
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * Order에 Member과 Delivery가 있기 때문에 Order 엔티티 자체를 return 하는 방법
     * 문제점 1: 무한루프에 빠지면서 오류가 난다. Order -> Member -> Order -> Member ...
     *          양방향 연관관계일 때에는 한 쪽을 @JsonIgnore 처리를 해주어야 무한루프에 빠지지 않는다.
     * 문제점 2: Order 엔티티의 Member는 fetch=LAZY(지연로딩)으로 설정되어 있다.
     *          -> DB에서 Order만 가지고 오기 때문에 Member에는 프록시 객체를 넣는다.(bytebuddy 라이브러리 사용)
     *          -> Jackson 라이브러리는 프록시 객체를 json으로 어떻게 생성해야하는지 모름. 이로 인해 오류 발생.
     * 해결: Hibernate5Module 을 통해 해결 가능 + 한 쪽에 @JsonIgnore 어노테이션 추가
     *      but, 성능 상의 문제, Entity 수정 시 API spec의 변화, Entity의 정보 누출 등의 문제가 발생한다.
     * 근본적인 해결: Entity 자체를 반환하지 말자.. + fetch는 반드시 LAZY로 설정하자(지연로딩)
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            // .get을 통해 Entity를 얻고, getName()이나 getAddress()를 통해 Lazy 강제 초기화할 수 있다.
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
    }

    /**
     * Entity를 DTO로 변환하여 사용하자. - 생성자에서 값 설정
     * 1+N 문제: 첫 번째 쿼리의 결과로 N번 쿼리가 추가 실행되는 것.
     * fetch는 반드시 LAZY로 설정하고, 이후 성능 튜닝은 fetch-join을 사용하자.
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDTO> ordersV2(){
        // ORDER 조회 -> 1(order) + N(회원) + N(배송)
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<SimpleOrderDTO> result = orders.stream()
                .map(o -> new SimpleOrderDTO(o)) // map()은 o를 SimpleOrderDTO로 변환한다.
                .collect(Collectors.toList());
        return result;
    }
    @Data
    static class SimpleOrderDTO{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address; // 배송지 정보

        public SimpleOrderDTO(Order o) {
            orderId = o.getId();
            name = o.getMember().getName(); // LAZY 초기화: 영속성 컨텍스트가 찾아보고 없다면 DB 쿼리를 날린다.
            orderDate = o.getOrderDate();
            orderStatus = o.getStatus();
            address = o.getDelivery().getAddress(); // LAZY 초기화
        }
    }

    /**
     * v2와 v3의 결과 값은 같지만, 날라가는 query 가 다르다. - v2는 5번, v3는 1번
     * fetch-join으로 order->member, order->delivery는 이미 조회된 상태이기 때문에, 지연 로딩을 하지 않는다.
     *  findAllWithMemberDelivery()에서 join-fetch 를 통해 한 번에 가지고 온다. (fetch-join을 통해 최적)
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDTO> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(o -> new SimpleOrderDTO(o))
                .collect(Collectors.toList());
    }

    /**
     * v3의 경우: 외부는 건들이지 않고, fetch-join을 이용하여 원하는 부분만 가져온 것.
     *          List<SimpleOrderDto>를 가지고 오기 때문에 다른 곳에서 재사용할 수 있을 것이다.
     * V4의 경우: JPQL로 쿼리문을 쭉 짜서 가져온 것. but, 재사용이 힘들고, 해당 DTO를 가져올 때에만 사용할 수 있다.
     *          단점- 해당 DTO에 맞게 딱 짜여져있기 때문에, 다른 곳에서 재사용이 힘들다.
     *          장점- v3보다 v4가 성능편에서 더 좋다
     *
     * Repository는 Entity만을 조회해야 한다.
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
        return orderSimpleQueryRepository.findOrderDtos();
    }
}
