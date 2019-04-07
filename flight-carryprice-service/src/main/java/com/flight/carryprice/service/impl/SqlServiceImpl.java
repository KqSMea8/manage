package com.flight.carryprice.service.impl;

import com.flight.carryprice.mapper.AlTmpMapper;
import com.flight.carryprice.service.SqlService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SqlServiceImpl implements SqlService {
	private final static Logger LOGGER = Logger.getLogger(SqlServiceImpl.class);
	@Resource
	private AlTmpMapper alTmpMapper;

	@Override
	public void execSql(String sql) {
		// 只能执行类似：update ccs_airline set fd_airline_type=0 where id =1
		// 通过id进行修改或者删除的操作
//		if(sql.toLowerCase().trim().startsWith("delete") || sql.toLowerCase().trim().startsWith("update")){
//			//删除只能通过id删除数据
//			String subSql = sql.substring(sql.indexOf("where"));
//			if(!subSql.contains(" id")){
//				LOGGER.info("执行的删除或更新SQL语句必须根据ID进行删除或更新");
//				return;
//			}
//		}
		alTmpMapper.execSql(sql);
	}

	public static void main(String[] args) {
		String sql ="update ccs_airline set fd_airline_type=0 where id =1";

		if(sql.toLowerCase().trim().startsWith("delete") || sql.toLowerCase().trim().startsWith("update")){
			//删除只能通过id删除数据
			String subSql = sql.substring(sql.indexOf("where"));
			if(!subSql.contains(" id")){
				LOGGER.info("执行的删除或更新SQL语句必须根据ID进行删除或更新");
			}
		}
	}

}
