package jpabook.jpashop.dto;

import jpabook.jpashop.domain.valueObject.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDTO {

    private String name;
    private Address address;

}
