package com.flight.carryprice.quartz.service.impl;

import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.enums.AirlineTypeEnum;
import com.flight.carryprice.common.enums.SyncStatusEnum;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.RegularUtil;
import com.flight.carryprice.entity.JdjpFdUpdatePolicy;
import com.flight.carryprice.entity.JdjpRunparameters;
import com.flight.carryprice.manager.JdjpFdUpdatePolicyManager;
import com.flight.carryprice.manager.JdjpRunparametersManager;
import com.flight.carryprice.quartz.service.FdUpdatePolicyAutoAddTaskService;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.airline.Airline;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 自动插入FD更新策略
 * @date 2019/3/20 10:04
 * @updateTime
 */
@Service
public class FdUpdatePolicyAutoAddTaskServiceImpl implements FdUpdatePolicyAutoAddTaskService {

    private static Logger LOGGER = Logger.getLogger(FdUpdatePolicyAutoAddTaskServiceImpl.class);

    @Resource
    private JdjpRunparametersManager jdjpRunparametersManager;

    @Resource
    private JdjpFdUpdatePolicyManager jdjpFdUpdatePolicyManager;

    /**
     * 功能描述: 自动插入FD更新策略
     * @param:
     * @return:
     */
    @Override
    public void autoAddFdUpdatePolicy() {
        //当前时间
        Date date = new Date();
        int currentDay = DateUtil.dateOfDay(date);
        List<JdjpFdUpdatePolicy> policyList = new ArrayList<>();
        //插入普通FD策略
        JdjpRunparameters runparameters = jdjpRunparametersManager.queryByName(CarryPriceConstants.FD_PLAN_QUARTZ_TIME);
        if (runparameters != null && RegularUtil.regexNumberSplit(runparameters.getValue())) {
            String[] updateDays = runparameters.getValue().split(",");
            for (int i = 0; i < updateDays.length; i++) {
                if (Integer.valueOf(updateDays[i]) == currentDay) {
                    JdjpFdUpdatePolicy policy = new JdjpFdUpdatePolicy();
                    policy.setAirWays("***");
                    policy.setDepCode("***");
                    policy.setArrCode("***");
                    // 计划执行更新策略时间（更新时间）'当前时间+1个小时(产品提测需求)
                    String dateStr = DateUtil.dateAddHours(date, 1);
                    policy.setPlanQuartzTime(DateUtil.stringToDate(dateStr, DateUtil.DATE_FMT3));
                    policy.setAirlineType(new Byte(AirlineTypeEnum.ORDINARY.getCode()));
                    policy.setSyncStatus(new Byte(SyncStatusEnum.WAITING.getCode()));
                    policy.setRemark(SyncStatusEnum.WAITING.getDesc());// '同步状态 0-未同步 1-同步中 2-同步完成'
                    policy.setCreateTime(new Date());
                    policy.setUpdateTime(new Date());
                    policy.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
                    policyList.add(policy);
                }
            }
        }

        //插入热门FD策略
        JdjpRunparameters hotRunparameters = jdjpRunparametersManager.queryByName(CarryPriceConstants.FD_HOT_PLAN_QUARTZ_TIME);
        if (hotRunparameters != null && RegularUtil.regexNumberSplit(hotRunparameters.getValue())) {
            //查询航线表中热门的FD航线
            Airline record = new Airline();
            record.setFdType(Integer.valueOf(AirlineTypeEnum.POPULAR.getCode()));
            List<Airline> airlineList = BaseDataUtils.getAirlineListWithCondition(record);
            if(CollectionUtils.isNotEmpty(airlineList)){
                String[] hotUpdateDays = hotRunparameters.getValue().split(",");
                for (int i = 0; i < hotUpdateDays.length; i++) {
                    if (Integer.valueOf(hotUpdateDays[i]) == currentDay) {
                        for (Airline airline : airlineList) {
                            JdjpFdUpdatePolicy policy = new JdjpFdUpdatePolicy();
                            policy.setAirWays(airline.getAirCorpCode());
                            policy.setDepCode(airline.getDepAirportCode());
                            policy.setArrCode(airline.getArrAirportCode());
                            // 计划执行更新策略时间（更新时间）'当前时间+1个小时(产品提测需求)
                            String dateStr = DateUtil.dateAddHours(date, 1);
                            policy.setPlanQuartzTime(DateUtil.stringToDate(dateStr, DateUtil.DATE_FMT3));
                            policy.setAirlineType(new Byte(AirlineTypeEnum.POPULAR.getCode()));
                            policy.setSyncStatus(new Byte(SyncStatusEnum.WAITING.getCode()));
                            policy.setRemark(SyncStatusEnum.WAITING.getDesc());// '同步状态 0-未同步 1-同步中 2-同步完成'
                            policy.setCreateTime(new Date());
                            policy.setUpdateTime(new Date());
                            policy.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
                            policyList.add(policy);
                        }
                    }
                }
            }
        }
        if(CollectionUtils.isNotEmpty(policyList)){
            jdjpFdUpdatePolicyManager.insertBatch(policyList);
        }
    }
}
