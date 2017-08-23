/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月29日 下午2:41:23
* 创建作者：周颖
* 文件名称：IVRControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.auth.ivr.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.auth.ivr.service.IVRService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonUtil.class,RedisUtil.class,SysConfigUtil.class,ExceptionUtil.class,ValidUtil.class})
public class IVRControllerTest {

    /**被测试类*/
    @InjectMocks
    private IVRController ivrController;
    
    /**mock*/
    @Mock(name="ivrService")
    private IVRService ivrService;
    
    /**mock httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ExceptionUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
    }
    
    /**
     * 呼起语音
     * @author 周颖  
     * @date 2017年6月29日 下午2:53:16
     */
    @Test
    public void testCall() {
        httpRequest.setParameter("customerId", "1");
        ivrController.call(httpRequest);
    }
    
    /**
     * 呼起语音 异常
     * @author 周颖  
     * @date 2017年6月29日 下午2:53:16
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCallException() {
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenThrow(Exception.class);
        ivrController.call(httpRequest);
        PowerMockito.verifyStatic();
        JsonUtil.toJson(anyObject());
    }

    /**
     * 手机号为空
     * @author 周颖  
     * @date 2017年6月29日 下午2:55:27
     */
    @Test
    public void testPollNull() {
        ivrController.poll(httpRequest);
    }
    
    /**
     * 手机号不为空 异常
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月29日 下午2:55:27
     */
    @Test
    public void testPollException() throws Exception {
        httpRequest.setParameter("phoneNumber", "13456545654");
        Mockito.doThrow(Exception.class).when(ivrService).poll(anyString(),anyObject());;
        ivrController.poll(httpRequest);
    }
    
    /**
     * 手机号不为空 异常
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月29日 下午2:55:27
     */
    @Test
    public void testPoll() throws Exception {
        httpRequest.setParameter("phoneNumber", "13456545654");
        Mockito.doNothing().when(ivrService).poll(anyString(),anyObject());
        ivrController.poll(httpRequest);
        Mockito.verify(ivrService).poll(anyString(),anyObject());
    }
}
