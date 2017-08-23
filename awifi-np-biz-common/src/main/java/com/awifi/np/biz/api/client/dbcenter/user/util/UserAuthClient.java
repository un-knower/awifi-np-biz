/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 上午10:08:20
* 创建作者：尤小平
* 文件名称：UserAuthClient.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.user.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.service.UserAuthApiService;
import com.awifi.np.biz.common.ms.util.MsCommonUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.RandomUtil;

public class UserAuthClient {
    
    /** 日志 */
    private static final Log logger = LogFactory.getLog(UserAuthClient.class);
    
    /**
     * 用户认证服务
     */
    @Resource
    private static UserAuthApiService userAuthApiService;

    /**
     * getUserAuthApiService.
     * 
     * @return UserAuthApiService
     * @author 尤小平  
     * @date 2017年4月24日 上午10:31:51
     */
    public static UserAuthApiService getUserAuthApiService() {
        if (userAuthApiService == null) {
            userAuthApiService = (UserAuthApiService) BeanUtil.getBean("userAuthApiService");
        }
        return userAuthApiService;
    }

    /**
     * 根据条件查询认证用户.
     * 
     * @param pubUserAuth PubUserAuth
     * @return 认证用户列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月24日 上午10:32:09
     */
    public static List<PubUserAuth> queryUserAuthByParam(PubUserAuth pubUserAuth) throws Exception {
        return getUserAuthApiService().queryUserAuthByParam(pubUserAuth);
    }

    /**
     * 根据手机号码获取用户信息.
     * 
     * @param telphone 手机号码
     * @return PubUserAuth
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月24日 上午10:51:02
     */
    public static PubUserAuth getUserByLogName(String telphone) throws Exception {
        PubUserAuth pubUserAuth = new PubUserAuth();
        pubUserAuth.setTelphone(telphone);

        List<PubUserAuth> list = getUserAuthApiService().queryUserAuthByParam(pubUserAuth);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 新增认证用户.
     * 
     * @param pubUserAuth PubUserAuth
     * @return 新增认证用户id
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月24日 上午11:35:18
     */
    public static Long addUserAuth(PubUserAuth pubUserAuth) throws Exception{
        return getUserAuthApiService().addUserAuth(pubUserAuth);
    }
    
    /**
     * 通过用户手机号获取用户id
     * @param userPhone 用户手机号
     * @return 用户id
     * @author 许小满  
     * @date 2017年6月19日 下午10:24:27
     */
    public static Long getUserIdByPhone(String userPhone){
        if(StringUtils.isBlank(userPhone)){
            logger.debug("错误: userPhone 为空！");
            return null;
        }
        Map<String,Object> params = new HashMap<String,Object>(3);//参数map
        params.put("telphone", userPhone);//手机号
        params.put("pageNum", "1");//页码
        params.put("pageSize", "1");//每页记录数
        
        List<PubUserAuth> pubUserAuthList = getUserAuthApiService().queryListByParam(params);
        if(pubUserAuthList == null || pubUserAuthList.size() <= 0){
            return null;
        }
        PubUserAuth pubUserAuth = pubUserAuthList.get(0);
        if(pubUserAuth == null){
            return null;
        }
        return pubUserAuth.getUserId();
    }
    
    /**
     * 新增userAuth记录
     * @param userName 用户名
     * @return 用户id
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月22日 下午5:10:15
     */
    public static Long addUserAuth(String userName) throws Exception {
        //判断userAuth记录是否已经存在，如果存在直接返回用户id
        Long userId = getUserIdByPhone(userName);//用户id
        if(userId != null){
            return userId;
        }
        PubUserAuth pubUserAuth = new PubUserAuth();
        pubUserAuth.setLogname(userName);//登录名
        pubUserAuth.setTelphone(userName);//手机号码
        String defaultPwd = SysConfigUtil.getParamValue("default_password");//默认密码
        pubUserAuth.setAuthPswd(EncryUtil.getMd5Str(defaultPwd));//登录密码
        pubUserAuth.setUserType("2");//用户类型：1 用户、2 商户
        pubUserAuth.setAuthPlatform(10);//认证平台(Integer)  网管:1,接入:2, 微站:3,TOG:4,TOC:5,APP:6
        String randomNum = RandomUtil.getRandomNumber(5);//5位随机数
        pubUserAuth.setSafetyCode(randomNum);//安全码
        pubUserAuth.setSeed(randomNum);//双方约定随机码
        pubUserAuth.setToken(MsCommonUtil.getCenterToken(randomNum));//token
        userId = addUserAuth(pubUserAuth);//新增认证用户
        return userId;
    }
}
