package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpPatFlightSeatConfig;
import com.github.pagehelper.PageInfo;

/**
 * Author wanghaiyuan
 * Date 2019/3/19 17:03.
 */
public interface JdjpPatFlightSeatService extends BaseService<JdjpPatFlightSeatConfig> {
    PageInfo<JdjpPatFlightSeatConfig> pagination(Integer pageIndex, Integer pageSize, JdjpPatFlightSeatConfig queryBean);
    Object addFlightSeatConfig(JdjpPatFlightSeatConfig jdjpPatFlightSeatConfig);
}
