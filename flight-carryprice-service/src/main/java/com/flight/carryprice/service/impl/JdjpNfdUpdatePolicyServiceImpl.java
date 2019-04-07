package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.enums.AirlineTypeEnum;
import com.flight.carryprice.common.enums.PrefixEnum;
import com.flight.carryprice.common.enums.SourceEnum;
import com.flight.carryprice.common.enums.SyncStatusEnum;
import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.CreatTicIdUtil;
import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import com.flight.carryprice.entity.JdjpNfdUpdatePolicy;
import com.flight.carryprice.esutil.ESUtils;
import com.flight.carryprice.manager.JdjpNfdUpdatePolicyManager;
import com.flight.carryprice.service.JdjpAirwaysTicketTypeService;
import com.flight.carryprice.service.JdjpNfdUpdatePolicyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.vo.airline.Airline;
import com.jd.es.client.ESClientOperations;
import com.jd.es.client.domain.client.query.ESResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

@Service
public class JdjpNfdUpdatePolicyServiceImpl extends BaseServiceImpl<JdjpNfdUpdatePolicy> implements JdjpNfdUpdatePolicyService {
	private static Logger LOGGER = Logger.getLogger(JdjpNfdUpdatePolicyServiceImpl.class);
	private static final String SPLIT = ",";
	private static final String COLON = ":";
	private static final String SPRIT = "/";


	@Resource
	private JdjpNfdUpdatePolicyManager jdjpNfdUpdatePolicyManager;

	@Resource
	private JdjpAirwaysTicketTypeService jdjpAirwaysTicketTypeService;

	@Autowired(required = false)
	private ESClientOperations selfWorkerOrderTxtClient;

	@Override
	public PageInfo<JdjpNfdUpdatePolicy> pagination(Integer pageIndex, Integer pageSize, JdjpNfdUpdatePolicy queryBean) {
		PageHelper.startPage(pageIndex, pageSize);
		List<JdjpNfdUpdatePolicy> jdjpNfdUpdatePolicyList = jdjpNfdUpdatePolicyManager.queryList(queryBean);
		// 单条航线类型直接取数据库 多条则存储的是ES的key
		for (JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy : jdjpNfdUpdatePolicyList) {
			if (jdjpNfdUpdatePolicy.getAirlines().startsWith(PrefixEnum.PREFIX_NFD_CARRYPRICE.getCode())) {
				jdjpNfdUpdatePolicy.setAirlines(ESUtils.queryEsDataValue(selfWorkerOrderTxtClient, jdjpNfdUpdatePolicy.getAirlines()));
			}
		}
		return new PageInfo<>(jdjpNfdUpdatePolicyList);
	}

	@Override
	public List<Airline> getOpenAirlineList(Airline record, List<JdjpAirwaysTicketType> jdjpAirwaysTicketTypes) {
		List<Airline> airlineList = BaseDataUtils.getAirlineListWithCondition(record);
		if (airlineList == null){
			return null;
		}
		if (CollectionUtils.isEmpty(jdjpAirwaysTicketTypes)){
			return airlineList;
		}
		Set jdjpClosedAirwaySet = new HashSet<String>();
		for (JdjpAirwaysTicketType jdjpAirwaysTicketType : jdjpAirwaysTicketTypes){
			jdjpClosedAirwaySet.add(jdjpAirwaysTicketType.getAirways());
		}
		//剔除关闭航司
		Iterator<Airline> iterator = airlineList.iterator();
		while (iterator.hasNext()){
			Airline airline = iterator.next();
			String airWays = airline.getAirCorpCode();
			if (jdjpClosedAirwaySet.contains(airWays)){
				iterator.remove();
			}
		}
		return airlineList;
	}

	@Override
	public void nfdAutoUpdatePolicy(byte airLineType, String deptDate) {
		//根据热门类型获取航线
		Airline airline = new Airline();
		airline.setNfdType(Integer.valueOf(airLineType));
		List<Airline> airlines = BaseDataUtils.getAirlineListWithCondition(airline);
		if (CollectionUtils.isEmpty(airlines)){
			return;
		}
		//入es
		String airLine = assemAirLinesInEs(airlines);
		JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy = getJdjpNfdUpdatePolicy(airLine, deptDate, airLineType);
		this.insertSelective(jdjpNfdUpdatePolicy);
	}
	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description  <br>装JdjpNfdUpdatePolicy<br/>
	 * 				 <br>实体 Source = 自动 SyncStatus = 未同步 执行任务时间 = 当前时间<br/>
	 * @Date 11:13 2019/3/28
	 * @Param [airline, deptDate, airlineType]
	 * @return com.flight.carryprice.entity.JdjpNfdUpdatePolicy
	 **/
	private JdjpNfdUpdatePolicy getJdjpNfdUpdatePolicy(String airline, String deptDate, byte airlineType){
		JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy = new JdjpNfdUpdatePolicy();
		jdjpNfdUpdatePolicy.setAirlines(airline);
		jdjpNfdUpdatePolicy.setDepDate(deptDate);
		jdjpNfdUpdatePolicy.setAirlineType(airlineType);
		jdjpNfdUpdatePolicy.setSource(SourceEnum.AUTO.getCode());
		jdjpNfdUpdatePolicy.setSyncStatus(Byte.valueOf(SyncStatusEnum.WAITING.getCode()));
		jdjpNfdUpdatePolicy.setRemark(SyncStatusEnum.WAITING.getDesc());
		Date currentDay = new Date();
		jdjpNfdUpdatePolicy.setPlanQuartzTime(currentDay);
		jdjpNfdUpdatePolicy.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
		return jdjpNfdUpdatePolicy;
	}
	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description 航线入es 返回主键key
	 * @Date 11:03 2019/3/28
	 * @Param [airlineList]
	 * @return java.lang.String
	 **/
	private String assemAirLinesInEs(List<Airline> airlineList){
		String airlines = getAirlines(airlineList);
		String bussinessId = CreatTicIdUtil.getPk(PrefixEnum.PREFIX_NFD_CARRYPRICE.getCode());
		ESResult esResult = ESUtils.insertEsData(selfWorkerOrderTxtClient, bussinessId,
				airlines);
		if (esResult == null) {
			throw new RuntimeException("创建CreateRowData异常---------返回值为空");
		}
		return bussinessId;
	}
	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description 拼接航线数据 格式：航司:出发/到达，航司:出发/到达
	 * @Date 11:01 2019/3/28
	 * @Param [airlines]
	 * @return java.lang.String
	 **/
	private String getAirlines(List<Airline> airlines){
		StringBuffer airlineSb = new StringBuffer();
		for (Airline airline : airlines){
			airlineSb.append(airline.getAirCorpCode()).append(COLON)
			.append(airline.getDepAirportCode()).append(SPRIT)
			.append(airline.getArrAirportCode()).append(SPLIT);
		}
		return airlineSb.deleteCharAt(airlineSb.length() - 1).toString();
	}

	@Override
	public int save(JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy) {
		String airlineType = String.valueOf(jdjpNfdUpdatePolicy.getAirlineType());
		//普通航信类型 直接存库
		if (AirlineTypeEnum.ORDINARY.getCode().equals(airlineType)) {
			if (StringUtils.isEmpty(jdjpNfdUpdatePolicy.getDepCode())) {
				jdjpNfdUpdatePolicy.setDepCode("***");
			}
			if (StringUtils.isEmpty(jdjpNfdUpdatePolicy.getArrCode())) {
				jdjpNfdUpdatePolicy.setArrCode("***");
			}
			if (StringUtils.isEmpty(jdjpNfdUpdatePolicy.getAirWays())) {
				jdjpNfdUpdatePolicy.setAirWays("***");
			}
			jdjpNfdUpdatePolicy.setAirlines(jdjpNfdUpdatePolicy.getAirWays() + COLON + jdjpNfdUpdatePolicy.getDepCode()
					+ SPRIT + jdjpNfdUpdatePolicy.getArrCode());
		} else {
			// 热门类型 反查 入ES
			Airline record = new Airline();
			if (StringUtils.isNotEmpty(jdjpNfdUpdatePolicy.getDepCode())) {
				record.setDepAirportCode(jdjpNfdUpdatePolicy.getDepCode());
			}
			if (StringUtils.isNotEmpty(jdjpNfdUpdatePolicy.getArrCode())) {
				record.setArrAirportCode(jdjpNfdUpdatePolicy.getArrCode());
			}
			if (StringUtils.isNotEmpty(jdjpNfdUpdatePolicy.getAirWays())) {
				record.setAirCorpCode(jdjpNfdUpdatePolicy.getAirWays());
			}
			record.setNfdType(jdjpNfdUpdatePolicy.getAirlineType().intValue());
			List<JdjpAirwaysTicketType> jdjpAirwaysTicketTypes = jdjpAirwaysTicketTypeService.queryCloseSwitchTypeList();
			List<Airline> airlineList = this.getOpenAirlineList(record, jdjpAirwaysTicketTypes);
			if (CollectionUtils.isEmpty(airlineList)) {
				throw new SystemException("反查航线类型无数据");
			} else {
				String airlines = getAirlines(airlineList);
				//测试环境 es无法连接 暂用db代替
				boolean isTestEnv = false;
				if (isTestEnv){
					jdjpNfdUpdatePolicy.setAirlines(airlines);
				}else {
					String bussinessId = CreatTicIdUtil.getPk(PrefixEnum.PREFIX_NFD_CARRYPRICE.getCode());
					ESResult esResult = ESUtils.insertEsData(selfWorkerOrderTxtClient, bussinessId, airlines);
					LOGGER.info("esResult = " + esResult.isSuccess());
					if (esResult == null || !esResult.isSuccess()) {
						LOGGER.error("存储ES 返回数据= " + esResult.getContent());
						throw new SystemException("插入ES 失败");
					}
					jdjpNfdUpdatePolicy.setAirlines(bussinessId);
				}
			}
		}
		return super.insertSelective(jdjpNfdUpdatePolicy);
		}


}