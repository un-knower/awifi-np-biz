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
 * 创建时间：2017年6月6日 上午8:50:42
 * 创建作者：方志伟
 * 文件名称：DefaultSiteControllerTest.java
 * 版本：v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.x"})
public class DefaultSiteControllerTest {
	/**被测试类*/
    @InjectMocks
	private DefaultSiteController defaultSiteController;
	
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
     * 测试默认站点列表获取
     * @author 方志伟
     * @date 2017年6月6日 上午8:37:42
     */
    @Test
    public void testGetDefaultListByParam(){
    	PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
    	String params = "{'pageSize':10,'maxPageSize':30,'keywords':'awa','pageNo':1}";
    	defaultSiteController.getDefaultListByParam("aaa", params);
    }
    
    /**
     * 测试添加默认站点
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月6日 上午8:42:33
     */
    @Test
    public void testAddOk() throws Exception{
    	defaultSiteController.add("access_token", new HashMap<String,Object>(), request);
    }
    
    /**
     * 测试默认站点编辑
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月6日 上午8:45:01
     */
    @Test
    public void testEditOk() throws Exception{
    	defaultSiteController.edit("access_token", new HashMap<String,Object>(), request,1L);
    }
    
    /**
     * 测试站点删除
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月6日 上午8:45:38
     */
    @Test
    public void testDeleteOk() throws Exception{
    	defaultSiteController.delete("access_token", 20L);
    }
}
