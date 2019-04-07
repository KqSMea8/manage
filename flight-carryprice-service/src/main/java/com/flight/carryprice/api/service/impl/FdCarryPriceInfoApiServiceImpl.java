package com.flight.carryprice.api.service.impl;

import com.flight.carryprice.api.service.FdCarryPriceInfoApiService;
import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.enums.CodeEnums;
import com.flight.carryprice.common.enums.StateEnum;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.common.util.RegularUtil;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.jdmodel.*;
import com.flight.carryprice.manager.JdjpFdCarryPriceInfoManager;
import com.flight.carryprice.service.JdCacheService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价API  Service层
 * @date 2019/3/7 16:00
 * @updateTime
 */
@Service
public class FdCarryPriceInfoApiServiceImpl implements FdCarryPriceInfoApiService {

    private static final Logger LOGGER = Logger.getLogger(FdCarryPriceInfoApiServiceImpl.class);

    @Resource
    private JdCacheService jdCacheService;

    @Resource
    private JdjpFdCarryPriceInfoManager fdCarryPriceInfoManager;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public QueryFdCarryPriceResponse queryFdCarryPriceInfoList(QueryFdCarryPriceRequest request) {

        //验参
        QueryFdCarryPriceResponse response = checkParamsForCarryPrice(request);
        if(!CodeEnums.SUCCESS.getCode().equals(response.getResponseCode())){
            return response;
        }

        LOGGER.info("FD运价服务请求参数：" + JacksonUtil.obj2json(request));
        //sourceType 1-只取缓存 2-缓存获取不到取数据库 3-只取数据库
        Integer sourceType = request.getSourceType();

        List<FdCarryPriceInfo> priceInfoList = new ArrayList<>();
        if(sourceType == 1){
            //直接查询缓存
            priceInfoList = queryFDCarryPriceFromRedis(request);
        }else if(sourceType == 2){
            //查询缓存，缓存不存在查询数据库
            priceInfoList = queryFDCarryPriceFromRedis(request);
            if(CollectionUtils.isEmpty(priceInfoList)){
                priceInfoList = queryFDCarryPriceFromDB(request);
            }
        }else if(sourceType == 3){
            //直接查询数据库
            priceInfoList = queryFDCarryPriceFromDB(request);
        }

        if(CollectionUtils.isEmpty(priceInfoList)){
            response.setResponseCode(CodeEnums.NOT_DATA.getCode());
            response.setResponseMessage(CodeEnums.NOT_DATA.getDescription());
            return response;
        }

        // 获取结果封装
        LOGGER.info("FD运价服务，获取运价集合信息为：" + JacksonUtil.obj2json(priceInfoList));
        response.setFdPriceInfoList(priceInfoList);
        response.setResponseCode(CodeEnums.SUCCESS.getCode());
        response.setResponseMessage(CodeEnums.SUCCESS.getDescription());
        LOGGER.info("FD运价服务，返回response数据：" + JacksonUtil.obj2json(response));
        return response;
    }

    /**
     * 功能描述: 根据舱位集合 查询舱位-运价信息
     *
     * @param: request Fd运价根据舱位列表取运价请求
     * @return: Fd运价根据舱位列表取运价返回
     */
    @Override
    public QueryFdCarryPriceBySeatsResponse queryFdPriceBySeatsList(QueryFdCarryPriceBySeatsRequest request){
        //验参
        QueryFdCarryPriceBySeatsResponse response = checkParamsForSeatPrice(request);
        if(!CodeEnums.SUCCESS.getCode().equals(response.getResponseCode())){
            return response;
        }
        LOGGER.info("FD查询舱位-运价信息请求参数：" + JacksonUtil.obj2json(request));
        String airWays = request.getAirWays();
        String depCode = request.getDepCode();
        String arrCode = request.getArrCode();
        List<String> seats = request.getSeats();
        List<JdjpFdCarryPriceInfo> list = fdCarryPriceInfoManager.queryListBySeats(airWays, depCode, arrCode, seats);
        if(CollectionUtils.isEmpty(list)){
            LOGGER.info("FD查询舱位-运价信息：无数据返回");
            response.setResponseCode(CodeEnums.NOT_DATA.getCode());
            response.setResponseMessage(CodeEnums.NOT_DATA.getDescription());
            return response;
        }
        List<FdCarryPriceAndSeat> returnList = new ArrayList<>();
        for (JdjpFdCarryPriceInfo info : list){
            FdCarryPriceAndSeat fdCarryPriceAndSeat = JacksonUtil.obj2obj(info, FdCarryPriceAndSeat.class);
            returnList.add(fdCarryPriceAndSeat);
        }
        response.setFdPriceAndSeatList(returnList);
        response.setResponseCode(CodeEnums.SUCCESS.getCode());
        response.setResponseMessage(CodeEnums.SUCCESS.getDescription());
        LOGGER.info("FD查询舱位-运价信息：返回response数据：："+JacksonUtil.obj2json(returnList));
        return response;

    }


    /**
     * 功能描述: 从缓存中查询运价信息
     * @param:
     * @return:
     */
    private List<FdCarryPriceInfo> queryFDCarryPriceFromRedis(final QueryFdCarryPriceRequest request){
        long startSeat = System.currentTimeMillis();
        final List<FdCarryPriceInfo> priceInfoList = new ArrayList<>();
        //获取redis的key
        String key = redisKey(request);
        String seat = request.getSeat();
        Map<String, String> valueMap = jdCacheService.getValue(key);
        if(valueMap == null){
            return null;
        }
        if(StringUtils.isNotBlank(seat)){
            String seatMapJson = valueMap.get(seat);
            Map seatMap = new HashMap();
            if (StringUtils.isNotBlank(seatMapJson)) {
                seatMap = JacksonUtil.json2Map(seatMapJson, String.class, Map.class);
            }
            if (seatMap != null && seatMap.size() > 0){
                for (Object value : seatMap.values()) {
                    JdjpFdCarryPriceInfo fdCarryPriceInfo =
                            JacksonUtil.json2Obj(JacksonUtil.obj2json(value), JdjpFdCarryPriceInfo.class);

                    Date takeOffEffectDate = fdCarryPriceInfo.getTakeOffEffectDate();
                    Date takeOffExpireDate = fdCarryPriceInfo.getTakeOffExpireDate();
                    Date depDate = DateUtil.stringToDate(request.getDepDate(), DateUtil.DATE_FMT1);
                    if(depDate != null && !(takeOffEffectDate.compareTo(depDate) <= 0 && takeOffExpireDate.compareTo(depDate) >= 0)){
                        continue;
                    }
                    FdCarryPriceInfo priceInfo = JacksonUtil.obj2obj(fdCarryPriceInfo, FdCarryPriceInfo.class);
                    priceInfoList.add(priceInfo);
                }
            }
        }else{
            //用于表示是否执行完毕
            CopyOnWriteArrayList<Future> futures = new CopyOnWriteArrayList<>();

            for (final String value : valueMap.values()) {
                LOGGER.info("FD运价服务，轮训map中的value：" + value);
                futures.add(taskExecutor.submit(new Runnable() {
                    // 多线程进行运行
                    @Override
                    public void run() {
                        Map seatMap = JacksonUtil.json2Map(value, String.class, Map.class);
                        LOGGER.info("FD运价服务，耗时，内层map将循环层：" + seatMap.size());
                        for (Object value : seatMap.values()) {
                            JdjpFdCarryPriceInfo fdCarryPriceInfo =
                                    JacksonUtil.json2Obj(JacksonUtil.obj2json(value), JdjpFdCarryPriceInfo.class);

                            Date takeOffEffectDate = fdCarryPriceInfo.getTakeOffEffectDate();
                            Date takeOffExpireDate = fdCarryPriceInfo.getTakeOffExpireDate();
                            Date depDate = DateUtil.stringToDate(request.getDepDate(), DateUtil.DATE_FMT1);
                            if(depDate != null && !(takeOffEffectDate.compareTo(depDate) <= 0) && takeOffExpireDate.compareTo(depDate) >= 0){
                                continue;
                            }
                            FdCarryPriceInfo priceInfo = JacksonUtil.obj2obj(fdCarryPriceInfo, FdCarryPriceInfo.class);
                            priceInfoList.add(priceInfo);
                        }
                    }
                }));
            }
            // 等待多线程运行完成
            while (true) {
                // 线程是否完成
                boolean isAllDone = true;
                for (Future taskResult : futures) {
                    isAllDone &= (taskResult.isDone() || taskResult.isCancelled());
                }
                // 任务都执行完毕或超时，跳出循环
                if (isAllDone) {
                    break;
                }
            }
            long endSeat = System.currentTimeMillis();
            LOGGER.info("FD运价服务，耗时，没有舱位传入时获取carryprice耗时：" + String.valueOf(endSeat - startSeat));
            return priceInfoList;
        }
        return priceInfoList;
    }

    /**
     * 功能描述: 从数据库查询运价信息
     * @param:
     * @return:
     */
    private List<FdCarryPriceInfo> queryFDCarryPriceFromDB(QueryFdCarryPriceRequest request){

        JdjpFdCarryPriceInfo jsjpFdCarryPriceInfo = new JdjpFdCarryPriceInfo();
        jsjpFdCarryPriceInfo.setAirWays(request.getAirWays());
        jsjpFdCarryPriceInfo.setDepCode(request.getDepCode());
        jsjpFdCarryPriceInfo.setArrCode(request.getDepCode());
        jsjpFdCarryPriceInfo.setSeat(request.getSeat());//非必填,如果为空，则set的就是空
        jsjpFdCarryPriceInfo.setState(new Byte(StateEnum.VALID.getCode()));
        jsjpFdCarryPriceInfo.setDepDate(DateUtil.stringToDate(request.getDepDate(), DateUtil.DATE_FMT1));
        List<JdjpFdCarryPriceInfo> list = fdCarryPriceInfoManager.queryList(jsjpFdCarryPriceInfo);
        List<FdCarryPriceInfo> returnList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            for (JdjpFdCarryPriceInfo info : list){
                FdCarryPriceInfo fdCarryPriceInfo = JacksonUtil.obj2obj(info, FdCarryPriceInfo.class);
                returnList.add(fdCarryPriceInfo);
            }
        }
        return returnList;
    }

    /**
     * 功能描述: FD信息查询 检验参数
     * @param:
     * @return:
     */
    private QueryFdCarryPriceResponse checkParamsForCarryPrice(QueryFdCarryPriceRequest request){
        QueryFdCarryPriceResponse response = new QueryFdCarryPriceResponse();
        response.setResponseCode(CodeEnums.SUCCESS.getCode());
        //验参
        if(request == null){
            LOGGER.info("请求实体为空");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        String airWays = request.getAirWays();
        if(StringUtils.isBlank(airWays) || !RegularUtil.regexAirways(airWays)){
            LOGGER.info("航司为空或格式不正确");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        String depCode = request.getDepCode();
        if(StringUtils.isBlank(depCode) || !RegularUtil.regexCode(depCode)){
            LOGGER.info("出发机场为空或格式不正确");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        String arrCode = request.getArrCode();
        if(StringUtils.isBlank(arrCode) || !RegularUtil.regexCode(arrCode)){
            LOGGER.info("到达机场为空或格式不正确");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        String depDate = request.getDepDate();
        if(StringUtils.isBlank(depDate) || !RegularUtil.regexDepDate(depDate)){
            LOGGER.info("出发时间为空或格式不正确");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        String seat = request.getSeat();
        if(StringUtils.isNotBlank(seat) && !RegularUtil.regexSeat(seat)){
            LOGGER.info("舱位不为空，但格式不正确");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        Integer sourceType = request.getSourceType();
        if(sourceType == null || (sourceType != 1 && sourceType !=2 && sourceType !=3)){
            LOGGER.info("运价获取方式为空，或者传参不对");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }
        return response;
    }

    /**
     * 功能描述: 查询舱位-运价信息 检验参数
     * @param:
     * @return:
     */
    private QueryFdCarryPriceBySeatsResponse checkParamsForSeatPrice(QueryFdCarryPriceBySeatsRequest request){
        QueryFdCarryPriceBySeatsResponse response = new QueryFdCarryPriceBySeatsResponse();
        response.setResponseCode(CodeEnums.SUCCESS.getCode());
        //验参
        if(request == null){
            LOGGER.info("请求实体为空");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        String airWays = request.getAirWays();
        if(StringUtils.isBlank(airWays) || !RegularUtil.regexAirways(airWays)){
            LOGGER.info("航司为空或格式不正确");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        String depCode = request.getDepCode();
        if(StringUtils.isBlank(depCode) || !RegularUtil.regexCode(depCode)){
            LOGGER.info("出发机场为空或格式不正确");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        String arrCode = request.getArrCode();
        if(StringUtils.isBlank(arrCode) || !RegularUtil.regexCode(arrCode)){
            LOGGER.info("到达机场为空或格式不正确");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        List<String> seats = request.getSeats();
        if(CollectionUtils.isEmpty(seats)){
            LOGGER.info("舱位集合为空");
            response.setResponseCode(CodeEnums.PARAM_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.PARAM_EXCEPTION.getDescription());
            return response;
        }

        return response;
    }

    /**
     * 功能描述: 组装缓存key
     * @param:
     * @return:
     */
    private String redisKey(QueryFdCarryPriceRequest request){
        StringBuffer sb = new StringBuffer();
        return sb.append(request.getAirWays()).append(CarryPriceConstants.CONNECTPARAM3)
                .append(request.getDepCode()).append(CarryPriceConstants.CONNECTPARAM3)
                .append(request.getArrCode()).append(CarryPriceConstants.CONNECTPARAM3)
                .append(CarryPriceConstants.FD_REDIS_KEY_TAIL)
                .toString();
    }

}
