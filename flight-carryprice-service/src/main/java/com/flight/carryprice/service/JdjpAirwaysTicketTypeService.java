package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;

import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/13 16:35.
 */
public interface JdjpAirwaysTicketTypeService extends BaseService<JdjpAirwaysTicketType> {
    List<JdjpAirwaysTicketType> queryCloseSwitchTypeList();

    /**
     * 获取可配置航司出票类型的航司
     * @return
     */
    List<AirCorp> getAllowAddAirways();
}
