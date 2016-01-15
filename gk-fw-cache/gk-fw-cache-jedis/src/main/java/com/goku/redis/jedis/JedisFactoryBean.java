package com.goku.redis.jedis;

import com.goku.redis.JedisCallbackFilter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Jedis代理
 * Created by moueimei on 2014/7/6.
 */
@Component
public class JedisFactoryBean implements FactoryBean<Jedis> {

    private final CallbackFilter finalizeFilter = new JedisCallbackFilter();
    @Autowired
    private JedisCallback jedisCallback;
    private Jedis jedis;

    /**
     * CallbackFilter可以实现不同的方法使用不同的回调方法
     * @return
     * @throws Exception
     */
    @Override
    public Jedis getObject() throws Exception {
        if (jedis != null) {
            return jedis;
        }
        Enhancer en = new Enhancer();
        en.setSuperclass(Jedis.class);
        en.setCallbackFilter(finalizeFilter);
        en.setCallbacks(new Callback[]{NoOp.INSTANCE, jedisCallback});
        jedis = (Jedis) en.create(new Class[]{String.class}, new Object[]{"JedisProxy"});
        return jedis;
    }

    @Override
    public Class getObjectType() {
        return Jedis.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
