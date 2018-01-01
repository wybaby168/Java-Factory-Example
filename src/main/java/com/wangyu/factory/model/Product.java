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

/**
 * 产品实体
 * @author wangyu
 */
public class Product extends BaseModel {

    private String name;

    private Integer status;

    private Long producer;

    private boolean quality;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getProducer() {
        return producer;
    }

    public void setProducer(Long producer) {
        this.producer = producer;
    }

    public boolean isQuality() {
        return quality;
    }

    public void setQuality(boolean quality) {
        this.quality = quality;
    }

    public static Product create(String name) {
        Product product = new Product();
        product.setName(name);
        product.setStatus(Status.NORMAL);
        return product;
    }

    public interface Status {
        /**
         * 正常状态
         */
        int NORMAL = 1;

        /**
         * 被销毁的
         */
        int DESTROYED = 2;
    }
}
