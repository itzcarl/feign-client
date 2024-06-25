package com.example.feign.client;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    // Define pointcut for methods annotated with @RestController or @Controller
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)")
    public void controllerPointcut() {
    }

    // Define advice to handle exceptions
    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "ex")
    public void handleException(Exception ex) {
        // Log the exception
        logger.error("Exception occurred: " + ex.getMessage(), ex);

        // Optionally, you can rethrow the exception or handle it as needed
        // For example, throw a custom exception or return an error response
    }
}
