package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpNfdCarryPriceInfo;
import com.github.pagehelper.PageInfo;

public interface JdjpNfdCarryPriceInfoService extends BaseService<JdjpNfdCarryPriceInfo> {

	PageInfo<JdjpNfdCarryPriceInfo> pagination(Integer pageIndex, Integer pageSize, JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo);

	int insetbatchNfdCarryPrice(JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo);

	int updateNfdCarryPrice(JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo);
	int updateBatch(String operator, Byte state, Integer[] ids);
}
