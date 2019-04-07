package com.flight.carryprice.quartz.service.impl;

import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.enums.*;
import com.flight.carryprice.common.util.*;
import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.entity.JdjpFdUpdatePolicy;
import com.flight.carryprice.entity.JdjpRunparameters;
import com.flight.carryprice.gds.service.GdsInterfaceService;
import com.flight.carryprice.manager.JdjpAirwaysTicketTypeManager;
import com.flight.carryprice.manager.JdjpFdCarryPriceInfoManager;
import com.flight.carryprice.manager.JdjpRunparametersManager;
import com.flight.carryprice.quartz.service.FdCarryPriceUpdateByPolicyTaskService;
import com.flight.carryprice.service.JdjpFdCarryPriceInfoRedisService;
import com.flight.carryprice.service.JdjpFdCarryPriceInfoService;
import com.flight.carryprice.service.JdjpFdUpdatePolicyService;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.airline.Airline;
import com.jd.airplane.flight.base.vo.cabin.Cabin;
import com.jd.gds.dto.FDInfoDTO;
import com.jd.gds.dto.FDResponseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description fd运价维护更新 (扫描FD更新策略更新FD运价)
 * @date 2019/3/20 11:04
 * @updateTime
 */
@Service
public class FdCarryPriceUpdateByPolicyTaskServiceImpl implements FdCarryPriceUpdateByPolicyTaskService{

    private static Logger LOGGER = Logger.getLogger(FdCarryPriceUpdateByPolicyTaskServiceImpl.class);

    @Resource
    private JdjpFdUpdatePolicyService jdjpFdUpdatePolicyService;

    @Resource
    private JdjpRunparametersManager jdjpRunparametersManager;

    @Resource
    private JdjpFdCarryPriceInfoManager jdjpFdCarryPriceInfoManager;

    @Resource
    private JdjpFdCarryPriceInfoService jdjpFdCarryPriceInfoService;

    @Resource
    private JdjpAirwaysTicketTypeManager jdjpAirwaysTicketTypeManager;

    @Resource
    private JdjpFdCarryPriceInfoRedisService jdjpFdCarryPriceInfoRedisService;

    @Resource
    private GdsInterfaceService gdsInterfaceService;

    /**
     * 功能描述: fd运价维护更新 (扫描FD更新策略更新FD运价)
     * @param:
     * @return:
     */
    @Override
    public void updateFdCarryPriceByPolicy(){

        JdjpFdUpdatePolicy policy = jdjpFdUpdatePolicyService.selectFdPolicyOne();
        if(policy == null ){
            LOGGER.info("fd运价维护更新 - 待处理的FD运价更新策略记录为空，直接返回");
            return;
        }

        //获取配置
        String routeType = CarryPriceConstants.FD_UPDATE_ROUTE_TYPE_DEFAULT;
        String officeNo = CarryPriceConstants.FD_UPDATE_OFFICE_NO_DEFALUT;
        Integer days = CarryPriceConstants.FD_UPDATE_DAY_DEFALUT;
        String specialAirline = null;
        JdjpRunparameters run = jdjpRunparametersManager.queryByName(CarryPriceConstants.FD_UPDATE_ROUTE_TYPE);
        if(run != null && StringUtils.isNotBlank(run.getValue())){
            routeType = run.getValue();
        }
        run = jdjpRunparametersManager.queryByName(CarryPriceConstants.FD_UPDATE_OFFICE_NO);
        if(run != null && StringUtils.isNotBlank(run.getValue())){
            officeNo = run.getValue();
        }
        run = jdjpRunparametersManager.queryByName(CarryPriceConstants.FD_UPDATE_DAY);
        if(run != null && RegularUtil.regexNumber(run.getValue())){
            days = Integer.valueOf(run.getValue());
        }
        run = jdjpRunparametersManager.queryByName(CarryPriceConstants.FD_UPDATE_SPECIAL_AIRLINE);
        if(run != null ){
            specialAirline = run.getValue();
        }

        //是否有起飞日期: true-有,false-无
        boolean isHaveDepDate = policy.getDepDate() != null ? true : false;
        //更新为同步中状态
        updateFdPolicyState(policy, SyncStatusEnum.SYNCING.getCode(), SyncStatusEnum.SYNCING.getDesc() );

        //查询航线信息
        Airline record = new Airline();
        if(!"***".equals(policy.getAirWays())){
            record.setAirCorpCode(policy.getAirWays());
        }
        if(!"***".equals(policy.getDepCode())){
            record.setDepAirportCode(policy.getDepCode());
        }
        if(!"***".equals(policy.getArrCode())){
            record.setArrAirportCode(policy.getArrCode());
        }
        //航线类型，热门和非热门
        if(policy.getAirlineType() != null){
            record.setFdType(Integer.valueOf(policy.getAirlineType()));
        }
        List<Airline> airlineList = BaseDataUtils.getAirlineListWithCondition(record);
        if(CollectionUtils.isEmpty(airlineList)){
            LOGGER.info("fd运价维护更新 - 没有航线信息，直接返回************");
            //更新为完成状态
            updateFdPolicyState(policy, SyncStatusEnum.OVER.getCode(), SyncStatusEnum.OVER.getDesc() );
            return;
        }

        //过滤航线，往返航线
        Map<String, Airline> map = new HashMap<>();
        for (Airline airline : airlineList){
            //判断航司是否关闭，如果关闭，则不更新
            boolean isCloseAirways = isCloseAirways(airline);
            if(isCloseAirways){
                continue;
            }
            StringBuilder oneWayBuffer = new StringBuilder();
            String oneWayKey = oneWayBuffer.append(airline.getAirCorpCode()).append(airline.getDepAirportCode())
                    .append(airline.getArrAirportCode()).toString();
            StringBuilder roundBuffer = new StringBuilder();
            String roundKey = roundBuffer.append(airline.getAirCorpCode()).append(airline.getArrAirportCode())
                    .append(airline.getDepAirportCode()).toString();

            if (routeType.equals(CarryPriceConstants.FD_UPDATE_ROUTE_TYPE_DEFAULT)
                    && (map.containsKey(oneWayKey) || map.containsKey(roundKey))) {
                continue;
            }
            map.put(oneWayKey, airline);
        }

        for (Airline airline : map.values()) {
            if(isHaveDepDate){
                LOGGER.info("**********有起飞时间：*********************************");
                updateFDWithDepDate(policy, airline, specialAirline, officeNo, routeType);
            }else{
                LOGGER.info("**********没有起飞时间：*********************************");
                updateFDWithoutDepDate(policy, airline, specialAirline, officeNo, routeType, days);
                policy.setDepDate(null);// 原数据没有起飞日期，则需要置空
            }
        }
        //更新为完成状态
        updateFdPolicyState(policy, SyncStatusEnum.OVER.getCode(), SyncStatusEnum.OVER.getDesc() );
    }



    /**
     * 功能描述: 有起飞时间 运价更新 处理逻辑
     * @param:
     * @return:
     */
    public void updateFDWithDepDate(JdjpFdUpdatePolicy policy, Airline airline, String specialAirline, String officeNo, String routeType){
        LOGGER.info("起飞时间不为空的 运价更新");
        Map<String, JdjpFdCarryPriceInfo> infoMap = new HashMap<>();
        boolean flag = reqGDSFdAndDeal(policy, airline, infoMap, specialAirline, officeNo, routeType, null);
        if (flag && !infoMap.isEmpty()) {
            updateFDPriceAndRedisWithDepDate(infoMap, airline, routeType);
        }
    }

    /**
     * 功能描述: 没有起飞时间 运价更新 递归处理逻辑
     * @param:
     * @return:
     */
    private void updateFDWithoutDepDate(JdjpFdUpdatePolicy policy, Airline airline, String specialAirline, String officeNo, String routeType, Integer days){
        LOGGER.info("没有起飞时间 运价更新,递归处理FD运价");
        policy.setDepDate(new Date());//无起飞时间，设置成当前时间
        Map<String, JdjpFdCarryPriceInfo> infoMap = new HashMap<>();
        boolean flag = reqGDSFdAndDeal(policy, airline, infoMap, specialAirline, officeNo,routeType, days);
        if (flag && !infoMap.isEmpty()) {
            updateFDPriceAndRedisWithoutDepDate( infoMap, airline, routeType);
        }
    }

    private boolean reqGDSFdAndDeal(JdjpFdUpdatePolicy policy, Airline airline, Map<String, JdjpFdCarryPriceInfo> infoMap, String specialAirline, String officeNo, String routeType, Integer days){

        LOGGER.info("FD更新时间：" + DateUtil.dateToString(policy.getDepDate(), DateUtil.DATE_FMT1));
        FDResponseDTO fdResponse = reqGdsFd(policy, airline, officeNo);
        if (fdResponse != null && CodeEnums.SUCCESS.getCode().equals(fdResponse.getResponseCode())
                && CollectionUtils.isNotEmpty(fdResponse.getFdResult())) {
            List<FDInfoDTO> fdInfoList = fdResponse.getFdResult();
            //把来源于NFD的运价置为无效
            updateNFD(airline);
            //判断是否是特殊航司，是否有淡旺季
            boolean isSpecialAirline = isSpecialAirline(specialAirline, airline);
            if(isSpecialAirline){
                //特殊航司组装运价Map
                LOGGER.info("特殊航司组装运价Map");
                mateSpecialAirwaysReturnMap( fdInfoList, policy, airline, infoMap, specialAirline, routeType, days);
            }else{
                //普通航司组装运价Map
                LOGGER.info("普通航司组装运价Map");
                mateCommonAirwaysReturnMap(fdInfoList, policy, airline, infoMap, routeType, days);
            }

            /**** 如果没有起飞时间，则days去总控参数中的数值，递归组装运价信息；如果有起飞时间，则days传入空值*******/
            if(days != null){
                //gds返回的最小的endDate
                String endDate = getGdsEndDate(fdInfoList);
                // 判断当前时间+day>endDate，是：递归，否结束
                Date end = DateUtil.stringToDate(endDate, DateUtil.DATE_FMT1);
                boolean flag = DateUtil.compare_date(DateUtil.dateAddDays(new Date(), days), end) > 0;
                LOGGER.info("增加后的时间:" + DateUtil.dateToString(DateUtil.dateAddDays(new Date(), days), DateUtil.DATE_FMT1)
                        + "，比较结果：" + flag);
                if (flag) {
                    policy.setDepDate(DateUtil.dateAddDays(end, 1));
                    return reqGDSFdAndDeal(policy, airline, infoMap, specialAirline, officeNo, routeType, days);
                }
            }
        } else {
            LOGGER.info("请求FD运价接口返回的异常信息：" + fdResponse.getResponseMessage());
            return false;
        }
        return true;
    }

    /**
     * 功能描述: 有起飞时间  运价更新，更新数据库和缓存
     * @param:
     * @return:
     */
    public void updateFDPriceAndRedisWithDepDate(Map<String, JdjpFdCarryPriceInfo> infoMap, Airline airline, String routeType){

        List<JdjpFdCarryPriceInfo> list = new ArrayList<>();
        for (JdjpFdCarryPriceInfo carry : infoMap.values()) {
            list.add(carry);
        }

        List<JdjpFdCarryPriceInfo> toInsertList = new ArrayList<JdjpFdCarryPriceInfo>();
        for (JdjpFdCarryPriceInfo carryPriceInfo : list) {
            //jdjpFdCarryPriceInfoService.insertSelective(carryPriceInfo);
            toInsertList.add(carryPriceInfo);
        }
        jdjpFdCarryPriceInfoManager.insertBatch(toInsertList);
        jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(toInsertList);

        //往返的增加返程的运价
        if(StringUtils.isNotBlank(routeType) && routeType.equals(CarryPriceConstants.FD_UPDATE_ROUTE_TYPE_DEFAULT)){
            List<JdjpFdCarryPriceInfo> backInsertList = new ArrayList<JdjpFdCarryPriceInfo>();
            for (JdjpFdCarryPriceInfo carryPriceInfo : list) {
                JdjpFdCarryPriceInfo backCarryPriceInfo = new JdjpFdCarryPriceInfo();
                BeanUtils.copyProperties(carryPriceInfo, backCarryPriceInfo);
                backCarryPriceInfo.setDepCode(carryPriceInfo.getArrCode());
                backCarryPriceInfo.setArrCode(carryPriceInfo.getDepCode());
                backCarryPriceInfo.setId(null);
                backInsertList.add(backCarryPriceInfo);
            }
            jdjpFdCarryPriceInfoManager.insertBatch(backInsertList);
            jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(backInsertList);
        }

        LOGGER.info("有起飞时间 === FD原航线[" + airline.getAirCorpCode() + "-" + airline.getDepAirportCode() + "-" + airline.getArrAirportCode()
                + "]FD数据置成无效。是否往返更新(1-往返；2-单程)：route=" + routeType + "。插入去程数据" + toInsertList.size() + "条。");
    }


    /**
     * 功能描述: 没有起飞时间 运价更新，更新数据库和缓存
     * @param:
     * @return:
     */
    public void updateFDPriceAndRedisWithoutDepDate(Map<String, JdjpFdCarryPriceInfo> infoMap, Airline airline, String routeType){

        List<JdjpFdCarryPriceInfo> list = new ArrayList<>();
        for (JdjpFdCarryPriceInfo carry : infoMap.values()) {
            list.add(carry);
        }
        // 将原来启用的置成无效，以下为条件
        JdjpFdCarryPriceInfo toBean = new JdjpFdCarryPriceInfo();
        toBean.setAirWays(airline.getAirCorpCode());
        toBean.setDepCode(airline.getDepAirportCode());
        toBean.setArrCode(airline.getArrAirportCode());
        toBean.setState(new Byte(StateEnum.VALID.getCode()));
        List<JdjpFdCarryPriceInfo> oldToList = jdjpFdCarryPriceInfoManager.queryList(toBean);
        if ( CollectionUtils.isNotEmpty(oldToList)) {
            //置为无效
            jdjpFdCarryPriceInfoManager.updateBatchToInvaildByParams(toBean);
            // 删除redis
            jdjpFdCarryPriceInfoRedisService.delCarryPriceRedis(oldToList);
        }
        List<JdjpFdCarryPriceInfo> toInsertList = new ArrayList<JdjpFdCarryPriceInfo>();
        for (JdjpFdCarryPriceInfo carryPriceInfo : list) {
            //jdjpFdCarryPriceInfoService.insertSelective(carryPriceInfo);
            toInsertList.add(carryPriceInfo);
        }
        jdjpFdCarryPriceInfoManager.insertBatch(toInsertList);
        jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(toInsertList);

        //往返的增加返程的运价
        if(StringUtils.isNotBlank(routeType) && routeType.equals(CarryPriceConstants.FD_UPDATE_ROUTE_TYPE_DEFAULT)){
            JdjpFdCarryPriceInfo backBean = new JdjpFdCarryPriceInfo();
            backBean.setAirWays(airline.getAirCorpCode());
            backBean.setDepCode(airline.getArrAirportCode());
            backBean.setArrCode(airline.getDepAirportCode());
            backBean.setState(new Byte(StateEnum.VALID.getCode()));
            List<JdjpFdCarryPriceInfo> oldBackList = jdjpFdCarryPriceInfoManager.queryList(backBean);
            if ( CollectionUtils.isNotEmpty(oldBackList)) {
                //置为无效
                jdjpFdCarryPriceInfoManager.updateBatchToInvaildByParams(backBean);
                // 删除redis
                jdjpFdCarryPriceInfoRedisService.delCarryPriceRedis(oldBackList);
            }
            List<JdjpFdCarryPriceInfo> backInsertList = new ArrayList<JdjpFdCarryPriceInfo>();
            for (JdjpFdCarryPriceInfo carryPriceInfo : list) {
                JdjpFdCarryPriceInfo backCarryPriceInfo = new JdjpFdCarryPriceInfo();
                BeanUtils.copyProperties(carryPriceInfo, backCarryPriceInfo);
                backCarryPriceInfo.setDepCode(carryPriceInfo.getArrCode());
                backCarryPriceInfo.setArrCode(carryPriceInfo.getDepCode());
                backCarryPriceInfo.setId(null);
                backInsertList.add(backCarryPriceInfo);
            }
            jdjpFdCarryPriceInfoManager.insertBatch(backInsertList);
            jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(backInsertList);
        }

        LOGGER.info("没有起飞时间 === FD原航线[" + airline.getAirCorpCode() + "-" + airline.getDepAirportCode() + "-" + airline.getArrAirportCode()
                + "]FD数据置成无效。是否往返更新(1-往返；2-单程)：route=" + routeType + "。插入去程数据" + toInsertList.size() + "条。");
    }

    /**
     * 功能描述: 获取GDS返回数据中的最小的结束时间
     * @param:
     * @return:
     */
    private String getGdsEndDate(List<FDInfoDTO> fdInfoList){
        String endDate = null;
        for (FDInfoDTO fdInfo : fdInfoList) {
            if (endDate == null) {
                endDate = fdInfo.getEndDate();
            } else {
                endDate = fdInfo.getEndDate().compareTo(endDate) < 0 ? fdInfo.getEndDate() : endDate;
            }
        }
        return endDate;
    }

    /**
     * 功能描述: 特殊航司组装运价Map
     * @param:
     * @return:
     */
    private void mateSpecialAirwaysReturnMap(List<FDInfoDTO> fdInfoList, JdjpFdUpdatePolicy policy,  Airline airline, Map<String, JdjpFdCarryPriceInfo> infoMap, String specialAirline, String routeType, Integer days){
        Date current = DateUtil.stringToDate(DateUtil.dateToString(new Date(), DateUtil.DATE_FMT1), DateUtil.DATE_FMT1);
        for (FDInfoDTO fdInfo : fdInfoList) {
            // 如果FD开始时间小于当前时间,以当前时间为准
            Date startdate = DateUtil.stringToDate(fdInfo.getStartDate(), DateUtil.DATE_FMT1);
            if (DateUtil.compare_date(startdate, current) < 0) {
                startdate = current;
            }
            // FD结束时间默认2030-01-01,如果不为空，则使用返回的结束时间
            Date enddate = DateUtil.stringToDate("2030-01-01", DateUtil.DATE_FMT1);
            if (StringUtils.isNotBlank(fdInfo.getEndDate())) {
                enddate = DateUtil.stringToDate(fdInfo.getEndDate(), DateUtil.DATE_FMT1);
            }
            Cabin cabin = getCabin(fdInfo);
            if(cabin == null){
                LOGGER.info("舱位表中舱位为空，直接过滤掉！");
                continue;
            }

            List<JdjpFdCarryPriceInfo> dateList = getParamList(specialAirline, enddate);
            /* 匹配时间,返回组装的map */
            Map<String, JdjpFdCarryPriceInfo> map = mateDateReturnMap(dateList, startdate, enddate, fdInfo, airline, cabin, policy);
            //解决特殊航司中某些舱位不是高价也不是低价的现象
            if(map != null && map.size() > 0){
                infoMap.putAll(map);
            }else{
                JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, policy);
                fdCarryPriceInfo.setTakeOffEffectDate(startdate);
                fdCarryPriceInfo.setTakeOffExpireDate(enddate);
                putInfoMap(infoMap, fdCarryPriceInfo);
            }
            //有起飞时间，则单条更新缓存和数据库
            if( days == null){
                updateRedisAndDbWithDepDate(policy,fdInfo, airline, startdate, enddate, routeType);
            }
        }
    }

    /**
     * 功能描述: 普通航司组装运价Map
     * @param:
     * @return:
     */
    private void mateCommonAirwaysReturnMap(List<FDInfoDTO> fdInfoList, JdjpFdUpdatePolicy policy,  Airline airline, Map<String, JdjpFdCarryPriceInfo> infoMap, String routeType, Integer days){
        Date current = DateUtil.stringToDate(DateUtil.dateToString(new Date(), DateUtil.DATE_FMT1), DateUtil.DATE_FMT1);
        for (FDInfoDTO fdInfo : fdInfoList){
            // 如果FD开始时间小于当前时间,以当前时间为准
            Date startdate = DateUtil.stringToDate(fdInfo.getStartDate(), DateUtil.DATE_FMT1);
            if (DateUtil.compare_date(startdate, current) < 0) {
                startdate = current;
            }
            // FD结束时间默认2030-01-01,如果不为空，则使用返回的结束时间
            Date enddate = DateUtil.stringToDate("2030-01-01", DateUtil.DATE_FMT1);
            if (StringUtils.isNotBlank(fdInfo.getEndDate())) {
                enddate = DateUtil.stringToDate(fdInfo.getEndDate(), DateUtil.DATE_FMT1);
            }
            Cabin cabin = getCabin(fdInfo);
            if(cabin == null){
                LOGGER.info("舱位表中舱位为空，直接过滤掉！");
                continue;
            }

            JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, policy);
            fdCarryPriceInfo.setTakeOffEffectDate(startdate);
            fdCarryPriceInfo.setTakeOffExpireDate(enddate);
            putInfoMap(infoMap, fdCarryPriceInfo);

            //有起飞时间，则单条更新缓存和数据库
            if( days == null){
                updateRedisAndDbWithDepDate(policy,fdInfo, airline, startdate, enddate, routeType);
            }

        }
    }

    /**
     * 功能描述: 有起飞时间，则单条更新缓存和数据库
     * @param:
     * @return: 
     */
    private void updateRedisAndDbWithDepDate(JdjpFdUpdatePolicy policy,FDInfoDTO fdInfo, Airline airline, Date startdate, Date enddate, String routeType){
        // 根据航司二字码、舱位、出发城市三字码、到达城市三字码、状态（状态为启动1），查询运价表
        JdjpFdCarryPriceInfo carryPriceInfo = new JdjpFdCarryPriceInfo();
        carryPriceInfo.setAirWays(fdInfo.getCarrier());
        carryPriceInfo.setSeat(fdInfo.getSeatCode());
        carryPriceInfo.setDepCode(airline.getDepAirportCode());
        carryPriceInfo.setArrCode(airline.getArrAirportCode());
        carryPriceInfo.setState(new Byte(StateEnum.VALID.getCode()));
        matchRuleAndUpdate( policy, carryPriceInfo, startdate, enddate);
        if(routeType.equals(CarryPriceConstants.FD_UPDATE_ROUTE_TYPE_DEFAULT)){
            //交换出发到达
            JdjpFdCarryPriceInfo carryPriceInfoBack = new JdjpFdCarryPriceInfo();
            carryPriceInfoBack.setAirWays(fdInfo.getCarrier());
            carryPriceInfoBack.setSeat(fdInfo.getSeatCode());
            carryPriceInfoBack.setDepCode(airline.getArrAirportCode());
            carryPriceInfoBack.setArrCode(airline.getDepAirportCode());
            carryPriceInfoBack.setState(new Byte(StateEnum.VALID.getCode()));
            matchRuleAndUpdate( policy, carryPriceInfoBack, startdate, enddate);

        }
    }



    /**
     * 匹配时间,返回组装的map
     *
     * @param dateList
     * @param startdate
     * @param enddate
     * @param fdInfo
     * @param airline
     * @param cabin
     * @param fdUpdatePolicy
     * @throws Exception
     */
    private Map<String, JdjpFdCarryPriceInfo> mateDateReturnMap(List<JdjpFdCarryPriceInfo> dateList, Date startdate, Date enddate,
                                   FDInfoDTO fdInfo, Airline airline, Cabin cabin, JdjpFdUpdatePolicy fdUpdatePolicy) {
        Map<String, JdjpFdCarryPriceInfo> map = new HashMap<>();
        if(CollectionUtils.isEmpty(dateList)){
            LOGGER.info("没有特殊航司，直接返回null；不封装数据！");
            return null;
        }
        String fdRule = fdInfo.getRule();
        Date variableStart = null;
        LOGGER.info("特殊航线匹配时间,返回组装的map开始!");

        /* 循环总控参数时间List */
        for (JdjpFdCarryPriceInfo data : dateList) {
            //高价标识
            String longPrice = data.getLongPrice();
            //低价标识
            String lowPrice = data.getLowPrice();
            if (fdInfo.getCarrier().equals(data.getAirWays()) && (fdRule.contains(longPrice) || fdRule.contains(lowPrice))) {
                //配置的起时间
                Date startDateParam = data.getTakeOffEffectDate();
                //配置的止时间
                Date endDateParam = data.getTakeOffExpireDate();
                //startDate FD返回的起时间
                int startNum = startdate.compareTo(startDateParam);
                int endStartNum = enddate.compareTo(startDateParam);
                int startEndNum = startdate.compareTo(endDateParam);
                int endNum = enddate.compareTo(endDateParam);
                LOGGER.info("开始时间和结束时间进行对比----开始-----");
                LOGGER.info("startdate:" + DateUtil.dateToString(startdate, DateUtil.DATE_FMT1));
                LOGGER.info("startDateParam:" + DateUtil.dateToString(startDateParam, DateUtil.DATE_FMT1));
                LOGGER.info("endDateParam:" + DateUtil.dateToString(endDateParam, DateUtil.DATE_FMT1));
                LOGGER.info("endDate:" + DateUtil.dateToString(enddate, DateUtil.DATE_FMT1));
                LOGGER.info("startNum:" + startNum + " endStartNum:" + endStartNum + " startEndNum:" + startEndNum
                        + " endNum:" + endNum);
                if (variableStart != null) {
                    LOGGER.info("variableStart:" + DateUtil.dateToString(variableStart, DateUtil.DATE_FMT1));
                } else {
                    LOGGER.info("variableStart:null");
                }
                LOGGER.info("开始时间和结束时间进行对比----结束-----");
                if (startEndNum > 0 || endStartNum < 0) {
                    LOGGER.info("暂时先不考虑!");
                    //FD的起比配置的起大，FD的止比配置的止大
                } else if (startNum <= 0 && endNum >= 0) {
                    if (fdRule.contains(longPrice)) { // 高价
                        JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, fdUpdatePolicy);
                        fdCarryPriceInfo.setTakeOffEffectDate(startDateParam);
                        fdCarryPriceInfo.setTakeOffExpireDate(endDateParam);
                        putInfoMap(map, fdCarryPriceInfo);
                    } else if (fdRule.contains(lowPrice)) {
                        /* 第一条 */
                        if (null == variableStart ) {
                            if(startNum < 0){
                                JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, fdUpdatePolicy);
                                fdCarryPriceInfo.setTakeOffEffectDate(startdate);
                                fdCarryPriceInfo.setTakeOffExpireDate(DateUtil.dateAddDays(startDateParam, -1));
                                putInfoMap(map, fdCarryPriceInfo);
                            }
                            // 获取高价结束时间
                            variableStart = endDateParam;
                            continue;
                        }
                        /* 第二条 */
                        if (null != variableStart && DateUtil.compare_date(enddate, variableStart) > 0) {
                            JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, fdUpdatePolicy);
                            fdCarryPriceInfo.setTakeOffEffectDate(DateUtil.dateAddDays(variableStart, 1));
                            fdCarryPriceInfo.setTakeOffExpireDate(DateUtil.dateAddDays(startDateParam, -1));
                            putInfoMap(map, fdCarryPriceInfo);
                        }
                    }
                } else if (startNum <= 0 && endStartNum >= 0 && endNum < 0) {
                    if (fdRule.contains(longPrice)) { // 高价1条
                        JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, fdUpdatePolicy);
                        fdCarryPriceInfo.setTakeOffEffectDate(startDateParam);
                        fdCarryPriceInfo.setTakeOffExpireDate(enddate);
                        putInfoMap(map, fdCarryPriceInfo);
                    } else if (fdRule.contains(lowPrice)) {
                        JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, fdUpdatePolicy);
                        fdCarryPriceInfo.setTakeOffEffectDate(DateUtil.dateAddDays(variableStart, 1));
                        fdCarryPriceInfo.setTakeOffExpireDate(DateUtil.dateAddDays(startDateParam, -1));
                        putInfoMap(map, fdCarryPriceInfo);
                    }
                    /* 分割成高价1条 */
                } else if (startNum >= 0 && endNum <= 0 && endStartNum > 0) {
                    if (fdRule.contains(longPrice)) { // 高价1条
                        JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, fdUpdatePolicy);
                        fdCarryPriceInfo.setTakeOffEffectDate(startDateParam);
                        fdCarryPriceInfo.setTakeOffExpireDate(enddate);
                        putInfoMap(map, fdCarryPriceInfo);
                    }
                } else if (startNum > 0 && startEndNum <= 0 && endNum > 0) {
                    if (fdRule.contains(longPrice)) { // 高价1条
                        JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, fdUpdatePolicy);
                        fdCarryPriceInfo.setTakeOffEffectDate(startdate);
                        fdCarryPriceInfo.setTakeOffExpireDate(endDateParam);
                        putInfoMap(map, fdCarryPriceInfo);
                    } else if (fdRule.contains(lowPrice)) {
                        /* 低价1条 */
                        if (null == variableStart) {
                            // 获取高价结束时间
                            variableStart = endDateParam;
                        } else if (DateUtil.compare_date(enddate, variableStart) > 0) {
                            /* 第二条 */
                            JdjpFdCarryPriceInfo fdCarryPriceInfo = formFdCarryPriceInfo(fdInfo, airline, cabin, fdUpdatePolicy);
                            fdCarryPriceInfo.setTakeOffEffectDate(DateUtil.dateAddDays(variableStart, 1));
                            fdCarryPriceInfo.setTakeOffExpireDate(DateUtil.dateAddDays(startDateParam, -1));
                            putInfoMap(map, fdCarryPriceInfo);
                        }
                    }
                }
            }
        }
        LOGGER.info("特殊航线匹配时间,返回组装的map结束!");
        return map;
    }


    /**
     * 功能描述: 有起飞时间的，则先更新数据库及缓存中的运价信息
     * @param:
     * @param
     * @param startDate
     * @param endDate
     * @param
     */
    public void matchRuleAndUpdate(JdjpFdUpdatePolicy fdUpdatePolicy, JdjpFdCarryPriceInfo carryPriceInfo, Date startDate, Date endDate) {

        List<JdjpFdCarryPriceInfo> carryPriceInfoToList = jdjpFdCarryPriceInfoService.select(carryPriceInfo);
        if(CollectionUtils.isEmpty(carryPriceInfoToList)){
            return;
        }

        for (JdjpFdCarryPriceInfo fdCarryPriceInfoTo : carryPriceInfoToList ){
            int i = startDate.compareTo(fdCarryPriceInfoTo.getTakeOffEffectDate());
            int j = endDate.compareTo(fdCarryPriceInfoTo.getTakeOffEffectDate());
            int i1 = startDate.compareTo(fdCarryPriceInfoTo.getTakeOffExpireDate());
            int j1 = endDate.compareTo(fdCarryPriceInfoTo.getTakeOffExpireDate());
            List<JdjpFdCarryPriceInfo> listForEdit = new ArrayList<JdjpFdCarryPriceInfo>();
            if (i1 > 0 || j < 0) {
                LOGGER.info("只需插入新数据" + JacksonUtil.obj2json(fdCarryPriceInfoTo));
            } else if (i <= 0 && j1 >= 0) {
                // 该条数据作废,改为禁用状态
                fdCarryPriceInfoTo.setState(new Byte(StateEnum.INVALID.getCode()));
                fdCarryPriceInfoTo.setUpdateTime(new Date());
                jdjpFdCarryPriceInfoService.updateByPrimaryKey(fdCarryPriceInfoTo);
                // 删除redis
                listForEdit.add(fdCarryPriceInfoTo);
                jdjpFdCarryPriceInfoRedisService.delCarryPriceRedis(listForEdit);
            } else if (i <= 0 && j >= 0 && j1 < 0) {
                // 更新起始时间
                fdCarryPriceInfoTo.setTakeOffEffectDate(DateUtil.dateAddDays(endDate, 1));
                fdCarryPriceInfoTo.setUpdateTime(new Date());
                fdCarryPriceInfoTo.setAirlineType(fdUpdatePolicy.getAirlineType());
                jdjpFdCarryPriceInfoService.updateByPrimaryKey(fdCarryPriceInfoTo);
                // 更新redis
                listForEdit.add(fdCarryPriceInfoTo);
                jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(listForEdit);
            } else if (i > 0 && i1 <= 0 && j1 >= 0) {
                // 更新中止时间
                fdCarryPriceInfoTo.setTakeOffExpireDate(DateUtil.dateAddDays(startDate, -1));
                fdCarryPriceInfoTo.setUpdateTime(new Date());
                fdCarryPriceInfoTo.setAirlineType(fdUpdatePolicy.getAirlineType());
                jdjpFdCarryPriceInfoService.updateByPrimaryKey(fdCarryPriceInfoTo);
                // 更新redis
                listForEdit.add(fdCarryPriceInfoTo);
                jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(listForEdit);
            } else {
                // i>0&&j1<0,不仅更新起始时间，还需更新中止时间，需添加一条
                Date enDates = fdCarryPriceInfoTo.getTakeOffExpireDate();
                // 更新中止时间,更新原来的那一条
                fdCarryPriceInfoTo.setTakeOffExpireDate(DateUtil.dateAddDays(startDate, -1));
                fdCarryPriceInfoTo.setAirlineType(fdUpdatePolicy.getAirlineType());
                fdCarryPriceInfoTo.setUpdateTime(new Date());
                jdjpFdCarryPriceInfoService.updateByPrimaryKey(fdCarryPriceInfoTo);
                // 更新redis
                listForEdit.add(fdCarryPriceInfoTo);
                // 插入新的一条
                fdCarryPriceInfoTo.setTakeOffEffectDate(DateUtil.dateAddDays(endDate, 1));
                fdCarryPriceInfoTo.setTakeOffExpireDate(enDates);
                fdCarryPriceInfoTo.setId(null);
                jdjpFdCarryPriceInfoService.insertSelective(fdCarryPriceInfoTo);
                // 更新redis
                listForEdit.add(fdCarryPriceInfoTo);
                jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(listForEdit);
            }
        }
    }

    /**
     * 功能描述: 组装运价实体类，(没有封装开始时间和结束时间)
     * @param:
     * @return:
     */
    private JdjpFdCarryPriceInfo formFdCarryPriceInfo(FDInfoDTO fdInfo, Airline airline, Cabin cabin, JdjpFdUpdatePolicy fdUpdatePolicy){
        JdjpFdCarryPriceInfo fdCarryPriceInfo = new JdjpFdCarryPriceInfo();
        fdCarryPriceInfo.setAirWays(fdInfo.getCarrier());
        fdCarryPriceInfo.setDepCode(airline.getDepAirportCode());
        fdCarryPriceInfo.setArrCode(airline.getArrAirportCode());
        fdCarryPriceInfo.setDistance(airline.getDistance());
        fdCarryPriceInfo.setSeat(fdInfo.getSeatCode());
        fdCarryPriceInfo.setCarryPrice(new BigDecimal(fdInfo.getSinglePrice()));
        fdCarryPriceInfo.setAirlineType(fdUpdatePolicy.getAirlineType());
        fdCarryPriceInfo.setState(new Byte(StateEnum.VALID.getCode()));
        // '舱位类型 1-经济舱 2-商务舱 3-头等舱 4-超级经济舱 5-特价舱'
        if (cabin.getType() != null) {
            fdCarryPriceInfo.setSeatType(new Byte(cabin.getType().toString()));
        }
        fdCarryPriceInfo.setSeatLevel(cabin.getLevel());
        Double discount = cabin.getDiscount();
        fdCarryPriceInfo.setDiscount(discount != null ?  new BigDecimal(discount): null);
        fdCarryPriceInfo.setSource(new Byte(FDorNFDEnum.FD.getCode()));
        fdCarryPriceInfo.setCreateTime(new Date());
        fdCarryPriceInfo.setUpdateTime(new Date());
        fdCarryPriceInfo.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
        return fdCarryPriceInfo;
    }

    /**
     * 功能描述: 封装运价Map
     * @param:
     * @return:
     */
    private void putInfoMap(Map<String, JdjpFdCarryPriceInfo> infoMap, JdjpFdCarryPriceInfo fdCarryPriceInfo){
        StringBuilder sb = new StringBuilder();
        String key = sb.append(fdCarryPriceInfo.getDepCode())
                .append("_").append(fdCarryPriceInfo.getArrCode())
                .append("_").append(fdCarryPriceInfo.getAirWays())
                .append("_").append(fdCarryPriceInfo.getSeat())
                .append("_").append(fdCarryPriceInfo.getTakeOffEffectDate())
                .append("_").append(fdCarryPriceInfo.getTakeOffExpireDate()).toString();
        infoMap.put(key,fdCarryPriceInfo);
    }
    /**
     * 获取总控参数时间List(特殊处理)
     *
     * @param
     * @param
     * @return
     */
    private List<JdjpFdCarryPriceInfo> getParamList(String specialAirline, Date enddate) {
        if(StringUtils.isBlank(specialAirline)){
            return null;
        }
        //示例：DR#2.1-2.28;7.1-8.31,W01-D01@SC#2.1-2.28;7.1-8.31,SCH-SCL
        String[] runParams = specialAirline.split("@");

        /* 组装时间list */
        List<JdjpFdCarryPriceInfo> dateList = new ArrayList<JdjpFdCarryPriceInfo>();
        for(String params : runParams){
            //示例：DR#2.1-2.28;7.1-8.31,W01-D01
            String[] param = params.split("#");
            /* 获取总控参数时间 */
            //示例：2.1-2.28;7.1-8.31,W01-D01
            String[] datese = param[1].split(",");
            //示例：2.1-2.28;7.1-8.31
            String[] dates = datese[0].split(";");
            //示例：W01-D01
            String[] price = datese[1].split("-");
            if (price.length < 2) {
                LOGGER.info("获取特殊航线配置参数高价或低价标识为空!");
                break;
            }
            for (int j = 0; j < dates.length; j++) {
                //示例：2.1-2.28
                String[] mons = dates[j].split("-");
                if (mons.length < 2) {
                    LOGGER.info("获取特殊航线配置参数开始或结束时间为空!");
                    break;
                }
                String[] mon0 = mons[0].split("\\.");
                String[] mon1 = mons[1].split("\\.");
                Calendar calendar = Calendar.getInstance(); // 获取年
                int year = calendar.get(Calendar.YEAR);
                String date0 = String.valueOf(year) + "-" + mon0[0] + "-" + mon0[1];
                String date1 = String.valueOf(year) + "-" + mon1[0] + "-" + mon1[1];
                Date startDateParam = DateUtil.stringToDate(date0, DateUtil.DATE_FMT1);
                Date endDateParam = DateUtil.stringToDate(date1, DateUtil.DATE_FMT1);
                /* 判断是否是闰年 */
                if (DateUtil.is_leapYear(year) && CarryPriceConstants.IS_LEAP_YEAR.equals(mon1[0] + "-" + mon1[1])) {
                    endDateParam = DateUtil.dateAddDays(endDateParam, 1);
                }
                JdjpFdCarryPriceInfo dateParam = new JdjpFdCarryPriceInfo();
                dateParam.setTakeOffEffectDate(startDateParam);
                dateParam.setTakeOffExpireDate(endDateParam);
                dateParam.setAirWays(param[0]);
                dateParam.setLongPrice(price[0]);
                dateParam.setLowPrice(price[1]);
                dateList.add(dateParam);

                /* 处理跨年的日期 */
                Calendar calBegin = Calendar.getInstance(); // 获取日历实例
                Calendar calEnd = Calendar.getInstance();
                calBegin.setTime(endDateParam);
                calEnd.setTime(enddate);
                /* FD结束时间(年)-总控参数结束时间(年) */
                int yearDifference = calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
                int dateNum = DateUtil.compare_mon(startDateParam, enddate);
                if (yearDifference > 0 && dateNum > 0) {
                    for (int k = 1; k <= yearDifference; k++) {
                        int addYear = calBegin.get(Calendar.YEAR) + k;
                        String date2 = String.valueOf(addYear) + "-" + mon0[0] + "-" + mon0[1];
                        String date3 = String.valueOf(addYear) + "-" + mon1[0] + "-" + mon1[1];
                        Date startDateYearParam = DateUtil.stringToDate(date2, DateUtil.DATE_FMT1);
                        Date endDateYearParam = DateUtil.stringToDate(date3, DateUtil.DATE_FMT1);
                        /* 判断是否是闰年 */
                        if (DateUtil.is_leapYear(year) && CarryPriceConstants.IS_LEAP_YEAR.equals(mon1[0] + "-" + mon1[1])) {
                            endDateYearParam = DateUtil.dateAddDays(endDateYearParam, 1);
                        }
                        JdjpFdCarryPriceInfo date_Param = new JdjpFdCarryPriceInfo();
                        date_Param.setTakeOffEffectDate(startDateYearParam);
                        date_Param.setTakeOffExpireDate(endDateYearParam);
                        date_Param.setAirWays(param[0]);
                        date_Param.setLongPrice(price[0]);
                        date_Param.setLowPrice(price[1]);
                        dateList.add(date_Param);
                    }
                }
            }
        }

        /* 给组装完成的list按照开始和结束时间升序排序 */
        if (CollectionUtils.isNotEmpty(dateList)) {
            SortUtil<JdjpFdCarryPriceInfo> sortUtil = new SortUtil<JdjpFdCarryPriceInfo>();
            // 升序排列
            sortUtil.sort(dateList, "takeOffEffectDate", "takeOffExpireDate", "asc", "asc");
            LOGGER.info("排序以后的list" + JacksonUtil.obj2json(dateList));
            return dateList;
        }
        return null;
    }







    /**
     * 功能描述: 判断是否是特殊航司，特殊航司有淡旺季
     * @param:
     * @return:
     */
    private boolean isSpecialAirline(String specialAirline, Airline airline){
        LOGGER.info("开始判断是否是特殊航司："+specialAirline);
        boolean flag = false;
        if(StringUtils.isBlank(specialAirline)){
            return flag;
        }
        //示例：DR#2.1-2.28;7.1-8.31,W01-D01@SC#2.1-2.28;7.1-8.31,SCH-SCL
        String[] runParams = specialAirline.split("@");
        for (String params : runParams){
            String[] param = params.split("#");
            if(param[0].toUpperCase().equals(airline.getAirCorpCode().toUpperCase())){
                flag = true;
                break;
            }
        }
        return flag;
    }


    /**
     * 功能描述: 判断航司是否处于关闭状态
     * @param:
     * @return:
     */
    private boolean isCloseAirways(Airline airline){
       //判断航司是否处于关闭状态
        boolean isCloseAirways = false;
        //查询航司开关管理，关闭的航司
        List<JdjpAirwaysTicketType> closeAirWaysList = jdjpAirwaysTicketTypeManager.queryCloseSwitchTypeList();
        if(CollectionUtils.isNotEmpty(closeAirWaysList)){
            for(JdjpAirwaysTicketType airwaysTicketType : closeAirWaysList){
                if(airwaysTicketType.getAirways().equals(airline.getAirCorpCode())){
                    isCloseAirways = true;
                    break;
                }
            }
        }
        return isCloseAirways;
    }

    /**
     * 功能描述: 修改FD表中来源为NFD的运价,全部置为无效，并删除缓存
     * @param:
     * @return:
     */
    private void updateNFD(Airline airline) {
        // 下面这些都为查询条件
        JdjpFdCarryPriceInfo queryBean = new JdjpFdCarryPriceInfo();
        queryBean.setDepCode(airline.getDepAirportCode());// 出发
        queryBean.setArrCode(airline.getArrAirportCode());// 到达
        queryBean.setAirWays(airline.getAirCorpCode());// 航司
        queryBean.setSeat(CarryPriceConstants.CARRY_PRICE_SEAT_Y);// 舱位
        queryBean.setSource(new Byte(FDorNFDEnum.NFD.getCode()));// 数据来源1:NFD
        queryBean.setState(new Byte(StateEnum.VALID.getCode()));// 启用
        // 该方法是将符合上面的查询条件的运价信息变为无效的
        List<JdjpFdCarryPriceInfo> fdCarryPriceInfoList = jdjpFdCarryPriceInfoManager.queryList(queryBean);
        if(CollectionUtils.isNotEmpty(fdCarryPriceInfoList)){
            LOGGER.info("updateNFD方法将修改carryprice："+JacksonUtil.obj2json(fdCarryPriceInfoList));
            jdjpFdCarryPriceInfoManager.updateBatchToInvaildByParams(queryBean);
            //删除redis中对应的数据
            jdjpFdCarryPriceInfoRedisService.delCarryPriceRedis(fdCarryPriceInfoList);
        }
    }


    /**
     * 功能描述: 请求gds接口，并返回响应信息
     * @param:
     * @return:
     */
    private FDResponseDTO reqGdsFd(JdjpFdUpdatePolicy policy, Airline airline, String officeNo){
        // 插入到fd运价表里
        FDResponseDTO fdResponseDTO = null;
        try {
            LOGGER.info("请求GDS的航线信息:"+JacksonUtil.obj2json(airline));
            // 加密请求参数，并得到响应结果
            String depDate = DateUtil.dateToString(policy.getDepDate(), DateUtil.DATE_FMT1);
            String passType = PassengerTypeEnums.ADULT.getCode();
            fdResponseDTO = gdsInterfaceService.fdprice(airline.getDepAirportCode(), airline.getArrAirportCode(), depDate,
                    airline.getAirCorpCode(), passType, officeNo);
        } catch (Exception e) {
            LOGGER.error("访问GDS-FD接口异常，原因=" + e.getMessage(), e);
            fdResponseDTO = new FDResponseDTO();
            fdResponseDTO.setResponseCode(CodeEnums.INTERFACE_EXCEPTION.getCode());
            fdResponseDTO.setResponseMessage("访问GDS-FD接口异常，原因=" + e.getMessage());
        }
        if (fdResponseDTO == null) {
            fdResponseDTO = new FDResponseDTO();
            fdResponseDTO.setResponseCode(CodeEnums.NOT_DATA.getCode());
            fdResponseDTO.setResponseMessage("访问GDS-FD接口返回为空，请查找GDS问题！");
        }
        return fdResponseDTO;
    }

    /**
     * 功能描述: 获取舱位信息
     * @param:
     * @return:
     */
    private Cabin getCabin(FDInfoDTO fdInfo){
        Cabin record = new Cabin();
        record.setAirCorpCode(fdInfo.getCarrier());
        record.setAdultCode(fdInfo.getSeatCode());
        List<Cabin> cabinList = BaseDataUtils.getCabinList(QueryTypeEnum.UNION_KEY, record);
        if(CollectionUtils.isEmpty(cabinList)){
            LOGGER.info("舱位表无此舱位:" + JacksonUtil.obj2json(fdInfo));
            return null;
        }
        return cabinList.get(0);
    }

    /**
     * 功能描述: 更新策略状态
     * @param:
     * @return:
     */
    private void updateFdPolicyState(JdjpFdUpdatePolicy policy, String syncStatus, String remark ){
        if(syncStatus.equals(SyncStatusEnum.OVER.getCode())){
            policy.setExecuteFinishTime(new Date());
        }
        policy.setExecuteQuartzTime(new Date());
        policy.setSyncStatus(new Byte(syncStatus));
        policy.setRemark(remark);// 备注
        //policy.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
        jdjpFdUpdatePolicyService.updateByPrimaryKeySelective(policy);

    }
}
