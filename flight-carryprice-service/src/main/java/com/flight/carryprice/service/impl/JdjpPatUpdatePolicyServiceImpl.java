package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.enums.PrefixEnum;
import com.flight.carryprice.common.enums.SourceEnum;
import com.flight.carryprice.common.enums.StateEnum;
import com.flight.carryprice.common.enums.SyncStatusEnum;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.CreatTicIdUtil;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.PatCarryPriceUtil;
import com.flight.carryprice.entity.JdjpPatFlightSeatConfig;
import com.flight.carryprice.entity.JdjpPatUpdatePolicy;
import com.flight.carryprice.esutil.ESUtils;
import com.flight.carryprice.manager.JdjpPatFlightSeatManager;
import com.flight.carryprice.manager.JdjpPatUpdatePolicyManager;
import com.flight.carryprice.service.JdjpPatUpdatePolicyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.model.enmus.StatusEnum;
import com.jd.airplane.flight.base.vo.airline.Airline;
import com.jd.airplane.flight.base.vo.cabin.Cabin;
import com.jd.es.client.ESClientOperations;
import com.jd.es.client.domain.client.query.ESResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/20 18:51.
 */
@Service
public class JdjpPatUpdatePolicyServiceImpl extends BaseServiceImpl<JdjpPatUpdatePolicy> implements JdjpPatUpdatePolicyService {
    private static Logger LOGGER = Logger.getLogger(JdjpPatUpdatePolicyServiceImpl.class);
    @Resource
    private JdjpPatUpdatePolicyManager jdjpPatUpdatePolicyManager;
    @Resource
    private JdjpPatFlightSeatManager jdjpPatFlightSeatManager;
    @Autowired(required = false)
    private ESClientOperations selfWorkerOrderTxtClient;
    @Override
    public PageInfo<JdjpPatUpdatePolicy> pagination(Integer pageIndex, Integer pageSize, JdjpPatUpdatePolicy queryBean) {
        PageHelper.startPage(pageIndex, pageSize);
        List<JdjpPatUpdatePolicy> list = jdjpPatUpdatePolicyManager.queryList(queryBean);
        for (JdjpPatUpdatePolicy jdjpPatUpdatePolicy : list){
            try {
                if (jdjpPatUpdatePolicy.getAirlines().startsWith(PrefixEnum.PREFIX_PAT_CARRYPRICE.getCode())){
                    String airLines = ESUtils.queryEsDataValue(selfWorkerOrderTxtClient,
                            jdjpPatUpdatePolicy.getAirlines());
                    jdjpPatUpdatePolicy.setAirlines(airLines);
                }
            }catch (Exception e){
                LOGGER.error("Es 取航班舱位信息异常", e);
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public Object addUpdatePolicy(JdjpPatUpdatePolicy policy) {
        policy.setPlanQuartzTime(DateUtil.stringToDate(policy.getPlanTime(), DateUtil.DATE_FMT3));
        policy.setSyncStatus(Byte.valueOf(SyncStatusEnum.WAITING.getCode()));
        policy.setRemark(SyncStatusEnum.WAITING.getDesc());
        policy.setSource(SourceEnum.MANUAL.getCode());
        policy.setFlightNo(policy.getFlightNo().toUpperCase());
        policy.setSeat(policy.getSeat().toUpperCase());
        Airline airline = getAirlineList(policy.getAirWays(), policy.getDepCode(), policy.getArrCode());
        if (null == airline){
            return "获取航线信息为空 ,请检查航线是否存在 是否开启状态";
        }
        policy.setDistance(airline.getDistance());
        Cabin cabin = getCabin(policy.getAirWays(), policy.getSeat());
        if (null == cabin){
            return "获取舱位信息为空,请检查[航司] [舱位]参数";
        }
        policy.setDiscount(BigDecimal.valueOf(cabin.getDiscount()));
        policy.setSeatType(cabin.getType().byteValue());
        policy.setSeatLevel(cabin.getLevel());
        policy.setAirlines(PatCarryPriceUtil.pasAirLines(policy.getAirWays(), policy.getDepCode(), policy.getArrCode(), policy.getFlightNo(), policy.getSeat(), policy.getDeptDate(), policy.getReserveTimeDuration(), policy.getDistance(), policy.getSeatType(), policy.getDiscount(), policy.getSeatLevel()));
        return super.insertSelective(policy);

    }

    @Override
    public Airline getAirlineList(String airCorpCode, String depAirportCode, String arrAirportCode) {
        Airline airline = new Airline();
        airline.setAirCorpCode(airCorpCode);
        airline.setDepAirportCode(depAirportCode);
        airline.setArrAirportCode(arrAirportCode);
        //目前不生效
        airline.setStatus(StatusEnum.ONLINE);
        List<Airline> airlines = BaseDataUtils.getAirlineListWithCondition(airline);
        if (CollectionUtils.isEmpty(airlines)){
            return null;
        }
        //过滤掉下线的航线
        for (Airline air : airlines){
            if (air.getStatus().equals(StatusEnum.ONLINE)){
                return air;
            }
        }
       return null;
    }

    @Override
    public Cabin getCabin(String airCorpCode, String adultCode) {
        Cabin cabin = new Cabin();
        cabin.setAirCorpCode(airCorpCode);
        cabin.setAdultCode(adultCode);
        List<Cabin> cabinList = BaseDataUtils.getCabinList(QueryTypeEnum.UNION_KEY, cabin);
        if (CollectionUtils.isEmpty(cabinList)){
            return null;
        }
        return cabinList.get(0);
    }

    @Override
    public void patAutoUpdatePolicy(byte airLineType, String deptDate) {
        JdjpPatFlightSeatConfig jdjpPatFlightSeatConfig = new JdjpPatFlightSeatConfig();
        jdjpPatFlightSeatConfig.setAirlineType(airLineType);
        jdjpPatFlightSeatConfig.setState(Byte.valueOf(StateEnum.VALID.getCode()));
        //按照热门类型 查询对应航班舱位配置信息
        List<JdjpPatFlightSeatConfig> jdjpPatFlightSeatConfigs = jdjpPatFlightSeatManager.queryList(jdjpPatFlightSeatConfig);
        if (CollectionUtils.isEmpty(jdjpPatFlightSeatConfigs)){
            return;
        }
        String airLines = assemAirLinesInEs(jdjpPatFlightSeatConfigs);
        JdjpPatUpdatePolicy jdjpPatUpdatePolicy = makeJdjpPatUpdatePolicy(airLines, deptDate, airLineType);
        this.insertSelective(jdjpPatUpdatePolicy);
    }
    private JdjpPatUpdatePolicy makeJdjpPatUpdatePolicy(String airlines, String deptDate, byte airlineType){
        JdjpPatUpdatePolicy jdjpPatUpdatePolicy = new JdjpPatUpdatePolicy();
        Date currentDay = new Date();
        jdjpPatUpdatePolicy.setAirlines(airlines);
        jdjpPatUpdatePolicy.setDepDate(deptDate);
        jdjpPatUpdatePolicy.setAirlineType(airlineType);
        jdjpPatUpdatePolicy.setPlanQuartzTime(currentDay);
        jdjpPatUpdatePolicy.setSource(SourceEnum.AUTO.getCode());
        jdjpPatUpdatePolicy.setSyncStatus(Byte.valueOf(SyncStatusEnum.WAITING.getCode()));
        jdjpPatUpdatePolicy.setRemark(SyncStatusEnum.WAITING.getDesc());
        jdjpPatUpdatePolicy.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
        return jdjpPatUpdatePolicy;
    }
    private String assemAirLinesInEs(List<JdjpPatFlightSeatConfig> lists) {
        StringBuffer airline = new StringBuffer();
        for (JdjpPatFlightSeatConfig seatConfig : lists) {
            String eachAirline = PatCarryPriceUtil.pasAirLines(seatConfig.getAirWays(), seatConfig.getDepCode(), seatConfig.getArrCode(), seatConfig.getFlightNo(), seatConfig.getSeat(), seatConfig.getDeptDate(), seatConfig.getReserveTimeDuration(), seatConfig.getDistance(), seatConfig.getSeatType(), seatConfig.getDiscount(), seatConfig.getSeatLevel());
            airline.append(eachAirline).append(',');
        }
        String airlines = airline.substring(0, airline.length() - 1);
        String bussinessId = CreatTicIdUtil.getPk(PrefixEnum.PREFIX_PAT_CARRYPRICE.getCode());
        ESResult esResult = ESUtils.insertEsData(selfWorkerOrderTxtClient, bussinessId,
                airlines);
        if (esResult == null) {
            throw new RuntimeException("创建CreateRowData异常---------返回值为空");
        }
        return bussinessId;
    }
}
