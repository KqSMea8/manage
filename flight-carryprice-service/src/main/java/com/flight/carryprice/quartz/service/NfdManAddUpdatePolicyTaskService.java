package com.flight.carryprice.quartz.service;

/**
 * @Description 扫描NFD运价更新策略任务
 * @Author: qinhaoran1
 * @Date: 2019/3/21
 */
public interface NfdManAddUpdatePolicyTaskService {

    /**
     * 扫描NFD运价更新策略
     */
    void nfdAddUpdatePolicyJob();
}
