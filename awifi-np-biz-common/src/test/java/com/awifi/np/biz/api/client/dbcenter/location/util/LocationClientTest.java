package com.awifi.np.biz.api.client.dbcenter.location.util;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.location.service.LocationApiService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 上午9:50:17
 * 创建作者：周颖
 * 文件名称：LocationClientTest.java
 * 版本：  v1.0
 * 功能：地区测试类
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({RedisUtil.class, BeanUtil.class})
public class LocationClientTest {

    /**被测试类*/
    @InjectMocks
    private LocationClient locationClient;
    
    /**
     * 地区
     */
    @Mock(name = "locationApiService")
    private LocationApiService locationApiService;
    
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
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(locationApiService);
    }
    
    /**
     * 获取地区列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:52:46
     */
    @Test
    public void testGetAllLocation() throws Exception {
        locationClient.getAllLocation();
    }

    /**
     * 缓存地区
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午10:06:34
     */
    @Test
    public void testCache() throws Exception {
        Map<Long,Map<String,Object>> allLocationMap = new HashMap<Long,Map<String,Object>>();
        Map<String,Object> locationMap = new HashMap<String,Object>();
        locationMap.put("parentId", 1L);
        locationMap.put("id", 31L);
        allLocationMap.put(31L, locationMap);
        locationMap = new HashMap<String,Object>();
        locationMap.put("id", 32L);
        allLocationMap.put(32L, locationMap);
        when(locationClient.getAllLocation()).thenReturn(allLocationMap);
        locationClient.cache();
    }

    /**
     * 获取地区名称 缓存中有
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午10:27:13
     */
    @Test
    public void testGetByIdAndParam() throws Exception {
        List<String> locationList = new ArrayList<String>();
        locationList.add("location");
        PowerMockito.when(RedisUtil.hmgetLike(anyString(),anyString())).thenReturn(locationList);
        locationClient.getByIdAndParam(31L, "name");
    }
    
    /**
     * 获取地区名称 缓存中有，当不匹配
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午10:27:39
     */
    @Test
    public void testGetByIdAndParamEmpty() throws Exception {
        PowerMockito.when(RedisUtil.hmgetLike(anyString(),anyString())).thenReturn(null);
        PowerMockito.when(RedisUtil.exist(anyString())).thenReturn(true);
        locationClient.getByIdAndParam(31L, "name");
    }
    
    /**
     * 获取地区缓存 缓存中没有 缓存后获取
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午10:30:45
     */
    @Test
    public void testGetByIdAndParamCache() throws Exception {
        PowerMockito.when(RedisUtil.hmgetLike(anyString(),anyString())).thenReturn(null);
        PowerMockito.when(RedisUtil.exist(anyString())).thenReturn(false);
        locationClient.getByIdAndParam(31L, "name");
    }
}
