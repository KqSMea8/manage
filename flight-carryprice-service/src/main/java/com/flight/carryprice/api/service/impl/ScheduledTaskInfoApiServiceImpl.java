package com.flight.carryprice.api.service.impl;

import com.flight.carryprice.api.service.ScheduledTaskInfoApiServce;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.constant.ResponseCodeEnum;
import com.flight.carryprice.entity.JdjpScheduledTask;
import com.flight.carryprice.jdmodel.AddScheduledTaskRequest;
import com.flight.carryprice.jdmodel.AddScheduledTaskResponse;
import com.flight.carryprice.manager.JdjpScheduledTaskManager;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 计划任务API service层实现
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
@Service
public class ScheduledTaskInfoApiServiceImpl implements ScheduledTaskInfoApiServce {

    private static final Logger LOGGER = Logger.getLogger(ScheduledTaskInfoApiServiceImpl.class);

    @Resource
    private JdjpScheduledTaskManager jdjpScheduledTaskManager;

    @Override
    public AddScheduledTaskResponse AddScheduledTask(AddScheduledTaskRequest request) {
        AddScheduledTaskResponse response = checkParams(request);
        if (!ResponseCodeEnum.SUCCESS.getCode().equals(response.getResponseCode())) {
            return response;
        }
        JdjpScheduledTask scheduledTask = new JdjpScheduledTask();
        LOGGER.info("insert scheduledTask: " + JacksonUtil.obj2json(scheduledTask));
        // 封装要插入的计划任务
        BeanUtils.copyProperties(request, scheduledTask);
        // 插入计划任务
        final Long count = jdjpScheduledTaskManager.insertScheduledTask(scheduledTask);
        if (1 != count) {
            response.setResponseCode(ResponseCodeEnum.EXCEPTION.getCode());
            response.setResponseCode(ResponseCodeEnum.EXCEPTION.getDesc());
            LOGGER.error("insert scheduledTask fail! scheduledTask: " + JacksonUtil.obj2json(scheduledTask));
        }
        return response;
    }

    /**
     * 请求参数校验
     */
    private AddScheduledTaskResponse checkParams(AddScheduledTaskRequest request) {
        AddScheduledTaskResponse response = new AddScheduledTaskResponse();
        response.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setResponseMessage(ResponseCodeEnum.SUCCESS.getDesc());
        try {
            Preconditions.checkNotNull(request, "请求参数为空！request = " + request);

            Integer taskType = request.getTaskType();
            Preconditions.checkArgument(null != taskType, "计划任务类型错误！taskType = " + taskType);

            // 备用参数校验逻辑根据实际使用情况完善
            if (StringUtils.isNotEmpty(request.getParam1())) {
                Preconditions.checkArgument(true, "备用参数校验失败！param1 = " + request.getParam1());
            }
            if (StringUtils.isNotEmpty(request.getParam2())) {
                Preconditions.checkArgument(true, "备用参数校验失败！param2 = " + request.getParam2());
            }
            if (StringUtils.isNotEmpty(request.getParam3())) {
                Preconditions.checkArgument(true, "备用参数校验失败！param3 = " + request.getParam3());
            }
        } catch (Exception e) {
            response.setResponseCode(ResponseCodeEnum.PARAM_ERROR.getCode());
            response.setResponseMessage(e.getMessage());
            LOGGER.error("request param check fail！errorMsg = " + e.getMessage());
        }
        return response;
    }

}
