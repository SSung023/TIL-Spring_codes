package jpabook.jpashop.domain;

import jpabook.jpashop.domain.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.TypedQuery;
import java.util.List;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}
