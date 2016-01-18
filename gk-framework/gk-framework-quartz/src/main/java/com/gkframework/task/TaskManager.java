package com.gkframework.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步程序子任务管理类
 *
 * Created by user on 15/11/28.
 */
public class TaskManager {
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 8);

    public static void executeTask(final AbsTask task){
        executorService.execute(new Runnable() {
            public void run() {
                long exeTime = System.currentTimeMillis();
                task.execute();
                System.out.println("[任务执行完毕, 耗时:]" + (System.currentTimeMillis() - exeTime));
            }
        });
    }
}
