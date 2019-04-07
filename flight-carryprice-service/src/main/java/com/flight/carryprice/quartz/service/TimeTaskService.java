package com.flight.carryprice.quartz.service;

import com.flight.carryprice.vo.QuartzResult;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 任务执行控制业务逻辑实现类
 * @date 2019/3/21 18:05
 * @updateTime
 */
public interface TimeTaskService {

	/**
	 * 单独任务开关
	 *
	 * @param jobTask
	 * @param status
	 * @return
	 */
	QuartzResult switchTimeTaskState(String jobTask, String status);

	/***
	 * 判断当前的任务调度器是否启动
	 *
	 * @return
	 */
	QuartzResult isStarted();

	/**
	 * 手动执行
	 *
	 * @param quartzBeanId
	 * @return
	 */
	QuartzResult doOnceMethod(String quartzBeanId);

}
