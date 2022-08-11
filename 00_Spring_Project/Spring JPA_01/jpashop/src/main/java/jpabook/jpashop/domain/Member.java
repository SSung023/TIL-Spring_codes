package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    /**
     * Member-Order 일대다 관계,
     * mappedBy = "member": order table에 있는 member 필드에 의해 매핑되었음 -> 읽기 전용이 됨
     * Hibernate가 List<>를 영속화하면서 한 번 감싸기 때문에, 이 이후에 List를 변경하면 절대 안된다.
     */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
