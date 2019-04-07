package com.jd;

import com.flight.carryprice.quartz.service.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 单元测试Test(这里用一句话描述这个方法的作用)
 * @date 2019/4/1 16:27
 * @updateTime
 */
public class CarryPriceQuartzTest {
    private static Logger logger = Logger.getLogger(CarryPriceQuartzTest.class);

    /**
     * 功能描述: 自动维护运价缓存任务(自动插入FD运价缓存更新任务)
     * @param:
     * @return:
     */
    @Test
    public void testAddCarryPriceTask(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        CarryPriceMissionTaskService service = (CarryPriceMissionTaskService)context.getBean("carryPriceMissionTaskServiceImpl");
        service.addCarryPriceTask();
    }


    /**
     * 功能描述: 自动刷新FD运价缓存信息
     * @param:
     * @return: 
     */
    @Test
    public void testAutoFlushFdToRedis(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        FdCarryPriceAutoToRedisTaskService service = (FdCarryPriceAutoToRedisTaskService)context.getBean("fdCarryPriceAutoToRedisTaskServiceImpl");
        service.autoFlushFdToRedis();
    }

    /**
     * 功能描述: 手动刷新FD运价缓存信息
     * @param:
     * @return:
     */
    @Test
    public void testManualFlushFdToRedis(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        FdCarryPriceManualToRedisTaskService service = (FdCarryPriceManualToRedisTaskService)context.getBean("fdCarryPriceManualToRedisTaskServiceImpl");
        service.manualFlushFdToRedis();
    }

    /**
     * 功能描述: 自动插入FD运价更新策略
     * @param:
     * @return:
     */
    @Test
    public void testAutoAddFdUpdatePolicy(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        FdUpdatePolicyAutoAddTaskService service = (FdUpdatePolicyAutoAddTaskService)context.getBean("fdUpdatePolicyAutoAddTaskServiceImpl");
        service.autoAddFdUpdatePolicy();
    }

    /**
     * 功能描述: fd运价更新
     * @param:
     * @return:
     */
    @Test
    public void testautoAddFdUpdatePolicy(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        FdCarryPriceUpdateByPolicyTaskService service = (FdCarryPriceUpdateByPolicyTaskService)context.getBean("fdCarryPriceUpdateByPolicyTaskServiceImpl");
        service.updateFdCarryPriceByPolicy();
    }



}
