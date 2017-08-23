/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月4日 下午1:58:25
* 创建作者：周颖
* 文件名称：GeolocationActionTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.device.geolocation.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExceptionUtil.class,ValidUtil.class})
public class GeolocationActionTest {

    /**被测试类*/
    @InjectMocks
    private GeolocationAction geolocationAction;
    
    /**mock httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(ExceptionUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
    }
    
    /**
     * get请求方式
     * @author 周颖  
     * @date 2017年7月5日 上午9:50:05
     */
    @Test
    public void testSaveGET() {
        httpRequest.setMethod("GET");
        geolocationAction.save(httpRequest);
    }

    /**
     * post请求方式
     * @author 周颖  
     * @date 2017年7月5日 上午9:50:19
     */
    @Test
    public void testSavePOST() {
        httpRequest.setMethod("POST");
        geolocationAction.save(httpRequest);
    }
}
