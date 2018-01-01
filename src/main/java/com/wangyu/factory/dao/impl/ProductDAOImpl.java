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
import com.wangyu.factory.dao.ProductDAO;
import com.wangyu.factory.model.Product;

import java.util.List;

/**
 *
 * @author wangyu
 */
@Component
public class ProductDAOImpl implements ProductDAO {

    @Override
    public Product insert(Product record) {
        return getStore().insert(record);
    }

    @Override
    public List<Product> insertList(List<Product> list) {
        return getStore().insertList(list);
    }

    @Override
    public Product selectById(Long objectId) {
        return getStore().selectById(Product.class, objectId);
    }

    @Override
    public List<Product> selectByFilter(Product filter) {
        return getStore().selectByFilter(filter);
    }

    @Override
    public Product update(Product record) {
        return getStore().update(record);
    }

    @Override
    public List<Product> updateList(List<Product> list) {
        return getStore().insertList(list);
    }

    private DataStore<Product> getStore() {
        return DataStore.getStore();
    }
}
