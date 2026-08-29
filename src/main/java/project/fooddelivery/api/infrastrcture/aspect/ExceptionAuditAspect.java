package project.fooddelivery.api.infrastrcture.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionAuditAspect {


    @AfterThrowing(
            pointcut = "execution(* project.fooddelivery.api..*.*(..))",
            throwing = "ex"
    )
    public void logAfterException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("Exception occurred while executing method: {}",methodName);
        log.error("Exception type: {}",ex.getClass().getSimpleName());
        log.error("Exception message: {}",ex.getMessage());
    }
}
