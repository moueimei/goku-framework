package com.goku.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 User: user
 Date: 16/1/17
 Version: 1.0
 */
public class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
            
    final int[] items = new int[5];
    int putptr, takeptr, count;
    
    public void put(int x) throws InterruptedException {
//        System.out.println("put start..."+x);
        lock.lock();
            try {
                  while (count == items.length)
                        notFull.await();
                  items[putptr] = x;
                  if (++putptr == items.length){
                      putptr = 0;
                  }
                  ++count;
                  notEmpty.signal();
                } finally {
                  lock.unlock();
                }
        System.out.println("put end..."+x);
    }
    
    public int take() throws InterruptedException {
        System.out.println("take start...");
        lock.lock();
            try {
                    while (count == 0)
                        notEmpty.await();
                     int x = items[takeptr];
                      if (++takeptr == items.length) takeptr = 0;
                      --count;
                      notFull.signal();
                        System.out.println("take start..."+x);
                      return x;
                } finally {
                  lock.unlock();
                }
          }
    public static void main(String[] args) {
        BoundedBuffer boundedBuffer = new BoundedBuffer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        System.out.println("cpu ~ "+Runtime.getRuntime().availableProcessors());
        AtomicInteger atomicInteger = new AtomicInteger();

        for (int i=0;i<10;i++){
            executorService.submit(() ->{
                try {
//                    System.out.println(executorService);
                    boundedBuffer.put(atomicInteger.incrementAndGet());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        ExecutorService takeExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i=0;i<10;i++){
            takeExecutorService.submit(() ->{
                try {
//                    System.out.println(takeExecutorService);
                   System.out.println("take result ~ " + boundedBuffer.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("finish!");
        executorService.shutdown();
        takeExecutorService.shutdown();

    }
}
