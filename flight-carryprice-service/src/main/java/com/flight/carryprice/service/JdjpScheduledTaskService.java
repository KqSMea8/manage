package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpScheduledTask;

import java.util.List;

/**
 * @Description 计划任务service
 * @Author: qinhaoran1
 * @Date: 2019/3/25
 */
public interface JdjpScheduledTaskService extends BaseService<JdjpScheduledTask>{

    /**
     * 根据任务类型查询计划任务，并更新任务的扫描状态
     * @param taskType  任务类型：1-屏蔽虚假舱位；2-NFDRunALL推送；
     * @param zeroDate  第一次间隔时间
     * @param oneDate   每次间隔时间
     * @param scanCount 扫描最大次数
     * @param pageSize  获取条数
     * @return
     */
    List<JdjpScheduledTask> selectTask(
            Integer taskType, Integer zeroDate, Integer oneDate, Integer scanCount, Integer pageSize);
}
