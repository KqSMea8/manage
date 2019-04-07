package com.flight.carryprice.quartz.service.impl;

import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.enums.AutoEnum;
import com.flight.carryprice.common.enums.CarryPriceTypeEnum;
import com.flight.carryprice.common.enums.TaskStatusEnum;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpCarryPriceTask;
import com.flight.carryprice.quartz.service.CarryPriceMissionTaskService;
import com.flight.carryprice.service.JdjpCarryPriceTaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 自动插入FD运价缓存更新任务
 * @date 2019/3/19 17:50
 * @updateTime
 */
@Service
public class CarryPriceMissionTaskServiceImpl implements CarryPriceMissionTaskService{

    private static Logger LOGGER = Logger.getLogger(CarryPriceMissionTaskServiceImpl.class);

    @Resource
    private JdjpCarryPriceTaskService jdjpCarryPriceTaskService;

    /**
     * 功能描述: 自动插入FD运价缓存更新任务
     * @param:
     * @return:
     */
    @Override
    public void addCarryPriceTask(){
        // 校验并插入FD定时更新任务
        JdjpCarryPriceTask task = new JdjpCarryPriceTask();
        task.setTaskType(new Byte(AutoEnum.AUTO.getCode()));// 设置任务类型为0-自动
        task.setCarryPriceType(new Byte(CarryPriceTypeEnum.FD.getCode()));// 设置运价类型为FD
        List<JdjpCarryPriceTask> taskList = jdjpCarryPriceTaskService.queryList(task);
        if(CollectionUtils.isEmpty(taskList)){
            JdjpCarryPriceTask newTask = new JdjpCarryPriceTask();
            newTask.setNextData(CarryPriceConstants.NEXTDATA);
            newTask.setOperateNo(CarryPriceConstants.OPERATENO_AUTO);
            newTask.setCarryPriceType(new Byte(CarryPriceTypeEnum.FD.getCode()));// 0-FD
            newTask.setTaskType(new Byte(AutoEnum.AUTO.getCode()));// 0-自动
            newTask.setTaskStatus(new Byte(TaskStatusEnum.WAITING.getCode()));
            newTask.setTaskRemark(TaskStatusEnum.WAITING.getDesc());
            newTask.setCreateTime(new Date());
            newTask.setUpdateTime(new Date());
            newTask.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
            jdjpCarryPriceTaskService.insertSelective(newTask);
        }else {
            LOGGER.debug("已存在未完成的FD定时更新任务，任务为：" + JacksonUtil.obj2json(taskList.get(0)));
        }
    }

}
