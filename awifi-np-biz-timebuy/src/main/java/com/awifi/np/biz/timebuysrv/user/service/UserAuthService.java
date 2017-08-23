/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午1:44:56
* 创建作者：尤小平
* 文件名称：UserAuthService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.user.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.log.ResultDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;

public interface UserAuthService {
    /**
     * 根据手机号码获取用户信息.
     *
     * @param telphone 手机号码
     * @return PubUserAuth
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月24日 上午10:51:02
     */
    PubUserAuth getUserByLogName(String telphone) throws Exception;

    /**
     * 保存注册.
     * 
     * @param username 用户名
     * @param password 密码
     * @return 认证用户id
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月24日 下午2:19:43
     */
    Long saveReg(String username, String password) throws Exception;
    
    /**
     * 登录获取用户信息并做相关保存操作.
     * 
     * @param request request
     * @param username 账号
     * @param deviceId 设备id
     * @param terMac 用户mac
     * @param apMac 设备mac
     * @param merchantId 商户id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月25日 上午10:51:37
     */
    Map<String, Object> saveLogin(HttpServletRequest request, String username, String deviceId, String terMac,
            String apMac, Long merchantId) throws Exception;
   
   

    public ResultDTO loginByPhoneAndSms(SessionDTO sessionDTO,
                               String phone, String captcha,HttpServletRequest request) throws Exception ;
    /**
     * 接入认证放行.
     * 
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月25日 下午2:30:45
     */
 //  Map<String, Object> permitWLAN(HttpServletRequest request) throws Exception;

    /**
     * 判断是否为vip用户.
     *
     * @param username 账号
     * @return boolean
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月25日 下午2:32:24
     */
    boolean isFreeUser(String username) throws Exception;

}
