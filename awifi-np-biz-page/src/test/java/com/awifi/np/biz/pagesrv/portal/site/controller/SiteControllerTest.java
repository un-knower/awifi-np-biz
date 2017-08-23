/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月5日 下午2:02:07
* 创建作者：周颖
* 文件名称：SiteControllerTest.java
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

import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.log.util.ExceptionLogUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.portal.site.model.SitePage;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SessionUtil.class,ExceptionLogUtil.class,SysConfigUtil.class,ErrorUtil.class,ValidUtil.class,SiteController.class})
public class SiteControllerTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private SiteController siteController;
    
    /** 站点Service */
    @Mock(name = "siteService")
    private SiteService siteService;
    
    /**mock httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**mock httpRequest*/
    private MockHttpServletResponse httpResponse;
    
    /** 日志  */
    @Mock
    private Log logger;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        httpResponse = new MockHttpServletResponse();
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(ExceptionLogUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ErrorUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
    }
    
    /**
     * 站点页面为空
     * @author 周颖  
     * @date 2017年7月6日 上午9:23:49
     */
    @Test
    public void testSiteNull() {
        httpRequest.addParameter("site_id", "1");
        Mockito.when(siteService.getSiteNameCache(Mockito.anyLong())).thenReturn("siteName");
        Mockito.when(siteService.getFirstSitePageCache(Mockito.anyLong())).thenReturn(null);
        siteController.site(httpRequest, httpResponse);
    }
    
    /**
     * 站点页面为空
     * @author 周颖  
     * @throws Exception 
     * @date 2017年7月6日 上午9:23:49
     */
    @Test
    public void testSite() throws Exception {
        httpRequest.addParameter("site_id", "1");
        httpRequest.addParameter("page_type", "2");
        httpRequest.addParameter("num", "1");
        Mockito.when(siteService.getSiteNameCache(Mockito.anyLong())).thenReturn("siteName");
        SitePage sitePage = new SitePage();
        Mockito.when(siteService.getNextPageCache(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(sitePage);
        FileReader fr = PowerMockito.mock(FileReader.class);
        PowerMockito.whenNew(FileReader.class).withArguments(Mockito.anyString()).thenReturn(fr);
        BufferedReader bf = PowerMockito.mock(BufferedReader.class);
        PowerMockito.whenNew(BufferedReader.class).withArguments(fr).thenReturn(bf);
        siteController.site(httpRequest, httpResponse);
    }
}
