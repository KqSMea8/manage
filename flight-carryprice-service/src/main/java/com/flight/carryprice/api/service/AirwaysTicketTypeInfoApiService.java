package com.flight.carryprice.api.service;

import com.flight.carryprice.jdmodel.QueryAirWaysTicketTypeRequest;
import com.flight.carryprice.jdmodel.QueryAirWaysTicketTypeResponse;

/**
 * @Description 航司出票类型API service层
 * @Author: qinhaoran1
 * @Date: 2019/3/19
 */
public interface AirwaysTicketTypeInfoApiService {

    /**
     * 航司出票类型查询
     *
     * @param request
     * @return
     */
    QueryAirWaysTicketTypeResponse queryAirwaysTicketType(QueryAirWaysTicketTypeRequest request);
}
