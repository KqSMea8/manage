package com.flight.carryprice.controller;

import com.flight.carryprice.common.enums.AutoEnum;
import com.flight.carryprice.entity.JdjpCarryPriceTask;
import com.flight.carryprice.service.JdjpCarryPriceTaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 
 * @Description (运价缓存维护任务表)
 * @see
 * @author lishuo 商旅事业部
 * @version 1.0.0
 * @date 2017年6月12日 上午10:10:11
 * @updatetime
 */
@Controller
@RequestMapping("/fd/carryPriceTask")
public class JdjpCarryPriceTaskController extends BaseController {

	private final static Logger LOGGER = Logger.getLogger(JdjpCarryPriceTaskController.class);

	@Resource
	private JdjpCarryPriceTaskService jdjpCarryPriceTaskService;

	/**
	 * 查询运价缓存维护任务表中手动任务的数量
	 * 
	 * @param carryPriceType
	 * @return
	 */
	@RequestMapping("/queryManualTaskCount")
	@ResponseBody
	public Object queryManualTaskCount(String carryPriceType) {
		int count = 0;
		try {
			JdjpCarryPriceTask task = new JdjpCarryPriceTask();
			task.setCarryPriceType(new Byte(carryPriceType));//运价类型(0:FD，1:NFD，2:SSD)
			task.setTaskType(new Byte(AutoEnum.MANUAL.getCode()));// 0:自动、定时，1:手动
			List<JdjpCarryPriceTask> list = jdjpCarryPriceTaskService.queryList(task);
			if (CollectionUtils.isNotEmpty(list)) {
				count = list.size();
			}
		} catch (Exception e) {
			LOGGER.error("", e);
			throw new RuntimeException("查询运价缓存维护任务表异常");
		}
		return count;
	}

	/**
	 * 插入运价缓存维护任务表
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/addManualTask")
	@ResponseBody
	public Object addManualTask(HttpSession session, String carryPriceType, String airWays, String depCode, String arrCode, String seat) {
		int count = 0;
		try {
			String operator = getCurrentUser(session).getUsername();
			Byte taskType = new Byte(AutoEnum.MANUAL.getCode());// 0:自动、定时，1:手动
			count = jdjpCarryPriceTaskService.insertTask(new Byte(carryPriceType), taskType, operator, airWays, depCode, arrCode, seat);
		} catch (Exception e) {
			LOGGER.error("", e);
			throw new RuntimeException("插入运价缓存维护任务表异常");
		}
		return count;
	}

}
