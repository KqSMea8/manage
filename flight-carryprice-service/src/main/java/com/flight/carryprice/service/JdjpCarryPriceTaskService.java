package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpCarryPriceTask;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 运价任务表
 * @date 2019/3/6 16:00
 * @updateTime
 */
public interface JdjpCarryPriceTaskService extends BaseService<JdjpCarryPriceTask> {

    /**
     * 功能描述: 查询
     *
     * @param:
     * @return:
     */
    List<JdjpCarryPriceTask> queryList(JdjpCarryPriceTask queryBean);

    /**
     * 功能描述: 新增计划任务，如果数据库中有，则中止计划任务再插入
     *
     * @param:
     * @return:
     */
    int insertTask(Byte carryPriceType, Byte taskType, String operator, String airWays, String depCode, String arrCode, String seat);

    /**
     * 功能描述: 更新为执行完成状态
     * @param:
     * @return:
     */
    void updateStateToOver(JdjpCarryPriceTask task);
}