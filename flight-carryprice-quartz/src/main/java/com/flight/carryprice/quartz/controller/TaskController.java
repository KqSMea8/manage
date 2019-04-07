package com.flight.carryprice.quartz.controller;

import com.flight.carryprice.quartz.service.TimeTaskService;
import com.flight.carryprice.vo.QuartzResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 任务执行控制类
 * @date 2019/3/21 18:05
 * @updateTime
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

	private final static Logger LOGGER = LogManager.getLogger(TaskController.class);

	@Autowired
	private TimeTaskService timeTaskService;

	/*@Resource
	BaseForUserController baseForUserController;*/

	@RequestMapping("/switch")
	public String execute(HttpSession session,HttpServletRequest request, ModelMap model) {
		try {
			QuartzResult result = new QuartzResult();
			String jobTask = request.getParameter("jobTask");
			String quartzBeanId = request.getParameter("quartzBeanId");
			String status = request.getParameter("status");
			LOGGER.info("后台，进入操作定时任务页面，请求数据为:jobTask："+jobTask+",quartzBeanId："+quartzBeanId+",status："+status);
			/************************************************* 记录用户start ********************************************/
			//AlUsers users = baseForUserController.getCurrentUser(session);
			//LOGGER.info("后台，进入操作定时任务页面，当前用户，用户名："+users.getUsername()+"。联系人："+users.getChargeperson()+" ，请求数据为:jobTask："+jobTask+",quartzBeanId："+quartzBeanId+",status："+status);
			/************************************************* 记录用户end ********************************************/

			//加上ip显示
			//String ip = IpUtil.getServerIp();
			//model.put("host", ip);
			if (StringUtils.isBlank(jobTask)) {
				result = timeTaskService.isStarted();
				model.put("result", result);
				return "/task/switchtask";
			}
			if ("doOnce".equals(jobTask)) {
				timeTaskService.doOnceMethod(quartzBeanId);
				result = timeTaskService.isStarted();
			} else {
				timeTaskService.switchTimeTaskState(jobTask, status);
				result = timeTaskService.isStarted();
			}

			model.put("result", result);
		} catch (Exception e) {
			LOGGER.error("work启动异常", e);
		}
		return "/task/switchtask";

	}

}
