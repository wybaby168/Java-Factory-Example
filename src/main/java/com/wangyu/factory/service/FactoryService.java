/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日14:22 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.service;

import com.wangyu.factory.annotation.Service;
import com.wangyu.factory.dao.DepartmentDAO;
import com.wangyu.factory.dao.FactoryDAO;
import com.wangyu.factory.dao.ProductDAO;
import com.wangyu.factory.model.Department;
import com.wangyu.factory.model.Factory;
import com.wangyu.factory.model.Product;
import com.wangyu.factory.model.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangyu
 */
@Service
public class FactoryService {

    private static final Logger logger = LogManager.getLogger();

    @Resource
    private FactoryDAO factoryDAO;
    @Resource
    private DepartmentDAO departmentDAO;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private ProductDAO productDAO;
    @Resource
    private ProductService productService;

    /**
     * 创建工厂
     *
     * @param factoryName
     * @return
     */
    public Factory createFactory(String factoryName) {
        Factory factory = Factory.create(factoryName);
        logger.info("【工厂创建】" + factoryName + "创建了");
        return factoryDAO.insert(factory);
    }

    /**
     * 创建部门，指定部门名字，职能
     *
     * @param factory
     * @param name
     * @return
     */
    public Department createDepartment(Factory factory, String name, Integer type) {
        Department department = Department.create(name, type);
        department.setFactoryId(factory.getObjectId());
        logger.info("【部门创建】" + name + "由工厂：" + factory.getName() + "创建了");
        return departmentDAO.insert(department);
    }

    public Department cancelDepartment(Factory factory, Department department) {
        logger.info("【解散部门】工厂：" + factory.getName() + "解散了部门" + department.getName());
        department.setStatus(Department.Status.DESTROY);
        return departmentDAO.update(department);
    }

    public Product destroyProduct(Product product) {
        logger.info("【摧毁产品】摧毁不合格品" + product.getName());
        product.setStatus(Product.Status.DESTROYED);
        return productDAO.update(product);
    }

    /**
     * 处分一个部门，心情不好可以主动处分
     *
     * @param department 部门
     */
    public void punish(Department qualifyDepartment, Department department) {
        logger.info("【检查结果】" + department.getName() + "检验不合格！开始惩罚！");
        // 摧毁不合格产品
        productService.findByDepartment(department)
                .stream().filter(product -> !product.isQuality())
                .forEach(this::destroyProduct);
        // 解雇工人
        List<Worker> partWorkers = departmentService.findWorkerByDepartment(department);
        partWorkers.forEach(worker -> departmentService.fireWorker(qualifyDepartment, worker));
        // 解散部门
        Factory factory = factoryDAO.selectById(department.getFactoryId());
        cancelDepartment(factory, department);
    }
}
