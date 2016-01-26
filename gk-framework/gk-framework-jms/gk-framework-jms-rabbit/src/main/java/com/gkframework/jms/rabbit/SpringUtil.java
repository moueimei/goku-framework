/*
 * Copyright (C) 2007-2014 AcFun.com
 * All rights reserved.
 */
package com.gkframework.jms.rabbit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * @author moueimei
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static final StandardEvaluationContext CONTEXT = new StandardEvaluationContext();
    private static volatile ApplicationContext applicationContext;

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T parseSPEL(String expression, Class<T> clz) throws EvaluationException {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        return exp.getValue(CONTEXT, clz);
    }

    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) {
        if (applicationContext != null) {
            return;
        }
        synchronized (SpringUtil.class) {
            if (applicationContext == null) {
                applicationContext = ac;
                CONTEXT.setBeanResolver(new BeanFactoryResolver(ac));
            }
        }
    }
}
