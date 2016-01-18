package com.gkframework.task;

/**
 * Created by user on 15/11/28.
 */
public abstract class AbsTask<T> {
    public abstract  T execute();

    public T execute(AbsTask<T> task){
        return null;
    }

    public T executeAfter(T result){
        return null;
    }
}
