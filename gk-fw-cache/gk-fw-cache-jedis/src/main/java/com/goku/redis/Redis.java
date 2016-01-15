package com.goku.redis;

import java.lang.annotation.*;

/**
 * JedisProxy注解
 * <p>使用注解后支持pipelined,watch,unwatch,multi方法</p>
 * Created on 2014/9/26.
 *
 * @author FZY
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Redis {

    String value() default "";

    boolean shard() default false;

}
