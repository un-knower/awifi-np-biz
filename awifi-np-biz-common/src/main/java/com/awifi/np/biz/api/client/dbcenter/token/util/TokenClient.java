package com.awifi.np.biz.api.client.dbcenter.token.util;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.api.client.dbcenter.token.service.TokenService;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午2:57:47
 * 创建作者：周颖
 * 文件名称：TokenClient.java
 * 版本：  v1.0
 * 功能：生成数据中心access_token工具类
 * 修改记录：
 */
public class TokenClient {

    /**生成数据中心access_token服务层*/
    private static TokenService tokenService;
    
    /**
     * 获取tokenService实例
     * @return tokenService
     * @author 周颖  
     * @date 2017年1月18日 下午8:29:56
     */
    public static TokenService getTokenService(){
        if(tokenService == null){
            tokenService = (TokenService)BeanUtil.getBean("tokenService");
        }
        return tokenService;
    }
    
    /**
     * 获取数据中心access_token
     * @return access_token
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年1月18日 下午8:30:01
     */
    public static String getAccessToken() throws Exception{
        String key = RedisConstants.DBC_ACCESS_TOKEN;//获取数据中心access_token rediskey
        String accessToken = RedisUtil.get(key);//redis获取access_token
        if(StringUtils.isNotBlank(accessToken)){//如果不为空
            return accessToken;//返回access_token
        }
        return getTokenService().getAccessToken(key);//返回生成的access_token
    }
    
    /**
     * 重置access_token
     * @return access_token
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年1月19日 上午8:56:57
     */
    public static String resetAccessToken() throws Exception{
        String key = RedisConstants.DBC_ACCESS_TOKEN;//获取数据中心access_token rediskey
        return getTokenService().getAccessToken(key);//返回生成的access_token
    }
}