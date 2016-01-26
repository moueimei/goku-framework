/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */
package com.gkframework.jms.rabbit.test;

import com.gkframework.jms.rabbit.SpringUtil;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 发送测试断言工具
 *
 * @author moueimei
 */
public class MessageAssert {

    private String json;

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        T t = SpringUtil.getBean(clazz);
        if (t instanceof Advised) {
            Advisor[] advisors = ((Advised) t).getAdvisors();
            int index = -1;
            int i = 0;
            for (Advisor advisor : advisors) {
                if (advisor.getAdvice() instanceof SendAdvice) {
                    index = i;
                    break;
                }
                i++;
            }
            if (index >= 0) {
                ((Advised) t).removeAdvisor(index);
            }
            ((Advised) t).addAdvice(new SendAdvice(json));
            return t;
        }
        ProxyFactory factory = new ProxyFactory(t);
        factory.addAdvice(new SendAdvice(json));
        return (T) factory.getProxy();
    }

    /**
     * 设置预期结果
     *
     * @param <T>
     * @param t
     * @return
     */
    public <T> MessageAssert setExpect(T t) {
        if (t instanceof String) {
            this.json = (String) t;
        } else if (t instanceof JSONObject) {
            this.json = ((JSONObject) t).toString();
        } else {
            Gson gson = new Gson();
            this.json = gson.toJson(t);
        }
        return this;
    }

}
