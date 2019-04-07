package com.flight.carryprice.controller;

import com.flight.carryprice.common.enums.PrefixEnum;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpLimitFlightInfo;
import com.flight.carryprice.service.JdCacheUtils;
import com.flight.carryprice.service.SqlService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/sqltest")
public class SqlController extends BaseController{

	private static Logger logger = Logger.getLogger(SqlController.class);

	@Resource
	private SqlService sqlService;
	@Resource
	private JdCacheUtils jdCacheUtils;


	@RequestMapping("/exceSql")
	@ResponseBody
	public Object exceSql(HttpSession session, @RequestParam(required = true) String name, @RequestParam(required = true) String pwd,
						  @RequestParam(required = true) String sql) {
		logger.info("进入执行sql");
		/************************************************* 记录用户start ********************************************/
		logger.info("执行sql，请求数据为：name：" + name +"，sql：" + sql);
		//判断是否为有权限的操作用户


		logger.info("执行sql，请求数据为：name：" + name +"，sql：" + sql+",该用户有操作权限，将执行sql");
		/************************************************* 记录用户end ********************************************/
		try {
			sqlService.execSql(sql);
			return true;
		} catch (Exception e) {
			logger.error("执行sql错误",e);
		}
		return false;
	}
	@RequestMapping("/getJimdb")
	@ResponseBody
	public Object queryJimdb(@RequestParam(required = true) String key){
		if(key.startsWith("DEL_")){
			key = key.substring(4);
			if (key.startsWith(PrefixEnum.LIMITFLIGHTM2REDIS.getCode())){
				if(jdCacheUtils.del(key)){
					return "sucess";
				}
			}
			return "failed";
		}
		Map<String, String> map = jdCacheUtils.hGetAll(key);
		if (map == null || map.isEmpty()){
			return "当前key不存在";
		}
		StringBuffer sb = new StringBuffer();
		Set<String> set = map.keySet();
		for (String s : set){
			sb.append(map.get(s)).append("\n");
		}
		return sb.toString();
	}

}
