package com.awifi.np.biz.portalsrv.site.controller;

import static org.mockito.Matchers.anyString;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

/**
 * 版权所有：爱WiFi无线运营中心
 * 创建时间：2017年6月6日 上午9:34:26
 * 创建作者：方志伟
 * 文件名称：LocationSiteControllerTest.java
 * 版本：v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.x"})
public class LocationSiteControllerTest {
	
	/**被测试类*/
    @InjectMocks
	private LocationSiteController locationSiteController;
	
	 
    /**组件服务*/
    @Mock(name = "siteService")
    private SiteService siteService;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    /**
     * 测试地区站点列表
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:34:38
     */
    @Test
    public void testGetIndustryListByParam() throws Exception{
    	PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
    	String params = "{'pageSize':10,'maxPageSize':30,'provinceId':1L,'cityId':2L,'keywords':'awa','pageNo':1}";
    	locationSiteController.getLocationListByParam("access_token", params);
    }
    
    /**
     * 测试添加地区站点
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:35:03
     */
    @Test
    public void testAddOk() throws Exception{
    	locationSiteController.add("access_token", new HashMap<String,Object>(), request);
    }
    
    /**
     * 测试编辑地区站点
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:35:27
     */
    @Test
    public void testEditOk() throws Exception{
    	locationSiteController.update("access_token", new HashMap<String,Object>(), request,1L);
    }
    
    /**
     * 测试删除行业站点
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:35:45
     */
    @Test
    public void testDeleteOk() throws Exception{
    	locationSiteController.delete("access_token", 20L);
    }
}
