/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午4:49:38
* 创建作者：周颖
* 文件名称：ThirdAuthServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdauth.service.impl;

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

import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class, MessageUtil.class,JsonUtil.class})
public class ThirdAuthServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private ThirdAuthServiceImpl thirdAuthServiceImpl;
    
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
     * byteBuffer为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午4:56:11
     */
    @Test(expected=InterfaceException.class)
    public void testStaticUserAuthNull() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(null);
        
        thirdAuthServiceImpl.staticUserAuth("interfaceUrl", "userName", "password");
        
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
    }
    
    /**
     * 接口返回值为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午5:00:06
     */
    @Test(expected=InterfaceException.class)
    public void testStaticUserAuthBlank() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("".getBytes());
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(byteBuffer);
        
        thirdAuthServiceImpl.staticUserAuth("interfaceUrl", "userName", "password");
        
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
    }
    
    /**
     * 转map为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午5:04:59
     */
    @Test(expected=InterfaceException.class)
    public void testStaticUserAuthMapNull() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("result".getBytes());
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(byteBuffer);
        PowerMockito.when(JsonUtil.fromJson(anyObject(),anyObject())).thenReturn(null);
        
        thirdAuthServiceImpl.staticUserAuth("interfaceUrl", "userName", "password");
        
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
        JsonUtil.fromJson(anyObject(),anyObject());
    }
    
    /**
     * 成功
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午5:04:49
     */
    @Test
    public void testStaticUserAuthMapOK() throws Exception {
        PowerMockito.when(HttpRequest.getParams(anyObject())).thenReturn("interfaceParam");
        ByteBuffer byteBuffer = ByteBuffer.wrap("result".getBytes());
        PowerMockito.when(HttpRequest.sendGetRequest(anyObject(),anyObject())).thenReturn(byteBuffer);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "ok");
        PowerMockito.when(JsonUtil.fromJson(anyObject(),anyObject())).thenReturn(resultMap);
        
        thirdAuthServiceImpl.staticUserAuth("interfaceUrl", "userName", "password");
        
        PowerMockito.verifyStatic();
        HttpRequest.getParams(anyObject());
        HttpRequest.sendGetRequest(anyObject(),anyObject());
        JsonUtil.fromJson(anyObject(),anyObject());
    }

}
