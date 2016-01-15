package com.goku.redis.shard;

import com.goku.redis.AbstractCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;

import java.lang.reflect.Method;

/**
 * Jedis拦截器
 * Created by moueimei on 2014/7/6.
 */
@Component
public class ShardedJedisCallback extends AbstractCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShardedJedisCallback.class);

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        ShardedJedis jedis;
        boolean status = jedisHolder.hasShardJedis();
        if (!status) {
            throw new UnsupportedOperationException("ShardedJedis proxy need use @Redis annotation.");
        }
        try {
            jedis = jedisHolder.getShardedJedis();
            return methodProxy.invoke(jedis, objects);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

}
