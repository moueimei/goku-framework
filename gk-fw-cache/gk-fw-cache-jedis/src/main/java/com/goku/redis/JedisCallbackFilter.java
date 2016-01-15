package com.goku.redis;

import org.springframework.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created on 2014/9/26.
 *
 * @author moueimei
 */
public class JedisCallbackFilter implements CallbackFilter {

    private int id = ThreadLocalRandom.current().nextInt();

    @Override
    public int accept(Method method) {
        if ("finalize".equals(method.getName()) &&
                method.getParameterTypes().length == 0 &&
                method.getReturnType() == Void.TYPE) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JedisCallbackFilter)) {
            return false;
        }

        JedisCallbackFilter that = (JedisCallbackFilter) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
