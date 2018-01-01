/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日14:24 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.service;

import com.wangyu.factory.annotation.Service;
import com.wangyu.factory.dao.ProductDAO;
import com.wangyu.factory.dao.WorkerDAO;
import com.wangyu.factory.model.Department;
import com.wangyu.factory.model.Product;
import com.wangyu.factory.model.Worker;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangyu
 */
@Service
public class WorkerService {

    @Resource
    private WorkerDAO workerDAO;
    @Resource
    private ProductDAO productDAO;

    /**
     * 工人诞生的时候只有名字和技能，不和任何东西关联
     *
     * @param name 名字
     * @return 工人
     */
    public Worker birth(String name, String[] productNames) {
        Worker worker = Worker.create(name, productNames);
        return workerDAO.insert(worker);
    }

    public List<Worker> findAll() {
        List<Worker> workers = workerDAO.selectByFilter(new Worker());
        System.out.println("找到了一批工人：");
        workers.forEach(System.out::println);
        return workers;
    }

    public List<Worker> findFired() {
        Worker filter = new Worker();
        filter.setStatus(Worker.Status.FIRED);
        return workerDAO.selectByFilter(filter);
    }

    public List<Worker> findByDepartment(Department department) {
        Worker filter = new Worker();
        filter.setDepartmentId(department.getObjectId());
        return workerDAO.selectByFilter(filter);
    }

    /**
     * 生产产品，生产的产品是随机的，从这个工人可生产的产品名里产生，产品质量随机
     *
     * @param worker 工人
     * @return 产品
     */
    public Product produce(Worker worker) {
        // 生产的合格性
        boolean quality = RandomUtils.nextBoolean();
        if (ArrayUtils.isNotEmpty(worker.getProductNames())) {
            // 产品名
            String name = worker.getProductNames()[RandomUtils.nextInt(0, worker.getProductNames().length)];
            // 产品
            Product product = Product.create(name);
            product.setProducer(worker.getObjectId());
            product.setQuality(quality);
            System.out.println("【工人生产】--" + worker.getName() + "生产了产品:" + name + "质量：" + (quality ? "合格" : "不合格"));
            return productDAO.insert(product);
        }
        return null;
    }
}
