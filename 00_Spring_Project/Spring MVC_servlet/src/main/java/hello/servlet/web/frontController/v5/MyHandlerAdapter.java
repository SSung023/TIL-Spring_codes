package hello.servlet.web.frontController.v5;

import hello.servlet.web.frontController.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyHandlerAdapter {

    // Adapter가 처리할 수 있는 handler 인지의 여부를 반환
    boolean supports(Object handler);

    // 실제 컨트롤러를 반환
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
