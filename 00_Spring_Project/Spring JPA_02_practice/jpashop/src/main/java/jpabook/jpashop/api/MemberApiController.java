package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.*;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 목록 조회, 회원 가입, 회원 정보 갱신 REST API
 * JSON(객체) 형태로 반환
 */
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    
    /**
     * 회원 목록 조회
     * API 스펙 : 회원 이름, 주소
     */
    @GetMapping("/api/members")
    public Result members(){
        List<Member> members = memberService.findAll();
        List<MemberDTO> result = members.stream()
                .map(m -> new MemberDTO(m.getName(), m.getAddress()))
                .collect(Collectors.toList());

        return new Result(result);
    }

    /**
     * 회원 가입
     * Request : String name, Address address
     * Response : Long id
     */
    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        member.setAddress(request.getAddress());

        Long memberId = memberService.join(member);
        return new CreateMemberResponse(memberId);
    }

    /**
     * 회원 정보 부분 수정
     * REQ : String name, Address address
     * RES : Long id, String name
     */
    @PatchMapping("/api/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName(), request.getAddress());

        Member findMember = memberService.findById(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
}
