/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月12日 上午8:56:50
* 创建作者：周颖
* 文件名称：UserAuthUtil.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.userauth.util;

import java.util.HashMap;
import java.util.Map;

import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.TerminalUtil;

public class UserAuthUtil {

    /**
     * 缓存设备认证记录
     * @param devId 设备id
     * @param phone 手机号
     * @param userMac 终端mac
     * @param authType 认证类型   sms：手机号+验证码认证
                              account：用户名+密码认证
                              authed：免认证（老用户 一键登录）
                              wechat：微信认证
                              ivr：IVR语音认证
     * @param userAgent 终端信息
     * @author 周颖  
     * @date 2017年7月12日 上午9:03:18
     */
    public static void putToCache(String devId,String phone,String userMac,String authType,String userAgent ){
        String redisKey = RedisConstants.USER_AUTH + devId;//生成rediskey np_biz_auth_log_devId
        String terminalType = TerminalUtil.getTerminalType(userAgent);//终端类型
        String terminalBrand = TerminalUtil.getTerminalBrand(userAgent, terminalType);//终端品牌
        String terminalVersion = TerminalUtil.getTerminalVersion(userAgent, terminalType);//终端版本
        Map<String,Object> paramMap = new HashMap<String,Object>(8);
        paramMap.put("phone", phone);//手机号
        paramMap.put("authDate", DateUtil.getNow());//认证时间
        paramMap.put("userMac", userMac);//终端mac
        paramMap.put("terminalType", terminalType);//终端类型
        paramMap.put("terminalBrand", terminalBrand);//终端品牌
        paramMap.put("terminalVersion", terminalVersion);//终端版本
        paramMap.put("authType", authType);//认证类型
        String userAuth = JsonUtil.toJson(paramMap);
        Long count = RedisUtil.lpush(redisKey, RedisConstants.USER_AUTH_TIME, userAuth);//保存到redis
        if(count > Constants.AUTHTOTAL){//超过设置大小，截取最新的
            RedisUtil.ltrim(redisKey, 0, Constants.AUTHTOTAL-1);
        }
    }
}
