package jpabook.jpashop.domain;

import jpabook.jpashop.domain.valueObject.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    // 연관관계의 주인이 아님 FK가 없음 -> FK쪽에서 어떤 필드와 연관이 있는지 명시해줘야 함
    @OneToMany(mappedBy = "member" /*cascade = CascadeType.ALL*/)
    private List<Order> orders = new ArrayList<>();
}
