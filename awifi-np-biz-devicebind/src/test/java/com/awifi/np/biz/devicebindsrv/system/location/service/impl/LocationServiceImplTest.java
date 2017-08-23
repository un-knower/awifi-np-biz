/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 22, 2017 3:40:33 PM
* 创建作者：季振宇
* 文件名称：LocationServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.system.location.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.redis.util.RedisUtil;


@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtils.class,RedisUtil.class})
public class LocationServiceImplTest {
    
    /**被测试类*/
    @InjectMocks
    private LocationServiceImpl locationServiceImpl;
    
    /**
     * 初始化
     * @author 季振宇  
     * @date Jun 21, 2017 3:01:29 PM
     */
    @Before
    public void before () {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }

    /**
     * 测试获取省地区信息
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 7:22:47 PM
     */
    @Test
    public void testGetProvinces() throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "12345");
        map1.put("id", "123");
        map1.put("areaName", "areaName1");
        map1.put("name", "name");
        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "23456");
        map2.put("id", "234");
        map2.put("areaName", "areaName2");
        map2.put("name", "name");
        list.add(map1);
        list.add(map2);
        
        PowerMockito.when(RedisUtil.hgetAllBatch(Mockito.anyString())).thenReturn(list);
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString(), Mockito.anyString())).thenReturn("12345").thenReturn("23456");
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString())).thenReturn("areaName1").thenReturn("areaName2");
        
        List<Map<String, Object>> result = locationServiceImpl.getProvinces();
        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> expectedMap1 = new HashMap<>();
        expectedMap1.put("id", 123L);
        expectedMap1.put("areaName", "areaName1");
        Map<String, Object> expectedMap2 = new HashMap<>();
        expectedMap2.put("id", 234L);
        expectedMap2.put("areaName", "areaName2");
        expected.add(expectedMap1);
        expected.add(expectedMap2);
        Assert.assertEquals(expected, result);
    }

    /**
     * 测试获取市地区信息
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 7:23:11 PM
     */
    @Test
    public void testGetCities() throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "12345");
        map1.put("id", "123");
        map1.put("areaName", "areaName1");
        map1.put("name", "name");
        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "23456");
        map2.put("id", "234");
        map2.put("areaName", "areaName2");
        map2.put("name", "name");
        list.add(map1);
        list.add(map2);
        
        PowerMockito.when(RedisUtil.hgetAllBatch(Mockito.anyString())).thenReturn(list);
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString(), Mockito.anyString())).thenReturn("12345").thenReturn("23456");
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString())).thenReturn("areaName1").thenReturn("areaName2");
        
        List<Map<String, Object>> result = locationServiceImpl.getCities("38L");
        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> expectedMap1 = new HashMap<>();
        expectedMap1.put("id", 123L);
        expectedMap1.put("areaName", "areaName1");
        Map<String, Object> expectedMap2 = new HashMap<>();
        expectedMap2.put("id", 234L);
        expectedMap2.put("areaName", "areaName2");
        expected.add(expectedMap1);
        expected.add(expectedMap2);
        Assert.assertEquals(expected, result);
    }

    /**
     * 测试获取区县地区信息
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 7:23:14 PM
     */
    @Test
    public void testGetAreas() throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "12345");
        map1.put("id", "123");
        map1.put("areaName", "areaName1");
        map1.put("name", "name");
        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "23456");
        map2.put("id", "234");
        map2.put("areaName", "areaName2");
        map2.put("name", "name");
        list.add(map1);
        list.add(map2);
        
        PowerMockito.when(RedisUtil.hgetAllBatch(Mockito.anyString())).thenReturn(list);
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString(), Mockito.anyString())).thenReturn("12345").thenReturn("23456");
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString())).thenReturn("areaName1").thenReturn("areaName2");
        
        List<Map<String, Object>> result = locationServiceImpl.getAreas("383L");
        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> expectedMap1 = new HashMap<>();
        expectedMap1.put("id", 123L);
        expectedMap1.put("areaName", "areaName1");
        Map<String, Object> expectedMap2 = new HashMap<>();
        expectedMap2.put("id", 234L);
        expectedMap2.put("areaName", "areaName2");
        expected.add(expectedMap1);
        expected.add(expectedMap2);
        Assert.assertEquals(expected, result);
    }
    
    /**
     * 测试从redis获取数据
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 23, 2017 3:20:23 PM
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetLocation () throws Exception {
        
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "12345");
        map1.put("id", "123");
        map1.put("areaName", "areaName1");
        map1.put("name", "name");
        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "23456");
        map2.put("id", "234");
        map2.put("areaName", "areaName2");
        map2.put("name", "name");
        list.add(map1);
        list.add(map2);
        
        PowerMockito.when(RedisUtil.hgetAllBatch(Mockito.anyString())).thenReturn(list);
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString(), Mockito.anyString())).thenReturn("12345").thenReturn("23456");
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString())).thenReturn("areaName1").thenReturn("areaName2");
        
        //获取目标类的class对象  
        Class<LocationServiceImpl> class1 = LocationServiceImpl.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("getLocation", new Class[]{String.class});
        method.setAccessible(true);
        List<Map<String, Object>> result = (List<Map<String, Object>>)method.invoke(instance, new Object[]{"redisKey"} );
        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> expectedMap1 = new HashMap<>();
        expectedMap1.put("id", 123L);
        expectedMap1.put("areaName", "areaName1");
        Map<String, Object> expectedMap2 = new HashMap<>();
        expectedMap2.put("id", 234L);
        expectedMap2.put("areaName", "areaName2");
        expected.add(expectedMap1);
        expected.add(expectedMap2);
        
        Assert.assertEquals(expected, result);
    }
    
    /**
     * 测试redis返回的list封装
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 3:49:31 PM
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFormateList() throws Exception {
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString(), Mockito.anyString())).thenReturn("12345").thenReturn("23456");
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString())).thenReturn("areaName1").thenReturn("areaName2");
        
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "12345");
        map1.put("id", "123");
        map1.put("areaName", "areaName1");
        map1.put("name", "name");
        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "23456");
        map2.put("id", "234");
        map2.put("areaName", "areaName2");
        map2.put("name", "name");
        list.add(map1);
        list.add(map2);
        
        //获取目标类的class对象  
        Class<LocationServiceImpl> class1 = LocationServiceImpl.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("formate", new Class[]{List.class});
        method.setAccessible(true);
        List<Map<String, Object>> result = (List<Map<String, Object>>)method.invoke(instance, new Object[]{list});
        
        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> expectedMap1 = new HashMap<>();
        expectedMap1.put("id", 123L);
        expectedMap1.put("areaName", "areaName1");
        Map<String, Object> expectedMap2 = new HashMap<>();
        expectedMap2.put("id", 234L);
        expectedMap2.put("areaName", "areaName2");
        expected.add(expectedMap1);
        expected.add(expectedMap2);
        
        Assert.assertEquals(expected, result);
    }
    
    /**
     * 测试只返回id+name
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 3:49:31 PM
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFormateMap() throws Exception {
        PowerMockito.when(StringUtils.isBlank(Mockito.anyString())).thenReturn(false);
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString())).thenReturn("areaName");
        
        Map<String, String> map = new HashMap<>();
        map.put("id", "123");
        map.put("areaName", "areaName");
        
        //获取目标类的class对象  
        Class<LocationServiceImpl> class1 = LocationServiceImpl.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("formate", new Class[]{Map.class});
        method.setAccessible(true);
        Map<String, Object> result = (Map<String, Object>)method.invoke(instance, new Object[]{map});
        Map<String, Object> expected = new HashMap<>();
        expected.put("id", 123L);
        expected.put("areaName", "areaName");
        Assert.assertEquals(expected, result);
    }
    
    /**
     * 测试只返回id+name
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 3:49:31 PM
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFormateMa1() throws Exception {
        PowerMockito.when(StringUtils.isBlank(Mockito.anyString())).thenReturn(true);
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString())).thenReturn("areaName");
        
        Map<String, String> map = new HashMap<>();
        map.put("id", "123");
        map.put("areaName", "areaName");
        
        //获取目标类的class对象  
        Class<LocationServiceImpl> class1 = LocationServiceImpl.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("formate", new Class[]{Map.class});
        method.setAccessible(true);
        Map<String, Object> result = (Map<String, Object>)method.invoke(instance, new Object[]{map});
        Assert.assertEquals(null, result);
    }
    
    /**
     * 测试按地区编号从小到大排序(采用冒泡排序)  
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 3:50:14 PM
     */
    @Test
    public void testSort() throws Exception {
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString(), Mockito.anyString())).thenReturn("23456").thenReturn("12345");
        
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "12345");
        map1.put("id", "123");
        map1.put("areaName", "areaName");
        map1.put("name", "name");
        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "23456");
        map2.put("id", "234");
        map2.put("areaName", "areaName");
        map2.put("name", "name");
        list.add(map1);
        list.add(map2);
        
        //获取目标类的class对象  
        Class<LocationServiceImpl> class1 = LocationServiceImpl.class;  
        Method method = class1.getDeclaredMethod("sort", List.class);
        method.setAccessible(true);
        method.invoke(class1, new Object[]{list});
        
        PowerMockito.verifyStatic();
        StringUtils.defaultString("12345", "zzz");
        StringUtils.defaultString("23456", "zzz");
    }
    
    /**
     * 测试按地区编号从小到大排序(采用冒泡排序)  
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 3:50:14 PM
     */
    @Test
    public void testSort1() throws Exception {
        PowerMockito.when(StringUtils.defaultString(Mockito.anyString(), Mockito.anyString())).thenReturn("12345").thenReturn("23456");
        
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "12345");
        map1.put("id", "123");
        map1.put("areaName", "areaName");
        map1.put("name", "name");
        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "23456");
        map2.put("id", "234");
        map2.put("areaName", "areaName");
        map2.put("name", "name");
        list.add(map2);
        list.add(map1);
        
        //获取目标类的class对象  
        Class<LocationServiceImpl> class1 = LocationServiceImpl.class;  
        Method method = class1.getDeclaredMethod("sort", List.class);
        method.setAccessible(true);
        method.invoke(class1, new Object[]{list});
        
        PowerMockito.verifyStatic();
        StringUtils.defaultString("12345", "zzz");
        StringUtils.defaultString("23456", "zzz");
    }
}
