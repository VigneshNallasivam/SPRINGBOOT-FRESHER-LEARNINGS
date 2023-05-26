package com.java.basics.utility;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AspectService
{
    @Before(value = "execution(* com.java.basics.service.AopService.*(..))")
    public void Before(JoinPoint joinPoint) {
        System.out.println("Before: "+joinPoint.getSignature().getName());

    }

    @After(value = "execution(* com.java.basics.service.AopService.*(..))")
    public void After(JoinPoint joinPoint) {
        System.out.println("After: "+ Arrays.toString(joinPoint.getArgs()));
    }

    @Around("execution(* com.java.basics.service.AopService.*(..))")
    public void Around(JoinPoint joinPoint)
    {
        System.out.println("Around "+joinPoint.getSignature().getName());
    }

    @AfterThrowing(value="execution(* com.java.basics.service.AopService.*(..))",throwing="ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex)
    {
        System.out.println("After Throwing exception in method:"+joinPoint.getSignature());
        System.out.println("Exception is:"+ex.getMessage());
    }
}
