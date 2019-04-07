package com.flight.carryprice.quartz.service.impl;

import com.flight.carryprice.quartz.service.TimeTaskService;
import com.flight.carryprice.vo.QuartzResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 任务执行控制业务逻辑实现类
 * @date 2019/3/21 18:05
 * @updateTime
 */
@Service
public class TimeTaskServiceImpl implements TimeTaskService, ApplicationContextAware {

	private final static Logger LOGGER = LogManager.getLogger(TimeTaskServiceImpl.class);

	private final static int START_ALL_TYPE = 1;

	private final static int STOP_ALL_TYPE = 2;

	private final static int SINGLE_SCHEDULER_TYPE = 3;

	private final static int IS_START_TYPE = 4;

	private final static int START_SELECT_TYPE = 5;

	private final static int STOP_SELECT_TYPE = 6;

	private ApplicationContext applicationContext;

	@Value("#{schedulerMap}")
	private Map schedulerMap;

	public QuartzResult switchTimeTaskState(String schedulerName, String status) {
		return doExecute(SINGLE_SCHEDULER_TYPE, schedulerName, status, null);
	}

	public QuartzResult isStarted() {
		QuartzResult result = doExecute(IS_START_TYPE, "isStarted", "", null);
		// Libg 这里没有做任何事情，固注释
		/*
		 * if (result.isSuccess()) { }
		 */
		return result;
	}

	public QuartzResult doOnceMethod(String quartzBeanId) {

		QuartzResult result = new QuartzResult();
		try {
			result.setSuccess(false);
			result.setResultCode("system.error");
			if (StringUtils.isEmpty(quartzBeanId)) {
				result.setResultCode("system.error");
			}

			MethodInvokingJobDetailFactoryBean b = (MethodInvokingJobDetailFactoryBean) (((JobDetail) applicationContext
					.getBean(quartzBeanId)).getJobDataMap().get("methodInvoker"));
			Method method = b.getPreparedMethod();
			method.invoke(b.getTargetObject(), null);

			result.setSuccess(true);
			result.setResultCode("system.timetask.execute.success");
		} catch (Exception e) {
			LOGGER.error("Method:doOnceMethod-->error", e);
		}
		return result;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

	private QuartzResult doExecute(int type, String schedulerName, String status, String[] taskItems) {

		QuartzResult result = new QuartzResult();
		if (schedulerMap == null || schedulerMap.size() <= 0) {
			return result;
		}
		Iterator<String> it = schedulerMap.keySet().iterator();
		Scheduler scheduler = null;
		try {

			while (it.hasNext()) {
				String key = it.next();
				if (type == START_ALL_TYPE) {
					// 启动所有的时间程序
					scheduler = (Scheduler) schedulerMap.get(key);
					scheduler.start();
					result.addDefaultModel(key, "1");
				} else if (type == STOP_ALL_TYPE) {
					// 关闭所有的时间程序
					scheduler = (Scheduler) schedulerMap.get(key);
					scheduler.standby();
					result.addDefaultModel(key, "0");
				} else if (type == SINGLE_SCHEDULER_TYPE) {
					// 启动一个指定的时间程序
					if (key.equals(schedulerName)) {
						scheduler = (Scheduler) schedulerMap.get(key);
						if (status.equals("start")) {
							scheduler.start();
						} else if (status.equals("stop")) {
							scheduler.standby();
						}
					}
				} else if (type == IS_START_TYPE) {
					scheduler = (Scheduler) schedulerMap.get(key);
					// logger.info("是否是isStarted的：" + scheduler.isStarted());
					if (scheduler.isStarted()) {
						result.addDefaultModel(key, "1");
						if (scheduler.isInStandbyMode()) {
							result.addDefaultModel(key, "0");
						}
					} else {
						result.addDefaultModel(key, "0");
					}
				} else if (type == START_SELECT_TYPE || type == STOP_SELECT_TYPE) {
					if (taskItems != null && taskItems.length > 0) {
						boolean flag = false;
						for (String s : taskItems) {
							if (s.equals(key)) {
								flag = true;
								break;
							}
						}
						scheduler = (Scheduler) schedulerMap.get(key);
						if (flag) {
							if (type == START_SELECT_TYPE) {
								scheduler.start();
								result.addDefaultModel(key, "1");
							} else {
								scheduler.standby();
								result.addDefaultModel(key, "0");
							}
						} else {
							if (scheduler.isStarted()) {
								result.addDefaultModel(key, "1");
								if (scheduler.isInStandbyMode()) {
									result.addDefaultModel(key, "0");
								}
							} else {
								result.addDefaultModel(key, "0");
							}
						}
					}
				}
			}
			result.setSuccess(true);
			result.setResultCode("system.timetask.execute.success");
			LOGGER.info("Method:" + schedulerName + "--->success");
		} catch (SchedulerException e) {
			result.setSuccess(false);
			result.setResultCode("system.timetask.execute.error");
			LOGGER.error("Method:" + schedulerName + "--->error", e);
		}
		return result;
	}

}
