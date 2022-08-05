package hello.servlet.web.frontController.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        // paramMap에 들어있는 요청 파라미터를 꺼내서 사용
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        // 요청 파라미터를 통해 Member 객체 생성 후, 리포지트리에 저장
        Member member = new Member(username, age);
        memberRepository.save(member);

        // 논리 이름 설정 & Map에 정보 저장
        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);
        return mv;
    }
}
