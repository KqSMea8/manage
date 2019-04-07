package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpNfdCarryPriceInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;


/**
 * @Description NFD运价 Mapper
 * @Author: qinhaoran1
 * @Date: 2019/3/12
 */
public interface JdjpNfdCarryPriceInfoMapper extends Mapper<JdjpNfdCarryPriceInfo> {

    /**
     * 条件查询
     * @param queryBean 查询条件bean
     * @return
     */
    List<JdjpNfdCarryPriceInfo> queryList(JdjpNfdCarryPriceInfo queryBean);
    int updateBatch(@Param("operator") String operator, @Param("state")Byte state, @Param("ids")Integer[] ids);
}
