package jpabook.jpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class CreateMemberResponse {

    private Long id;
}
