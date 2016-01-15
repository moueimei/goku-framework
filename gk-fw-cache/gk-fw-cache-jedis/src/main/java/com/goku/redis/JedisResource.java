package com.goku.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryJedisCommands;

/**
 * Created on 2014/12/11.
 *
 * @author FZY
 */
public class JedisResource<T extends BinaryJedisCommands> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisResource.class);

    private T t;

    private int connectionNum = 0;

    public JedisResource(T t) {
        this.t = t;
    }

    public T getJedis() {
        return t;
    }

    public void setJedis(T t) {
        this.t = t;
        incrementAndGet();
    }

    public int incrementAndGet() {
        this.connectionNum++;
        return connectionNum;
    }

    public int decrementAndGet() {
        this.connectionNum--;
        return connectionNum;
    }

    public boolean hasJedis() {
        return t != null;
    }

}
