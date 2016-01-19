package com.gkframework.model.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by guohaitao on 15-4-11.
 * Description: json工具类
 */
public class JsonUtil {

    private static SerializerFeature[] features = {SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteDateUseDateFormat};


    /**
     * object to json string
     *
     * @param object
     * @return
     */
    public static String object2Json(Object object) {
        return JSON.toJSONString(object, features);
    }


}
