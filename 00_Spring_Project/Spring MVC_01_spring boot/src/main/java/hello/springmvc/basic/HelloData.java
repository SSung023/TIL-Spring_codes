package hello.springmvc.basic;

import lombok.Data;

@Data
public class HelloData {
    // @Data를 하면, @Getter, Setter, ToString, RequiredArgsConstructor, EqualAndHashCode를 자동 적용해준다.
    private String username;
    private int age;
}
