package com.goku.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * Created on 2014/9/26.
 *
 * @author FZY
 */
public abstract class AbstractCallback implements MethodInterceptor {

    @Autowired
    protected JedisHolder jedisHolder;

    protected void filterMethod(Method method) {
        boolean status = jedisHolder.hasJedis();
        filterNoSupportMethod(method, "pipelined", status);
        filterNoSupportMethod(method, "watch", status);
        filterNoSupportMethod(method, "unwatch", status);
        filterNoSupportMethod(method, "multi", status);
    }

    protected void filterNoSupportMethod(Method method, String methodName, boolean status) {
        if (method.getName().equals(methodName) && !status) {
            throw new UnsupportedOperationException("Jedis proxy does not support " + methodName + " method.");
        }
    }
}
