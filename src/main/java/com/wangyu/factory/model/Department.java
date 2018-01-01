/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日11:02 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.model;

/**
 * 部门实体
 * @author wangyu
 */
public class Department extends BaseModel {

    private String name;

    private Long factoryId;

    private Integer type;

    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static Department create(String name, Integer type) {
        Department department = new Department();
        department.setName(name);
        department.setType(type);
        department.setStatus(Status.NORMAL);
        return department;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public interface Type {
        /**
         * 普通部门
         */
        int PRODUCTION = 0;

        /**
         * 质检部门
         */
        int QUALITY = 1;
    }

    public interface Status {
        /**
         * 正常状态
         */
        int NORMAL = 0;
        /**
         * 解散的
         */
        int DESTROY = 1;
    }

}
