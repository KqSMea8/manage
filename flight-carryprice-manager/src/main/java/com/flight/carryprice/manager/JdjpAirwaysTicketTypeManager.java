package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/13 16:31.
 */
public interface JdjpAirwaysTicketTypeManager{

    List<JdjpAirwaysTicketType> queryCloseSwitchTypeList();

    /**
     * 根据航司二字码查询航司出票类型
     * @param airways 航司二字码
     * @return 航司出票类型
     */
    JdjpAirwaysTicketType queryTicketTypeByAirways(String airways);
}
