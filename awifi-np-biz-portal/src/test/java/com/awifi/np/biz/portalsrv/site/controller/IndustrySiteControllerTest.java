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

@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.x"})
public class IndustrySiteControllerTest {
	/**被测试类*/
    @InjectMocks
	private IndustrySiteController industrySiteController;
	
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
     * 测试行业站点列表获取
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午8:51:04
     */
    @Test
    public void testGetIndustryListByParam() throws Exception{
    	PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
    	String params = "{'pageSize':10,'maxPageSize':30,'priIndustryCode':'','secIndustryCode':'','keywords':'awa','pageNo':1}";
    	industrySiteController.getIndustryListByParam("access_token", params);
    }
    
    /**
     * 测试添加行业站点
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午8:53:12
     */
    @Test
    public void testAddOk() throws Exception{
    	industrySiteController.add("access_token", new HashMap<String,Object>(), request);
    }
    
    /**
     * 测试编辑行业站点
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月6日 上午8:54:07
     */
    @Test
    public void testEditOk() throws Exception{
    	industrySiteController.update("access_token", new HashMap<String,Object>(), request,1L);
    }
    
    /**
     * 测试删除行业站点
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:30:03
     */
    @Test
    public void testDeleteOk() throws Exception{
    	industrySiteController.delete("access_token", 20L);
    }
}
