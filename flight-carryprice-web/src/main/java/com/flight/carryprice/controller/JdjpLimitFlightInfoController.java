package com.flight.carryprice.controller;

import com.alibaba.fastjson.JSONObject;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.entity.JdjpLimitFlightInfo;
import com.flight.carryprice.service.JdjpLimitFlightInfoService;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import com.jd.airplane.flight.base.vo.airport.Airport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/baseData/limitFlight")
public class JdjpLimitFlightInfoController extends BaseController {

	@Resource
	private JdjpLimitFlightInfoService jdjpLimitFlightInfoService;

	/**
	 * 限制航班列表
	 * 
	 * @return
	 */
	@RequestMapping("/limitFlightList")
	public String seatInfoList(HttpServletRequest req, ModelMap model, JdjpLimitFlightInfo queryBean) {
		Integer pageIndex = getPageIndex(req);
		Integer pageSize = getPageSize(req);
		PageInfo<JdjpLimitFlightInfo> pageInfo = jdjpLimitFlightInfoService.pagination(pageIndex, pageSize, queryBean);
		model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
		model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
		model.put("pageInfo", pageInfo);
		model.put("queryBean", queryBean);
		return "basedata/limitFlight/limitFlightList";
	}
	/**
	 * 进入添加限制航班页面
	 *
	 * @return
	 */
	@RequestMapping("/toAddLimitFlight")
	public String toAddRefundRule(ModelMap model) {

		// 航司下拉框选择航司
		model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
		model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
		return "basedata/limitFlight/addLimitFlight";
	}

	/**
	 * 执行添加限制航班
	 *
	 * @return
	 */
	@RequestMapping("/doAddLimitFlight")
	@ResponseBody
	public int doAddRefundRule(HttpSession session, String jsonStr) {
		JdjpLimitFlightInfo jdjpLimitFlightInfo = JSONObject.parseObject(jsonStr, JdjpLimitFlightInfo.class);
		if (jdjpLimitFlightInfo == null){
			return -1;
		}
		jdjpLimitFlightInfo.setOperator(getCurrentUser(session).getUsername());
		try {
			return jdjpLimitFlightInfoService.addLimitFlights(jdjpLimitFlightInfo);
		} catch (Exception e) {
			LOGGER.error("新增限制航班信息异常", e);
			return -1;
		}
	}
	/**
	 * 去修改规则页面
	 *
	 * @return
	 */
	@RequestMapping("/toUpdateLimitFlight")
	public String toUpdateSeatInfo(ModelMap model, Long limitFlightId) {
		JdjpLimitFlightInfo jdjpLimitFlightInfo = jdjpLimitFlightInfoService.selectByPrimaryKey(limitFlightId);
		model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
		model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
		model.put("limitFlight", jdjpLimitFlightInfo);
		return "basedata/limitFlight/updateLimitFlight";
	}
	/**
	 * 更新限制航班 同步缓存
	 *
	 * @return
	 */
	@RequestMapping("/doUpdateLimitFlight")
	@ResponseBody
	public Object doUpdateSeatInfo(HttpSession session, String jsonStr) {
		JdjpLimitFlightInfo jdjpLimitFlightInfo = JSONObject.parseObject(jsonStr, JdjpLimitFlightInfo.class);
		if (jdjpLimitFlightInfo == null){
			LOGGER.error("参数转json异常");
			return -1;
		}
		jdjpLimitFlightInfo.setOperator(getCurrentUser(session).getUsername());
		try {
			return jdjpLimitFlightInfoService.updateLimitFlights(jdjpLimitFlightInfo);
		}catch (Exception e){
			LOGGER.error("更新限制航班信息到缓存异常", e);
			return -1;
		}
	}
	/**
	 * 删除限制航班
	 *
	 * @return
	 */
	@RequestMapping("/delLimitFlightInfo")
	@ResponseBody
	public Object delLimitFlightInfo(long limitFlightId){
		try {
			return jdjpLimitFlightInfoService.delFlightInfo(limitFlightId);
		}catch (Exception e){
			LOGGER.info("删除限制航班信息异常", e);
			return -1;
		}
	}
	/**
	 * 全量更新限制航班 同步缓存
	 *
	 * @return
	 */
	@RequestMapping("/doFlushAllFlightInfo")
	@ResponseBody
	public Object doFlushToJimdb(){
		try {
			return jdjpLimitFlightInfoService.flushAllFlightInfoToJimdb();
		}catch (Exception e){
			LOGGER.info("全量同步限制航班信息 到缓存异常", e);
		}
		return -1;
	}

}
