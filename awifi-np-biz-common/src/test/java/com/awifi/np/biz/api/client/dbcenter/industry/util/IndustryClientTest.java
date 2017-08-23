package com.awifi.np.biz.api.client.dbcenter.industry.util;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.industry.service.IndustryApiService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 上午10:34:42
 * 创建作者：周颖
 * 文件名称：IndustryClientTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({RedisUtil.class, BeanUtil.class})
public class IndustryClientTest {

    /**被测试类*/
    @InjectMocks
    private IndustryClient industryClient;
    
    /**
     * 行业
     */
    @Mock(name = "industryApiService")
    private IndustryApiService industryApiService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(industryApiService);
    }
    
    /**
     * 获取所有行业
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:27:06
     */
    @Test
    public void testGetAllIndustry() throws Exception {
        industryClient.getAllIndustry();
    }

    /**
     * 缓存
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:27:22
     */
    @Test
    public void testCache() throws Exception {
        List<Map<String,Object>> industryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> indestryMap = new HashMap<String,Object>();
        indestryMap.put("labelCode", "OCAB01");
        indestryMap.put("labelLevel", 1);
        industryList.add(indestryMap);
        when(industryClient.getAllIndustry()).thenReturn(industryList);
        industryClient.cache();
    }

    /**
     * 获取行业名称 行业编号为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:47:04
     */
    @Test
    public void testGetNameByCodeNull() throws Exception {
        industryClient.getNameByCode(null);
    }

    /**
     * 获取行业名称 缓存中有值
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:47:04
     */
    @Test
    public void testGetNameByCode() throws Exception {
        PowerMockito.when(RedisUtil.get(anyString())).thenReturn("行业");
        industryClient.getNameByCode("OCABO101");
        PowerMockito.verifyStatic();
        RedisUtil.get(anyString());
    }
    
    /**
     * 获取行业名称 缓存中有值，但是不对应
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:47:53
     */
    @Test
    public void testGetNameByCodeError() throws Exception {
        PowerMockito.when(RedisUtil.get(anyString())).thenReturn(null);
        Set<String> keys = new HashSet<String>();
        keys.add("行业");
        PowerMockito.when(RedisUtil.keys(anyString())).thenReturn(keys);
        industryClient.getNameByCode("OCABO1");
        PowerMockito.verifyStatic();
        RedisUtil.get(anyString());
    }
}
