package com.goku.redis;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2014/12/11.
 *
 * @author moueimei
 */
public class JedisHolderTest extends AbstractTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisHolderTest.class);

    @Autowired
    private JedisNested jedisNested;

    @Autowired
    private JedisHolder jedisHolder;

    private int times = 5;

    private String key = "test:jedis";

    @Test
    public void testCreateAndRelease() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CountDownLatch latch = new CountDownLatch(10);
        for (int i=0;i<10;i++)
            executorService.submit(() ->{
                Jedis jedis =JedisProxy.create();
                System.out.println(jedis);
                System.out.println("getConnectionNum~"+jedisHolder.getConnectionNum(null));
               // jedis.close();
                latch.countDown();
            });
        latch.await();
        Assert.assertEquals(0, jedisHolder.getConnectionNum(null));
    }

}
