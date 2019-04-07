package com.flight.carryprice.common.util;

import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.jd.airplane.flight.base.client.service.BaseDataServiceClient;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.request.dict.airline.AirlinePageRequest;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import com.jd.airplane.flight.base.vo.aircorp.AirCorpQueryRequest;
import com.jd.airplane.flight.base.vo.aircorp.AirCorpQueryResponse;
import com.jd.airplane.flight.base.vo.airline.Airline;
import com.jd.airplane.flight.base.vo.airline.AirlineQueryRequest;
import com.jd.airplane.flight.base.vo.airline.AirlineQueryResponse;
import com.jd.airplane.flight.base.vo.airport.Airport;
import com.jd.airplane.flight.base.vo.airport.AirportQueryRequest;
import com.jd.airplane.flight.base.vo.airport.AirportQueryResponse;
import com.jd.airplane.flight.base.vo.cabin.Cabin;
import com.jd.airplane.flight.base.vo.cabin.CabinQueryRequest;
import com.jd.airplane.flight.base.vo.cabin.CabinQueryResponse;
import com.jd.airplane.infra.response.AirResponse;
import com.jd.airplane.infra.response.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 获取基础数据工具类
 * @date 2019/3/1 09:22
 * @updateTime
 */
public class BaseDataUtils {
    private static Logger LOGGER = Logger.getLogger(BaseDataUtils.class);

    /**
     * 功能描述: 获取机场
     * @param:
     * @return:
     */
    public static List<Airport> getAirportList(QueryTypeEnum queryTypeEnum, Airport record){
        List<Airport> airportList = null;
        AirportQueryRequest airportQueryRequest = new AirportQueryRequest(queryTypeEnum, record);
        AirResponse<AirportQueryResponse> airResponse = BaseDataServiceClient.execute(airportQueryRequest);
        if(airResponse == null){
            LOGGER.info("获取的机场信息失败，接口返回为空");
            return null;
        }
        if(airResponse.checkSuccess()){
            AirportQueryResponse airportQueryResponse =airResponse.getData();
            if(airportQueryResponse != null){
                airportList = airportQueryResponse.getDatas();
            }
        }
        LOGGER.info("获取的机场返回信息："+airResponse.getErrorMessage());
        //LOGGER.info("机场："+JacksonUtil.obj2json(airResponse));
        return airportList;
    }

    /**
     * 功能描述: 获取航司
     * @param:
     * @return:
     */
    public static List<AirCorp> getAirCorpList(QueryTypeEnum queryTypeEnum, AirCorp record){
        List<AirCorp> aircorpList = new ArrayList<>();
        AirCorpQueryRequest airCorpQueryRequest = new AirCorpQueryRequest(queryTypeEnum, record);
        AirResponse<AirCorpQueryResponse> airResponse = BaseDataServiceClient.execute(airCorpQueryRequest);
        if(airResponse == null){
            LOGGER.info("获取的航司信息失败，接口返回为空");
            return null;
        }
        if( airResponse.checkSuccess()){
            AirCorpQueryResponse airCorpQueryResponse =airResponse.getData();
            if(airCorpQueryResponse != null){
                aircorpList = airCorpQueryResponse.getDatas();
            }
        }
        LOGGER.info("获取的航司返回信息："+airResponse.getErrorMessage());
        //LOGGER.info("航司："+JacksonUtil.obj2json(airResponse));
        return aircorpList;
    }

    /**
     * 功能描述: 查询数据库获取 分页航线
     * @param:
     * @return:
     */
    public static Page<Airline> getAirlinePage(Integer pageNo, Integer pageSize, Airline record){
        Page<Airline> airlinePage = null;
        //每页大小小于默认值500时，按照分页查询数据库
        if(pageSize <= CarryPriceConstants.PAGE_SIZE_DEFALUT){
            com.jd.airplane.flight.base.model.air.Airline modelAirline = new com.jd.airplane.flight.base.model.air.Airline();
            if(record != null){
                modelAirline.setAirCorpCode(record.getAirCorpCode());
                modelAirline.setDepAirportCode(record.getDepAirportCode());
                modelAirline.setArrAirportCode(record.getArrAirportCode());
                modelAirline.setFdType(record.getFdType());
                modelAirline.setNfdType(record.getNfdType());
            }
            AirlinePageRequest pageRequest = new AirlinePageRequest(pageNo, pageSize, modelAirline);
            AirResponse<Page<com.jd.airplane.flight.base.model.air.Airline>> airResponse = BaseDataServiceClient.execute(pageRequest);
            if(airResponse == null){
                LOGGER.info("获取的航线信息失败，接口返回为空");
                return null;
            }
            if(airResponse.checkSuccess()){
                Page<com.jd.airplane.flight.base.model.air.Airline> page = airResponse.getData();
                if(page != null){
                    List<com.jd.airplane.flight.base.model.air.Airline> items = page.getData();
                    if (CollectionUtils.isEmpty(items)) {
                        LOGGER.error("加载航线库信息数据为空!");
                        return null;
                    }
                    List<Airline> list = new LinkedList<>();
                    for(com.jd.airplane.flight.base.model.air.Airline airline : items){
                        Airline vo = new Airline();
                        BeanUtils.copyProperties(airline,vo);
                        list.add(vo);
                    }
                    airlinePage = new Page<>();
                    airlinePage.setPageNo(page.getPageNo());
                    airlinePage.setPageSize(page.getPageSize());
                    airlinePage.setTotalRecord(page.getTotalRecord());
                    airlinePage.setTotalPage(page.getTotalPage());
                    airlinePage.setData(list);
                }
            }

        }else{
            /**查询符合条件的全部航线，组装成page***/
            List<Airline> airlineList = new ArrayList<>();
            QueryTypeEnum queryTypeEnum = QueryTypeEnum.ALL;
            if(record != null){
                queryTypeEnum = getQueryTypeEnum(record);
            }else{
                record = new Airline();
            }
            AirlineQueryRequest airlineQueryRequest = new AirlineQueryRequest(queryTypeEnum, record);
            AirResponse<AirlineQueryResponse> airResponse = BaseDataServiceClient.execute(airlineQueryRequest);
            if(airResponse.checkSuccess()){
                AirlineQueryResponse airlineQueryResponse =airResponse.getData();
                if(airlineQueryResponse != null){
                    airlineList = airlineQueryResponse.getDatas();
                }
            }
            if(CollectionUtils.isNotEmpty(airlineList)){

                List<Airline> list = new ArrayList<>();
                for (int i= (pageNo-1)*pageSize ; i<pageNo*pageSize && i< airlineList.size(); i++){
                    list.add(airlineList.get(i));
                }
                airlinePage = new Page<>();
                airlinePage.setPageNo(pageNo);
                airlinePage.setPageSize(pageSize);
                airlinePage.setTotalRecord(airlineList.size());
                airlinePage.setTotalPage(airlineList.size()%pageSize == 0 ? airlineList.size()/pageSize : (airlineList.size()/pageSize + 1));
                airlinePage.setData(list);
            }
        }

        return airlinePage;
    }

    /**
     * 功能描述: 根据条件获取航线信息
     * @param:
     * @return:
     */
    public static List<Airline> getAirlineListWithCondition(Airline record){
        List<Airline> airlineList = new ArrayList<>();
        QueryTypeEnum queryTypeEnum = QueryTypeEnum.ALL;
        if(record != null){
            queryTypeEnum = getQueryTypeEnum(record);
        }else{
            record = new Airline();
        }
        AirlineQueryRequest airlineQueryRequest = new AirlineQueryRequest(queryTypeEnum, record);
        AirResponse<AirlineQueryResponse> airResponse = BaseDataServiceClient.execute(airlineQueryRequest);
        if(airResponse == null){
            LOGGER.info("获取的航线信息失败，接口返回为空");
            return null;
        }
        if(airResponse.checkSuccess()){
            AirlineQueryResponse airlineQueryResponse =airResponse.getData();
            if(airlineQueryResponse != null){
                airlineList = airlineQueryResponse.getDatas();
            }
        }
        LOGGER.info("航线："+JacksonUtil.obj2json(airlineList));
        return airlineList;
    }

    /**
     * 功能描述: 获取航线
     * @param:
     * @return:
     */
    public static List<Airline> getAirlineList(QueryTypeEnum queryTypeEnum, Airline record){
        List<Airline> airlineList = new ArrayList<>();
        AirlineQueryRequest airlineQueryRequest = new AirlineQueryRequest(QueryTypeEnum.ALL, new Airline());
        AirResponse<AirlineQueryResponse> airResponse = BaseDataServiceClient.execute(airlineQueryRequest);
        if(airResponse == null){
            LOGGER.info("获取的航线信息失败，接口返回为空");
            return null;
        }
        if(airResponse.checkSuccess()){
            AirlineQueryResponse airlineQueryResponse =airResponse.getData();
            if(airlineQueryResponse != null){
                List<Airline> list = airlineQueryResponse.getDatas();
                if(CollectionUtils.isEmpty(list)){
                    LOGGER.info("航线返回的数据为空，直接返回");
                    return airlineList;
                }
                airlineList = filterAirline(list, record);
            }
        }
        LOGGER.info("获取的航线返回信息："+airResponse.getErrorMessage());
        LOGGER.info("航线："+JacksonUtil.obj2json(airlineList));
        return airlineList;
    }

    /**
     * 功能描述: 获取舱位
     * @param:
     * @return:
     */
    public static List<Cabin> getCabinList(QueryTypeEnum queryTypeEnum, Cabin record){
        List<Cabin> cabinList = new ArrayList<>();
        CabinQueryRequest cabinQueryRequest = new CabinQueryRequest(queryTypeEnum, record);
        AirResponse<CabinQueryResponse> airResponse = BaseDataServiceClient.execute(cabinQueryRequest);
        if(airResponse == null){
            LOGGER.info("获取的舱位信息失败，接口返回为空");
            return null;
        }
        if(airResponse.checkSuccess()){
            CabinQueryResponse cabinQueryResponse =airResponse.getData();
            if(cabinQueryResponse != null){
                cabinList = cabinQueryResponse.getDatas();
            }
        }
        //LOGGER.info("获取的舱位返回信息："+airResponse.getErrorMessage());
        LOGGER.info("舱位："+JacksonUtil.obj2json(airResponse));
        return cabinList;
    }


    /**
     * 功能描述: 过滤航线
     * @param:
     * @return:
     */
    public static List<Airline> filterAirline(List<Airline> list, Airline record){

        List<Airline> airlineList = new ArrayList<>();
        if(record == null){
            return list;
        }
        for(Airline airline : list){
            //FD航司类型不一致，直接过滤掉
            if(StringUtils.isNotBlank(record.getAirCorpCode()) && !record.getAirCorpCode().equals(airline.getAirCorpCode())){
                continue;
            }
            //FD出发机场类型不一致，直接过滤掉
            if(StringUtils.isNotBlank(record.getDepAirportCode()) && !record.getDepAirportCode().equals(airline.getDepAirportCode())){
                continue;
            }
            //FD到达机场类型不一致，直接过滤掉
            if(StringUtils.isNotBlank(record.getArrAirportCode()) && !record.getArrAirportCode().equals(airline.getArrAirportCode())){
                continue;
            }
            //FD航线类型不一致，直接过滤掉
            if(record.getFdType() != null && record.getFdType() != airline.getFdType()){
                continue;
            }
            //NFD航线类型不一致，直接过滤掉
            if(record.getNfdType() != null && record.getNfdType() != airline.getNfdType()){
                continue;
            }
            //是否售卖婴儿不一致，直接过滤掉
            /*if(record.getIsSellBabyTicket() != null && record.getIsSellBabyTicket().equals(airline.getNfdType())){
                continue;
            }*/
            airlineList.add(airline);
        }
        return airlineList;

    }

    /**
     * 功能描述: 获取类型
     * @param:
     * @return:
     */
    public static QueryTypeEnum getQueryTypeEnum(Airline record){
        QueryTypeEnum queryTypeEnum = QueryTypeEnum.ALL;
        if(StringUtils.isNotBlank(record.getAirCorpCode())){
            queryTypeEnum = QueryTypeEnum.UNION_KEY;
        }
        if(StringUtils.isNotBlank(record.getAirCorpCode())){
            queryTypeEnum = QueryTypeEnum.UNION_KEY;
        }
        if(StringUtils.isNotBlank(record.getAirCorpCode())){
            queryTypeEnum = QueryTypeEnum.UNION_KEY;
        }
        if(record.getFdType() != null){
            queryTypeEnum = QueryTypeEnum.UNION_KEY;
        }
        if(record.getNfdType() != null){
            queryTypeEnum = QueryTypeEnum.UNION_KEY;
        }
        return queryTypeEnum;
    }

}
