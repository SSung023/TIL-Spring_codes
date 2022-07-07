package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean{
        // required = true 인 경우에는 오류가 발생한다.
        // 자동 주입할 대상이 없으면 이 메서드 자체가 호출되지 않는다.
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            // Member는 스프링 컨테이너에 의해 관리되는 대상이 아니다.
            System.out.println("member = " + noBean1);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            // Member는 스프링 컨테이너에 의해 관리되는 대상이 아니다.
            System.out.println("member = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
