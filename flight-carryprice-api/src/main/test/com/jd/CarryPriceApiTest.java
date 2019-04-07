package com.jd;

import com.flight.carryprice.common.enums.CodeEnums;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.common.util.RegularUtil;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceBySeatsRequest;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceBySeatsResponse;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceRequest;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceResponse;
import com.flight.carryprice.service.CarryPriceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 单元测试Test(这里用一句话描述这个方法的作用)
 * @date 2019/2/1 16:27
 * @updateTime
 */
public class CarryPriceApiTest {
    private static Logger logger = Logger.getLogger(CarryPriceApiTest.class);

    @Test
    public void checkParams(){
        String depDate = "2019-04-20";
        if(StringUtils.isBlank(depDate) || !RegularUtil.regexDepDate(depDate)){
            System.out.println("数据为：");
        }
    }


    @Test
    public void testGetRateCommonPolicys() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        CarryPriceService service = (CarryPriceService)context.getBean("carryPriceService");
        QueryFdCarryPriceRequest request = new QueryFdCarryPriceRequest();
        request.setAirWays("CA");
        request.setDepCode("PEK");
        request.setArrCode("CAN");
        request.setDepDate("2019-04-20");
        request.setSourceType(1);
        //QueryFdCarryPriceResponse response = service.queryFdPriceList(request);

        System.out.println("数据为："+ JacksonUtil.obj2json(request));
    }

    @Test
    public void testGetRateCommonPolicys1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        CarryPriceService service = (CarryPriceService)context.getBean("carryPriceService");
        QueryFdCarryPriceBySeatsRequest request = new QueryFdCarryPriceBySeatsRequest();
        request.setAirWays("CA");
        request.setDepCode("PEK");
        request.setArrCode("CAN");
        List<String> seats = new ArrayList<>();
        seats.add("K");
        request.setSeats(seats);
        QueryFdCarryPriceBySeatsResponse response = service.queryFdPriceBySeatsList(request);

        System.out.println("数据为："+ JacksonUtil.obj2json(response));
    }



}
