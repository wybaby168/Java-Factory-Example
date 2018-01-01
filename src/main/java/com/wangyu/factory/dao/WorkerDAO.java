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

import com.wangyu.factory.model.Worker;

import java.util.List;

/**
 *
 * @author wangyu
 */
public interface WorkerDAO {

    Worker insert(Worker record);

    List<Worker> insertList(List<Worker> list);

    Worker selectById(Long objectId);

    List<Worker> selectByFilter(Worker filter);

    Worker update(Worker record);

    List<Worker> updateList(List<Worker> list);
}
