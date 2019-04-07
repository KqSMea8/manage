package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpPatFlightSeatConfig;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/19 17:01.
 */
public interface JdjpPatFlightSeatManager {
    List<JdjpPatFlightSeatConfig> queryList(JdjpPatFlightSeatConfig queryBean);
}
