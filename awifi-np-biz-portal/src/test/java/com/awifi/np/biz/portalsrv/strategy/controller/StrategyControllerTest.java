package com.awifi.np.biz.portalsrv.strategy.controller;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.x"})
public class StrategyControllerTest {
	/**被测试类*/
    @InjectMocks
	private StrategyController strategyController;
	
    /**组件服务*/
    @Mock(name = "strategyService")
    private StrategyService strategyService;
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
     * 测试策略列表获取
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:52:30
     */
    @Test
    public void testGetListByParam() throws Exception{
    	PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
    	String params = "{'paramsMap':'{}','pageSize':10,'maxPageSize':30,'pageNo':1}";
    	strategyController.getListByParam("access_token", params, request, 1L);
    }
    
    /**
     * 测试添加策略
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:58:47
     */
    @Test
    public void testAdd() throws Exception{
    	Map<String, Object> bodyParam = new HashMap<String, Object>();
    	bodyParam.put("strategyName", "aaa");
    	bodyParam.put("startDate", "1970-01-01 00:00:00");
    	bodyParam.put("endDate", "2017-06-01 23:59:59");
    	bodyParam.put("strategyType", 2);
    	bodyParam.put("content", "111");
    	strategyController.add("access_token", bodyParam, 1L);
    }
    
    /**
     * 测试编辑策略
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:59:11
     */
    @Test
    public void testUpdate() throws Exception{
    	Map<String, Object> bodyParam = new HashMap<String, Object>();
    	bodyParam.put("strategyName", "aaa");
    	bodyParam.put("startDate", "1970-01-01 00:00:00");
    	bodyParam.put("endDate", "2017-06-01 23:59:59");
    	bodyParam.put("strategyType", 2);
    	bodyParam.put("content", "111");
    	strategyController.update("access_token", bodyParam, 1L, 2L);
    }
    
    /**
     * 测试站点下最新策略
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:59:32
     */
    @Test
    public void testGetBySiteId() throws Exception{
    	strategyController.getBySiteId("access_token", 1L);
    }
    
    /**
     * 测试策略详情
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月6日 上午9:59:51
     */
    @Test
    public void testGetById() throws Exception{
    	strategyController.getById("access_token", 1L);
    }
    
    /**
     * 测试策略删除
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月6日 上午10:00:15
     */
    @Test
    public void testDelete() throws Exception{
    	strategyController.delete("access_token", 1L);
    }
    
    /**
     * 测试策略优先级调整
     * @throws Exception 
     * @author 方志伟
     * @date 2017年6月6日 上午10:00:34
     */
    @Test
    public void testPriority() throws Exception{
    	strategyController.priority("access_token", new HashMap<String, Object>(), 1L);
    }
}
