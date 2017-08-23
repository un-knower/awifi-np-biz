/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午4:20:20
* 创建作者：周颖
* 文件名称：WeChatApiServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.wechat.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class,EncryUtil.class,FormatUtil.class,JsonUtil.class,MessageUtil.class})
public class WeChatApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private WeChatApiServiceImpl weChatApiServiceImpl;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HttpRequest.class);
        PowerMockito.mockStatic(EncryUtil.class);
        PowerMockito.mockStatic(FormatUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
    
    /**
     * ByteBuffer为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午4:32:19
     */
    @Test(expected=InterfaceException.class)
    public void testCallWechatNull() throws Exception {
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("extend");
        PowerMockito.when(FormatUtil.formatApMac(anyString())).thenReturn("userMacFormat");
        PowerMockito.when(EncryUtil.getMd5Str(anyString())).thenReturn("sign");
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        PowerMockito.when(HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject())).thenReturn(null);
        weChatApiServiceImpl.callWechat("appId", "redisKey", "token", "shopId", "userMac", "devMac", "devId", "ssid", 
                "secretkey", "userPhone", "authType", "forceAttention", "wechatAuthUrl");
        PowerMockito.verifyStatic();
        JsonUtil.toJson(anyObject());
        FormatUtil.formatApMac(anyString());
        EncryUtil.getMd5Str(anyString());
        HttpRequest.getParams(anyObject());
        HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject());
    }
    
    /**
     * ByteBuffer不为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午4:32:19
     */
    @Test
    public void testCallWechat() throws Exception {
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("extend");
        PowerMockito.when(FormatUtil.formatApMac(anyString())).thenReturn("userMacFormat");
        PowerMockito.when(EncryUtil.getMd5Str(anyString())).thenReturn("sign");
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("'result':'FAIL','message':'1234'".getBytes());
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(byteBuffer);
        weChatApiServiceImpl.callWechat("appId", "redisKey", "token", "shopId", "userMac", "devMac", "devId", "ssid", 
                "secretkey", "userPhone", "authType", "forceAttention", "wechatAuthUrl");
        PowerMockito.verifyStatic();
        JsonUtil.toJson(anyObject());
        FormatUtil.formatApMac(anyString());
        EncryUtil.getMd5Str(anyString());
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
    }
}
