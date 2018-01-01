/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日11:48 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.model;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangyu
 */
public class BaseModel {

    protected Long objectId;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        List<Method> methodList = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is"))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        methodList.forEach(method -> {
            String name = StringUtils.substringAfter(method.getName(), "get")
                    + StringUtils.substringAfter(method.getName(), "is");
            sb.append(name)
                    .append("：");
            Object value = invokeMethod(this, method);
            if (value instanceof String[]) {
                sb.append("[");
                Arrays.stream(((String[]) value)).forEach(item -> sb.append(item).append(", "));
                sb.append("]");
            } else {
                sb.append(value);
            }
            sb.append("； ");
        });
        return sb.append("}").toString();
    }

    private Object invokeMethod(Object target, Method method, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
