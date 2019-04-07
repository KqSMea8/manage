package com.flight.carryprice.controller;

import com.flight.carryprice.common.enums.SourceEnum;
import com.flight.carryprice.common.enums.SyncStatusEnum;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpNfdUpdatePolicy;
import com.flight.carryprice.service.JdjpNfdUpdatePolicyService;
import com.flight.carryprice.service.JdjpRunparametersService;
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
@RequestMapping("/nfd/nfdUpdatePolicy")
public class JdjpNfdUpdatePolicyController extends BaseController {

	@Resource
	private JdjpNfdUpdatePolicyService jdjpNfdUpdatePolicyService;


	@Resource
	private JdjpRunparametersService jdjpRunparametersService;

	/**
	 * nfd运价跟新策略列表
	 * 
	 * @return
	 */
	@RequestMapping("/nfdUpdatePolicyList")
	public String nfdUpdatePolicyList(HttpServletRequest req, ModelMap model, JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy) {
		PageInfo<JdjpNfdUpdatePolicy> pageInfo = jdjpNfdUpdatePolicyService.pagination(getPageIndex(req),
				getPageSize(req), jdjpNfdUpdatePolicy);
		model.put("pageInfo", pageInfo);
		model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
		model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
		model.put("queryBean", jdjpNfdUpdatePolicy);
		// 航线类型
		model.put("airLineTypeList", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
		return "nfd/nfdUpdatePolicy/nfdUpdatePolicyList";
	}

	/**
	 * 转到添加nfd运价更新策略页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddNfdUpdatePolicy")
	public String toAddFdUpdatePolicy(HttpServletRequest req, ModelMap model) {
		model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
		model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
		// 航线类型
		model.put("airLineTypeList", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
		return "nfd/nfdUpdatePolicy/addNfdUpdatePolicy";
	}
	/**
	 * 新增nfd更新策略
	 *
	 * @return
	 */
	@RequestMapping("/doAddNfdUpdatePolicy")
	@ResponseBody
	public Object doAddFdUpdatePolicy(HttpSession session, String jsonStr) {
		String flag = "0";
		JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy = JacksonUtil.json2Obj(jsonStr, JdjpNfdUpdatePolicy.class);
		if (jdjpNfdUpdatePolicy == null) {
			return flag;
		}
		jdjpNfdUpdatePolicy
				.setPlanQuartzTime(DateUtil.stringToDate(jdjpNfdUpdatePolicy.getPlanTime(), DateUtil.DATE_FMT3));
		jdjpNfdUpdatePolicy.setOperator(getCurrentUser(session).getUsername());
		jdjpNfdUpdatePolicy.setSource(SourceEnum.MANUAL.getCode());
		jdjpNfdUpdatePolicy.setSyncStatus(Byte.parseByte(SyncStatusEnum.WAITING.getCode()));
		jdjpNfdUpdatePolicy.setRemark(SyncStatusEnum.WAITING.getDesc());
		try {
			return jdjpNfdUpdatePolicyService.save(jdjpNfdUpdatePolicy);
		} catch (Exception e) {
			LOGGER.error("nfd运价策略插入异常", e);
			return e.getMessage();
		}
	}

	@RequestMapping("/deleteNfdUpdatePolicy")
	@ResponseBody
	public Object deleteNfdUpdatePolicy(Integer id) {
		int count = 0;
		try {
			count = jdjpNfdUpdatePolicyService.deleteByPrimaryKey(id);
		}catch (Exception e){
			LOGGER.error("nfd运价策略删除异常", e);
		}
		return count;
	}
}
