package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpPatUpdatePolicy;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/20 18:47.
 */
public interface JdjpPatUpdatePolicyManager {
    List<JdjpPatUpdatePolicy> queryList(JdjpPatUpdatePolicy jdjpPatUpdatePolicy);
}
