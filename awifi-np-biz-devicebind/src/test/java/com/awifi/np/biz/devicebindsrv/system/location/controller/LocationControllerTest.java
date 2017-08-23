/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 22, 2017 5:26:02 PM
* 创建作者：季振宇
* 文件名称：LocationControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.system.location.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.devicebindsrv.system.location.service.LocationService;

@SuppressWarnings("rawtypes")
@RunWith(PowerMockRunner.class)
@PrepareForTest({})
public class LocationControllerTest {
    
    /**被测试类*/
    @InjectMocks
    private LocationController locationController;
    
    /**地区服务层*/
    @Mock(name = "locationService")
    private LocationService locationService;
    
    /**
     * 初始化
     * @author 季振宇  
     * @date Jun 21, 2017 3:01:29 PM
     */
    @Before
    public void before () {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 测试获取所有省
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 6:06:15 PM
     */
    @Test
    public void testGetProvinces() throws Exception {
        PowerMockito.when(locationService.getProvinces()).thenReturn(new ArrayList<>());
        Map result = locationController.getProvinces();
        Assert.assertNotNull(result);
    }

    /**
     * 测试获取所有市
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 6:06:15 PM
     */
    @Test
    public void testGetCities() throws Exception {
        PowerMockito.when(locationService.getCities("parentId")).thenReturn(new ArrayList<>());
        Map result = locationController.getCities("parentId");
        Assert.assertNotNull(result);
    }

    /**
     * 测试获取所有区县
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 6:06:15 PM
     */
    @Test
    public void testGetAreas() throws Exception {
        PowerMockito.when(locationService.getAreas("parentId")).thenReturn(new ArrayList<>());
        Map result = locationController.getAreas("parentId");
        Assert.assertNotNull(result);
    }

}
