package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpPatUpdatePolicy;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2018/11/14 11:37.
 */
public interface JdjpPatUpdatePolicyMapper extends Mapper<JdjpPatUpdatePolicy> {
    List<JdjpPatUpdatePolicy> queryList(JdjpPatUpdatePolicy jdjpPatUpdatePolicy);
}
