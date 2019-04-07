package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.enums.ScanStatusEnum;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpScheduledTask;
import com.flight.carryprice.manager.JdjpScheduledTaskManager;
import com.flight.carryprice.service.JdjpScheduledTaskService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description 计划任务service实现
 * @Author: qinhaoran1
 * @Date: 2019/3/25
 */
@Service
public class JdjpScheduledTaskServiceImpl extends BaseServiceImpl<JdjpScheduledTask> implements JdjpScheduledTaskService {

    private static final Logger LOGGER = Logger.getLogger(JdjpScheduledTaskServiceImpl.class);

    @Resource
    private JdjpScheduledTaskManager jdjpScheduledTaskManager;

    @Transactional
    @Override
    public List<JdjpScheduledTask> selectTask(
            Integer taskType, Integer zeroDate, Integer oneDate, Integer scanCount, Integer pageSize) {

        final JdjpScheduledTask scheduledTask = new JdjpScheduledTask();
        final Date date = new Date();
        scheduledTask.setTaskType(taskType);
        scheduledTask.setScanCount(scanCount);
        scheduledTask.setPageSize(pageSize);
        scheduledTask.setZeroDate(null != zeroDate ? DateUtil.getCalulationDate(date, -zeroDate) : null);
        scheduledTask.setOneDate(null != oneDate ? DateUtil.getCalulationDate(date, -oneDate) : null);
        // 根据任务类型查询待执行的计划任务
        LOGGER.info("query scheduledTask by type, queryParam = " + JacksonUtil.obj2json(scheduledTask));
        final List<JdjpScheduledTask> tasks = jdjpScheduledTaskManager.queryScheduledTaskByType(scheduledTask);
        LOGGER.info("query scheduledTask by type, result = " + JacksonUtil.obj2json(tasks));
        for (JdjpScheduledTask task : tasks) {
            task.setScanStatus(ScanStatusEnum.SCANNING.getStatus());
            task.setScanTime(date);
            task.setScanCount(task.getScanCount() + 1);
            task.setUpdateTime(null);
        }
        // 将取出的计划任务状态改为“处理中”，同时修改扫描次数
        jdjpScheduledTaskManager.updateScheduledTaskBatch(tasks);
        LOGGER.info("update scheduledTask batch success!");
        return tasks;
    }
}
