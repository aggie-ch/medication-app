package io.github.aggie.medicalapp.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogicAspect {
    public static final Logger logger = LoggerFactory.getLogger(LogicAspect.class);
    private final Timer templateCreateGroupTimer;

    public LogicAspect(MeterRegistry registry) {
        templateCreateGroupTimer = registry.timer("logic.template.create.group");
    }

    @Pointcut("execution(* io.github.aggie.medicalapp.logic.TemplateService.createGroup(..))")
    static void templateServiceCreateGroup() {
    }

    @Before("templateServiceCreateGroup()")
    void logMethodCall(JoinPoint jp) {
        logger.info("Before {} with {}", jp.getSignature().getName(), jp.getArgs());
    }

    @Around("templateServiceCreateGroup()")
    Object aroundTemplateCreateGroup(ProceedingJoinPoint jp) {
        return templateCreateGroupTimer.record(() -> {
        try {
            return jp.proceed();
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
        });
    }
}
