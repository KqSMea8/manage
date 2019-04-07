package com.flight.carryprice.filter;

import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.config.ParamConfig;
import com.flight.carryprice.entity.AlPopedomTblmenu;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * session验证过滤器
 * 
 * @author wanghx
 * @date (2014-6-26 上午09:25:32)
 */
public class SessionFilter implements Filter {

	protected static final Logger logger = Logger.getLogger(SessionFilter.class);

	private List<String> unCheckSessionList = new ArrayList<String>(); // 不检查权限列表

	// private List<String> unCheckAuthorityList = new ArrayList<String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 初始化例外url
		String unCheckSessionUrls = filterConfig.getInitParameter("unCheckSessionUrls");
		if (unCheckSessionUrls != null) {
			Collections.addAll(this.unCheckSessionList, unCheckSessionUrls.split(","));
		}
		logger.info("filter initializing,the unCheckSession urls:");
		for (String nurl : this.unCheckSessionList) {
			logger.info(nurl);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// // 设置请求的字符编码
		// request.setCharacterEncoding("UTF-8");
		// // 设置返回请求的字符编码
		// response.setCharacterEncoding("UTF-8");
		// 转换ServletRequest为 HttpServletRequest
		HttpServletRequest req = (HttpServletRequest) request;
		// 转换ServletResponse为HttpServletRequest
		HttpServletResponse res = (HttpServletResponse) response;
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");

		request.setAttribute("staticPath", req.getContextPath());
	//	handlePath(req);
		String uri = req.getServletPath();
		if (uri != null && !isNotFiltUrl(uri) && !uri.endsWith(".css") && !uri.endsWith(".jpg")
				&& !uri.endsWith(".png") && !uri.endsWith(".gif") && !uri.endsWith(".ico") && !uri.endsWith(".js")// 不过滤的类型
		) {
			logger.info("-------------请求的地址：" + uri + "-------------");
			HttpSession session = req.getSession();

			Object o = session.getAttribute(ParamConfig.SESSION_USER);
			String ref = req.getHeader("referer");

			if (o == null) {
				logger.info("session中user为空。请求的url:" + uri + ",来源：" + ref);
				session.invalidate();
				PrintWriter out = res.getWriter();
				out.print("<script>");
				out.print("alert('当前未登录或当前登录已过期！');");
				out.print("window.location.href='" + req.getContextPath() + "/common/anon/login';");
				out.print("</script>");
				out.flush();
				out.close();
				// res.sendRedirect(req.getContextPath() +
				// "/common/anon/login");
				return;
			}

			// 处理路径
			// this.handlePath(req);

			// 验证权限
			// Map<String, String> powerMap = (Map<String, String>)
			// session.getAttribute(ParamConfig.SESSION_URLS); //
			// 权限url，key为模块名+菜单名，如（userManager/user/userList，key为userManager/user）

			// 根据requestUri截取模块名+菜单名 ,截取前 /userManager/user/userList，截取后
			// userManager/user
			if (uri.startsWith("/")) { // uri以/开头，去掉/
				uri = uri.substring(1);
			}
			String modelName = "";
			if (uri.contains("/")) {
				modelName = uri.substring(0, uri.lastIndexOf("/")); // 模块名+菜单名
			}

			if (StringUtils.isBlank(modelName)) { // 模块名为空
				logger.info("模块名为空");
				// res.sendRedirect(req.getContextPath() +
				// "/common/anon/login");
				// return;
				throw new SystemException("用户没有访问此功能的权限");
			}

			if (!"common".equals(modelName)) { // 首页非权限相关的不进行验证

				String modelRegex = modelName + "/[\\d\\D]*";
				if (StringUtils.isBlank(modelRegex)) { // 该用户没有访问此requestUri的权限
					logger.info("用户没有访问此功能的权限");
					// res.sendRedirect(req.getContextPath() +
					// "/common/anon/login");
					// return;
					throw new SystemException("用户没有访问此功能的权限");
				}

				Pattern p = Pattern.compile(modelRegex);
				// 进行匹配，并将匹配结果放在Matcher对象中
				Matcher m = p.matcher(uri);
				if (!m.matches()) { // 该用户没有访问此requestUri的权限
					logger.info("用户没有访问此功能的权限");
					// res.sendRedirect(req.getContextPath() +
					// "/common/anon/login");
					// return;
					throw new SystemException("用户没有访问此功能的权限");
				}
			}
			// 左侧树导航的选中
			session.setAttribute("thisModuleUrl", modelName);
		}

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		unCheckSessionList.clear();
	}


	/**
	 * 处理路径
	 * 
	 * @param req
	 * @author wanghx 2015年3月25日上午11:38:20
	 */
	private void handlePath(HttpServletRequest req) {
		String contextPath = req.getContextPath();
		if ("/".equals(contextPath))
			contextPath = "";
		String port = "";
		if (req.getServerPort() != 80)
			port = ":" + req.getServerPort();
		String staticPath = req.getScheme() + "://" + req.getServerName() + port + contextPath;
		req.setAttribute("staticPath", staticPath);
		req.setAttribute("contextPath", contextPath);
	}

	/**
	 * 判断是否包含在不过滤列表中
	 * 
	 * @Author wanghx02
	 * @Date 2014-6-26 上午09:29:08
	 * @Description
	 * @param uri
	 * @return
	 */
	private boolean isNotFiltUrl(String uri) {
		if (unCheckSessionList.contains(uri))
			return true;
		return false;
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
	public boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;
		return isAjax;
	}
}
