package com.goku.redis;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 2014/9/26.
 *
 * @author FZY
 */
@Component
public class RedisAdvisor extends AbstractPointcutAdvisor {

    private Pointcut pointcut = new AnnotationMatchingPointcut(null, Redis.class);

    @Autowired
    private RedisInterceptor redisInterceptor;

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return redisInterceptor;
    }

}
