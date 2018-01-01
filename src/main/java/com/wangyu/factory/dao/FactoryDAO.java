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

import com.wangyu.factory.model.Factory;

import java.util.List;

/**
 *
 * @author wangyu
 */
public interface FactoryDAO {

    Factory insert(Factory record);

    List<Factory> insertList(List<Factory> list);

    Factory selectById(Long objectId);

    List<Factory> selectByFilter(Factory filter);

    Factory update(Factory record);

    List<Factory> updateList(List<Factory> list);
}
