package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpLimitFlightInfo;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/21 18:21.
 */
public interface JdjpLimitFlightInfoManager {
    List<JdjpLimitFlightInfo> queryList(JdjpLimitFlightInfo queryBean);
    List<JdjpLimitFlightInfo> queryEffectList(JdjpLimitFlightInfo queryBean);
    List<JdjpLimitFlightInfo> queryFields();
}
