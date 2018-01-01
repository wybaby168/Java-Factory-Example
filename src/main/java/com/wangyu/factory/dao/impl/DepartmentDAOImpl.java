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
import com.wangyu.factory.dao.DepartmentDAO;
import com.wangyu.factory.model.Department;

import java.util.List;

/**
 *
 * @author wangyu
 */
@Component
public class DepartmentDAOImpl implements DepartmentDAO {

    @Override
    public Department insert(Department record) {
        return getStore().insert(record);
    }

    @Override
    public List<Department> insertList(List<Department> list) {
        return getStore().insertList(list);
    }

    @Override
    public Department selectById(Long objectId) {
        return getStore().selectById(Department.class, objectId);
    }

    @Override
    public List<Department> selectByFilter(Department filter) {
        return getStore().selectByFilter(filter);
    }

    @Override
    public Department update(Department record) {
        return getStore().update(record);
    }

    @Override
    public List<Department> updateList(List<Department> list) {
        return getStore().insertList(list);
    }

    private DataStore<Department> getStore() {
        return DataStore.getStore();
    }
}
