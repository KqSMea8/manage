package com.flight.carryprice.quartz.service.impl;

import com.flight.carryprice.common.enums.SyncStatusEnum;
import com.flight.carryprice.common.enums.TaskTypeEnum;
import com.flight.carryprice.entity.JdjpNfdUpdatePolicy;
import com.flight.carryprice.entity.JdjpScheduledTask;
import com.flight.carryprice.manager.JdjpNfdUpdatePolicyManager;
import com.flight.carryprice.manager.JdjpScheduledTaskManager;
import com.flight.carryprice.quartz.service.NfdManAddUpdatePolicyTaskService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 扫描NFD更新策略任务实现
 * @Author: qinhaoran1
 * @Date: 2019/3/21
 */
@Service
public class NfdManAddUpdatePolicyTaskServiceImpl implements NfdManAddUpdatePolicyTaskService {

    private static final Logger LOGGER = Logger.getLogger(NfdManAddUpdatePolicyTaskServiceImpl.class);

    @Resource
    private JdjpNfdUpdatePolicyManager jdjpNfdUpdatePolicyManager;
    @Resource
    private JdjpScheduledTaskManager jdjpScheduledTaskManager;

    @Override
    public void nfdAddUpdatePolicyJob() {
        try {
            // 查询未同步的nfd运价更新策略
            List<JdjpNfdUpdatePolicy> policyList = jdjpNfdUpdatePolicyManager.queryNfdUpdatePolicys();
            for (JdjpNfdUpdatePolicy policy : policyList) {
                // 更新nfd运价更新策略的同步状态
                policy.setSyncStatus(Byte.parseByte(SyncStatusEnum.SYNCING.getCode()));
                policy.setRemark(SyncStatusEnum.SYNCING.getDesc());
                jdjpNfdUpdatePolicyManager.updateByPrimaryKeySelective(policy);
                // 插入计划任务
                final JdjpScheduledTask scheduledTask = new JdjpScheduledTask();
                scheduledTask.setTaskType(TaskTypeEnum.PUSH_NFDRUNALL.getCode());
                scheduledTask.setParam1(String.valueOf(policy.getId()));
                jdjpScheduledTaskManager.insertScheduledTask(scheduledTask);
            }
        } catch (Exception e) {
            LOGGER.error("处理人工新增的nfd更新策略返回的异常信息：" + e.getMessage(), e);
        }
    }
}
