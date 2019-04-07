package com.flight.carryprice.quartz.service.impl;

import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.enums.AutoEnum;
import com.flight.carryprice.common.enums.CarryPriceTypeEnum;
import com.flight.carryprice.common.enums.StateEnum;
import com.flight.carryprice.common.enums.TaskStatusEnum;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.entity.JdjpCarryPriceTask;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.manager.JdjpFdCarryPriceInfoManager;
import com.flight.carryprice.quartz.service.FdCarryPriceAutoToRedisTaskService;
import com.flight.carryprice.service.JdjpCarryPriceTaskService;
import com.flight.carryprice.service.JdjpFdCarryPriceInfoRedisService;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.airline.Airline;
import com.jd.airplane.infra.response.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 自动刷新FD运价缓存信息
 * @date 2019/3/19 18:08
 * @updateTime
 */
@Service
public class FdCarryPriceAutoToRedisTaskServiceImpl implements FdCarryPriceAutoToRedisTaskService{

    private static Logger LOGGER = Logger.getLogger(FdCarryPriceAutoToRedisTaskServiceImpl.class);

    @Resource
    private JdjpCarryPriceTaskService jdjpCarryPriceTaskService;

    @Resource
    private JdjpFdCarryPriceInfoManager jdjpFdCarryPriceInfoManager;

    @Resource
    private JdjpFdCarryPriceInfoRedisService jdjpFdCarryPriceInfoRedisService;

    /**
     * 功能描述: 自动刷新FD运价缓存信息
     * @param:
     * @return:
     */
    @Override
    public void autoFlushFdToRedis() {

        JdjpCarryPriceTask task = new JdjpCarryPriceTask();
        task.setTaskType(new Byte(AutoEnum.AUTO.getCode()));// 设置任务类型为0-自动
        task.setCarryPriceType(new Byte(CarryPriceTypeEnum.FD.getCode()));// 设置运价类型为FD
        List<JdjpCarryPriceTask> taskList = jdjpCarryPriceTaskService.queryList(task);
        if(CollectionUtils.isEmpty(taskList)){
            LOGGER.info("自动刷新FD运价缓存信息 - 缓存任务为空，直接返回");
            return;
        }

        task = taskList.get(0);
        Integer nextData = task.getNextData();
        Integer operateNo = task.getOperateNo();
        Integer pageNo = nextData/operateNo + 1;
        Integer pageSize = operateNo;
        //查询航线
        Page<Airline> airlinePage = BaseDataUtils.getAirlinePage(pageNo, pageSize, new Airline());
        if(airlinePage == null){
            LOGGER.info("自动刷新FD运价缓存信息 - 查询航线Page为空，直接返回");
            jdjpCarryPriceTaskService.updateStateToOver(task);
            return;
        }
        List<Airline> airlineList = airlinePage.getData();
        if(CollectionUtils.isEmpty(airlineList)){
            LOGGER.info("自动刷新FD运价缓存信息 - 查询航线List为空，直接返回");
            jdjpCarryPriceTaskService.updateStateToOver(task);
            return;
        }
        for(int i=0; i < airlineList.size(); i++){
            Airline airline = airlineList.get(i);
            JdjpFdCarryPriceInfo queryBean = new JdjpFdCarryPriceInfo();
            queryBean.setAirWays(airline.getAirCorpCode());
            queryBean.setDepCode(airline.getDepAirportCode());
            queryBean.setArrCode(airline.getArrAirportCode());
            queryBean.setState(new Byte(StateEnum.VALID.getCode()));
            List<JdjpFdCarryPriceInfo> fdCarryPriceInfoList = jdjpFdCarryPriceInfoManager.queryList(queryBean);

            //更新缓存
            jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(fdCarryPriceInfoList);
        }


        if(pageNo < airlinePage.getTotalPage()){
            //执行中
            task.setNextData(nextData + operateNo);
            task.setTaskStatus(new Byte(TaskStatusEnum.SYNCING.getCode()));
            task.setTaskRemark(TaskStatusEnum.SYNCING.getDesc());
        }else{
            //执行完成
            task.setNextData(nextData + airlineList.size());
            task.setTaskStatus(new Byte(TaskStatusEnum.OVER.getCode()));
            task.setTaskRemark(TaskStatusEnum.OVER.getDesc());
        }

        task.setUpdateTime(new Date());
        task.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
        jdjpCarryPriceTaskService.updateByPrimaryKeySelective(task);
    }


}
