package com.flight.carryprice.initdata;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Service
public class InitDataListener implements InitializingBean, ServletContextAware {

	private static Logger logger = Logger.getLogger(InitDataListener.class);

	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("-------------InitDataListener 初始化--------------");

		// 把枚举放入application属性
		// Map<String, Map<String, String>> repository =
		// EnumRepository.getRepository();
		// servletContext.setAttribute("repository", repository);
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
