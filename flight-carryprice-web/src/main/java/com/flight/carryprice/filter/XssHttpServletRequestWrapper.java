package com.flight.carryprice.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

/**
 * 字符过滤包装类
 * @author wanghx02
 * 2014年11月12日 上午11:16:51
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper { 
	
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    @Override
    public Map<String,String[]> getParameterMap() {
    	 Map<String,String[]> map = super.getParameterMap();
    	 if (map == null) {
             return null;
         }else{
	    	 for (String key : map.keySet()) {
	    		 String [] vs = map.get(key);
	    		 for(int i=0;i<vs.length;i++){
	    			 String v = vs[i];
	    			 v = cleanXSS(v);
	    			 vs[i] = v;
	    		 }
			 }
	    	return map;
         }
    }
    @Override
    public String getParameter(String parameter) {
          String value = super.getParameter(parameter);
          if (value == null) {
                 return null;
          }
          return cleanXSS(value);
    }
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
        {
            return null;
        }
        return cleanXSS(value);
    }
    private String cleanXSS(String value) {
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
       // value = value.replaceAll("script", "");
        return value;
    }

}
