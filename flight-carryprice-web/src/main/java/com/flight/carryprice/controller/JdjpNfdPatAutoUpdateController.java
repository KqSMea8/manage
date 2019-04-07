package com.flight.carryprice.controller;

import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpNfdPatAutoUpdate;
import com.flight.carryprice.service.JdjpNfdPatAutoUpdateService;
import com.flight.carryprice.service.JdjpRunparametersService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description NFD运价自动更新配置
 * @see
 * @author wanghaiyuan 商旅事业部
 * @version 1.0.0
 * @date 2019年3月5日 上午10:18:35
 * @updateTime
 */
@Controller
@RequestMapping("/nfdAndPat/nfdPatAutoUpdate")
public class JdjpNfdPatAutoUpdateController extends BaseController {

	@Resource
	private JdjpNfdPatAutoUpdateService jdjpNfdPatAutoUpdateService;

	@Resource
	private JdjpRunparametersService jdjpRunparametersService;

	@RequestMapping("/nfdPatAutoUpdateList")
	public String nfdPatAutoUpdateList(HttpServletRequest req, ModelMap model, JdjpNfdPatAutoUpdate queryBean) {
		List<JdjpNfdPatAutoUpdate> jdjpNfdPatAutoUpdateList = jdjpNfdPatAutoUpdateService.queryByPopularAndAirType(queryBean);
		model.put("nfdPatAutoUpdateList", jdjpNfdPatAutoUpdateList);
		model.put("queryBean", queryBean);
		model.put("airLineTypeList", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
		return "nfdAndPat/nfdPatAutoUpdate/nfdPatAutoUpdateList";
	}

	/**
	 * 到新增页面
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddNfdPatAutoUpdate")
	public String toAddNfdAutoUpdate(HttpServletRequest req, ModelMap model) {
		// 航班类型
		model.put("airLineTypeList", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
		// 更新频率
		model.put("dayList", jdjpRunparametersService.queryUpdateDay());
		return "nfdAndPat/nfdPatAutoUpdate/addNfdPatAutoUpdate";
	}

	/**
	 * 新增
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping("/addNfdPatAutoUpdate")
	@ResponseBody
	public String addNfdAutoUpdate(HttpSession session, String json) {
		Map<String, Object> map = JacksonUtil.json2Obj(json, Map.class);
		String userName = getCurrentUser(session).getUsername();
		JdjpNfdPatAutoUpdate record = new JdjpNfdPatAutoUpdate();
		try {
			record.setAirlinePopularType(Byte.parseByte((String)map.get("airline_Popular_Type")));
			record.setAirlineType(Byte.valueOf((String)map.get("airline_type")));
			record.setDeptDate(parseDeptDate((ArrayList) map.get("dept_day")));
			record.setExecuteIntervalDay(new BigDecimal((String)map.get("execute_interval_day")));
			record.setExecuteQuartzTime(DateUtil.stringToDate((String) map.get("execute_time"), DateUtil.DATE_TIME6));
		}catch (Exception e){
			LOGGER.error("新增自动更新策略 组装实体异常", e);
			throw new SystemException("新增自动更新策略 参数异常");
		}
		record.setOperator(userName);
		List<JdjpNfdPatAutoUpdate> jdjpNfdPatAutoUpdateList = jdjpNfdPatAutoUpdateService.queryByPopularAndAirType(record);
		if (CollectionUtils.isNotEmpty(jdjpNfdPatAutoUpdateList)) {
			LOGGER.warn("已存在此航线类型, 航线信息 = "  + record.toString());
			return "已经存在此热门类型";
		}
		if (jdjpNfdPatAutoUpdateService.insertSelective(record) >= 1) {
			return "1";
		} else {
			return  "添加失败";
		}
	}

	/**
	 * 到更新页面
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdateNfdPatAutoUpdate")
	public String toUpdateNfdAutoUpdate(HttpServletRequest req, ModelMap model) {
		String id = getParam(req, "id");
		try {
			JdjpNfdPatAutoUpdate jdjpNfdPatAutoUpdate = jdjpNfdPatAutoUpdateService.selectByPrimaryKey(Integer.valueOf(id));
			model.put("nfd", jdjpNfdPatAutoUpdate);
			if(jdjpNfdPatAutoUpdate.getExecuteIntervalDay().intValue()>=1){
				model.put("executeIntervalDay", jdjpNfdPatAutoUpdate.getExecuteIntervalDay().intValue());
			}else{
				model.put("executeIntervalDay", jdjpNfdPatAutoUpdate.getExecuteIntervalDay().doubleValue());
			}
			// 获取更新时间dept_day
			String[] deptDay = jdjpNfdPatAutoUpdate.getDeptDate().split(",");
			decodeDeptDate(model, deptDay);
		}catch (Exception e){
			LOGGER.error("修改nfd pat自动更新配置异常", e);
			throw new SystemException("跳转更新自动更新配置页面异常");
		}
		// 更新频率
		model.put("dayList", jdjpRunparametersService.queryUpdateDay());
		// 航线类型
		model.put("airLineTypeList", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
		return "nfdAndPat/nfdPatAutoUpdate/updateNfdPatAutoUpdate";
	}
	/**
	 * 修改
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping("/updateNfdPatAutoUpdate")
	@ResponseBody
	public String updateNfdAutoUpdate(HttpSession session, String json) {
		Map<String, Object> map = JacksonUtil.json2Obj(json, Map.class);
		JdjpNfdPatAutoUpdate record = new JdjpNfdPatAutoUpdate();
		try {
			record.setId(Integer.parseInt((String) map.get("id")));
			record.setDeptDate(parseDeptDate((ArrayList) map.get("dept_day")));
			record.setExecuteIntervalDay(new BigDecimal((String)map.get("execute_interval_day")));
			record.setExecuteQuartzTime(DateUtil.stringToDate((String) map.get("execute_time"), DateUtil.DATE_TIME6));
			record.setOperator(getCurrentUser(session).getUsername());
		}catch (Exception e){
			LOGGER.error("更新NFD PAT自动更新配置 数据解析异常", e);
			return "修改异常";
		}
		int count = jdjpNfdPatAutoUpdateService.updateByPrimaryKeySelective(record);
		if (count == 1) {
			return  "1";
		} else {
			return "修改失败";
		}
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteNfdPatAutoUpdate")
	@ResponseBody
	public String deleteNfdAutoUpdate(String id) {
		int count;
		try {
			count = jdjpNfdPatAutoUpdateService.deleteByPrimaryKey(Integer.valueOf(id));
		}catch (Exception e){
			LOGGER.error("删除配置解析id异常", e);
			return "删除失败";
		}
		if (count == 1) {
			return  "1";
		} else {
			return "删除失败";
		}
	}
	/**
	 * @Author hYuan
	 * @Description deptDate 转换
	 * 规则 1，3，5 转为 T+1, T+3, T+5
	 * @Date  2019/3/5 15:32
	 * @Param deptDay
	 * @return
	 **/
	private String parseDeptDate(ArrayList deptDay){
		String deptDate = "";
		for (int i = 0; i < deptDay.size(); i++) {
			if (!"".equals(deptDay.get(i))) {
				deptDate = deptDate + "T+" + deptDay.get(i) + ",";
			}
		}
		if (StringUtils.isNotBlank(deptDate)){
			deptDate = deptDate.substring(0, deptDate.length() - 1);
		}
		return deptDate;
	}

	/**
	 * @Author hYuan
	 * @Description T+1, T+3 转为1 ，3
	 * @Date  2019/3/5 15:59
	 * @Param model deptDay
	 * @return
	 **/
	private void decodeDeptDate(ModelMap model, String[] deptDay){
		for (int i = 0; i < 3; i++) {
			String detp = "";
			try {
				detp = deptDay[i];
				detp = detp.substring(2);
			} catch (Exception e) {
				LOGGER.error("解析更新日期异常",e);
			}
			model.put("deptDay" + i, detp);
		}
	}
}
