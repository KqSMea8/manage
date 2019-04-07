package com.flight.carryprice.api.service;

import com.flight.carryprice.jdmodel.AddScheduledTaskRequest;
import com.flight.carryprice.jdmodel.AddScheduledTaskResponse;

/**
 * @Description 计划任务API service层
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
public interface ScheduledTaskInfoApiServce {

    /**
     * 插入计划任务
     *
     * @param request 插入计划任务请求实体
     * @return 插入计划任务响应实体
     */
    AddScheduledTaskResponse AddScheduledTask(AddScheduledTaskRequest request);
}
