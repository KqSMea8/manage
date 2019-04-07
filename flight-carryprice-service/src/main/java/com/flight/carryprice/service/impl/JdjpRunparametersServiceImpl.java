package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.exception.BusinessException;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.JdjpRunparameters;
import com.flight.carryprice.manager.JdjpRunparametersManager;
import com.flight.carryprice.service.JdjpRunparametersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JdjpRunparametersServiceImpl extends BaseServiceImpl<JdjpRunparameters> implements JdjpRunparametersService {

	private final static Logger LOGGER = Logger.getLogger(JdjpRunparametersServiceImpl.class);

	@Resource
	private JdjpRunparametersManager jdjpRunparametersManager;

	@Override
	public PageInfo<JdjpRunparameters> pagination(Integer pageNo, Integer pageSize, JdjpRunparameters queryBean) {
		PageHelper.startPage(pageNo, pageSize);
		List<JdjpRunparameters> list = jdjpRunparametersManager.queryList(queryBean);
		PageInfo<JdjpRunparameters> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 获取更新频率
	 * dayList
	 * @param
	 */
	@Override
	public List<Map<String, String>> queryUpdateDay() {
		JdjpRunparameters jdjpRunparameters = new JdjpRunparameters();
		jdjpRunparameters.setName("UPDATE_DAY");
		try {
			jdjpRunparameters = jdjpRunparametersManager.selectOne(jdjpRunparameters);
			String[] updateDays = jdjpRunparameters.getValue().split(",");
			List<Map<String, String>> updateDayList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < updateDays.length; i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("key", updateDays[i]);
				String value;
				BigDecimal day = new BigDecimal(updateDays[i]);
				if (day.compareTo(BigDecimal.ONE) >= 0) {
					value = day.toString() + "天1次";
				} else {
					value = "1天" + (BigDecimal.ONE).divide(day).intValue() + "次";
				}
				map.put("value", value);
				updateDayList.add(map);
			}
			return updateDayList;
		} catch (Exception e) {
			LOGGER.error("获取更新频率错误", e);
			throw new BusinessException("获取更新频率错误，请检查全控参数【UPDATE_DAY】，格式<数字1,数字2,数字3>，原因：" + e.getMessage());
		}
	}
	@Override
	public List<Map<String, String>> queryParamByName(String name) {
		JdjpRunparameters jdjpRunparameters = new JdjpRunparameters();
		jdjpRunparameters.setName(name);
		jdjpRunparameters = jdjpRunparametersManager.selectOne(jdjpRunparameters);
		if (jdjpRunparameters == null){
			return null;
		}
		if (jdjpRunparameters.getValue() == null){
			return null;
		}
		String[] jsParams = jdjpRunparameters.getValue().split(",");
		List<Map<String, String>> mapArrayList = new ArrayList<>();
		for (String param : jsParams){
			Map<String, String> map = new HashMap<String, String>();
			//TODO
			map.put("key", param.split(":")[0]);
			map.put("value", param.split(":")[1]);
			mapArrayList.add(map);
		}
		return mapArrayList;

	}

	@Override
	public Map<String, String> getSeatTypeMap() {
		JdjpRunparameters jdjpRunparameters = new JdjpRunparameters();
		jdjpRunparameters.setName("BASE_DATA_SEAT_TYPE");
		Map<String, String> map = new HashMap<>();
		try {
			jdjpRunparameters = jdjpRunparametersManager.selectOne(jdjpRunparameters);
			String value = jdjpRunparameters.getValue();
			String[] values = value.split("\\|");
			for (int i = 0; i < values.length; i++) {
				String[] maps = values[i].split(",");
				map.put(maps[0], maps[1]);
			}
		} catch (Exception e) {
			LOGGER.error("获舱位级别错误", e);
			throw new BusinessException(
					"获舱位级别错误，请检查全控参数【BASE_DATA_SEAT_TYPE】，格式<数字1,舱位级别名称1|数字2,舱位级别名称2>，原因：" + e.getMessage());
		}
		return map;
	}

}

