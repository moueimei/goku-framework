package com.goku.redis;

/**
 * Created on 2014/10/30.
 *
 * @author moueimei
 */
public interface ShardedJedisInterface {

    void incr(String key, int times);
}
