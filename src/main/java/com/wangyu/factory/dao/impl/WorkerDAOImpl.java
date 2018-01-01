/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日14:15 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.dao.impl;

import com.wangyu.factory.annotation.Component;
import com.wangyu.factory.dao.DataStore;
import com.wangyu.factory.dao.WorkerDAO;
import com.wangyu.factory.model.Worker;

import java.util.List;

/**
 *
 * @author wangyu
 */
@Component
public class WorkerDAOImpl implements WorkerDAO {

    @Override
    public Worker insert(Worker record) {
        return getStore().insert(record);
    }

    @Override
    public List<Worker> insertList(List<Worker> list) {
        return getStore().insertList(list);
    }

    @Override
    public Worker selectById(Long objectId) {
        return getStore().selectById(Worker.class, objectId);
    }

    @Override
    public List<Worker> selectByFilter(Worker filter) {
        return getStore().selectByFilter(filter);
    }

    @Override
    public Worker update(Worker record) {
        return getStore().update(record);
    }

    @Override
    public List<Worker> updateList(List<Worker> list) {
        return getStore().insertList(list);
    }

    private DataStore<Worker> getStore() {
        return DataStore.getStore();
    }
}
