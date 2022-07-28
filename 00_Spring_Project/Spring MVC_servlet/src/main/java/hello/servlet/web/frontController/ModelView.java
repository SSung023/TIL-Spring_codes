package hello.servlet.web.frontController;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ModelView {

    private String viewName; // view의 논리적 이름
    private Map<String, Object> model = new HashMap<>(); // parameter 정보들을 넣어둘 객체

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

}
