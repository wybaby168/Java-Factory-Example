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
package com.wangyu.factory.dao;

import com.wangyu.factory.dao.impl.GenericDataStore;

import java.util.List;

/**
 * 数据存储（模拟数据库）
 * 为了快速，不实现删除方法
 *
 * @author wangyu
 */
public interface DataStore<T> {

    static <T> DataStore<T> getStore() {
        return GenericDataStore.store();
    }

    T insert(T record);

    List<T> insertList(List<T> list);

    T selectById(Class<T> target, Long objectId);

    List<T> selectByFilter(T filter);

    T update(T record);

    List<T> updateList(List<T> list);
}
