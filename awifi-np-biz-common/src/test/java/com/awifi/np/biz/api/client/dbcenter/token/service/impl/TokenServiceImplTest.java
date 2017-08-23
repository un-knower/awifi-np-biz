package com.awifi.np.biz.api.client.dbcenter.token.service.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月24日 下午3:00:35
 * 创建作者：周颖
 * 文件名称：TokenServiceImplTest.java
 * 版本：  v1.0
 * 功能：测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,RedisUtil.class,JsonUtil.class,MessageUtil.class,HttpRequest.class})
public class TokenServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private TokenServiceImpl tokenServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(HttpRequest.class);
    }
    
    /**
     * ByteBuffer 为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月24日 下午3:14:02
     */
    @Test(expected=InterfaceException.class)
    public void testGetAccessTokenByteBuffer() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url");
        PowerMockito.when(HttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(null);
        
        tokenServiceImpl.getAccessToken("key");
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        HttpRequest.sendGetRequest(anyString(),anyString());
    }
    
    /**
     * resultMap为空 
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月24日 下午3:14:02
     */
    @Test(expected=InterfaceException.class)
    public void testGetAccessTokenMapNull() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url");
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        PowerMockito.when(HttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(byteBuffer);
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(null);
        
        tokenServiceImpl.getAccessToken("key");
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        HttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.fromJson(anyString(),anyObject());
    }

    /**
     * resultMap为空 
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月24日 下午3:14:02
     */
    @Test(expected=BizException.class)
    public void testGetAccessTokenFail() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url");
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        PowerMockito.when(HttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(byteBuffer);
        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("state", "fail");
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
        
        tokenServiceImpl.getAccessToken("key");
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        HttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.fromJson(anyString(),anyObject());
    }
    
    /**
     * 成功
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月24日 下午3:14:02
     */
    @Test
    public void testGetAccessTokenOk() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url");
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        PowerMockito.when(HttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(byteBuffer);
        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("state", "success");
        Map<String, Object> data = new HashMap<String,Object>();
        data.put("oauthToken", "oauthToken");
        data.put("expiresIn", 1);
        resultMap.put("data", data);
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
        PowerMockito.when(RedisUtil.set(anyString(),anyString(),anyObject())).thenReturn("value");
        
        tokenServiceImpl.getAccessToken("key");
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        HttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.fromJson(anyString(),anyObject());
        RedisUtil.set(anyString(),anyString(),anyObject());
    }
}