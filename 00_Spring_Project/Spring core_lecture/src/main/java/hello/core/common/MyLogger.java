package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;


    public void log(String meesage){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + meesage);
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }


    @PostConstruct
    public void init(){
        // 랜덤으로 만들어서 uuid에 저장
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope been created: " + this);
    }

    @PreDestroy
    public void close(){
        System.out.println("[" + uuid + "] request scope been close: " + this);
    }
}
