package com.awifi.np.biz.api.client.dbcenter.token.service.impl;

import java.nio.ByteBuffer;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.token.service.TokenService;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午3:00:29
 * 创建作者：周颖
 * 文件名称：TokenServiceImpl.java
 * 版本：  v1.0
 * 功能：获取数据中心access_token实现类
 * 修改记录：
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    /**
     * 获取数据中心access_token
     * @param key rediskey
     * @return access_token oauthToken
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年1月18日 下午8:01:52
     */
    @SuppressWarnings("unchecked")
    public String getAccessToken(String key) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_getaccesstoken_url");
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(url, null);
        if(byteBuffer == null){//如果为空
            throw new InterfaceException(MessageUtil.getMessage("E2000009"),url);//抛接口异常 接口无返回值！
        }
        String returnMessage = new String(byteBuffer.array(), "UTF-8");
        Map<String, Object> resultMap = JsonUtil.fromJson(returnMessage,Map.class);//转成map
        if(resultMap == null){//如果为空
            throw new InterfaceException(MessageUtil.getMessage("E2000010"),url);//抛接口异常 接口返回值不允许为空!
        }
        if(!resultMap.get("state").equals("success")){//如果返回失败 fail 
            throw new BizException("E2000017", MessageUtil.getMessage("E2000017"));//抛异常 获取数据中心access_token失败!
        }
        Map<String, Object> data = (Map<String,Object>)resultMap.get("data");//获取data数据
        String oauthToken = (String) data.get("oauthToken");//access_token
        //Long oauthTimestamp = (Long) data.get("oauthTimestamp");//token生成时间
        //Long loseTimestamp = (Long) data.get("loseTimestamp");//token失效时间
        // int seconds = (int) ((loseTimestamp-oauthTimestamp)/1000);//access_token有效时间
        int seconds = (int) data.get("expiresIn");
        RedisUtil.set(key, oauthToken, seconds);//access_token存到redis
        return oauthToken;//返回access_token
    }
}