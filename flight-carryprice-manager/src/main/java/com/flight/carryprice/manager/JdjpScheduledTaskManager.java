package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpScheduledTask;

import java.util.List;

/**
 * @Description 定时任务 manager层
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
public interface JdjpScheduledTaskManager {

    /**
     * 插入定时任务
     *
     * @param jdjpScheduledTask
     * @return
     */
    Long insertScheduledTask(JdjpScheduledTask jdjpScheduledTask);

    /**
     * 根据任务类型查询计任务
     *
     * @param task 查询条件
     * @return
     */
    List<JdjpScheduledTask> queryScheduledTaskByType(JdjpScheduledTask task);

    /**
     * 批量更新tasks
     * @param tasks
     */
    void updateScheduledTaskBatch(List<JdjpScheduledTask> tasks);

}
