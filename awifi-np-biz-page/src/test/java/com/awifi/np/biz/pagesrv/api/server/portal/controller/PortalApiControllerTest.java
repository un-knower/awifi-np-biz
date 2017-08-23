/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月28日 上午9:30:06
* 创建作者：周颖
* 文件名称：PortalApiControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.server.portal.controller;

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
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.strategy.service.StrategyService;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DeviceClient.class,MerchantClient.class,RedisUtil.class,SysConfigUtil.class,
        MessageUtil.class,ValidUtil.class,ExceptionUtil.class})
public class PortalApiControllerTest {

    /**被测试类*/
    @InjectMocks
    private PortalApiController portalApiController;
    
    /** 策略Service */
    @Mock
    private StrategyService strategyService;
    
    /**mock*/
    @Mock
    private SiteService siteService;
    
    /**mock httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**mock httpResponse*/
    private MockHttpServletResponse httpResponse;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        httpResponse = new MockHttpServletResponse();
        PowerMockito.mockStatic(DeviceClient.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(ExceptionUtil.class);
    }
    
    /**
     * 登陆类型为空
     * @author 周颖  
     * @date 2017年6月28日 上午9:47:05
     */
    @Test
    public void testPortalUserTypeNull() {
        httpRequest.addHeader("User-Agent", "");
        portalApiController.portal(httpRequest, httpResponse);
    }
    
//    /**
//     * 登陆类型为空
//     * @author 周颖  
//     * @date 2017年6月28日 上午9:47:05
//     */
//    @Test
//    public void testPortalUserTypeAuthed() {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "EXEMPT_AUTH_USER");
//        portalApiController.portal(httpRequest, httpResponse);
//    }
//    
//    /**
//     * 设备不为空
//     * @author 周颖  
//     * @throws Exception 
//     * @date 2017年6月28日 上午9:47:05
//     */
//    @Test
//    public void testPortalDeviceNotNull() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        Device device = new Device();
//        device.setMerchantId(1L);
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//    }
//    
//    /**
//     * 设备归属商户id为空
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:02:23
//     */
//    @Test
//    public void testPortalMerchantIdNull() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        Device device = new Device();
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("");
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//    }
//    
//    /**
//     * 默认商户id值不为空
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:02:37
//     */
//    @Test
//    public void testPortalMerchantIdNotNull() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        Device device = new Device();
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
//        Merchant merchant = new Merchant();
//        merchant.setId(1L);
//        merchant.setCascadeLabel("1");
//        PowerMockito.when(MerchantClient.getByIdCache(anyObject())).thenReturn(merchant);
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//        MerchantClient.getByIdCache(anyObject());
//    }
//    
//    /**
//     * 站点id为空
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:02:37
//     */
//    @Test
//    public void testPortalSiteIdNotNull() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        Device device = new Device();
//        device.setBelongTo("PUB");
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
//        Merchant merchant = new Merchant();
//        merchant.setId(2L);
//        merchant.setCascadeLabel("1-2");
//        PowerMockito.when(MerchantClient.getByIdCache(anyObject())).thenReturn(merchant);
//        when(strategyService.getSiteIdCache(anyLong(),anyString(),anyString())).thenReturn(null);
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//        MerchantClient.getByIdCache(anyObject());
//        Mockito.verify(strategyService,Mockito.times(2)).getSiteIdCache(anyLong(),anyString(),anyString());
//    }
//    
//    /**
//     * toe设备
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:33:47
//     */
//    @Test
//    public void testPortalTOE() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        Device device = new Device();
//        device.setBelongTo("ToE");
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
//        Merchant merchant = new Merchant();
//        merchant.setId(2L);
//        merchant.setCascadeLabel("1-2");
//        PowerMockito.when(MerchantClient.getByIdCache(anyObject())).thenReturn(merchant);
//        when(strategyService.getSiteIdCache(anyLong(),anyString(),anyString())).thenReturn(null);
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//        MerchantClient.getByIdCache(anyObject());
//        Mockito.verify(strategyService,Mockito.times(2)).getSiteIdCache(anyLong(),anyString(),anyString());
//    }
//    
//    /**
//     * 园区酒店
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:33:32
//     */
//    @Test
//    public void testPortalMWH() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        Device device = new Device();
//        device.setBelongTo("MWH");
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
//        Merchant merchant = new Merchant();
//        merchant.setId(2L);
//        merchant.setCascadeLabel("1-2");
//        PowerMockito.when(MerchantClient.getByIdCache(anyObject())).thenReturn(merchant);
//        when(strategyService.getSiteIdCache(anyLong(),anyString(),anyString())).thenReturn(null);
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//        MerchantClient.getByIdCache(anyObject());
//        Mockito.verify(strategyService,Mockito.times(2)).getSiteIdCache(anyLong(),anyString(),anyString());
//    }
//    
//    /**
//     * 默认站点为空
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:33:18
//     */
//    @Test
//    public void testPortaDefaultSite() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        Device device = new Device();
//        device.setBelongTo("MWH");
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
//        Merchant merchant = new Merchant();
//        merchant.setId(2L);
//        merchant.setCascadeLabel("1-2");
//        PowerMockito.when(MerchantClient.getByIdCache(anyObject())).thenReturn(merchant);
//        when(strategyService.getSiteIdCache(anyLong(),anyString(),anyString())).thenReturn(null);
//        when(siteService.getIndustrySitIdCache(anyString(),anyString())).thenReturn(null);
//        when(siteService.getDefaultSitIdCache()).thenReturn(null);
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//        MerchantClient.getByIdCache(anyObject());
//        Mockito.verify(strategyService,Mockito.times(2)).getSiteIdCache(anyLong(),anyString(),anyString());
//        Mockito.verify(siteService).getDefaultSitIdCache();
//        Mockito.verify(siteService).getIndustrySitIdCache(anyString(),anyString());
//    }
//    
//    /**
//     * 防蹭网OFF
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:34:04
//     */
//    @Test
//    public void testPortaNetDefSwitchOFF() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        httpRequest.addParameter("deviceid", "deviceid");
//        Device device = new Device();
//        device.setBelongTo("MWH");
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
//        Merchant merchant = new Merchant();
//        merchant.setId(2L);
//        merchant.setCascadeLabel("1-2");
//        PowerMockito.when(MerchantClient.getByIdCache(anyObject())).thenReturn(merchant);
//        when(strategyService.getSiteIdCache(anyLong(),anyString(),anyString())).thenReturn(null);
//        when(siteService.getIndustrySitIdCache(anyString(),anyString())).thenReturn(null);
//        when(siteService.getDefaultSitIdCache()).thenReturn(1L);
//        PowerMockito.when(RedisUtil.get(anyString())).thenReturn("");
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//        MerchantClient.getByIdCache(anyObject());
//        RedisUtil.get(anyString());
//        Mockito.verify(strategyService,Mockito.times(2)).getSiteIdCache(anyLong(),anyString(),anyString());
//        Mockito.verify(siteService).getDefaultSitIdCache();
//        Mockito.verify(siteService).getIndustrySitIdCache(anyString(),anyString());
//    }
//    
//    /**
//     * 防蹭网ON
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:34:04
//     */
//    @Test
//    public void testPortaNetDefSwitchON() throws Exception {
//        httpRequest.addHeader("User-Agent", "");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        httpRequest.addParameter("deviceid", "deviceid");
//        Device device = new Device();
//        device.setBelongTo("MWH");
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
//        Merchant merchant = new Merchant();
//        merchant.setId(2L);
//        merchant.setCascadeLabel("1-2");
//        PowerMockito.when(MerchantClient.getByIdCache(anyObject())).thenReturn(merchant);
//        when(strategyService.getSiteIdCache(anyLong(),anyString(),anyString())).thenReturn(null);
//        when(siteService.getIndustrySitIdCache(anyString(),anyString())).thenReturn(null);
//        when(siteService.getDefaultSitIdCache()).thenReturn(1L);
//        PowerMockito.when(RedisUtil.get(anyString())).thenReturn("NetDefSwitch");
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//        MerchantClient.getByIdCache(anyObject());
//        RedisUtil.get(anyString());
//        Mockito.verify(strategyService,Mockito.times(2)).getSiteIdCache(anyLong(),anyString(),anyString());
//        Mockito.verify(siteService).getDefaultSitIdCache();
//        Mockito.verify(siteService).getIndustrySitIdCache(anyString(),anyString());
//    }
//
//    /**
//     * 浏览器版本过低
//     * @author 周颖  
//     * @date 2017年6月28日 上午9:47:05
//     */
//    @Test
//    public void testPortalIsLowBrowser() {
//        httpRequest.addHeader("User-Agent", "MSIE 8.0");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        httpRequest.addParameter("deviceid", "deviceid");
//        portalApiController.portal(httpRequest, httpResponse);
//    }
//    
//    /**
//     * 浏览器版本
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:52:15
//     */
//    @Test
//    public void testPortalNotLowBrowser() {
//        httpRequest.addHeader("User-Agent", "MSIE 9.0");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        httpRequest.addParameter("deviceid", "deviceid");
//        portalApiController.portal(httpRequest, httpResponse);
//    }
//    
//    /**
//     * 设备不为空
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:52:42
//     */
//    @Test
//    public void testPortalIsLowBrowserDev() throws Exception {
//        httpRequest.addHeader("User-Agent", "MSIE 8.0");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        httpRequest.addParameter("deviceid", "deviceid");
//        Device device = new Device();
//        device.setMerchantId(1L);
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        portalApiController.portal(httpRequest, httpResponse);
//        
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//    }
//    
//    /**
//     * 商户不为空
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:53:00
//     */
//    @Test
//    public void testPortalIsLowBrowserMer() throws Exception {
//        httpRequest.addHeader("User-Agent", "MSIE 8.0");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        httpRequest.addParameter("deviceid", "deviceid");
//        Device device = new Device();
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
//        Merchant merchant = new Merchant();
//        merchant.setId(1L);
//        merchant.setCascadeLabel("1");
//        PowerMockito.when(MerchantClient.getById(anyObject())).thenReturn(merchant);
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//        MerchantClient.getById(anyObject());
//    }
//    
//    /**
//     * 商户为空
//     * @throws Exception 异常
//     * @author 周颖  
//     * @date 2017年6月28日 上午10:53:16
//     */
//    @Test
//    public void testPortalIsLowBrowserMerNull() throws Exception {
//        httpRequest.addHeader("User-Agent", "MSIE 8.0");
//        httpRequest.addParameter("usertype", "NEW_USER");
//        httpRequest.addParameter("deviceid", "deviceid");
//        Device device = new Device();
//        PowerMockito.when(DeviceClient.getByDevIdCache(anyString())).thenReturn(device);
//        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("");
//        
//        portalApiController.portal(httpRequest, httpResponse);
//        
//        PowerMockito.verifyStatic();
//        DeviceClient.getByDevIdCache(anyString());
//        SysConfigUtil.getParamValue(anyString());
//    }
}
