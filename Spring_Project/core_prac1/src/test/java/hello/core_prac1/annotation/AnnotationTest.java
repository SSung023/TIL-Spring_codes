package hello.core_prac1.annotation;

import hello.core_prac1.AutoAppConfig;
import hello.core_prac1.member.Grade;
import hello.core_prac1.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationTest {

    @Test
    void test1(){
        System.out.println("test1");
    }

    @Test
    @Disabled
    void test2(){
        System.out.println("test2");
    }

    @Test
    void test(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean(Member.class)
        );
    }
}
