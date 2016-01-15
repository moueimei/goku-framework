package com.goku.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Jedis代理
 * Created by moueimei on 2014/7/6.
 */
@Component
public class JedisProxy implements ApplicationContextAware {

    private static volatile ApplicationContext ac;

    /**
     * 创建Jedis 代理
     *
     * @return
     */
    public static Jedis create() {
        return ac.getBean(Jedis.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (ac != null) {
            return;
        }
        synchronized (JedisProxy.class) {
            if (ac != null) {
                return;
            }
            ac = applicationContext;
        }
    }
}
