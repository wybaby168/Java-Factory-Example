/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2018年01月01日23:22 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2018, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.inject.proxy;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 对注入的Bean进行动态代理
 * 从而让代理的bean支持AOP
 *
 * @author wangyu
 */
public class DynamicProxy implements InvocationHandler {

    private static final Logger logger = LogManager.getLogger();
    /**
     * 被代理的对象
     */
    private Object target;

    @SuppressWarnings("unchecked")
    private DynamicProxy(Object target) {
        this.target = target;
        Class<?>[] classes = ArrayUtils.toArray(target.getClass());
        Proxy.newProxyInstance(target.getClass().getClassLoader(), classes, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // todo 此处实现动态代理
        if ("getName".equals(method.getName())) {
            logger.info("++++++before" + method.getName() + "++++++");
            Object result = method.invoke(target, args);
            logger.info("++++++after" + method.getName() + "++++++");
            return result;
        } else {
            return method.invoke(target, args);
        }
    }
}
