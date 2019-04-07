package com.flight.carryprice.controller;

import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.common.util.CustomerDateFormat;
import com.flight.carryprice.config.ParamConfig;
import com.flight.carryprice.entity.AlPopedomTblmenu;
import com.flight.carryprice.entity.AlUsers;
import org.apache.log4j.Logger;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * controller基类
 * 
 * @author wanghx
 */
public class BaseController {

	protected final Logger LOGGER = Logger.getLogger(this.getClass());

	/**
	 * 获取页面传递参数
	 * 
	 */
	public String getParam(HttpServletRequest request, String param){
		return request.getParameter(param) == null ? "" : request.getParameter(param).toString().trim();
	}
	
	/**
	 * 格式处理器
	 * 
	 * @param binder
	 * @author wanghx 2015年3月13日下午2:55:28
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new CustomerDateFormat(), true));
		binder.setBindingErrorProcessor(new BindingErrorProcessor() {

			@Override
			public void processMissingFieldError(String missingField, BindingResult bindingResult) {
				LOGGER.debug(missingField);
			}

			@Override
			public void processPropertyAccessException(PropertyAccessException ex, BindingResult bindingResult) {
				// LOGGER.trace(ex);
			}
		});
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @param session
	 * @return
	 */
	public AlUsers getCurrentUser(HttpSession session) {
		AlUsers user = (AlUsers) session.getAttribute(ParamConfig.SESSION_USER);
		return user;
	}

	/**
	 * 获取当前登录用户的权限菜单
	 * 
	 * @param session
	 * @return
	 */
	public List<AlPopedomTblmenu> getCurrentMenu(HttpSession session) {
		List<AlPopedomTblmenu> menus = (List<AlPopedomTblmenu>) session.getAttribute(ParamConfig.SESSION_MEMUS);
		return menus;
	}

	/**
	 * 获取分页的pageIndex
	 * 
	 * @param request
	 * @return
	 */
	public Integer getPageIndex(HttpServletRequest request) {
		String strPageIndex = request.getParameter("pageIndex");
		int nPageIndex = 1;
		if (strPageIndex != null && strPageIndex.trim().length() != 0) {
			nPageIndex = Integer.parseInt(strPageIndex);
		}
		return nPageIndex;
	}

	/**
	 * 获取pageSize
	 * 
	 * @param request
	 * @return
	 */
	public Integer getPageSize(HttpServletRequest request) {
		String strPageSize = request.getParameter("pageSize");
		int nPageSize = 20;
		if (strPageSize != null && strPageSize.trim().length() != 0) {
			nPageSize = Integer.parseInt(strPageSize);
		}
		return nPageSize;
	}

	/**
	 * 获取pageSize默认显示50条
	 * 
	 * @param request
	 * @return
	 */
	public Integer getPageSizeDef50(HttpServletRequest request) {
		String strPageSize = request.getParameter("pageSize");
		int nPageSize = 50;
		if (strPageSize != null && strPageSize.trim().length() != 0) {
			nPageSize = Integer.parseInt(strPageSize);
		}
		return nPageSize;
	}

	/**
	 * 输出js到页面
	 * 
	 * @param out
	 * @param js
	 */
	public void outPrint(HttpServletResponse resp, String js) {
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			out.println("<script>");
			out.println(js);
			out.println("</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error("",e);
			throw new SystemException("输出js信息异常");
		}
	}
}
