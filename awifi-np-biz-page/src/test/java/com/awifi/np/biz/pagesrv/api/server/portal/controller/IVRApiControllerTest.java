/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午5:15:38
* 创建作者：周颖
* 文件名称：IVRApiControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.server.portal.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.auth.ivr.service.IVRService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class,IPUtil.class,MessageUtil.class,ValidUtil.class,ExceptionUtil.class})
public class IVRApiControllerTest {

    /**被测试类*/
    @InjectMocks
    private IVRApiController ivrApiController;
    
    /**mock*/
    @Mock(name="ivrService")
    private IVRService ivrService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(HttpRequest.class);
        PowerMockito.mockStatic(IPUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(ExceptionUtil.class);
    }
    
    /**
     * post请求方式
     * @author 周颖  
     * @date 2017年6月28日 上午8:38:27
     */
    @Test
    public void testIvrPOST() {
        httpRequest.setMethod("POST");
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("errormsg");
        
        ivrApiController.ivr(httpRequest);
        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyString());
    }
    
    /**
     * 
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月28日 上午9:25:22
     */
    @Test
    public void testIvrOK() throws Exception{
        httpRequest.setMethod("GET");
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyObject(),anyObject(),anyObject());
        PowerMockito.when(IPUtil.getIpAddr(anyObject())).thenReturn("192.168.3.5");
        PowerMockito.when(IPUtil.getRemotePort(anyObject())).thenReturn("8080");
        //Mockito.doNothing().when(ivrService).ivr(anyString(), anyString(), anyString(), anyString());
        PowerMockito.doNothing().when(ExceptionUtil.class,"formatMsg",anyObject(),anyObject(),anyObject(),anyObject(),anyObject());
        
        ivrApiController.ivr(httpRequest);
        
        PowerMockito.verifyStatic();
        ValidUtil.valid(anyObject(),anyObject(),anyObject());
        IPUtil.getIpAddr(anyObject());
        IPUtil.getRemotePort(anyObject());
        //verify(ivrService).ivr(anyString(), anyString(), anyString(), anyString());
        ExceptionUtil.formatMsg(anyObject(),anyObject(),anyObject(),anyObject(),anyObject());
    }
}
