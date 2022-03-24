package Practice1.practicespring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* Practice1.practicespring..*(..)))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        // 시간 로직
        long start = System.currentTimeMillis();

        System.out.println("start: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("finish: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}