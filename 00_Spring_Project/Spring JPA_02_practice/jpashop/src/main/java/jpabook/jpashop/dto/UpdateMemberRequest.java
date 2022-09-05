package jpabook.jpashop.dto;

import jpabook.jpashop.domain.valueObject.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class UpdateMemberRequest {
    @NotEmpty
    private String name;

    private Address address;
}
