package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.JdjpPatFlightSeatConfig;
import com.flight.carryprice.manager.JdjpPatFlightSeatManager;
import com.flight.carryprice.service.JdjpPatFlightSeatService;
import com.flight.carryprice.service.JdjpPatUpdatePolicyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.vo.airline.Airline;
import com.jd.airplane.flight.base.vo.cabin.Cabin;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/19 17:06.
 */
@Service
public class JdjpPatFlightSeatServiceImpl extends BaseServiceImpl<JdjpPatFlightSeatConfig> implements JdjpPatFlightSeatService {
    @Resource
    private JdjpPatFlightSeatManager jdjpPatFlightSeatManager;

    @Resource
    private JdjpPatUpdatePolicyService jdjpPatUpdatePolicyService;
    @Override
    public PageInfo<JdjpPatFlightSeatConfig> pagination(Integer pageIndex, Integer pageSize, JdjpPatFlightSeatConfig queryBean) {
        PageHelper.startPage(pageIndex, pageSize);
        List<JdjpPatFlightSeatConfig> list = jdjpPatFlightSeatManager.queryList(queryBean);
        return new PageInfo<>(list);
    }

    @Override
    public Object addFlightSeatConfig(JdjpPatFlightSeatConfig jdjpPatFlightSeatConfig) {
        jdjpPatFlightSeatConfig.setFlightNo(jdjpPatFlightSeatConfig.getFlightNo().toUpperCase());
        jdjpPatFlightSeatConfig.setSeat(jdjpPatFlightSeatConfig.getSeat().toUpperCase());
        Airline airline = jdjpPatUpdatePolicyService.getAirlineList(jdjpPatFlightSeatConfig.getAirWays(), jdjpPatFlightSeatConfig.getDepCode(), jdjpPatFlightSeatConfig.getArrCode());
        if (null == airline){
            return "获取航线信息为空,请检查[航司] [出发] [到达]参数";
        }
        jdjpPatFlightSeatConfig.setDistance(airline.getDistance());
        Cabin cabin = jdjpPatUpdatePolicyService.getCabin(jdjpPatFlightSeatConfig.getAirWays(), jdjpPatFlightSeatConfig.getSeat());
        if (null == cabin){
            return "获取舱位信息为空,请检查[航司] [舱位]参数";
        }
        jdjpPatFlightSeatConfig.setDiscount(BigDecimal.valueOf(cabin.getDiscount()));
        jdjpPatFlightSeatConfig.setSeatType(cabin.getType().byteValue());
        jdjpPatFlightSeatConfig.setSeatLevel(cabin.getLevel());
        return super.insertSelective(jdjpPatFlightSeatConfig);
    }

}
