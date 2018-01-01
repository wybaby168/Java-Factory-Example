/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日14:12 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.dao;

import com.wangyu.factory.model.Product;

import java.util.List;

/**
 *
 * @author wangyu
 */
public interface ProductDAO {

    Product insert(Product record);

    List<Product> insertList(List<Product> list);

    Product selectById(Long objectId);

    List<Product> selectByFilter(Product filter);

    Product update(Product record);

    List<Product> updateList(List<Product> list);
}
