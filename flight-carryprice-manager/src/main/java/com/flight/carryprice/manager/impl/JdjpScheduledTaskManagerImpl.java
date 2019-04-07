package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpScheduledTask;
import com.flight.carryprice.manager.JdjpScheduledTaskManager;
import com.flight.carryprice.mapper.JdjpScheduledTaskMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 定时任务 manager层实现
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
@Service
public class JdjpScheduledTaskManagerImpl implements JdjpScheduledTaskManager {

    @Resource
    private JdjpScheduledTaskMapper jdjpScheduledTaskMapper;

    @Override
    public Long insertScheduledTask(JdjpScheduledTask jdjpScheduledTask) {
        return jdjpScheduledTaskMapper.insertScheduledTask(jdjpScheduledTask);
    }

    @Override
    public List<JdjpScheduledTask> queryScheduledTaskByType(JdjpScheduledTask task) {
        return jdjpScheduledTaskMapper.queryScheduledTaskByType(task);
    }

    @Override
    public void updateScheduledTaskBatch(List<JdjpScheduledTask> tasks) {
        jdjpScheduledTaskMapper.updateScheduledTaskBatch(tasks);
    }


}
