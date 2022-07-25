package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);

    }


    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect(){
        System.out.println("connect : " + url);
    }

    public void call(String message){
        System.out.println("call: " + url + "  message = " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect(){
        System.out.println("close " + url);
    }

    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        // 의존 관계 주입이 끝나면 호출
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        // 빈 소멸 직전에 호출
        disconnect();
    }
}
