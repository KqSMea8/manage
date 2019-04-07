package com.flight.carryprice.controller;

import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.common.util.Base64Util;
import com.flight.carryprice.config.ParamConfig;
import com.flight.carryprice.entity.AlUsers;
import com.flight.carryprice.service.JdjpUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	private static Logger LOGGER = Logger.getLogger(CommonController.class);

	@Resource
	private JdjpUserService jdjpUserService;

	/**
	 * 登陆页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/anon/login", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request, HttpSession session) {
		return "user/login";
	}

	/**
	 * 处理登陆表单
	 * 
	 * @param req
	 * @param session
	 * @param modelMap
	 * @param userName
	 * @param password
	 * @param captcha
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/anon/login", method = RequestMethod.POST)
	public String login(HttpServletRequest req, HttpSession session, ModelMap modelMap,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "captcha", required = true) String captcha) throws Exception {

		String sessionCaptcha = (String) session.getAttribute(ParamConfig.CAPTCHA);
		LOGGER.info("进行登陆验证,接收的验证码：" + captcha + ",session验证码：" + sessionCaptcha);
		String loginPage = "user/login";
		if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(sessionCaptcha)) {
			loginReturnError(modelMap, userName, password, "验证码错误");
			return loginPage;
		}
		AlUsers user = jdjpUserService.findUserByUserNameAndAppCode(userName, ParamConfig.APP_CODE);
		if (user == null) {
			loginReturnError(modelMap, userName, password, "用户不存在");
			return loginPage;
		}
		// 登录时输入的密码
		String encryptPSWD = Base64Util.encryptBASE64(
				MessageDigest.getInstance("MD5").digest(password.getBytes("UTF8"))).trim();

		String exPassword = user.getPassword();

		if (!encryptPSWD.equals(exPassword)) {
			loginReturnError(modelMap, userName, null, "密码错误");
			LOGGER.info("密码错误，：" + encryptPSWD);
			return loginPage;
		}
		// 设置session中菜单url
		Map<String, Object> map = jdjpUserService.initLeftMenu(ParamConfig.APP_CODE, user.getUsercode() + "");

		// 将用户，菜单和权限集合放入session
		session.setAttribute(ParamConfig.SESSION_USER, user);
		session.setAttribute(ParamConfig.SESSION_MEMUS, map.get(ParamConfig.SESSION_MEMUS));
		//session.setAttribute(ParamConfig.SESSION_URLS, map.get(ParamConfig.SESSION_URLS));

		return "redirect:/common/home";
	}

	/**
	 * 登录首页
	 * 
	 * @param session
	 * @param modelMap
	 * @param select
	 * @return
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest req,HttpSession session, ModelMap modelMap) {
		LOGGER.info("登陆首页");
		return "user/home";
	}

	/**
	 * 登出，需要获取session，在不拦截器中拦截
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/anon/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletResponse response) {
		LOGGER.info("logout...");
		AlUsers user = getCurrentUser(session);
		// SessionUserListener.removeUserSession(user.getId() + ""); //
		// 移除这个用户的session，让此用户重新登录
		session.invalidate(); // 设置session失效
		return "redirect:/common/anon/login";
	}

	/**
	 * 生成验证码
	 * 
	 * @param req
	 * @param res
	 * @param session
	 */
	@RequestMapping(value = "/anon/genMask", method = RequestMethod.GET)
	public void validateCode(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		try {
			int width = 75;
			int height = 23;
			// 取得一个4位随机字母数字字符串
			String s = RandomStringUtils.random(4, true, true);

			session.setAttribute(ParamConfig.CAPTCHA, s);

			res.setContentType("images/jpeg");
			res.setHeader("Pragma", "No-cache");
			res.setHeader("Cache-Control", "no-cache");
			res.setDateHeader("Expires", 0);

			ServletOutputStream out = res.getOutputStream();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			// 设定背景色
			g.setColor(new Color(255, 255, 255));
			g.fillRect(0, 0, width, height);

			// 设定字体
			// Font mFont = new Font("Times New Roman", Font.BOLD, 20);// 设置字体
			Font mFont = new Font(null, Font.PLAIN, 22);// 设置字体
			g.setFont(mFont);

			// 画边框
			g.setColor(new Color(240, 240, 240));
			g.drawRect(0, 0, width - 1, height - 1);
			Random random = new Random();

			// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160, 200));
			// 生成随机类
			for (int i = 0; i < 30; i++) {
				int x2 = random.nextInt(width);
				int y2 = random.nextInt(height);
				int x3 = random.nextInt(29);
				int y3 = random.nextInt(29);
				g.drawLine(x2, y2, x2 + x3, y2 + y3);
			}

			// 将认证码显示到图象中
			// g.setColor(new Color(20 + random.nextInt(110), 20 +
			// random.nextInt(110), 20 + random.nextInt(110)));
			g.setColor(new Color(38, 93, 75));
			g.drawString(s, 10, 19);

			// 图象生效
			g.dispose();
			// 输出图象到页面
			ImageIO.write((BufferedImage) image, "JPEG", out);
			out.close();
		} catch (Exception e) {
			LOGGER.error("生成验证码时报错", e);
			throw new SystemException("生成验证码时报错");
		}
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255){
			fc = 255;
		}
		if (bc > 255){
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 登陆未通过返回
	 * 
	 * @param modelMap
	 * @param userName
	 * @param pwd
	 * @param result
	 * @return
	 */
	private ModelMap loginReturnError(ModelMap modelMap, String userName, String pwd, String result) {
		modelMap.put("userName", userName);
		modelMap.put("password", pwd);
		modelMap.put("loginResult", result);
		return modelMap;
	}
}
