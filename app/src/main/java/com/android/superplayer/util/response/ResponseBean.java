package com.android.superplayer.util.response;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * authr : edz on 2017/8/28  下午6:41
 * describe ;统一输出
 * 父类
 */
public class ResponseBean<T> {


    /**
     * code : 201
     * msg : 查询成功无数据
     * data : null
     * version : 1.0
     */

    private int code;
    private String msg;
    private T data;
    private String version;



    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }


    public String getVersion() {
        return version;
    }







    public static ResponseBean fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResponseBean.class, clazz);
        return gson.fromJson(json, objectType);
    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResponseBean.class, clazz);
        return gson.toJson(this, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            @Override
            public Type getRawType() {
                return raw;
            }
            @Override
            public Type[] getActualTypeArguments() {
                return args;
            }
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

}
