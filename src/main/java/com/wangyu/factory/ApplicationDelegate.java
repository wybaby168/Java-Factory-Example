/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2017年12月29日10:56 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2017, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory;

import com.wangyu.factory.inject.InjectionLoader;
import com.wangyu.factory.model.Department;
import com.wangyu.factory.model.Factory;
import com.wangyu.factory.model.Worker;
import com.wangyu.factory.service.DepartmentService;
import com.wangyu.factory.service.FactoryService;
import com.wangyu.factory.service.ProductService;
import com.wangyu.factory.service.WorkerService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public class ApplicationDelegate extends Application {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        launch(args);
    }

    @Resource
    private FactoryService factoryService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private WorkerService workerService;
    @Resource
    private ProductService productService;

    /**
     * 统一手动注入
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        // 加载注入
        InjectionLoader.loadResources();
        InjectionLoader.loadResource(this);
    }

    @Override
    public void start(Stage primaryStage) {
        // 0. 有一批工人失业，找工作，如下
        String[] productNames = {"配件1", "配件2", "螺丝刀", "手机", "电器",
                "剪刀", "废物", "你啊", "又是一个废物", "就这么着吧",
                "玩玩", "随性", "模型", "塑料", "实体"};
        String[] workerNames = {"张三", "李四", "王五", "赵六", "毛了个先", "毛主席", "党中央", "红太阳", "经理"};
        // 随机产生工人
        Arrays.stream(workerNames).forEach(workerName ->
                workerService.birth(workerName, randomSkill(productNames))
        );
        // 1. 创建工厂
        Factory factory = factoryService.createFactory("太原化工厂");
        // 2. 创建部门
        Department department1 = factoryService.createDepartment(factory, "生产部门1", Department.Type.PRODUCTION);
        Department department2 = factoryService.createDepartment(factory, "生产部门2", Department.Type.PRODUCTION);
        Department qualifyDepartment = factoryService.createDepartment(factory, "质检部门", Department.Type.QUALITY);
        // 3. 雇佣工人，所有人分两拨入职
        List<Worker> workers = workerService.findAll();
        int middlePoint = workers.size() / 2;
        // 第一批工人
        workers.subList(0, middlePoint).forEach(worker ->
                departmentService.hireWorker(department1, worker)
        );
        // 第二批工人
        workers.subList(middlePoint, workers.size()).forEach(worker ->
                departmentService.hireWorker(department2, worker)
        );
        // 4. 入职完毕，开始干活
        workers.forEach(worker -> {
            // 随机产量
            for (int i = 0; i < RandomUtils.nextInt(0,7); i++) {
                workerService.produce(worker);
            }
        });
        // 5. 干活完毕，开始验收
        // 先查部门1， 不合格就处分
        if (!departmentService.qualify(qualifyDepartment, department1)) {
            factoryService.punish(qualifyDepartment, department1);
        }
        // 查查部门2？
        if (!departmentService.qualify(qualifyDepartment, department2)) {
            factoryService.punish(qualifyDepartment, department2);
        }
        // 查完了，看看多少人失业了
        logger.info("【失业的工人】：");
        workerService.findFired().forEach(logger::info);
        // 看看总共多少产品废了
        logger.info("【废掉的产品】：");
        productService.findDestroyed().forEach(logger::info);
        // 执行完了关了吧
        System.exit(0);
    }

    /**
     * 随机取出若干个值
     *
     * @param productNames
     * @return
     */
    private String[] randomSkill(String[] productNames) {
        int length = productNames.length;
        int args1 = RandomUtils.nextInt(0, length);
        int args2 = RandomUtils.nextInt(0, length);
        int start = NumberUtils.min(args1, args2);
        int end = NumberUtils.max(args1, args2);
        if (end == start && end == length) {
            start--;
        }
        return ArrayUtils.subarray(productNames, start, end);
    }
}
