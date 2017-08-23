package com.awifi.np.biz.pub.system.industry.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.pub.system.industry.service.IndustryService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月21日 下午6:49:09
 * 创建作者：周颖
 * 文件名称：IndustryControllerTest.java
 * 版本：  v1.0
 * 功能：行业测试类
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@RunWith(PowerMockRunner.class)
@PrepareForTest({IndustryClient.class,SessionUtil.class,RedisUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class IndustryControllerTest {
    
    /**被测试类*/
    @InjectMocks
    private IndustryController industryController;

    /**行业服务层*/
    @Mock(name = "industryService")
    private IndustryService industryService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(IndustryClient.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
    
    /**
     * 缓存行业信息
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月21日 下午7:07:00
     */
    @Test
    public void testCache() throws Exception {
        PowerMockito.doNothing().when(IndustryClient.class,"cache");
        
        Map result = industryController.cache("access_token");
        Assert.assertNotNull(result);
    }

    /**
     * 清除缓存
     * @author 周颖  
     * @date 2017年2月21日 下午7:15:51
     */
    @Test
    public void testCacheClear() {
        Set<String> keySet = new HashSet<String>();
        PowerMockito.when(RedisUtil.keys(anyString())).thenReturn(keySet);
        PowerMockito.when(RedisUtil.delBatch(anyObject())).thenReturn(1L);
        
        Map result = industryController.cacheClear("access_token");
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RedisUtil.keys(anyString());
        RedisUtil.delBatch(anyObject());
    }

    /**
     * 行业列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月21日 下午7:24:56
     */
    @Test
    public void testGetListByParam() throws Exception {
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        
        List<Map<String,String>> industryList = new ArrayList<Map<String,String>>();
        when(industryService.getListByParam(anyObject(),anyObject())).thenReturn(industryList);
        
        Map result = industryController.getListByParam("access_token", "parentCode", httpRequest);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SessionUtil.getCurSessionUser(anyObject());
        industryService.getListByParam(anyObject(),anyObject());
    }
}