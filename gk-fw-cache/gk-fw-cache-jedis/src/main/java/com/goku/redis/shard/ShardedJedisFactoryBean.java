package com.goku.redis.shard;

import com.goku.redis.JedisCallbackFilter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.Arrays;
import java.util.List;

/**
 * Jedis代理
 * Created by fzy on 2014/7/6.
 */
@Component
public class ShardedJedisFactoryBean implements FactoryBean<ShardedJedis> {

    private final CallbackFilter finalizeFilter = new JedisCallbackFilter();
    @Autowired
    private ShardedJedisCallback jedisCallback;
    private ShardedJedis jedis;

    @Override
    public ShardedJedis getObject() throws Exception {
        if (jedis != null) {
            return jedis;
        }
        Enhancer en = new Enhancer();
        en.setSuperclass(ShardedJedis.class);
        en.setCallbackFilter(finalizeFilter);
        en.setCallbacks(new Callback[]{NoOp.INSTANCE, jedisCallback});
        jedis = (ShardedJedis) en.create(new Class[]{List.class}, new Object[]{Arrays.asList(new JedisShardInfo("shardedJedisProxy"))});
        return jedis;
    }

    @Override
    public Class getObjectType() {
        return ShardedJedis.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
