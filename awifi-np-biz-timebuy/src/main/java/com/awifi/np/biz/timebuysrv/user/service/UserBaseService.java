/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月14日 下午2:16:26
 * 创建作者：尤小平
 * 文件名称：UserBaseService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.user.service;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.timebuysrv.user.model.SysUser;

public interface UserBaseService {
    /**
     * 根据userId获取用户信息.
     * 
     * @param userId userId
     * @return PubUser
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午3:01:46
     */
    PubUser getByUseId(Long userId) throws Exception;

    /**
     * 添加用户基础信息.
     *
     * @param pubUser PubUser
     * @return userId
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:40:39
     */
    Long add(PubUser pubUser) throws Exception;

    /**
     * 根据userid更新用户基础信息.
     *
     * @param pubUser PubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:41:24
     */
    void update(PubUser pubUser) throws Exception;

    /**
     * 本地库添加用户信息.
     *
     * @param sysUser SysUser
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:44:18
     */
    int addSysUser(SysUser sysUser) throws Exception;
    /**
     * PubUser转SysUser
     *
     * @param sysUser SysUser
     * @param pubUser PubUser
     * @return SysUser
     * @author 尤小平
     * @date 2017年4月17日 下午3:46:08
     */
    SysUser getSysUserFromPubUser(SysUser sysUser, PubUser pubUser) ;
    
    
}
