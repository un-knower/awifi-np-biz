/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月13日 下午3:53:58
 * 创建作者：尤小平
 * 文件名称：UserBaseApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.user.service;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;

import java.util.List;
import java.util.Map;

public interface UserBaseApiService {
    /**
     * 新增用户.
     *
     * @param pubUser PubUser
     * @return Long
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:20
     */
    Long add(PubUser pubUser) throws Exception;

    /**
     * 更新用户.
     *
     * @param pubUser PubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:20
     */
    void update(PubUser pubUser) throws Exception;

    /**
     * 根据userId查询用户.
     *
     * @param userId userId
     * @return PubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:54:35
     */
    PubUser queryByUserId(Long userId) throws Exception;

    /**
     * 根据userIds查询用户列表.
     *
     * @param userIds userIds
     * @return List<PubUser>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:59:52
     */
    List<PubUser> queryByUserIds(String userIds) throws Exception;

    /**
     * 根据条件查询用户列表条数.
     *
     * @param params 条件参数
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月13日 下午4:59:52
     */
    int queryCountByParam(Map<String, Object> params) throws Exception;
    
    /**
     * 条件查询用户列表，分页 
     * @param params 参数
     * @return 用户集合
     * @author 许小满  
     * @date 2017年6月19日 下午10:35:53
     */
    List<PubUser> queryListByParam(Map<String,Object> params);
}
