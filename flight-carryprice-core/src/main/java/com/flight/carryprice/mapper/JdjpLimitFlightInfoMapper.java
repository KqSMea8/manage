package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpLimitFlightInfo;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface JdjpLimitFlightInfoMapper extends Mapper<JdjpLimitFlightInfo> {

	List<JdjpLimitFlightInfo> queryList(JdjpLimitFlightInfo queryBean);
	List<JdjpLimitFlightInfo> queryEffectList(JdjpLimitFlightInfo queryBean);
	List<JdjpLimitFlightInfo> queryFields();
}