/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 上午9:44:40
* 创建作者：尤小平
* 文件名称：UserAuthApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.user.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;

public interface UserAuthApiService {
    /**
     * 根据条件查询认证用户.
     * 
     * @param pubUserAuth PubUserAuth
     * @return List<PubUserAuth>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月24日 上午10:04:12
     */
    List<PubUserAuth> queryUserAuthByParam(PubUserAuth pubUserAuth) throws Exception;
    
    /**
     * 新增认证用户.
     * 
     * @param pubUserAuth PubUserAuth
     * @return 新增认证用户id
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月24日 上午10:04:28
     */
    Long addUserAuth(PubUserAuth pubUserAuth) throws Exception;
    
    /**
     * 根据用户userId修改认证用户的密码.
     * 
     * @param pubUserAuth PubUserAuth
     * @return 操作是否成功
     * @throws Exception  异常
     * @author 尤小平  
     * @date 2017年4月24日 上午10:05:40
     */
    boolean updateUserPswd(PubUserAuth pubUserAuth) throws Exception;
    
    /**
     * 条件查询用户列表，分页 
     * @param params 参数
     * @return 用户集合
     * @author 许小满  
     * @date 2017年7月19日 下午6:29:57
     */
    List<PubUserAuth> queryListByParam(Map<String,Object> params);
}
