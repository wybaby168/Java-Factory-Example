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
import com.wangyu.factory.dao.WorkerDAO;
import com.wangyu.factory.model.Department;
import com.wangyu.factory.model.Product;
import com.wangyu.factory.model.Worker;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangyu
 */
@Service
public class DepartmentService {

    @Resource
    private WorkerDAO workerDAO;
    @Resource
    private FactoryService factoryService;
    @Resource
    private ProductService productService;

    /**
     * 雇佣工人
     *
     * @param department
     * @param worker
     */
    public void hireWorker(Department department, Worker worker) {
        worker.setStatus(Worker.Status.NORMAL);
        worker.setDepartmentId(department.getObjectId());
        worker.setFactoryId(department.getFactoryId());
        System.out.println("【工人入职】" + worker.getName() + "入职" + department.getName() + "部门！");
        workerDAO.update(worker);
    }

    /**
     * 解雇工人
     *
     * @param department
     * @param worker
     */
    public void fireWorker(Department department, Worker worker) {
        System.out.println("【解雇工人】质监部门" + department.getName() + "解雇了" + worker.getName());
        worker.setStatus(Worker.Status.FIRED);
        workerDAO.update(worker);
    }

    public List<Worker> findWorkerByDepartment(Department department) {
        Long departmentId = department.getObjectId();
        Worker filter = new Worker();
        filter.setDepartmentId(departmentId);
        return workerDAO.selectByFilter(filter);
    }

    /**
     * 检验部门的检验
     *
     * @param department
     * @param targetDepartment
     * @return
     */
    public boolean qualify(Department department, Department targetDepartment) {
        if (department.getType() == Department.Type.QUALITY) {
            System.out.println("【质监部门】质监部门：" + department.getName() + "正在检查" + targetDepartment.getName() + "部门");
            // 检查的话，就是检查合格个数
            List<Product> products = productService.findByDepartment(targetDepartment);
            // 产品不为空时，找，否则没产品，直接就算通过了，然后才检测个数
            boolean result = CollectionUtils.isEmpty(products) ||
                    products.stream().filter(product -> !product.isQuality()).count() < 5;
            if (result) {
                System.out.println("【质监部门】" + targetDepartment.getName() + "检验通过！");
            }
            return result;
        } else {
            throw new RuntimeException("不是质检部门，哪能检测？");
        }
    }


}
