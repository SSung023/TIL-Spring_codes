package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    // MyLogger를 찾을 수 있는(DL 기능의) Provider가 생성
    //private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;


    @RequestMapping("log-demo")
    @ResponseBody // 원래는 view로 보내야 하지만, ResponseBody하면 문자 그대로 보낼 수 있다.
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURL().toString();
        //MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
