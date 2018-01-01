/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日11:26 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.dao.impl;

import com.wangyu.factory.dao.DataStore;
import com.wangyu.factory.model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据存储
 * 单例
 *
 * @author wangyu
 */
public class GenericDataStore<T extends BaseModel> implements DataStore<T> {

    // 单例Holder
    private static final DataStore dataStore = createStore();

    // 存储数据源
    private Map<Class<?>, List<T>> store = new HashMap<>();

    // 存储Key
    private Map<Class<?>, Long> keyMap = new HashMap<>();

    // 存储获取唯一接口
    @SuppressWarnings("unchecked")
    public static <T> DataStore<T> store() {
        return (DataStore<T>) dataStore;
    }

    /**
     * 创建Store
     *
     * @return 创建好的store
     */
    @SuppressWarnings("unchecked")
    private static <T extends BaseModel> DataStore<T> createStore() {
        GenericDataStore dataStore = new GenericDataStore();

        Map<Class<?>, List<Object>> store = dataStore.store;
        Map<Class<?>, Long> keyMap = dataStore.keyMap;
        // 往store Map里塞默认的所有数据
        store.put(Factory.class, new ArrayList<>());
        store.put(Department.class, new ArrayList<>());
        store.put(Worker.class, new ArrayList<>());
        store.put(Product.class, new ArrayList<>());
        // 初始化keyMap
        keyMap.put(Factory.class, 0L);
        keyMap.put(Department.class, 0L);
        keyMap.put(Worker.class, 0L);
        keyMap.put(Product.class, 0L);
        // 返回最终的store
        return dataStore;
    }

    @Override
    public T insert(T record) {
        List<T> store = getStore(record);
        if (store != null) {
            // 主键不存在，插入
            if (duplicateKey(store, record.getObjectId())) {
                throw new RuntimeException("duplicated Key Exception");
            } else {
                // 将生成的Key放入对象中
                if (record.getObjectId() == null) {
                    // 如果没有key，那么这条记录默认是插入失败的
                    record.setObjectId(generateKey(record));
                }
                store.add(record);
            }
        }
        return record;
    }

    @Override
    public List<T> insertList(List<T> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<T> store = getStore(list.get(0));
            if (store != null) {
                list.forEach(this::insert);
            }
        }
        return list;
    }

    @Override
    public T selectById(Class<T> target, Long objectId) {
        List<T> store = getStore(target);
        if (store != null) {
            return store.stream().filter(row -> row.getObjectId().equals(objectId)).findAny().orElse(null);
        }
        return null;
    }

    /**
     * 这里用反射实现
     *
     * @param filter 过滤器
     * @return 数据
     */
    @Override
    public List<T> selectByFilter(T filter) {
        List<T> store = getStore(filter);
        if (store != null) {
            // 过滤器设置为当前对象的一类表示，所以找出对象所有可枚举的get方法，然后和存储里的一一比对
            List<Method> methods = allMethods(filter, "get");
            // 循环做测试
            return store.stream().filter(row -> {
                // 匹配一旦开始，如果无传参，匹配全部
                boolean match = true;
                for (Method method : methods) {
                    Object value = invokeMethod(filter, method);
                    if (value != null) {
                        Object pick = invokeMethod(row, method);
                        if (!value.equals(pick)) {
                            match = false;
                        }
                    }
                }
                return match;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 如果存在记录才更新，否则不更新
     *
     * @param record 记录
     * @return 更新后的记录
     */
    @Override
    @SuppressWarnings("unchecked")
    public T update(T record) {
        List<T> store = getStore(record);
        if (store != null) {
            if (null != record.getObjectId() && duplicateKey(store, record.getObjectId())) {
                T target = selectById((Class<T>) record.getClass(), record.getObjectId());
                Map<String, Method> setMethods = allMethods(target, "set")
                        .stream()
                        .collect(
                                Collectors.toMap(method ->
                                        StringUtils.substringAfter(method.getName(), "set"), t -> t)
                        );
                Map<String, Method> getMethods = allMethods(target, "get")
                        .stream()
                        .collect(
                                Collectors.toMap(method ->
                                        StringUtils.substringAfter(method.getName(), "get") +
                                                StringUtils.substringAfter(method.getName(), "is"), t -> t)
                        );
                setMethods.keySet().forEach(key -> {
                    Method setMethod = setMethods.get(key);
                    Method getMethod = getMethods.get(key);
                    Object value = invokeMethod(record, getMethod);
                    // 值不为空时，塞进去
                    if (value != null) {
                        invokeMethod(target, setMethod, value);
                    }
                });
                // 更新完毕
            }
        }
        return record;
    }

    @Override
    public List<T> updateList(List<T> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(this::update);
        }
        return list;
    }


    // 获取store
    @SuppressWarnings("unchecked")
    private List<T> getStore(T target) {
        if (target != null) {
            return this.store.get(target.getClass());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<T> getStore(Class<?> targetClass) {
        if (targetClass != null) {
            return this.store.get(targetClass);
        }
        return null;
    }

    // 判断主键存在性
    private boolean duplicateKey(List<T> store, Long testKey) {
        return store.stream().anyMatch(row -> row.getObjectId().equals(testKey));
    }

    private Long generateKey(T target) {
        Class<?> targetClass = target.getClass();
        Long current = this.keyMap.get(targetClass);
        if (current != null) {
            this.keyMap.put(targetClass, ++current);
            return current;
        }
        return null;
    }

    private Object invokeMethod(Object target, Method method, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Method> allMethods(Object target, String prefix) {
        return Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(
                        method -> method.getName().startsWith(prefix) ||
                                prefix.equals("get") && method.getName().startsWith("is")
                )
                .collect(Collectors.toList());
    }
}
