/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 22, 2017 2:31:51 PM
* 创建作者：季振宇
* 文件名称：IndustryServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.system.industry.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;

import static org.mockito.Matchers.anyString;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtils.class,RedisUtil.class,IndustryClient.class})
public class IndustryServiceImplTest {
    
    /**被测试类*/
    @InjectMocks
    private IndustryServiceImpl industryServiceImpl;
    
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
        PowerMockito.mockStatic(IndustryClient.class);
    }

    /**
     * 测试获取行业信息
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 2:35:24 PM
     */
    @Test
    public void testGetListByParam() throws Exception {
        PowerMockito.when(StringUtils.isNotBlank(anyString())).thenReturn(true);
        
        Map<String,String> redisMap = new HashMap<>();
        redisMap.put("np_biz_industry_12345678*_2", "12345678");
        redisMap.put("np_biz_industry_23456789*_2", "23456789");
        PowerMockito.when(RedisUtil.getBatch(anyString())).thenReturn(redisMap);
        PowerMockito.doNothing().when(IndustryClient.class,"cache");
        
        List<Map<String, String>> result = industryServiceImpl.getListByParam("12345678");
        List<Map<String, String>> expected = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("industryCode", "12345678");
        map1.put("industryName", "12345678");
        Map<String, String> map2 = new HashMap<>();
        map2.put("industryCode", "23456789");
        map2.put("industryName", "23456789");
        expected.add(map1);
        expected.add(map2);
        
        Assert.assertEquals(expected, result);
    }
    
    
    
    /**
     * 测试获取行业信息
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 2:35:24 PM
     */
    @Test
    public void testGetListByParam1() throws Exception {
        PowerMockito.when(StringUtils.isNotBlank(anyString())).thenReturn(false);
        
        Map<String,String> redisMap = new HashMap<>();
        redisMap.put("np_biz_industry_12345678*_2", "12345678");
        redisMap.put("np_biz_industry_23456789*_2", "23456789");
        PowerMockito.when(RedisUtil.getBatch(anyString())).thenReturn(redisMap);
        PowerMockito.doNothing().when(IndustryClient.class,"cache");
        
        List<Map<String, String>> result = industryServiceImpl.getListByParam("");
        List<Map<String, String>> expected = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("industryCode", "123456");
        map1.put("industryName", "12345678");
        Map<String, String> map2 = new HashMap<>();
        map2.put("industryCode", "234567");
        map2.put("industryName", "23456789");
        expected.add(map1);
        expected.add(map2);
        
        Assert.assertEquals(expected, result);
    }
    
    /**
     * 测试获取行业信息
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 2:35:24 PM
     */
    @Test
    public void testGetListByParam2() throws Exception {
        PowerMockito.when(StringUtils.isNotBlank(anyString())).thenReturn(true);
        
        Map<String,String> redisMap = new HashMap<>();
        redisMap.put("np_biz_industry_12345678*_2", "12345678");
        redisMap.put("np_biz_industry_23456789*_2", "23456789");
        PowerMockito.when(RedisUtil.getBatch(anyString())).thenReturn(null);
        PowerMockito.doNothing().when(IndustryClient.class,"cache");
        PowerMockito.when(RedisUtil.getBatch(anyString())).thenReturn(redisMap);
        
        List<Map<String, String>> result = industryServiceImpl.getListByParam("");
        List<Map<String, String>> expected = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("industryCode", "12345678");
        map1.put("industryName", "12345678");
        Map<String, String> map2 = new HashMap<>();
        map2.put("industryCode", "23456789");
        map2.put("industryName", "23456789");
        expected.add(map1);
        expected.add(map2);
        
        Assert.assertEquals(expected, result);
        PowerMockito.verifyStatic();
        RedisUtil.getBatch(anyString());
        RedisUtil.getBatch("key");
    }
    
    /**
     * 测试redisMap 格式化
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 10:01:52 AM
     */
    @Test
    public void testFormateIndustry () throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("industryCode", "12345678");
        map1.put("industryName", "12345678");
        Map<String, String> map2 = new HashMap<>();
        map2.put("industryCode", "23456789");
        map2.put("industryName", "23456789");
        list.add(map1);
        list.add(map2);
        
        Map<String, String> redismap = new HashMap<>();
        redismap.put("np_biz_industry_12345678*_2", "12345678");
        redismap.put("np_biz_industry_23456789*_2", "23456789");
        //获取目标类的class对象  
        Class<IndustryServiceImpl> class1 = IndustryServiceImpl.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("formateIndustry", new Class[]{Map.class,int.class});
        method.setAccessible(true);
        List<Map<String, String>> result = (List<Map<String, String>>)method.invoke(instance, new Object[]{redismap,24});
        Assert.assertEquals(list, result);
    }
    
    /**
     * 测试按industryCode排序
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 10:01:52 AM
     */
    @Test
    public void testSort () throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("industryCode", "industryCode1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("industryCode", "industryCode2");
        list.add(map1);
        list.add(map2);
        
        //获取目标类的class对象  
        Class<IndustryServiceImpl> class1 = IndustryServiceImpl.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("sort", new Class[]{List.class});
        method.setAccessible(true);
        List<Map<String, String>> result = (List<Map<String, String>>)method.invoke(instance, new Object[]{list});
        
        Assert.assertEquals(list, result);
    }

}
