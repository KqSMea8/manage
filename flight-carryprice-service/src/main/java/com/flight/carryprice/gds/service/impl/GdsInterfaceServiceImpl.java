package com.flight.carryprice.gds.service.impl;

import com.flight.carryprice.common.config.GDSConfig;
import com.flight.carryprice.common.constant.GDSConstants;
import com.flight.carryprice.common.enums.GDSEnums;
import com.flight.carryprice.common.enums.PassengerTypeEnums;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.gds.service.GdsInterfaceService;
import com.flight.carryprice.gds.service.GdsRequestService;
import com.jd.gds.dto.FDRequestDTO;
import com.jd.gds.dto.FDResponseDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 请求GDS接口的封装类
 * @date 2019/3/21 14:45
 * @updateTime
 * @see
 */
@Service
public class GdsInterfaceServiceImpl implements GdsInterfaceService {

	private static Logger LOGGER = Logger.getLogger(GdsInterfaceServiceImpl.class);

	@Resource
	private GdsRequestService gdsRequestService;

	@Override
	public FDResponseDTO fdprice(String depCode, String arrCode, String depDate, String airways){
		return fdprice(depCode, arrCode, depDate, airways, PassengerTypeEnums.ADULT.getCode(), GDSConfig.OFFICENO);
	}

	@Override
	public FDResponseDTO fdprice(String depCode, String arrCode, String depDate, String airways, String passType){
		return fdprice(depCode, arrCode, depDate, airways, passType, GDSConfig.OFFICENO);
	}

	@Override
	public FDResponseDTO fdprice(String depCode, String arrCode, String depDate, String airways, String passType, String officeID){
		FDRequestDTO request = new FDRequestDTO();
		request.setOrgCity(depCode);
		request.setDstCity(arrCode);
		request.setFlightDate(depDate);
		request.setCarrier(airways);
		request.setPassType(passType);// 默认为成人0
		request.setOfficeID(officeID);
		FDResponseDTO fdResponseDTO = gdsRequestService.doService(request, officeID, GDSEnums.GDS_INTERFACE_FD,
				FDResponseDTO.class);
		LOGGER.info("---fdprice请求request="+ JacksonUtil.obj2json(request)
				+"返回结果fdResponseDTO="+JacksonUtil.obj2json(fdResponseDTO));
		return fdResponseDTO;
	}



}
