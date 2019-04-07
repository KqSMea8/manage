package com.flight.carryprice.api;

import com.flight.carryprice.api.service.AirwaysTicketTypeInfoApiService;
import com.flight.carryprice.api.service.CarryPriceMonitorInfoApiService;
import com.flight.carryprice.api.service.FdCarryPriceInfoApiService;
import com.flight.carryprice.api.service.LimitFlightInfoApiServcie;
import com.flight.carryprice.api.service.NfdCarryPriceInfoApiService;
import com.flight.carryprice.api.service.ScheduledTaskInfoApiServce;
import com.flight.carryprice.common.constant.UmpConstants;
import com.flight.carryprice.common.enums.CodeEnums;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.constant.ResponseCodeEnum;
import com.flight.carryprice.jdmodel.AddCarryPriceMonitorRequest;
import com.flight.carryprice.jdmodel.AddCarryPriceMonitorResponse;
import com.flight.carryprice.jdmodel.AddScheduledTaskRequest;
import com.flight.carryprice.jdmodel.AddScheduledTaskResponse;
import com.flight.carryprice.jdmodel.QueryAirWaysTicketTypeRequest;
import com.flight.carryprice.jdmodel.QueryAirWaysTicketTypeResponse;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceBySeatsRequest;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceBySeatsResponse;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceRequest;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceResponse;
import com.flight.carryprice.jdmodel.QueryLimitFlightRequest;
import com.flight.carryprice.jdmodel.QueryLimitFlightResponse;
import com.flight.carryprice.jdmodel.QueryNfdCarryPriceRequest;
import com.flight.carryprice.jdmodel.QueryNfdCarryPriceResponse;
import com.flight.carryprice.service.CarryPriceService;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 运价信息相关对外jsf服务
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
@Service("carryPriceService")
public class CarryPriceInfoApi implements CarryPriceService {

    private static final Logger LOGGER = Logger.getLogger(CarryPriceInfoApi.class);

    // fd运价服务
    @Resource
    private FdCarryPriceInfoApiService fdCarryPriceInfoApiService;
    // nfd运价服务
    @Resource
    private NfdCarryPriceInfoApiService nfdCarryPriceInfoApiService;
    // 计划任务服务
    @Resource
    private ScheduledTaskInfoApiServce scheduledTaskInfoApiServce;
    // 运价监控服务
    @Resource
    private CarryPriceMonitorInfoApiService carryPriceMonitorInfoApiService;
    // 航司出票类型查询服务
    @Resource
    private AirwaysTicketTypeInfoApiService airwaysTicketTypeInfoApiService;
    // 限制航班查询服务
    @Resource
    private LimitFlightInfoApiServcie limitFlightInfoApiServcie;

    /**
     * 功能描述:查询FD运价信息
     *
     * @param:
     * @return:
     */
    public QueryFdCarryPriceResponse queryFdPriceList(QueryFdCarryPriceRequest request) {

        long start = System.currentTimeMillis();
        // 增加ump
        CallerInfo callerInfo = Profiler.registerInfo(UmpConstants.CARRYPRICE_API_NAME,
                UmpConstants.QUERY_FD_PRICE_LIST_API, false, true);

        QueryFdCarryPriceResponse response = new QueryFdCarryPriceResponse();
        try {
            //调用fd运价相关方法
            response = fdCarryPriceInfoApiService.queryFdCarryPriceInfoList(request);
        } catch (Exception e) {
            response.setResponseCode(CodeEnums.INTERFACE_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.INTERFACE_EXCEPTION.getDescription());
            Profiler.functionError(callerInfo);
            LOGGER.error("FD运价服务获取fd运价时出错", e);
        } finally {
            long end = System.currentTimeMillis();
            long interval = end - start;
            LOGGER.info("FD运价服务，总耗时：" + String.valueOf(interval));
            Profiler.registerInfoEnd(callerInfo);
        }
        LOGGER.info("FD运价服务返回response数据：" + JacksonUtil.obj2json(response));
        return response;
    }

    /**
     * 功能描述: 根据舱位集合 查询舱位-运价信息
     *
     * @param: request Fd运价根据舱位列表取运价请求
     * @return: Fd运价根据舱位列表取运价返回
     */
    public QueryFdCarryPriceBySeatsResponse queryFdPriceBySeatsList(QueryFdCarryPriceBySeatsRequest request) {
        long start = System.currentTimeMillis();
        // 增加ump
        CallerInfo callerInfo = Profiler.registerInfo(UmpConstants.CARRYPRICE_API_NAME,
                UmpConstants.QUERY_FD_PRICE_BY_SEATS_LIST_API, false, true);

        QueryFdCarryPriceBySeatsResponse response = new QueryFdCarryPriceBySeatsResponse();
        try {
            //调用fd运价相关方法
            response = fdCarryPriceInfoApiService.queryFdPriceBySeatsList(request);
        } catch (Exception e) {
            response.setResponseCode(CodeEnums.INTERFACE_EXCEPTION.getCode());
            response.setResponseMessage(CodeEnums.INTERFACE_EXCEPTION.getDescription());
            Profiler.functionError(callerInfo);
            LOGGER.error("FD查询舱位-运价信息时出错", e);
        } finally {
            long end = System.currentTimeMillis();
            long interval = end - start;
            LOGGER.info("FD查询舱位-运价信息服务，总耗时：" + String.valueOf(interval));
            Profiler.registerInfoEnd(callerInfo);
        }
        LOGGER.info("FD查询舱位-运价信息服务返回response数据：" + JacksonUtil.obj2json(response));
        return response;
    }


    /**
     * 查询NFD运价信息
     *
     * @param request 查询NFD运价请求实体类
     * @return 查询NFD运价响应实体类
     */
    @Override
    public QueryNfdCarryPriceResponse queryNfdPriceList(QueryNfdCarryPriceRequest request) {

        long startTime = System.currentTimeMillis();
        CallerInfo callerInfo = Profiler.registerInfo(UmpConstants.CARRYPRICE_API_NAME,
                UmpConstants.QUERY_NFD_PRICE_LIST_API, false, true);
        QueryNfdCarryPriceResponse response = new QueryNfdCarryPriceResponse();
        try {
            LOGGER.info("CarryPriceInfoApi.queryNfdPriceList request：" + JacksonUtil.obj2json(request));
            response = nfdCarryPriceInfoApiService.queryNfdCarryPriceInfoList(request);
        } catch (Exception e) {
            response.setResponseCode(ResponseCodeEnum.EXCEPTION.getCode());
            response.setResponseMessage(ResponseCodeEnum.EXCEPTION.getDesc());
            LOGGER.error("CarryPriceInfoApi.queryNfdPriceList error!", e);
            Profiler.functionError(callerInfo);
        } finally {
            long endTime = System.currentTimeMillis();
            LOGGER.info("CarryPriceInfoApi.queryNfdPriceList total time：" + (endTime - startTime));
            Profiler.registerInfoEnd(callerInfo);
        }
        LOGGER.info("CarryPriceInfoApi.queryNfdPriceList response：" + JacksonUtil.obj2json(response));
        return response;
    }

    /**
     * 插入计划任务
     *
     * @param request 插入计划任务请求实体
     * @return 插入计划任务响应实体
     */
    @Override
    public AddScheduledTaskResponse addScheduledTask(AddScheduledTaskRequest request) {

        long startTime = System.currentTimeMillis();
        CallerInfo callerInfo = Profiler.registerInfo(UmpConstants.CARRYPRICE_API_NAME,
                UmpConstants.ADD_SCHEDULED_TASK, false, true);
        AddScheduledTaskResponse response = new AddScheduledTaskResponse();
        try {
            LOGGER.info("CarryPriceInfoApi.addScheduledTask request:" + JacksonUtil.obj2json(request));
            response = scheduledTaskInfoApiServce.AddScheduledTask(request);
        } catch (Exception e) {
            response.setResponseCode(ResponseCodeEnum.EXCEPTION.getCode());
            response.setResponseMessage(ResponseCodeEnum.EXCEPTION.getDesc());
            LOGGER.error("CarryPriceInfoApi.addScheduledTask fail!", e);
            Profiler.functionError(callerInfo);
        } finally {
            long endTime = System.currentTimeMillis();
            LOGGER.info("CarryPriceInfoApi.addScheduledTask total time:" + (endTime - startTime));
            Profiler.registerInfoEnd(callerInfo);
        }
        return response;
    }

    /**
     * 插入运价监控
     *
     * @param request 插入运价监控请求实体
     * @return 插入运价监控响应实体
     */
    @Override
    public AddCarryPriceMonitorResponse addCarryPriceMonitor(AddCarryPriceMonitorRequest request) {

        long startTime = System.currentTimeMillis();
        CallerInfo callerInfo = Profiler.registerInfo(UmpConstants.CARRYPRICE_API_NAME,
                UmpConstants.ADD_CARRY_PRICE_MONITOR, false, true);
        AddCarryPriceMonitorResponse response = new AddCarryPriceMonitorResponse();
        try {
            LOGGER.info("CarryPriceInfoApi.addCarryPriceMonitor request: " + JacksonUtil.obj2json(request));
            response = carryPriceMonitorInfoApiService.addCarryPriceMonitor(request);
        } catch (Exception e) {
            response.setResponseCode(ResponseCodeEnum.EXCEPTION.getCode());
            response.setResponseMessage(ResponseCodeEnum.EXCEPTION.getDesc());
            LOGGER.error("CarryPriceInfoApi.addCarryPriceMonitor fail!", e);
            Profiler.functionError(callerInfo);
        } finally {
            long endTime = System.currentTimeMillis();
            LOGGER.info("CarryPriceInfoApi.addCarryPriceMonitor total time: " + (endTime - startTime));
            Profiler.registerInfoEnd(callerInfo);
        }
        return response;
    }

    /**
     * 查询航司出票类型
     *
     * @param request 查询航司出票类型请求实体
     * @return 查询航司出票类型响应实体
     */
    @Override
    public QueryAirWaysTicketTypeResponse queryAirwaysTicketType(QueryAirWaysTicketTypeRequest request) {

        long startTime = System.currentTimeMillis();
        CallerInfo callerInfo = Profiler.registerInfo(UmpConstants.CARRYPRICE_API_NAME,
                UmpConstants.QUERY_AIRWAYS_TICKET_TYPE, false, true);
        QueryAirWaysTicketTypeResponse response = new QueryAirWaysTicketTypeResponse();
        try {
            LOGGER.info("CarryPriceInfoApi.queryAirwaysTicketType request: " + JacksonUtil.obj2json(request));
            response = airwaysTicketTypeInfoApiService.queryAirwaysTicketType(request);
        } catch (Exception e) {
            LOGGER.error("CarryPriceInfoApi.queryAirwaysTicketType fail!", e);
            response.setResponseCode(ResponseCodeEnum.EXCEPTION.getCode());
            response.setResponseMessage(ResponseCodeEnum.EXCEPTION.getDesc());
            Profiler.functionError(callerInfo);
        } finally {
            long endTime = System.currentTimeMillis();
            LOGGER.info("CarryPriceInfoApi.queryAirwaysTicketType total time = " + (endTime - startTime));
            Profiler.registerInfoEnd(callerInfo);
        }
        LOGGER.info("CarryPriceInfoApi.queryAirwaysTicketType response: " + JacksonUtil.obj2json(response));
        return response;
    }

    /**
     * 查询限制航班
     *
     * @param request 查询限制航班请求实体
     * @return 查询限制航班响应实体
     */
    @Override
    public QueryLimitFlightResponse queryLimitFlight(QueryLimitFlightRequest request) {

        long startTime = System.currentTimeMillis();
        CallerInfo callerInfo = Profiler.registerInfo(UmpConstants.CARRYPRICE_API_NAME,
                UmpConstants.QUERY_LIMIT_FLIGHT, false, true);
        QueryLimitFlightResponse response = new QueryLimitFlightResponse();
        try {
            LOGGER.info("CarryPriceInfoApi.queryLimitFlight request: " + JacksonUtil.obj2json(request));
            response = limitFlightInfoApiServcie.queryLimitFlight(request);
        } catch (Exception e) {
            LOGGER.error("CarryPriceInfoApi.queryLimitFlight fail!", e);
            response.setResponseCode(ResponseCodeEnum.EXCEPTION.getCode());
            response.setResponseMessage(ResponseCodeEnum.EXCEPTION.getDesc());
            Profiler.functionError(callerInfo);
        } finally {
            long endTime = System.currentTimeMillis();
            LOGGER.info("CarryPriceInfoApi.queryLimitFlight total time = " + (endTime - startTime));
            Profiler.registerInfoEnd(callerInfo);
        }
        LOGGER.info("CarryPriceInfoApi.queryLimitFlight response: " + JacksonUtil.obj2json(response));
        return response;
    }
}
