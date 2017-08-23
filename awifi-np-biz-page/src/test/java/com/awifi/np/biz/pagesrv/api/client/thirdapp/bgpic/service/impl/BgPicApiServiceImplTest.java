/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 上午10:54:04
* 创建作者：周颖
* 文件名称：BgPicApiServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.service.impl;

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
public class BgPicApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private BgPicApiServiceImpl bgPicApiServiceImpl;
    
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
     * @date 2017年6月27日 上午11:15:02
     */
    @Test(expected=InterfaceException.class)
    public void testGetBgPicNull() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        PowerMockito.when(HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("errormsg");
        
        bgPicApiServiceImpl.getBgPic("redisKey", "interfaceUrl");
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject());
        MessageUtil.getMessage(anyString());
    }
    
    /**
     * json转map为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午2:15:29
     */
    @Test(expected=InterfaceException.class)
    public void testGetBgPicResultMapNull() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("'result':'FAIL','message':'1234'".getBytes());
        PowerMockito.when(HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject())).thenReturn(byteBuffer);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("errormsg");
        
        bgPicApiServiceImpl.getBgPic("redisKey", "interfaceUrl");
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject());
        MessageUtil.getMessage(anyString());
    }
    
    /**
     * 返回FAIL
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午2:16:19
     */
    @Test(expected=InterfaceException.class)
    public void testGetBgPicFail() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("'result':'FAIL','message':'1234'".getBytes());
        PowerMockito.when(HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject())).thenReturn(byteBuffer);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "FAIL");
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("errormsg");
        
        bgPicApiServiceImpl.getBgPic("redisKey", "interfaceUrl");
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject());
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
    public void testGetBgPicOK() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("'result':'OK','message':'1234'".getBytes());
        PowerMockito.when(HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject())).thenReturn(byteBuffer);
        
        bgPicApiServiceImpl.getBgPic("redisKey", "interfaceUrl");
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendPostRequest(anyObject(),anyObject(),anyObject());
    }
}
