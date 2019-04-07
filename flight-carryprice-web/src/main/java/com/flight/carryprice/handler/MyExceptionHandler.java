package com.flight.carryprice.handler;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理
 * 
 * @author lgp
 * @date 2016年9月2日 下午3:07:57
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

	private static Logger LOGGER = Logger.getLogger(MyExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		LOGGER.error(ex.getMessage(), ex);

		if (!isAjaxRequest(request)) { // 非ajax请求
			LOGGER.info("*****进入了spring的异常处理,当前请求为普通url请求**********");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("ex", ex);
			model.put("errorMessage", ex.getMessage());

			return new ModelAndView("error/500", model);
		} else { // ajax请求
			LOGGER.info("*****进入了spring的异常处理,当前请求为ajax请求**********");
			try {
				PrintWriter writer = response.getWriter();
				writer.write(ex.getMessage());
				writer.flush();
			} catch (IOException e) {
				LOGGER.error("",e);
			}
			return null;
		}

	}

	/**
	 * 判断是否是Ajax异步请求
	 * 
	 * @Author wanghx02
	 * @Date 2014-6-26 上午09:30:05
	 * @Description
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;
		return isAjax;
	}

}
