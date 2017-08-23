package com.awifi.np.biz.portalsrv.component.controller;

import static org.mockito.Matchers.anyString;

import java.util.Map;

import org.junit.Assert;
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
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.portal.component.service.ComponentService;

/**
 * 版权所有：爱WiFi无线运营中心
 * 创建时间：2017年6月5日 下午2:44:38
 * 创建作者：方志伟
 * 文件名称：ComponentControllerTest.java
 * 版本：v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class ComponentControllerTest {
	/**被测试类*/
    @InjectMocks
	private ComponentController componentController;
	
    /**组件服务*/
    @Mock(name = "componentService")
    private ComponentService componentService;
    
    /**模板服务*/
    @Mock(name = "templateService")
    private TemplateService templateService;
    
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
     * 测试组件列表获取
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月5日 下午2:49:02
     */
    @Test
    public void testGetListByParam() throws Exception{
    	PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
    	String params = "{'pageSize':10,'maxPageSize':30,'keywords':'awa','pageNo':1}";
    	Map<String, Object> resultMap = componentController.getListByParam("access_token", params);
    	Assert.assertNotNull(resultMap);
    }
    
    /**
     * 测试组件添加
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月5日 下午2:52:51
     */
    @Test
    public void testAddOk() throws Exception{
        componentController.add("access_token", request);
    }
    
    /**
     * 测试获取组件详情
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月5日 下午2:52:51
     */
    @Test
    public void testgetByIdOk() throws Exception{
    	componentController.getById("access_token", 1L);
    }
    
    /**
     * 测试组件编辑
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月5日 下午2:52:51
     */
    @Test
    public void testEditOk() throws Exception{
    	componentController.edit("access_token", request, 1L);
    }
    
    /**
     * 测试组件列表 按类型 获取
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月5日 下午2:52:51
     */
    @Test
    public void testGetListByType() throws Exception{
    	String params = "{'pageSize':10,'maxPageSize':30,'keywords':'awa','pageNo':1}";
    	componentController.getListByType("aaa", params, 1);
    }
}
