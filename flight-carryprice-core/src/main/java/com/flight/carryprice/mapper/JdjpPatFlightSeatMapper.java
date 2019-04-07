package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpPatFlightSeatConfig;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
 * @Author hYuan 机票供应链研发部
 * @Date 17:00 2019/3/19
 **/
public interface JdjpPatFlightSeatMapper extends Mapper<JdjpPatFlightSeatConfig> {
    List<JdjpPatFlightSeatConfig> queryList(JdjpPatFlightSeatConfig queryBean);
}
