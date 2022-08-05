package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [status-line] 응답 코드(상태 코드) 설정
        response.setStatus(HttpServletResponse.SC_OK);

        // [response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // cache 무효화
        response.setHeader("Pragma", "no-cache"); // cache 무효화
        response.setHeader("my-header", "hello");

        // [Header 편의 메서드]
        content(response);

        // [Cookie 편의 메서드]
        cookie(response);
        
        // [Redirect 편의 메서드]
        redirect(response);

        PrintWriter writer = response.getWriter();
        writer.write("ok");
    }

    // content 편의 메서드
    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2

        //response.setHeader("Content-Type", "text/plain;charset=utf-8"); 이 밑과 같다다
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    // 쿠키 편의 메서드
    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good"); // Cookie 객체 생성
        cookie.setMaxAge(600); // Cookie의 유효 시간 600초
        response.addCookie(cookie); // Cookie 추가

        // 이렇게 보낸 Cookie는 request에서 .getCookie를 통해 접근할 수 있다.
    }
    
    // 리다이렉트 편의 메서드
    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html

        // response.setStatus(HttpServletResponse.SC_FOUND); //302
        // response.setHeader("Location", "/basic/hello-form.html");
        // 위 코드를 .sendRedirect 로 대체할 수 있다.
        response.sendRedirect("/basic/hello-form.html");
    }
}
