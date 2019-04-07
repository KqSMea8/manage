package com.flight.carryprice.gds.service.impl;

import com.flight.carryprice.common.config.GDSConfig;
import com.flight.carryprice.common.enums.GDSEnums;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.gds.service.GdsRequestService;
import com.jd.gds.dto.BaseRequest;
import com.jd.gds.dto.BaseResponse;
import com.jd.gds.service.GdsCommonService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author juln 商旅事业部
 * @version 1.0.0
 * @Description 通过JSF请求GDS的服务
 * @date 2018/3/20 17:40
 * @updateTime
 * @see
 */
@Service
public class GdsRequestServiceImpl implements GdsRequestService {

	private final static Logger LOGGER = Logger.getLogger(GdsRequestServiceImpl.class);

	@Autowired
	private GdsCommonService gdsCommonService;

	/**
	 * 获取请求的BaseRequest
	 *
	 * @param request
	 * @param gdsEnums
	 * @param agencyCode
	 * @return
	 * @throws Exception
	 */
	public BaseRequest getRequest(Object request, String officeID, GDSEnums gdsEnums, String agencyCode){
		String requestType = gdsEnums.getCode();
		String saleSysId = GDSConfig.getGdsSalesysId(officeID);
		LOGGER.info("数据来源agencyCode=" + agencyCode + ",商家ID saleSysId=" + saleSysId);
		String jsonStr = JacksonUtil.obj2json(request);
		if (StringUtils.isBlank(jsonStr)) {
			return null;
		}
		BaseRequest bean = new BaseRequest();
		bean.setRequestType(requestType);
		bean.setAgencyCode(agencyCode);
		bean.setData(jsonStr);
		bean.setSaleSysId(saleSysId);
		return bean;
	}

	@Override
	public <T> T doService(Object request, String officeID, GDSEnums gdsEnums, Class<T> respClazz) {
		return doService(request, officeID, gdsEnums, respClazz, null);
	}

	@Override
	public <T> T doService(Object request, String officeID, GDSEnums gdsEnums, Class<T> respClazz, String agencyCode) {
		T t = null;
		try {
			BaseRequest baseRequest = getRequest(request, officeID, gdsEnums, agencyCode);
			LOGGER.info("GDS接口请求信息=" + JacksonUtil.obj2json(baseRequest));
			BaseResponse baseResponse = gdsCommonService.doService(baseRequest);
			LOGGER.info("GDS接口返回信息=" + JacksonUtil.obj2json(baseResponse));
			t = JacksonUtil.obj2obj(baseResponse, respClazz);
		} catch (Exception e) {
			LOGGER.error("请求GDS接口异常", e);
		}
		return t;
	}

}
