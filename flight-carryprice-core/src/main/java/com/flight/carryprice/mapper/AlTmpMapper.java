package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.AlTmp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AlTmpMapper extends Mapper<AlTmp> {

	public void execSql(@Param("sql") String sql);
}