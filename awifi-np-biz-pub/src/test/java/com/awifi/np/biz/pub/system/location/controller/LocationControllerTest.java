package com.awifi.np.biz.pub.system.location.controller;

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

import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.pub.system.location.service.LocationService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月21日 下午7:32:37
 * 创建作者：周颖
 * 文件名称：LocationControllerTest.java
 * 版本：  v1.0
 * 功能：地区测试类
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocationClient.class,SessionUtil.class,RedisUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class LocationControllerTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private LocationController locationController;
    
    /**地区服务层*/
    @Mock(name = "locationService")
    private LocationService locationService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
    
    /**
     * 缓存地区
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月21日 下午7:38:55
     */
    @Test
    public void testCache() throws Exception {
        PowerMockito.doNothing().when(LocationClient.class,"cache");
        
        Map result = locationController.cache("access_token");
        Assert.assertNotNull(result);
    }

    /**
     * 清除缓存
     * @author 周颖  
     * @date 2017年2月21日 下午7:40:09
     */
    @Test
    public void testCacheClear() {
        Set<String> keySet = new HashSet<String>();
        PowerMockito.when(RedisUtil.keys(anyString())).thenReturn(keySet);
        PowerMockito.when(RedisUtil.delBatch(anyObject())).thenReturn(1L);
        
        Map result = locationController.cacheClear("access_token");
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RedisUtil.keys(anyString());
        RedisUtil.delBatch(anyObject());
    }

    /**
     * 省列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月21日 下午8:08:09
     */
    @Test
    public void testGetProvinces() throws Exception {
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        
        List<Map<String,Object>> industryList = new ArrayList<Map<String,Object>>();
        when(locationService.getProvinces( anyObject())).thenReturn(industryList);
        
        Map result = locationController.getProvinces("access_token", httpRequest);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SessionUtil.getCurSessionUser(anyObject());
        locationService.getProvinces( anyObject());
    }

    /**
     * 市列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月21日 下午8:08:09
     */
    @Test
    public void testGetCities() throws Exception {
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        
        List<Map<String,Object>> industryList = new ArrayList<Map<String,Object>>();
        when(locationService.getCities(anyObject(),anyObject())).thenReturn(industryList);
        
        Map result = locationController.getCities("access_token", httpRequest,"parantId");
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SessionUtil.getCurSessionUser(anyObject());
        locationService.getCities(anyObject(),anyObject());
    }

    /**
     * 区县列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月21日 下午8:08:09
     */
    @Test
    public void testGetAreas() throws Exception {
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        
        List<Map<String,Object>> industryList = new ArrayList<Map<String,Object>>();
        when(locationService.getAreas(anyObject(),anyObject())).thenReturn(industryList);
        
        Map result = locationController.getAreas("access_token", httpRequest,"parentId");
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SessionUtil.getCurSessionUser(anyObject());
        locationService.getAreas(anyObject(),anyObject());
    }
}