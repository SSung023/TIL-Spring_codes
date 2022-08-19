package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * Order에 Member과 Delivery가 있기 때문에 Order 엔티티 자체를 return 하는 방법
     * 문제점 1: 무한루프에 빠지면서 오류가 난다. Order -> Member -> Order -> Member ...
     *          양방향 연관관계일 때에는 한 쪽을 @JsonIgnore 처리를 해주어야 무한루프에 빠지지 않는다.
     * 문제점 2: Order 엔티티의 Member는 fetch=LAZY(지연로딩)으로 설정되어 있다.
     *          -> DB에서 Order만 가지고 오기 때문에 Member에는 프록시 객체를 넣는다.(bytebuddy 라이브러리 사용)
     *          -> Jackson 라이브러리는 프록시 객체를 json으로 어떻게 생성해야하는지 모름. 이로 인해 오류 발생.
     * 해결: Hibernate5Module 을 통해 해결 가능 + 한 쪽에 @JsonIgnore 어노테이션 추가
     *      but, 성능 상의 문제, Entity 수정 시 API spec의 변화, Entity의 정보 누출 등의 문제가 발생한다.
     * 근본적인 해결: Entity 자체를 반환하지 말자..
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



}
