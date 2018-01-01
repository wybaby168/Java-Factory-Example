/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日11:03 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.model;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 工人实体
 *
 * @author wangyu
 */
public class Worker extends BaseModel {

    private String name;

    private Long factoryId;

    private Long departmentId;

    private Integer status;

    /**
     * 工人可产出的产品名
     */
    private String[] productNames;

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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String[] getProductNames() {
        return productNames;
    }

    public void setProductNames(String[] productNames) {
        this.productNames = productNames;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static Worker create(String name, String[] productNames) {
        Worker worker = new Worker();
        worker.setName(name);
        if (ArrayUtils.isNotEmpty(productNames)) {
           worker.setProductNames(productNames);
        }
        worker.setStatus(Status.FIRED);
        return worker;
    }

    public interface Status {
        /**
         * 在职
         */
        int NORMAL = 0;

        /**
         * 被解雇
         */
        int FIRED = 1;
    }
}
