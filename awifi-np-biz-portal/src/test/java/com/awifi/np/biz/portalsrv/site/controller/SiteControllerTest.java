package com.awifi.np.biz.portalsrv.site.controller;

import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

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
import com.awifi.np.biz.toe.admin.strategy.service.StrategyService;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

/**
 * 版权所有：爱WiFi无线运营中心
 * 创建时间：2017年6月6日 上午9:36:17
 * 创建作者：方志伟
 * 文件名称：SiteControllerTest.java
 * 版本：v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.x"})
public class SiteControllerTest {
	/**被测试类*/
    @InjectMocks
	private SiteController siteController;
	
    /**组件服务*/
    @Mock(name = "siteService")
    private SiteService siteService;
    /**组件服务*/
    @Mock(name = "strategyService")
    private StrategyService strategyService;
    
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
     * 测试站点策略列表
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:38:18
     */
    @Test
    public void testGetListByParam() throws Exception{
    	PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
    	String params = "{'pageSize':10,'maxPageSize':30,'siteId':1L,'merchantId':2L,'status':'2','keywords':'awa','pageNo':1}";
    	siteController.getListByParam("access_token", params, request);
    }
    
    /**
     * 测试新增站点
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:38:39
     */
    @Test
    public void testAddOk() throws Exception{
    	siteController.add("access_token", new HashMap<String,Object>(), request);
    }
    
    /**
     * 测试编辑站点
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:39:14
     */
    @Test
    public void testEditOk() throws Exception{
    	siteController.update("access_token", new HashMap<String,Object>(), request,1L);
    }
    
    /**
     * 测试站点删除
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:39:34
     */
    @Test
    public void testDeleteOk() throws Exception{
    	siteController.delete("access_token", 2L);
    }
    
    /**
     * 测试站点详情获取
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:39:56
     */
    @Test
    public void testGetByIdOk() throws Exception{
    	siteController.getById("access_token", 20L);
    }
    
    /**
     * 测试站点审核
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:40:15
     */
    @Test
    public void testverifyOk() throws Exception{
    	siteController.verify("access_token", 20L);
    }
    
    /**
     * 测试站点发布
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月6日 上午9:40:41
     */
    @Test
    public void testPublish() throws Exception{
    	Map<String, Object> bodyParam = new HashMap<String, Object>();
    	bodyParam.put("strategyId", 1L);
    	bodyParam.put("strategyName", "aaa");
    	bodyParam.put("startDate", "1970-01-01 00:00:00");
    	bodyParam.put("endDate", "2017-06-01 23:59:59");
    	bodyParam.put("strategyType", 2);
    	bodyParam.put("content", "111");
    	siteController.publish("access_token", 2L, bodyParam);
    }
    
    /**
     * 测试审核站点 站点页信息
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:41:52
     */
    @Test
    public void testGetSitePageForPreview() throws Exception{
    	siteController.getSitePageForPreview("access_token", "{}", 2L);
    }
}
