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
import com.wangyu.factory.dao.FactoryDAO;
import com.wangyu.factory.model.Factory;

import java.util.List;

/**
 *
 * @author wangyu
 */
@Component
public class FactoryDAOImpl implements FactoryDAO {

    @Override
    public Factory insert(Factory record) {
        return getStore().insert(record);
    }

    @Override
    public List<Factory> insertList(List<Factory> list) {
        return getStore().insertList(list);
    }

    @Override
    public Factory selectById(Long objectId) {
        return getStore().selectById(Factory.class, objectId);
    }

    @Override
    public List<Factory> selectByFilter(Factory filter) {
        return getStore().selectByFilter(filter);
    }

    @Override
    public Factory update(Factory record) {
        return getStore().update(record);
    }

    @Override
    public List<Factory> updateList(List<Factory> list) {
        return getStore().insertList(list);
    }

    private DataStore<Factory> getStore() {
        return DataStore.getStore();
    }
}
