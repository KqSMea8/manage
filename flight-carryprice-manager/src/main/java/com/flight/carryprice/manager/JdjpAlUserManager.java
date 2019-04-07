package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.AlUsers;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 15:46.
 */
public interface JdjpAlUserManager extends BaseService<AlUsers> {
    /**
     * @Author hYuan
     * @Description 通过username和appcode查询Aluser
     * @Date  2019/2/28 15:50
     * @Param userName appCode
     * @return 
     **/
    AlUsers findUserByUserNameAndAppCode(@Param("userName") String userName, @Param("appCode") Integer appCode);
    
    /**
     * @Author hYuan
     * @Description 通过Aluser查找用户列表
     * @Date  2019/2/28 15:51
     * @Param queryUser
     * @return 
     **/
    List<AlUsers> queryUserList(AlUsers queryUser);

    AlUsers selectByPrimaryKey(Integer userId);

}
