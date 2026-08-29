package project.fooddelivery.api.infrastrcture.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAndPerformanceAspect {
    @Around("execution(* project.fooddelivery.api..*.*(..))")
    public Object logAndMeasureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
//        Object[] methodArgs = joinPoint.getArgs();
        log.info("Entering method:{}", methodName);
//        log.info("Arguments: {}", Arrays.toString(methodArgs));
        Object result = joinPoint.proceed();
        Long executionTime = System.currentTimeMillis() - startTime;
        log.info("Method Executed successfully: {}", methodName);
        log.info("Execution time:{} ms", executionTime);
        return result;
    }
}
