/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午10:29:06
* 创建作者：许小满
* 文件名称：ParamServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.thirdapp.param.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.pagesrv.thirdapp.param.service.ParamService;

@Service("paramService")
public class ParamServiceImpl extends BaseService implements ParamService {

    /**
     * 将参数存入到redis缓存中
     * @param merchantId 商户id
     * @param deviceId 设备id
     * @param devMac 设备MAC
     * @param userIp 用户IP
     * @param userMac 用户MAC
     * @param userPhone 用户手机号
     * @param terminalType 终端类型
     * @return redis key
     * @author 许小满  
     * @date 2017年5月13日 上午10:30:17
     */
    public String putParamsToCache(String merchantId, String deviceId, String devMac, String userIp, String userMac, String userPhone, String terminalType) {
        Map<String,String> paramMap = new HashMap<String, String>(7);//参数map
        paramMap.put("customerId", merchantId);//客户ID
        paramMap.put("devMac", devMac);//设备ID
        paramMap.put("deviceId", deviceId);//设备MAC
        paramMap.put("userIp", userIp);//用户IP
        paramMap.put("userMac", userMac);//用户MAC
        paramMap.put("userPhone", userPhone);//用户手机
        paramMap.put("terminalType", terminalType);//用户终端类型
        
        String redisKey = RedisConstants.PORTAL_PARAM + KeyUtil.generateKey();//redis key
        RedisUtil.set(redisKey, JsonUtil.toJson(paramMap), RedisConstants.PORTAL_PARAM_TIME);
        logger.debug("提示：参数成功放入redis缓存中，redisKey=" + redisKey);
        return redisKey;
    }
    
}
