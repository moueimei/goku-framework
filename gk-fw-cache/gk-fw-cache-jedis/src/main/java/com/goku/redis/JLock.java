package com.goku.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * redis实现的分布式锁
 * Created by fzy on 2014/9/1.
 */
public final class JLock {

    private static final String LOCK = "redis:lock:%s";

    private static final int DEFAULT_SINGLE_EXPIRE_TIME = 1;
    private static final int EXPIRE = DEFAULT_SINGLE_EXPIRE_TIME*1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(JLock.class);

    private JLock() {

    }

    /**
     * 获得锁,未取得时一直阻塞
     *
     * @param id
     */
    public static void getLock(String id) {
        getLock(id, 0);
    }

    /**
     * 获得锁,超时退出
     *
     * @param key
     * @param timeout 超时时间(ms)
     * @return
     */
    public static boolean getLock(String key, int timeout) {
        return getLock(key,timeout,TimeUnit.MILLISECONDS);
    }

    /**
     * 获得锁,超时退出
     *
     * @param key
     * @param timeout 超时时间(ms)
     * @return
     */
    public static boolean getLock(String key, long timeout, TimeUnit unit) {
        try {
            Jedis  jedis = JedisProxy.create();
            long start = System.nanoTime();
            key = String.format(LOCK, key);
            LOGGER.debug("try lock key: " + key);
            long lock = 0;
            while (lock != 1) {
                long now = System.currentTimeMillis();
                //判断超时
                if (timeout > 0 && (now - start) >= unit.toNanos(timeout)) {
                    return false;
                }
                lock = jedis.setnx(key, key);
                if (lock == 1) {
                    jedis.expire(key, EXPIRE);
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("get lock, key: " + key + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
                    return Boolean.TRUE;
                } else { // 存在锁
                    sleep();
                    if (LOGGER.isDebugEnabled()) {
                        String desc = jedis.get(key);
                        LOGGER.debug("key: " + key + " locked by another business：" + desc);
                    }
                }
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Boolean.FALSE;
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            LOGGER.error("An unexpected error occurred.", e);
        }
    }

    /**
     * 释放锁
     *
     * @param id
     */
    public static void releaseLock(String id) {
        Jedis jedis = JedisProxy.create();
        String key = String.format(LOCK, id);
        jedis.del(key);
    }
}
