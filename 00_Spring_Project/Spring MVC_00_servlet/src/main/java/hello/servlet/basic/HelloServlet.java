package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// /hello 요청이 들어오면 이 클래스가 실행된다.
// @WebServlet의 name와 urlPatterns는 중복되면 안된다.
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    // 웹 브라우저가 http 요청 메세지 생성 -> 서버에 전달 = request 객체, 응답을 위한 response
    // WAS 서버들이 HttpServletRequest 인터페이스를 구현체로 구현
    // localhost:8080/hello?username=kim 에서 ?username=kim 이 query parameter 이다
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        //.getParameter 를 통해 쿼리 파라미터를 얻을 수 있다.
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // response 메세지 생성하기
        response.setContentType("text/plain"); // response 헤더의 Content-Type 설정
        response.setCharacterEncoding("utf-8"); // 문자 인코딩은 왠만하면 utf-8 사용
        response.getWriter().write("hello " + username);
    }
}
