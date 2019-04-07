package com.flight.carryprice.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description quartz任务启动结果
 * @date 2019/3/21 16:47
 * @updateTime
 */
public class QuartzResult {

	private boolean success;

	private String resultCode;

	private Map<String, String> model = new HashMap<String, String>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public void addDefaultModel(String key, String value) {
		model.put(key, value);
	}

	public Map<String, String> getModel() {
		return model;
	}

	public void setModel(Map<String, String> model) {
		this.model = model;
	}

}
