package com.flight.carryprice.controller;

import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.entity.JdjpFdDifference;
import com.flight.carryprice.service.JdCacheService;
import com.flight.carryprice.service.JdjpFdDifferenceService;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import com.jd.airplane.flight.base.vo.airport.Airport;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description fd运价缓存和数据库差异Controller层
 * @date 2019/3/07 14:15
 * @updateTime
 */
@Controller
@RequestMapping("/fd/fdDifference")
public class JdjpFdDifferenceController extends BaseController {

	private final static Logger LOGGER = Logger.getLogger(JdjpFdDifferenceController.class);

	@Resource
	private JdjpFdDifferenceService jdjpFdDifferenceService;

	@Resource
	private JdCacheService jdCacheService;

	/**
	 * 列表查询
	 *
	 * @return
	 */
	@RequestMapping("/getFdDifferenceList")
	public String getFdDifferenceList(HttpServletRequest req, ModelMap model, JdjpFdDifference queryBean) {
		//查询版本号列表
		List<String> versionNumList = jdjpFdDifferenceService.getVersionNumList();
		if(StringUtils.isBlank(queryBean.getVersionNum()) && CollectionUtils.isNotEmpty(versionNumList)){
			queryBean.setVersionNum(versionNumList.get(0));
		}

		PageInfo<JdjpFdDifference> pageInfo = jdjpFdDifferenceService.pagination(getPageIndex(req), getPageSize(req),
				queryBean);


		//判断当前版本是否正在同步。只有最近的一个版本才有可能正在同步中，其他版本肯定是已经同步完成的。
		//策略是只有同步完一个版本，才会去同步下一个版本，所以只有最近一个版本才有可能正在同步中
		boolean runningFlag = false;
		if(CollectionUtils.isNotEmpty(versionNumList) && versionNumList.get(0).equals(queryBean.getVersionNum())){
			//放置是否正在执行标志
			String flag = jdCacheService.get(CarryPriceConstants.FD_COMPARE_IS_RUNNING_FLAG);
			if(StringUtils.isNotBlank(flag) && flag.equals("1")){
				runningFlag = true;
			}
		}

		//查询机场信息和航司信息
		model.put("runningFlag", runningFlag);
		model.put("pageInfo", pageInfo);
		model.put("versionNumList", versionNumList);
		model.put("aircorpList", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
		model.put("airportList", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
		model.put("queryBean", queryBean);
		return "fd/fdDifference/fdDifferenceList";
	}

	/**
	 * 功能描述: 下载数据
	 * @param:
	 * @return:
	 */
	@RequestMapping("/downLoadExcel")
	public void downLoadExcel(HttpServletRequest req, HttpServletResponse res, JdjpFdDifference queryBean) {
		jdjpFdDifferenceService.downLoadExcel(req, res, queryBean);
	}


}
