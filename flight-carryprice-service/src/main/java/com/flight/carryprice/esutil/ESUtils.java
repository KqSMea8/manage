package com.flight.carryprice.esutil;

import com.alibaba.fastjson.JSON;
import com.flight.carryprice.vo.WrkOrderContentES;
import com.jd.es.client.ESClientOperations;
import com.jd.es.client.domain.client.create.ESCreateSelector;
import com.jd.es.client.domain.client.query.ESResult;
import com.jd.es.client.domain.client.query.condition.query.ESAnd;
import com.jd.es.client.domain.client.query.condition.sort.ESOrder;
import com.jd.es.client.domain.client.selector.ESQuerySelector;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.List;
import java.util.Map;



public class ESUtils {
	private static Logger LOGGER = Logger.getLogger(ESUtils.class);
	private static final String ES_CONTENT = "content";
	private static final String ES_BUSSINESSID = "bussinessId";
	private static final String ES_ADDTIME = "addTime";
	private static final String ES_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String queryEsDataValue(ESClientOperations selfWorkerOrderTxtClient, String value){
		List<Map<String, Object>> dataList = null;
		try {
			dataList = queryEsData(selfWorkerOrderTxtClient,
					value);
		}catch (Exception e){
			LOGGER.error("Es读取数据异常", e);
			return null;
		}
		if (dataList == null){
			return null;
		}
		for (Map<String, Object> dataMap : dataList) {
			if (dataMap.get(ES_CONTENT) != null){
				return String.valueOf(dataMap.get(ES_CONTENT));
			}
		}
		return null;
	}
	public static List<Map<String, Object>> queryEsData(ESClientOperations selfWorkerOrderTxtClient, String value) {
		ESQuerySelector esQuerySelector = new ESQuerySelector();
		ESAnd esAnd = new ESAnd();
		esAnd.setFiled(ES_BUSSINESSID);
		esAnd.setValue(value);
		esQuerySelector.addFilterQuery(esAnd);
		esQuerySelector.setSort(ES_ADDTIME, ESOrder.DESC);
		ESResult esResult = selfWorkerOrderTxtClient.search(esQuerySelector);
		List<Map<String, Object>> dataList = esResult.getData().getData();
		return dataList;
	}

	public static ESResult insertEsData(ESClientOperations selfWorkerOrderTxtClient, String bussinessId, String value) {
		// 插入ES
		WrkOrderContentES woces = new WrkOrderContentES();
		woces.setBussinessId(bussinessId);
		woces.setContent(value);
		woces.setAddTime(new Date());
		String content = JSON.toJSONStringWithDateFormat(woces, ES_DATEFORMAT);
		ESCreateSelector esCreateSelector = new ESCreateSelector(true);
		esCreateSelector.setId(bussinessId);
		esCreateSelector.setContent(content);
		esCreateSelector.setTcp(false);
		ESResult esResult = selfWorkerOrderTxtClient.createRowData(esCreateSelector);
		return esResult;
	}
}
