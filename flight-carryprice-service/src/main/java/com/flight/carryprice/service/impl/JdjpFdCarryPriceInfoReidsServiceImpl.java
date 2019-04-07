package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.enums.StateEnum;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpCarryPriceTask;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.manager.JdjpCarryPriceTaskManager;
import com.flight.carryprice.service.JdCacheService;
import com.flight.carryprice.service.JdjpFdCarryPriceInfoRedisService;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.airline.Airline;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价缓存处理
 * @date 2019/3/4 17:37
 * @updateTime
 */
@Service
public class JdjpFdCarryPriceInfoReidsServiceImpl extends BaseServiceImpl<JdjpFdCarryPriceInfo> implements JdjpFdCarryPriceInfoRedisService {

    private static Logger LOGGER = Logger.getLogger(JdjpFdCarryPriceInfoReidsServiceImpl.class);

    @Resource
    private JdCacheService jdCacheService;

    @Resource
    private JdjpCarryPriceTaskManager jdjpCarryPriceTaskManager;

    @Override
    public Boolean updateOrAddCarryPriceRedis(List<JdjpFdCarryPriceInfo> fdCarryPriceInfos) {
        if(CollectionUtils.isEmpty(fdCarryPriceInfos)){
            LOGGER.info("FD运价批量新增或更新缓存的集合为空，不存入缓存");
            return true;
        }
        for(JdjpFdCarryPriceInfo fdCarryPriceInfo : fdCarryPriceInfos){
            if(fdCarryPriceInfo.getState() != Byte.parseByte(StateEnum.VALID.getCode())){
                LOGGER.info("FD运价状态为不可用，不存入缓存");
                continue;
            }
            Boolean flag = updateOrAddCarryPriceRedis(fdCarryPriceInfo);
            if(!flag){
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean updateOrAddCarryPriceRedis(JdjpFdCarryPriceInfo fdCarryPriceInfo) {
        /**
         * 缓存
         * key=航司_出发_到达_FD
         * value=Map<seat,seatValue>  map的key为舱位
         * seatValue=Map<id,entity>  map的key为id
         */
        //验参数
        String airWays = fdCarryPriceInfo.getAirWays();
        String depCode = fdCarryPriceInfo.getDepCode();
        String arrCode = fdCarryPriceInfo.getArrCode();
        String seat = fdCarryPriceInfo.getSeat();
        Integer carryInfoId = fdCarryPriceInfo.getId();
        if(!checkParams(airWays, depCode, arrCode, seat, carryInfoId)){
            LOGGER.info("FD运价航司、出发、到达、舱位、ID为空，不存入缓存");
            LOGGER.info(JacksonUtil.obj2json(fdCarryPriceInfo));
            return false;
        }
        String id = carryInfoId.toString();

        //组装外层key
        String key = redisKey(airWays, depCode, arrCode, CarryPriceConstants.FD_REDIS_KEY_TAIL);
        //获取到舱位和map对应得map
        Map<String, String> valueMap = jdCacheService.getValue(key);
        if(valueMap != null){
            String seatValueJson = valueMap.get(seat);//舱位对应的Map
            Map seatMap = new HashMap();
            if (StringUtils.isNotBlank(seatValueJson)) {
                seatMap = JacksonUtil.json2Map(seatValueJson, String.class, Map.class);
            }
            if(seatMap != null && seatMap.size()>0){
                seatMap.put(id,fdCarryPriceInfo);
                valueMap.put(seat,JacksonUtil.obj2json(seatMap));
                jdCacheService.refreshAll(key,valueMap);
            }else{
                seatMap = new HashMap();
                seatMap.put(id, fdCarryPriceInfo);
                valueMap.put(seat, JacksonUtil.obj2json(seatMap));
                jdCacheService.refreshAll(key, valueMap);
            }
        }else{
            Map seatMap = new HashMap();
            seatMap.put(id, fdCarryPriceInfo);
            valueMap = new HashMap();
            valueMap.put(seat, JacksonUtil.obj2json(seatMap));
            jdCacheService.refreshAll(key, valueMap);
        }

        return true;
    }

    @Override
    public Boolean delCarryPriceRedis(List<JdjpFdCarryPriceInfo> FdCarryPriceInfos) {
        if(CollectionUtils.isEmpty(FdCarryPriceInfos)){
            LOGGER.info("FD运价批量删除缓存的集合为空，不存入缓存");
            return true;
        }
        for(JdjpFdCarryPriceInfo carrypriceInfo : FdCarryPriceInfos){
            Boolean delFlag = delCarryPriceRedis(carrypriceInfo);
            if(!delFlag){
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean delCarryPriceRedis(JdjpFdCarryPriceInfo fdCarryPriceInfo) {
        //验参数
        String airWays = fdCarryPriceInfo.getAirWays();
        String depCode = fdCarryPriceInfo.getDepCode();
        String arrCode = fdCarryPriceInfo.getArrCode();
        String seat = fdCarryPriceInfo.getSeat();
        Integer carryInfoId = fdCarryPriceInfo.getId();
        if(!checkParams(airWays, depCode, arrCode, seat, carryInfoId)){
            LOGGER.info("FD运价航司、出发、到达、舱位、ID为空，不存入缓存");
            LOGGER.info(JacksonUtil.obj2json(fdCarryPriceInfo));
            return false;
        }
        String id = carryInfoId.toString();

        //组装外层key
        String key = redisKey(airWays, depCode, arrCode, CarryPriceConstants.FD_REDIS_KEY_TAIL);
        //获取到舱位和map对应得map
        Map<String, String> valueMap = jdCacheService.getValue(key);
        //如果valueMap为空，直接返回
        if(valueMap ==null || valueMap.size() <=0){
            return true;
        }
        String seatValueJson = valueMap.get(seat);
        //如果seatValueJson为空，直接返回
        if(StringUtils.isBlank(seatValueJson)){
            return true;
        }
        Map seatMap = JacksonUtil.json2Map(seatValueJson, String.class, Map.class);
        //如果seatMap为空，直接返回
        if(seatMap ==null || seatMap.size() <=0 ){
            return true;
        }
        //如果seatMap长度为1，直接移除
        seatMap.remove(id);
        //先移除ID实体，判断移除后是否为空，如果为空，则判断valueMap长度是否为1，长度为1，则删除大key,如果不为1，valueMap则移除seat,刷新缓存
        if(seatMap.isEmpty()){
            if (valueMap.size() == 1) {
                jdCacheService.delMap(key, valueMap);
            } else {
                valueMap.remove(seat);
                jdCacheService.refreshAll(key, valueMap);
            }
        }else{
            //重新seatMap转json，存入valueMap,刷新缓存
            seatValueJson = JacksonUtil.obj2json(seatMap);
            valueMap.put(seat, seatValueJson);//放入舱位
            jdCacheService.refreshAll(key, valueMap);
        }
        return true;
    }

    /**
     * 获取缓存中的FD运价
     * @param fdCarryPriceInfo
     */
    public JdjpFdCarryPriceInfo getRedisFdCarryPriceInfo(JdjpFdCarryPriceInfo fdCarryPriceInfo){
        JdjpFdCarryPriceInfo fdCarryPriceInfoRedis = null;
        String airWays = fdCarryPriceInfo.getAirWays();
        String depCode = fdCarryPriceInfo.getDepCode();
        String arrCode = fdCarryPriceInfo.getArrCode();
        String seat = fdCarryPriceInfo.getSeat();
        //组装外层key
        String key = redisKey(airWays, depCode, arrCode, CarryPriceConstants.FD_REDIS_KEY_TAIL);

        Map<String, String> valueMap = jdCacheService.getValue(key);
        if (valueMap != null) {
            String seatMapJson = valueMap.get(seat);
            Map seatMap = new HashMap();
            if (StringUtils.isNotBlank(seatMapJson)) {
                seatMap = JacksonUtil.json2Map(seatMapJson, String.class, Map.class);
            }
            if (seatMap != null && seatMap.size() > 0){
                fdCarryPriceInfoRedis = JacksonUtil.json2Obj(JacksonUtil.obj2json(seatMap.get(fdCarryPriceInfo.getId().toString())),
                        JdjpFdCarryPriceInfo.class);
            }
        }
        return fdCarryPriceInfoRedis;
    }

    /**
     * 功能描述: 清除缓存
     * @param:
     * @return:
     */
    public void cleanRedisForCarryPrice(String airWays, String depCode, String arrCode, String seat, String carryPriceType){
        LOGGER.info("----------------清除缓存数据开始-------------------------");
        //运价类型(0:FD，1:NFD，2:SSD)
        if(StringUtils.isBlank(carryPriceType)){
            LOGGER.info("运价类型为空");
            return;
        }
        String carryPriceTypeDes = "";
        if(carryPriceType.equals("0")){
            carryPriceTypeDes = CarryPriceConstants.FD_REDIS_KEY_TAIL;
        }else if(carryPriceType.equals("1")){
            carryPriceTypeDes = CarryPriceConstants.NFD_REDIS_KEY_TAIL;
        }else if(carryPriceType.equals("2")){
            carryPriceTypeDes = CarryPriceConstants.SSD_REDIS_KEY_TAIL;
        }else{
            return;
        }

        //获取缓存key集合
        List<String> keyList = getKeyList(airWays, depCode, arrCode, carryPriceTypeDes);
        if(CollectionUtils.isEmpty(keyList)){
            LOGGER.info("缓存key集合为空");
            return;
        }
        for (String key : keyList){
            //如果舱位为空，则删除整条航线
            if(StringUtils.isBlank(seat)){
                jdCacheService.delete(key);
            }else{
                Map<String, String> valueMap = jdCacheService.getValue(key);
                valueMap.remove(seat);
                jdCacheService.refreshAll(key,valueMap);
            }
        }

        //删除任务表中的数据
        // 查询条件，格式如：航司-CA;出发-PEK;到达-SHA;舱位-Y
        String queryCondition = formQueryCondition(airWays, depCode, arrCode, seat);


        JdjpCarryPriceTask task = new JdjpCarryPriceTask();
        task.setQueryCondition(queryCondition);
        task.setCarryPriceType(new Byte(carryPriceType));//运价类型(0:FD，1:NFD，2:SSD)
        jdjpCarryPriceTaskManager.deleteByQueryCondition(task);
        LOGGER.info("--------清除缓存数据---airWays:"+airWays+" -depCode:"+depCode+" -arrCode:"+arrCode+" -seat:"+seat+"----");
        LOGGER.info("--------清除缓存数据结束-------------------------");
    }

    /**
     * 功能描述: 检验参数
     * @param:
     * @return:
     */
    private boolean checkParams(String airWays, String depCode, String arrCode, String seat, Integer id){

        if(StringUtils.isBlank(airWays)){
            LOGGER.info("FD运价航司为空，不存入缓存");
            return false;
        }

        if(StringUtils.isBlank(depCode)){
            LOGGER.info("FD运价出发为空，不存入缓存");
            return false;
        }

        if(StringUtils.isBlank(arrCode)){
            LOGGER.info("FD运价到达为空，不存入缓存");
            return false;
        }

        if(StringUtils.isBlank(seat)){
            LOGGER.info("FD运价舱位为空，不存入缓存");
            return false;
        }

        if(id == null){
            LOGGER.info("FD运价id为空，不存入缓存");
            return false;
        }
        return true;
    }

    /**
     * 功能描述: 组装缓存key
     * @param:
     * @return:
     */
    private String redisKey(String airWays, String depCode, String arrCode, String carryPriceTypeDes){
        StringBuffer sb = new StringBuffer();
        return sb.append(airWays).append(CarryPriceConstants.CONNECTPARAM3)
                .append(depCode).append(CarryPriceConstants.CONNECTPARAM3)
                .append(arrCode).append(CarryPriceConstants.CONNECTPARAM3)
                .append(carryPriceTypeDes)
                .toString();
    }

    /**
     * 功能描述: 获取缓存keyList
     * @param:
     * @return:
     */
    private List<String> getKeyList(String airWays, String depCode, String arrCode, String carryPriceTypeDes){
        List<String> keyList = new ArrayList<>();
        if(StringUtils.isNotEmpty(airWays) && StringUtils.isNotEmpty(depCode) && StringUtils.isNotEmpty(arrCode)){
            //组装key
            String key = redisKey(airWays, depCode, arrCode,carryPriceTypeDes);
            keyList.add(key);
        }else{
            //默认全部
            Airline record = new Airline();
            if(StringUtils.isNotEmpty(airWays)){
                record.setAirCorpCode(airWays);
            }
            if(StringUtils.isNotEmpty(depCode)){
                record.setDepAirportCode(depCode);
            }
            if(StringUtils.isNotEmpty(arrCode)){
                record.setArrAirportCode(arrCode);
            }
            List<Airline> airlineList = BaseDataUtils.getAirlineListWithCondition(record);
            if (CollectionUtils.isNotEmpty(airlineList)) {
                for (Airline airline : airlineList) {
                    String key = redisKey(airline.getDepAirportCode(), airline.getArrAirportCode(), airline.getArrAirportCode(),carryPriceTypeDes);
                    keyList.add(key);
                }
            }
        }
        return keyList;
    }

    /**
     * 功能描述: 封装查询条件
     * @param:
     * @return:
     */
    private String formQueryCondition(String airWays, String depCode, String arrCode, String seat){
        //拼接条件
        StringBuffer querySqlBuffer = new StringBuffer();
        if (StringUtils.isNotEmpty(airWays)) {
            querySqlBuffer.append(CarryPriceConstants.AIRWAYS)
                    .append(CarryPriceConstants.CONNECTPARAM1)
                    .append(airWays)
                    .append(CarryPriceConstants.CONNECTPARAM2);
        }
        if (StringUtils.isNotEmpty(depCode)) {
            querySqlBuffer.append(CarryPriceConstants.DEPCODE)
                    .append(CarryPriceConstants.CONNECTPARAM1)
                    .append(depCode)
                    .append(CarryPriceConstants.CONNECTPARAM2);
        }
        if (StringUtils.isNotEmpty(arrCode)) {
            querySqlBuffer.append(CarryPriceConstants.ARRCODE)
                    .append(CarryPriceConstants.CONNECTPARAM1)
                    .append(arrCode)
                    .append(CarryPriceConstants.CONNECTPARAM2);
        }
        if (StringUtils.isNotEmpty(seat)) {
            querySqlBuffer.append(CarryPriceConstants.SEAT)
                    .append(CarryPriceConstants.CONNECTPARAM1)
                    .append(seat.toUpperCase())
                    .append(CarryPriceConstants.CONNECTPARAM2);
        }
        String queryCondition = querySqlBuffer.toString();
        if (StringUtils.isNotEmpty(queryCondition)) {
            queryCondition = queryCondition.substring(0, queryCondition.length() - 1);
        }
        return queryCondition;
    }

}
