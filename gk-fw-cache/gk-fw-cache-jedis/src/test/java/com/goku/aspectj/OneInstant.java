package com.goku.aspectj;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * User: user
 * Date: 16/1/16
 * Version: 1.0
 */
public class OneInstant implements  BaseInstant{

    private int value=1;
    public static OneInstant INSTANCE = new OneInstant();

    private OneInstant(){

    }

    @Override
    public long setResult(long i) {
        return this.value*i;
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {

            final AtomicLong atomicLong = new AtomicLong();
            for( int i=0;i<10;i++) {
                Future<Long> future = executor.submit(() -> {
                    latch.countDown();
                    long rt = atomicLong.incrementAndGet();
                    System.out.println(rt);
                    return OneInstant.INSTANCE.setResult(rt);
                });
            }
            latch.await();
            System.out.println("finish ok!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
    }
}
