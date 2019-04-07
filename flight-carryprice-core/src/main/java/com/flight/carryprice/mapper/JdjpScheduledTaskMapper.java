package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpScheduledTask;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Description 定时任务表
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
public interface JdjpScheduledTaskMapper extends Mapper<JdjpScheduledTask> {

    /**
     * 插入定时任务
     *
     * @param jdjpScheduledTask
     * @return
     */
    Long insertScheduledTask(JdjpScheduledTask jdjpScheduledTask);

    /**
     * 根据任务类型查询计划任务
     * @param jdjpScheduledTask
     * @return
     */
    List<JdjpScheduledTask> queryScheduledTaskByType(JdjpScheduledTask jdjpScheduledTask);

    /**
     * 批量更新tasks
     * @param tasks
     */
    void updateScheduledTaskBatch(List<JdjpScheduledTask> tasks);
}
