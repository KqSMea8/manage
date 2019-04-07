package com.flight.carryprice.common.config;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class VariableExpander {
	
	private Config config;

	private String pre;
	private String post;

	/**
	 * 配置文件缓存
	 */
	private Map cache;

	public VariableExpander(Config config, String pre, String post)
	{
		this.config = config;
		this.pre = pre;
		this.post = post;
		cache = new HashMap();
	}

	public void clearCache()
	{
		cache.clear();
	}
	
	@SuppressWarnings("unchecked")
	public String expandVariables(String source, ConfigType configType)
	{
		String result = (String)cache.get(source);
		if (source == null || result != null) {
			return result;
		}
		int fIndex = source.indexOf(pre);
		if (fIndex == -1) {
			return source;
		}
		StringBuffer sb = new StringBuffer(source);
		while (fIndex > -1) {
			int lIndex = sb.indexOf(post);
			int start = fIndex + pre.length();
			if (fIndex == 0) {
				String key = sb.substring(start, start + lIndex - pre.length());
				sb.replace(fIndex, fIndex + lIndex + 1, config.getConfig(key, configType));
			}
			else {
				String key = sb.substring(start, lIndex);
				sb.replace(fIndex, lIndex + 1, config.getConfig(key, configType));
			}
			fIndex = sb.indexOf(pre);
		}
		result = sb.toString();
		cache.put(source, result);
		return result;
	}
}
