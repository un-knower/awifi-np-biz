/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月5日 上午9:51:33
* 创建作者：周颖
* 文件名称：LowBrowserControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.portal.site.controller;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.logging.Log;
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
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorUtil.class,ValidUtil.class,FileReader.class,BufferedReader.class,LowBrowserController.class})
public class LowBrowserControllerTest {

    /**被测试类*/
    @InjectMocks
    private LowBrowserController lowBrowserController;
    
    /**mock httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**mock httpRequest*/
    private MockHttpServletResponse httpResponse;
    
    /** 日志  */
    @Mock
    private Log logger; //= LogFactory.getLog(BaseController.class);
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        httpResponse = new MockHttpServletResponse();
        PowerMockito.mockStatic(ErrorUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
    }
   
    /**
     * 
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月5日 下午1:52:32
     */
    @Test
    public void testIe8() throws Exception {
        FileReader fr = PowerMockito.mock(FileReader.class);
        PowerMockito.whenNew(FileReader.class).withArguments(Mockito.anyString()).thenReturn(fr);
        BufferedReader bf = PowerMockito.mock(BufferedReader.class);
        PowerMockito.whenNew(BufferedReader.class).withArguments(fr).thenReturn(bf);
        httpRequest.addParameter("pageType", "3");
        httpRequest.addParameter("num", "1");
        lowBrowserController.ie8(httpRequest, httpResponse); 
    }
}
