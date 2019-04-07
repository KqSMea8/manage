package com.flight.carryprice.quartz.service.impl;

import com.flight.carryprice.common.constant.GDSConstants;
import com.flight.carryprice.common.enums.CodeEnums;
import com.flight.carryprice.common.enums.GDSEnums;
import com.flight.carryprice.common.enums.ScanStatusEnum;
import com.flight.carryprice.common.enums.SyncStatusEnum;
import com.flight.carryprice.common.enums.TaskTypeEnum;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpNfdUpdatePolicy;
import com.flight.carryprice.entity.JdjpScheduledTask;
import com.flight.carryprice.esutil.ESUtils;
import com.flight.carryprice.gds.service.GdsRequestService;
import com.flight.carryprice.manager.JdjpNfdUpdatePolicyManager;
import com.flight.carryprice.quartz.service.NfdRunAllScheduleTaskService;
import com.flight.carryprice.service.JdjpScheduledTaskService;
import com.jd.es.client.ESClientOperations;
import com.jd.gds.dto.NFDRunALLRegDTO;
import com.jd.gds.dto.NFDRunALLRequestDTO;
import com.jd.gds.dto.NFDRunALLResponseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description nfd定时推送nfdrunAll任务处理实现
 * @Author: qinhaoran1
 * @Date: 2019/3/25
 */
@Service
public class NfdRunAllScheduleTaskServiceImpl implements NfdRunAllScheduleTaskService {

    private static final Logger LOGGER = Logger.getLogger(NfdRunAllScheduleTaskServiceImpl.class);
    // 最大扫描次数
    private static final int MAX_SCAN_COUNT = 3;
    // 每次执行条数
    private static final int PAGE_SIZE = 10;
    // 调用gds返回结果码
    private static final String GDS_SUCCESS = "0";

    @Resource
    private JdjpScheduledTaskService jdjpScheduledTaskService;
    @Resource
    private JdjpNfdUpdatePolicyManager jdjpNfdUpdatePolicyManager;
    @Resource
    private GdsRequestService gdsRequestService;
    // ES注入
    @Autowired(required = false)
    private ESClientOperations selfWorkerOrderTxtClient;

    @Override
    public void nfdScheduleTask() {

        // 查询待执行的NFD运价更新的计划任务
        final List<JdjpScheduledTask> tasks = jdjpScheduledTaskService.selectTask(
                TaskTypeEnum.PUSH_NFDRUNALL.getCode(), null, 0, MAX_SCAN_COUNT, PAGE_SIZE);
        // 执行计划任务
        for (JdjpScheduledTask task : tasks) {
            try {
                // 获取NFD更新策略
                JdjpNfdUpdatePolicy policy = jdjpNfdUpdatePolicyManager.selectByPrimaryKey(task.getParam1());
                // 封装请求GDS的对象
                NFDRunALLRequestDTO nfdRunALLRequestDTO = getNfdRunALLRequestDTO(policy);
                // 调用gds服务
                LOGGER.info("execute gds service start!");
                final NFDRunALLResponseDTO responseDTO = gdsRequestService.doService(nfdRunALLRequestDTO,
                        GDSConstants.GDS_OFFICENO, GDSEnums.GDS_INTERFACE_NFDRUNALL, NFDRunALLResponseDTO.class);
                LOGGER.info("execute gds service end! result = " + JacksonUtil.obj2json(responseDTO));
                // 处理调用结果
                if (null != responseDTO && GDS_SUCCESS.equals(responseDTO.getCode())
                        && CodeEnums.SUCCESS.getCode().equals(responseDTO.getResponseCode())) {
                    updateNfdPolicy(policy);
                    updateTaskScanStatus(task, ScanStatusEnum.SUCCESS);
                } else {
                    if (task.getScanCount() >= MAX_SCAN_COUNT) {
                        updateNfdPolicy(policy);
                        updateTaskScanStatus(task, ScanStatusEnum.FAIL);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("execute scheduledTask error! task = " + JacksonUtil.obj2json(task), e);
            }
        }

    }

    /**
     * 封装请求GDS的对象
     *
     * @param policy NFD运价更新策略
     * @return
     */
    private NFDRunALLRequestDTO getNfdRunALLRequestDTO(JdjpNfdUpdatePolicy policy) {

        NFDRunALLRequestDTO nfdRunALLRequestDTO = new NFDRunALLRequestDTO();
        List<NFDRunALLRegDTO> nfdregList = new ArrayList<>();
        // 从ES获取airlines
        List<Map<String, Object>> dataList = ESUtils.queryEsData(selfWorkerOrderTxtClient, policy.getAirlines());
        if (CollectionUtils.isNotEmpty(dataList)) {
            // 取到从ES中获取的航线信息内容
            final String content = String.valueOf(dataList.get(0).get("content"));
            final String[] airlinesArray = content.split(",");
            for (String airlines : airlinesArray) {
                String[] airlineArray = airlines.split("[:/]");
                NFDRunALLRegDTO nfdRunALLReg = new NFDRunALLRegDTO();
                // 封装航司、出发城市、到达城市
                nfdRunALLReg.setCarrier("***".equals(airlineArray[0]) ? "All" : airlineArray[0]);
                nfdRunALLReg.setOrgCity("***".equals(airlineArray[1]) ? "All" : airlineArray[1]);
                nfdRunALLReg.setDstCity("***".equals(airlineArray[2]) ? "All" : airlineArray[2]);
                nfdRunALLReg.setOfficeID(GDSConstants.GDS_OFFICENO);
                // depDate格式：多个用1,2,3隔开，一个的时候用具体日期格式
                nfdRunALLReg.setRunDate(policy.getDepDate());
                // 0:自动添加nfd更新策略，1：手工添加nfd更新策略
                nfdRunALLReg.setParam1(String.valueOf(policy.getSource()));
                nfdregList.add(nfdRunALLReg);
            }
        }
        nfdRunALLRequestDTO.setNfdreg(nfdregList);
        return nfdRunALLRequestDTO;
    }

    /**
     * 根据扫描状态更新计划任务
     *
     * @param task
     * @param scanStatusEnum
     */
    private void updateTaskScanStatus(JdjpScheduledTask task, ScanStatusEnum scanStatusEnum) {
        task.setScanStatus(scanStatusEnum.getStatus());
        task.setResponseInfo(scanStatusEnum.getDesc());
        jdjpScheduledTaskService.updateByPrimaryKeySelective(task);
    }

    /**
     * 将nfd运价更新策略状态置为“同步完成”
     *
     * @param policy
     */
    private void updateNfdPolicy(JdjpNfdUpdatePolicy policy) {
        final Date date = new Date();
        policy.setExecuteQuartzTime(date);
        policy.setExecuteFinishTime(date);
        policy.setSyncStatus(Byte.parseByte(SyncStatusEnum.OVER.getCode()));
        policy.setRemark(SyncStatusEnum.OVER.getDesc());
        jdjpNfdUpdatePolicyManager.updateByPrimaryKeySelective(policy);
    }

}
