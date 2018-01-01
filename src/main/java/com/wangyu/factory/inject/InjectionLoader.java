/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日16:16 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.inject;

import com.wangyu.factory.annotation.Component;
import com.wangyu.factory.annotation.Service;
import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 注入加载器
 *
 * @author wangyu
 */
public class InjectionLoader {

    // 基础包
    private static final String[] packageNames = {"com.wangyu.factory.dao", "com.wangyu.factory.service"};

    private static InjectionLoader loader;

    private Map<Class<?>, Object> beanMap;

    private InjectionLoader() {
        this.beanMap = new HashMap<>();
        // 初始化反射
        Reflections reflections = new Reflections((Object[]) packageNames);
        // 得到Resource注解的类
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Component.class);
        classSet.addAll(reflections.getTypesAnnotatedWith(Service.class));
        // 添加到bean
        classSet.forEach(this::addBean);
    }

    public static void loadResources() {
        getLoader().initBeans();
    }

    public static void loadResource(Object bean) {
        getLoader().inject(bean);
    }

    private static InjectionLoader getLoader() {
        if (loader == null) {
            loader = new InjectionLoader();
        }
        return loader;
    }

    /**
     * 添加一个bean
     *
     * @param cls 通过类名添加bean
     */
    private void addBean(Class<?> cls) {
        try {
            // 实例化，管理单例
            Object instance = cls.newInstance();
            // 判断是否继承了接口，设置为第一个接口的类型
            if (ArrayUtils.isNotEmpty(cls.getInterfaces())) {
                this.beanMap.put(cls.getInterfaces()[0], instance);
            } else {
                // 否则直接用类作为类型
                this.beanMap.put(cls, instance);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化所有的bean，处理注入
     */
    private void initBeans() {
        this.beanMap.values().forEach(this::inject);
    }

    /**
     * 注入，自动初始化
     * @param bean 要注入的bean
     */
    private void inject(Object bean) {
        Arrays.stream(bean.getClass().getDeclaredFields()).filter(field ->
                field.getAnnotation(Resource.class) != null
        ).forEach(field -> {
            Class<?> type = field.getType();
            field.setAccessible(true);
            try {
                field.set(bean, this.beanMap.get(type));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
