package com.awifi.np.biz.pub.system.location.service.impl;

import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月22日 上午9:24:59
 * 创建作者：周颖
 * 文件名称：LocationServiceImplTest.java
 * 版本：  v1.0
 * 功能：地区实现类junit
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocationClient.class,MessageUtil.class,RedisUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class LocationServiceImplTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private LocationServiceImpl locationServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
    
    /**
     * 抛异常
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午10:00:44
     */
    /*@Test(expected=BizException.class)
    public void testGetProvincesBizException() throws Exception {
        PowerMockito.when(MessageUtil.getMessage(anyObject())).thenReturn("error");
        
        SessionUser sessionUser = new SessionUser();
        locationServiceImpl.getProvinces(sessionUser);
        
        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyObject());
    }*/
    
    /**
     * 有数据
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午10:13:09
     */
    @Test
    public void testGetProvincesProvince() throws Exception {
        List<Map<String, String>> redisValue = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String,String>();
        map.put("id", "101");
        map.put("name", "崇左");
        map.put("code", "451400");
        
        redisValue.add(map);
        map = new HashMap<String,String>();
        map.put("id", "100");
        map.put("name", "北海");
        map.put("code", "450500");
        redisValue.add(map);
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(redisValue);
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setProvinceId(1L);
        locationServiceImpl.getProvinces(sessionUser);
        
        PowerMockito.verifyStatic();
        RedisUtil.hgetAllBatch(anyObject());
    }

    /**
     * 市列表有数据
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午10:21:57
     */
    @Test
    public void testGetCities() throws Exception {
        
        List<Map<String, String>> redisValue = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String,String>();
        map.put("id", null);
        map.put("name", "崇左");
        map.put("code", "451400");
        
        redisValue.add(map);
        
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(redisValue);
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setCityId(383L);
        locationServiceImpl.getCities(sessionUser, "31");
        
        PowerMockito.verifyStatic();
        RedisUtil.hgetAllBatch(anyObject());
    }

    /**
     * 区县列表有数据
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午10:22:14
     */
    @Test
    public void testGetAreas() throws Exception {
        
        List<Map<String, String>> redisValue = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String,String>();
        map = new HashMap<String,String>();
        map.put("id", "100");
        map.put("name", "北海");
        map.put("code", "450500");
        redisValue.add(map);
        map.put("id", "101");
        map.put("name", "崇左");
        map.put("code", "451400");
        redisValue.add(map);
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(redisValue);
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setAreaId(3220L);
        locationServiceImpl.getAreas(sessionUser, "383");
        
        PowerMockito.verifyStatic();
        RedisUtil.hgetAllBatch(anyObject());
    }
    
    /**
     * 没有数据
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午10:25:59
     */
    @Test
    public void testGetAreasNull() throws Exception{
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(null);
        Set<String> keys = new HashSet<String>();
        keys.add("key");
        PowerMockito.when(RedisUtil.keys(anyObject())).thenReturn(keys);
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setAreaId(3220L);
        locationServiceImpl.getAreas(sessionUser, "383");
        
        PowerMockito.verifyStatic();
        RedisUtil.hgetAllBatch(anyObject());
        RedisUtil.keys(anyObject());
    }
    
    /**
     * 没有数据
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午10:25:59
     */
    /*@Test
    public void testGetAreasCache() throws Exception{
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(null);
        Set<String> keys = new HashSet<String>();
        PowerMockito.when(RedisUtil.keys(anyObject())).thenReturn(keys);
        PowerMockito.doNothing().when(LocationClient.class,"cache");
        
        
        SessionUser sessionUser = new SessionUser();
        locationServiceImpl.getAreas(sessionUser, "383");
        
        PowerMockito.verifyStatic();
        RedisUtil.hgetAllBatch(anyObject());
        RedisUtil.keys(anyObject());
        LocationClient.cache();
    }*/
}