package com.goku.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: user
 * Date: 16/1/17
 * Version: 1.0
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(2);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        System.out.println("cpu ~ "+Runtime.getRuntime().availableProcessors());
        AtomicInteger atomicInteger = new AtomicInteger();

        for (int i=0;i<100;i++){
            executorService.submit(() ->{
//                try {
//                    System.out.println(executorService);
                    int v = atomicInteger.incrementAndGet();
                   boolean rt = queue.offer(v);
                System.out.println("rt~"+rt+"| val~"+v);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            });
        }

        ExecutorService takeExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i=0;i<100;i++){
            takeExecutorService.submit(() -> {
//                try {
//                    System.out.println(takeExecutorService);
                try {
                    System.out.println("take result ~ " + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("take result ~ " + queue.peek());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            });
        }

        System.out.println("finish!");
        executorService.shutdown();
//        takeExecutorService.shutdown();
    }
}
