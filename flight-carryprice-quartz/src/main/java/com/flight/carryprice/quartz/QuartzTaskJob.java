package com.flight.carryprice.quartz;

import com.flight.carryprice.common.constant.UmpConstants;
import com.flight.carryprice.quartz.service.CarryPriceMissionTaskService;
import com.flight.carryprice.quartz.service.FdCarryPriceAutoToRedisTaskService;
import com.flight.carryprice.quartz.service.FdCarryPriceManualToRedisTaskService;
import com.flight.carryprice.quartz.service.NfdManAddUpdatePolicyTaskService;
import com.flight.carryprice.quartz.service.FdUpdatePolicyAutoAddTaskService;
import com.flight.carryprice.quartz.service.NfdRunAllScheduleTaskService;
import com.flight.carryprice.service.JdjpNfdPatAutoUpdateService;
import com.flight.carryprice.quartz.service.*;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import org.apache.log4j.Logger;
import javax.annotation.Resource;

/**
 * @author wangyanwei
 * @version 1.0.0
 * @Description Quartz Task Job
 * @date 2019年03月19日 上午18:09:36
 * @updateTime
 * @see
 */
public class QuartzTaskJob {

    private static Logger LOGGER = Logger.getLogger(QuartzTaskJob.class);

    @Resource
    private CarryPriceMissionTaskService carryPriceMissionTaskService;
    @Resource
    private FdCarryPriceAutoToRedisTaskService fdCarryPriceAutoToRedisTaskService;
    @Resource
    private FdCarryPriceManualToRedisTaskService fdCarryPriceManualToRedisTaskService;
    @Resource
    private FdUpdatePolicyAutoAddTaskService fdUpdatePolicyAutoAddTaskService;
    @Resource
    private FdCarryPriceUpdateByPolicyTaskService fdCarryPriceUpdateByPolicyTaskService;
    @Resource
    private NfdManAddUpdatePolicyTaskService nfdManAddUpdatePolicyTaskService;
    @Resource
    private NfdRunAllScheduleTaskService nfdRunAllScheduleTaskService;
    @Resource
    private JdjpNfdPatAutoUpdateService jdjpNfdPatAutoUpdateService;


    /**
     * 自动维护运价缓存任务(自动插入FD运价缓存更新任务)
     */
    @JProfiler(jAppName = UmpConstants.CARRYPRICE_QUARTZ_NAME, jKey = UmpConstants.ADD_CARRY_PRICE_TASK, mState = {
            JProEnum.TP, JProEnum.Heartbeat})
    public void addCarryPriceTask() {
        LOGGER.info("--------自动维护运价缓存任务(自动插入FD运价缓存更新任务) 开始-----");
        carryPriceMissionTaskService.addCarryPriceTask();
        LOGGER.info("--------自动维护运价缓存任务(自动插入FD运价缓存更新任务) 结束-----");
    }

    /**
     * 自动刷新FD运价缓存信息
     */
    @JProfiler(jAppName = UmpConstants.CARRYPRICE_QUARTZ_NAME, jKey = UmpConstants.AUTO_FLUSH_FD_TO_REDIS, mState = {
            JProEnum.TP, JProEnum.Heartbeat})
    public void autoFlushFdToRedis() {
        LOGGER.info("--------自动刷新FD运价缓存信息 开始-----");
        fdCarryPriceAutoToRedisTaskService.autoFlushFdToRedis();
        LOGGER.info("--------自动刷新FD运价缓存信息 结束-----");
    }

    /**
     * 手动刷新FD运价缓存信息
     */
    @JProfiler(jAppName = UmpConstants.CARRYPRICE_QUARTZ_NAME, jKey = UmpConstants.MANUAL_FLUSH_FD_TO_REDIS, mState = {
            JProEnum.TP, JProEnum.Heartbeat})
    public void manualFlushFdToRedis() {
        LOGGER.info("--------手动刷新FD运价缓存信息 开始-----");
        fdCarryPriceManualToRedisTaskService.manualFlushFdToRedis();
        LOGGER.info("--------手动刷新FD运价缓存信息 结束-----");
    }

    /**
     * 自动插入FD运价更新策略
     */
    @JProfiler(jAppName = UmpConstants.CARRYPRICE_QUARTZ_NAME, jKey = UmpConstants.AUTO_ADD_FD_UPDATE_POLICY, mState = {
            JProEnum.TP, JProEnum.Heartbeat})
    public void autoAddFdUpdatePolicy() {
        LOGGER.info("--------自动插入FD运价更新策略 开始-----");
        fdUpdatePolicyAutoAddTaskService.autoAddFdUpdatePolicy();
        LOGGER.info("--------自动插入FD运价更新策略 结束-----");
    }

    /**
     * FD运价维护更新(扫描FD更新策略更新FD运价)
     */
    @JProfiler(jAppName = UmpConstants.CARRYPRICE_QUARTZ_NAME, jKey = UmpConstants.UPDATE_FD_CARRY_PRICE_BY_POLICY, mState = {
            JProEnum.TP, JProEnum.Heartbeat})
    public void updateFdCarryPriceByPolicy() {
        LOGGER.info("--------FD运价维护更新(扫描FD更新策略更新FD运价) 开始-----");
        fdCarryPriceUpdateByPolicyTaskService.updateFdCarryPriceByPolicy();
        LOGGER.info("--------FD运价维护更新(扫描FD更新策略更新FD运价) 结束-----");
    }

    /**
     * 扫描NFD运价更新策略任务
     * 1、nfd运价更新策略的两个来源：①自动扫描jdjp_nfd_pat_auto_update表插入；②手动插入。
     * 2、此定时任务会扫描nfd更新策略表，将自动、手动插入的更新策略同步状态修改，同时会逐条插入计划任务。
     */
    @JProfiler(jAppName = UmpConstants.CARRYPRICE_QUARTZ_NAME, jKey = UmpConstants.NFD_ADD_UPDATE_POLICY_JOB_QUARTZ, mState = {
            JProEnum.TP, JProEnum.Heartbeat})
    public void nfdAddUpdatePolicyJob() {
        LOGGER.info("--------扫描NFD运价更新策略任务开始-----");
        nfdManAddUpdatePolicyTaskService.nfdAddUpdatePolicyJob();
        LOGGER.info("--------扫描NFD运价更新策略任务结束-----");
    }

    /**
     * NFD定时推送NFDRunAll接口任务
     */
    @JProfiler(jAppName = UmpConstants.CARRYPRICE_QUARTZ_NAME, jKey = UmpConstants.NFD_SCHEDULE_TASK_QUARTZ, mState = {
            JProEnum.TP, JProEnum.Heartbeat})
    public void nfdScheduleTask() {
        LOGGER.info("--------NFD定时推送NFDRunAll接口任务开始-----");
        nfdRunAllScheduleTaskService.nfdScheduleTask();
        LOGGER.info("--------NFD定时推送NFDRunAll接口任务结束-----");
    }
    /**
     * 扫描NFD PAT自动更新配置生成策略任务
     */
    @JProfiler(jAppName = UmpConstants.CARRYPRICE_QUARTZ_NAME, jKey = UmpConstants.NFD_PAT_AUTO_UPDATE_JOB_QUARTZ, mState = {
            JProEnum.TP, JProEnum.Heartbeat})
    public void nfdPatAutoUpdateJob() {
        LOGGER.info("--------扫描NFD PAT自动更新配置生成策略任务开始-----");
        jdjpNfdPatAutoUpdateService.nfdPatAutoUpdateJob();
        LOGGER.info("--------扫描NFD PAT自动更新配置生成策略任务结束-----");
    }

}


