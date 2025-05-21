package com.thainh.taskmanagement.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {
    private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class.getName());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(* com.thainh.taskmanagement.controller..*(..))")  // Use ".." to match sub-packages
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        Logger classLogger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());

        // Get HTTP request details
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        String requestBody = objectMapper.writeValueAsString(joinPoint.getArgs());

        classLogger.info("API CALL - {} {} - Body: {}", method, url, requestBody);

        Object result = joinPoint.proceed();  // Execute API method

        // Log response
        classLogger.info("API RESPONSE - {} {} - Status: {} - Response: {}", method, url,
                response.getStatus(), objectMapper.writeValueAsString(result));

        return result;
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        logger.error("Exception in method: {} - Message: {}", joinPoint.getSignature(), ex.getMessage(), ex);
    }
}
