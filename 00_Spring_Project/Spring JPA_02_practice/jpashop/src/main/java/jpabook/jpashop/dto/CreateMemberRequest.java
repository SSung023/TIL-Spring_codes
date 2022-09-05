package jpabook.jpashop.dto;

import jpabook.jpashop.domain.valueObject.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class CreateMemberRequest {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    private Address address;
}
