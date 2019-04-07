package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.enums.TaskStatusEnum;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.entity.JdjpCarryPriceTask;
import com.flight.carryprice.manager.JdjpCarryPriceTaskManager;
import com.flight.carryprice.service.JdjpCarryPriceTaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 运价任务表
 * @date 2019/3/6 16:00
 * @updateTime
 */
@Service
public class JdjpCarryPriceTaskServiceImpl extends BaseServiceImpl<JdjpCarryPriceTask> implements JdjpCarryPriceTaskService {

    private final static Logger LOGGER = Logger.getLogger(JdjpCarryPriceTaskServiceImpl.class);

    @Resource
    private JdjpCarryPriceTaskManager jdjpCarryPriceTaskManager;

    /**
     * 功能描述: 查询
     * @param:
     * @return:
     */
    @Override
    public List<JdjpCarryPriceTask> queryList(JdjpCarryPriceTask queryBean) {
        return jdjpCarryPriceTaskManager.queryList(queryBean);
    }

    /**
     * 功能描述: 新增计划任务，如果数据库中有，则中止计划任务再插入
     * @param:
     * @return:
     */
    @Override
    @Transactional
    public int insertTask(Byte carryPriceType, Byte taskType, String operator, String airWays, String depCode, String arrCode, String seat){
        //根据条件查询计划任务
        JdjpCarryPriceTask task = new JdjpCarryPriceTask();
        task.setCarryPriceType(carryPriceType);//运价类型(0:FD，1:NFD，2:SSD)
        task.setTaskType(taskType);// 0:自动，1:手动
        List<JdjpCarryPriceTask> list = jdjpCarryPriceTaskManager.queryList(task);
        if(CollectionUtils.isNotEmpty(list)){
            Byte taskStatus = new Byte(TaskStatusEnum.INTERRUPT.getCode());// 0待执行、1执行中、2中断、3执行完成
            for (JdjpCarryPriceTask carryPriceTask : list) {
                carryPriceTask.setTaskStatus(taskStatus);
                carryPriceTask.setTaskRemark(TaskStatusEnum.INTERRUPT.getDesc());
                this.updateByPrimaryKeySelective(carryPriceTask);
            }
        }

        String queryCondition = formQueryCondition(airWays, depCode, arrCode, seat);

        JdjpCarryPriceTask insertTask = new JdjpCarryPriceTask();

        insertTask.setQueryCondition(queryCondition);// 查询条件，格式如：航司-CA;出发-PEK;到达-SHA;舱位-Y
        insertTask.setNextData(CarryPriceConstants.NEXTDATA);// 下次更新开始序号
        insertTask.setOperateNo(CarryPriceConstants.OPERATENO_MANUAL);// 一次批量操作的条数
        insertTask.setCarryPriceType(carryPriceType);// 运价类型(0:FD，1:NFD，2:SSD)
        insertTask.setTaskType(taskType);// 务类型(0:手工，1定时)
        insertTask.setTaskStatus(new Byte(TaskStatusEnum.WAITING.getCode()));// 状态(0待执行、1执行中、2中断、3执行完成)
        insertTask.setTaskRemark(TaskStatusEnum.WAITING.getDesc());
        insertTask.setCreateTime(new Date());
        insertTask.setUpdateTime(new Date());
        insertTask.setOperator(operator);// 操作人
        return this.insertSelective(insertTask);
    }

    /**
     * 功能描述: 更新为执行完成状态
     * @param:
     * @return:
     */
    @Override
    public void updateStateToOver(JdjpCarryPriceTask task){
        task.setTaskStatus(new Byte(TaskStatusEnum.OVER.getCode()));
        task.setTaskRemark(TaskStatusEnum.OVER.getDesc());
        task.setUpdateTime(new Date());
        task.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
        this.updateByPrimaryKeySelective(task);
    }

    /**
     * 功能描述: 封装查询条件
     * @param:
     * @return:
     */
    private String formQueryCondition(String airWays, String depCode, String arrCode, String seat){
        //拼接条件
        StringBuffer querySqlBuffer = new StringBuffer();
        if (StringUtils.isNotEmpty(airWays)) {
            querySqlBuffer.append(CarryPriceConstants.AIRWAYS)
                    .append(CarryPriceConstants.CONNECTPARAM1)
                    .append(airWays)
                    .append(CarryPriceConstants.CONNECTPARAM2);
        }
        if (StringUtils.isNotEmpty(depCode)) {
            querySqlBuffer.append(CarryPriceConstants.DEPCODE)
                    .append(CarryPriceConstants.CONNECTPARAM1)
                    .append(depCode)
                    .append(CarryPriceConstants.CONNECTPARAM2);
        }
        if (StringUtils.isNotEmpty(arrCode)) {
            querySqlBuffer.append(CarryPriceConstants.ARRCODE)
                    .append(CarryPriceConstants.CONNECTPARAM1)
                    .append(arrCode)
                    .append(CarryPriceConstants.CONNECTPARAM2);
        }
        if (StringUtils.isNotEmpty(seat)) {
            querySqlBuffer.append(CarryPriceConstants.SEAT)
                    .append(CarryPriceConstants.CONNECTPARAM1)
                    .append(seat.toUpperCase())
                    .append(CarryPriceConstants.CONNECTPARAM2);
        }
        String queryCondition = querySqlBuffer.toString();
        if (StringUtils.isNotEmpty(queryCondition)) {
            queryCondition = queryCondition.substring(0, queryCondition.length() - 1);
        }
        return queryCondition;
    }

}
