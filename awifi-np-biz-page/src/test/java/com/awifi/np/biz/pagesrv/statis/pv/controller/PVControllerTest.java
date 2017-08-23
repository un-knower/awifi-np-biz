/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月6日 上午9:40:08
* 创建作者：周颖
* 文件名称：PVControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.statis.pv.controller;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantVisitClient;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MerchantVisitClient.class,HttpRequest.class,ValidUtil.class,ExceptionUtil.class})
public class PVControllerTest {
    
    /**被测试类*/
    @InjectMocks
    private PVController pvController;
    
    /**mock httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(MerchantVisitClient.class);
        PowerMockito.mockStatic(HttpRequest.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(ExceptionUtil.class);
    }
    
    /**
     * push get
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月6日 上午9:53:33
     */
    @Test
    public void testPushGET() throws Exception {
        httpRequest.setMethod("GET");
        pvController.push(httpRequest);
    }

    /**
     * push
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月6日 上午9:53:33
     */
    @Test
    public void testPush() throws Exception {
        httpRequest.setMethod("POST");
        PowerMockito.doNothing().when(MerchantVisitClient.class, "pvPush", anyString(),anyString(),anyString(),anyString(),anyString(),anyString());
        pvController.push(httpRequest);
    }
}
