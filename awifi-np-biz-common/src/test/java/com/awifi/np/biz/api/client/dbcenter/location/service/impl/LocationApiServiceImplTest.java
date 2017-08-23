package com.awifi.np.biz.api.client.dbcenter.location.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月24日 下午3:47:32
 * 创建作者：周颖
 * 文件名称：LocationApiServiceImplTest.java
 * 版本：  v1.0
 * 功能：地区测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,JsonUtil.class})
public class LocationApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private LocationApiServiceImpl locationApiServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
    }
    
    /**
     * 获取所有地区
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月27日 下午3:55:17
     */
    @Test
    public void testGetAllLocation() throws Exception {
        List<Map<String, Object>> locationList = new ArrayList<Map<String,Object>>();
        Map<String, Object> location1 = new HashMap<String,Object>();
        location1.put("id", 31L);
        location1.put("parentId", 1L);
        location1.put("areaName", "浙江");
        location1.put("areaCnCode", "330000");
        location1.put("areaType", "PROVINCE");
        locationList.add(location1);
        location1 = new HashMap<String,Object>();
        location1.put("id", 383L);
        location1.put("parentId", 31L);
        location1.put("areaName", "杭州");
        location1.put("areaCnCode", "330100");
        location1.put("areaType", "CITY");
        locationList.add(location1);
        location1 = new HashMap<String,Object>();
        location1.put("id", 3230L);
        location1.put("parentId", 383L);
        location1.put("areaName", "上城区");
        location1.put("areaCnCode", "330102");
        location1.put("areaType", "COUNTY");
        locationList.add(location1);
        location1 = new HashMap<String,Object>();
        location1.put("parentId", 383L);
        location1.put("areaName", "上城区");
        location1.put("areaCnCode", "330102");
        location1.put("areaType", "COUNTY");
        locationList.add(location1);
        location1 = new HashMap<String,Object>();
        location1.put("id", 295L);
        location1.put("areaName", "威海");
        location1.put("areaCnCode", "371000");
        location1.put("areaType", "CITY");
        locationList.add(location1);
        location1 = new HashMap<String,Object>();
        location1.put("id", 2960L);
        location1.put("parentId", 347L);
        location1.put("areaName", "墨脱县");
        location1.put("areaCnCode", "542624");
        location1.put("areaType", "COUNTY");
        locationList.add(location1);
        location1 = new HashMap<String,Object>();
        location1.put("id", 2955L);
        location1.put("parentId", 346L);
        location1.put("areaName", "洛隆县");
        location1.put("areaCnCode", "542132");
        locationList.add(location1);
        location1 = new HashMap<String,Object>();
        location1.put("id", 295L);
        location1.put("parentId", 34L);
        location1.put("areaName", "洛");
        location1.put("areaCnCode", "542");
        location1.put("areaType", "OTHER");
        locationList.add(location1);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", locationList);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
        
        locationApiServiceImpl.getAllLocation();
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.toJson(anyObject());
    }
}