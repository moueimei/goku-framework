package com.gkframework.jms.rabbit.config;

import com.gkframework.jms.rabbit.annotation.Message;

import java.util.*;

/**
 * Created on 2014/9/5.
 *
 * @author moueimei
 */
public final class MessageConfig {

    /**
     * 消息配置表
     */
    public static final Map<String, Message> MESSAGE_MAP = new HashMap<>();
    /**
     * 监听程序列表
     */
    public static final List<String> LISTENERS = new ArrayList<>();

    private MessageConfig() {

    }

    /**
     * 保存消息配置注解
     *
     * @param key
     * @param message
     */
    public static void putMessageMap(String key, Message message) {
        MESSAGE_MAP.put(key, message);
    }

    /**
     * 添加监听者
     *
     * @param listener
     */
    public static void addListener(String listener) {
        LISTENERS.add(listener);
    }

    public static Message get(String key) {
        return MESSAGE_MAP.get(key);
    }

    public static List<String> getListeners() {
        return Collections.unmodifiableList(LISTENERS);
    }

    public static boolean isListenerEmpty() {
        return LISTENERS.isEmpty();
    }

    public static int getListenerSize() {
        return LISTENERS.size();
    }
}
