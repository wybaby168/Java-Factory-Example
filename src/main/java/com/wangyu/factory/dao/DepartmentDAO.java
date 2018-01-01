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

import com.wangyu.factory.model.Department;

import java.util.List;

/**
 *
 * @author wangyu
 */
public interface DepartmentDAO {

    Department insert(Department record);

    List<Department> insertList(List<Department> list);

    Department selectById(Long objectId);

    List<Department> selectByFilter(Department filter);

    Department update(Department record);

    List<Department> updateList(List<Department> list);
}
