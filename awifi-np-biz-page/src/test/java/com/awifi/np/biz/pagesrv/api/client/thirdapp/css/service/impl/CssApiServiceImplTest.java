/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午2:22:31
* 创建作者：周颖
* 文件名称：CssApiServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.css.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

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

import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class, MessageUtil.class,JsonUtil.class})
public class CssApiServiceImplTest {

    /**测试类*/
    @InjectMocks
    private CssApiServiceImpl cssApiServiceImpl;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HttpRequest.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
    }
    
    /**
     * ByteBuffer为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午2:27:06
     */
    @Test(expected=InterfaceException.class)
    public void testGetCssNull() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("errormsg");
        
        cssApiServiceImpl.getCss("redisKey", "interfaceUrl");
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
        MessageUtil.getMessage(anyString());
    }

    /**
     * json转map为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午2:15:29
     */
    @Test(expected=InterfaceException.class)
    public void testGetCssResultMapNull() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("'result':'FAIL','message':'1234'".getBytes());
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(byteBuffer);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("errormsg");
        
        cssApiServiceImpl.getCss("redisKey", "interfaceUrl");
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
        MessageUtil.getMessage(anyString());
    }
    
    /**
     * 返回FAIL
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午2:16:19
     */
    @Test(expected=InterfaceException.class)
    public void testGetCssFail() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("'result':'FAIL','message':'1234'".getBytes());
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(byteBuffer);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "FAIL");
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("errormsg");
        
        cssApiServiceImpl.getCss("redisKey", "interfaceUrl");
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
        JsonUtil.fromJson(anyString(),anyObject());
        MessageUtil.getMessage(anyString());
    }
    
    /**
     * OK
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午2:21:56
     */
    @Test
    public void testGetCssOK() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("'result':'OK','message':'1234'".getBytes());
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(byteBuffer);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "OK");
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
        
        cssApiServiceImpl.getCss("redisKey", "interfaceUrl");
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
        JsonUtil.fromJson(anyString(),anyObject());
    }
}
