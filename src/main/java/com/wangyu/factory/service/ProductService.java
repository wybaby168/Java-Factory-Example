/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日14:25 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.service;

import com.wangyu.factory.annotation.Service;
import com.wangyu.factory.dao.ProductDAO;
import com.wangyu.factory.model.Department;
import com.wangyu.factory.model.Product;
import com.wangyu.factory.model.Worker;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author wangyu
 */
@Service
public class ProductService {

    @Resource
    private ProductDAO productDAO;
    @Resource
    private WorkerService workerService;

    /**
     * 找下被摧毁的
     * @return
     */
    public List<Product> findDestroyed() {
        Product filter = new Product();
        filter.setStatus(Product.Status.DESTROYED);
        return productDAO.selectByFilter(filter);
    }

    public List<Product> findByWorker(Worker worker) {
        Product filter = new Product();
        filter.setProducer(worker.getObjectId());
        return productDAO.selectByFilter(filter);
    }

    /**
     * 先查人，再查产品
     * @param department
     * @return
     */
    public List<Product> findByDepartment(Department department) {
        List<Worker> workers = workerService.findByDepartment(department);
        return workers.stream()
                .flatMap(worker -> findByWorker(worker).stream())
                .collect(Collectors.toList());
    }
}
