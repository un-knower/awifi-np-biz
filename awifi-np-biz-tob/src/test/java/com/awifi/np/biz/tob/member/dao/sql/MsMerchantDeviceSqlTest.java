/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 27, 2017 10:14:11 AM
* 创建作者：季振宇
* 文件名称：MsMerchantDeviceSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.tob.member.dao.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MsMerchantDeviceSqlTest {
    
    /**被测试类*/
    @InjectMocks
    private MsMerchantDeviceSql msMerchantDeviceSql;
    
    /**
     * 初始化
     * @author 季振宇  
     * @date Jun 21, 2017 3:01:29 PM
     */
    @Before
    public void before () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateSwitchStatusAll() {
        Map<String, Object> map = new HashMap<>();
        List<String> deviceList = new ArrayList<>();
        deviceList.add("12345678");
        map.put("deviceList", deviceList);
        map.put("status", new Byte("1"));
        
        String result = msMerchantDeviceSql.updateSwitchStatusAll(map);
        String expected = "update wii_device_extend set status=1 where devId in ( '12345678')";
        
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testUpdateSwitchStatusAll1() {
        Map<String, Object> map = new HashMap<>();
        List<String> deviceList = new ArrayList<>();
        deviceList.add("12345678");
        deviceList.add("23456789");
        map.put("deviceList", deviceList);
        map.put("status", new Byte("1"));
        
        String result = msMerchantDeviceSql.updateSwitchStatusAll(map);
        String expected = "update wii_device_extend set status=1 where devId in ( '12345678','23456789')";
        
        Assert.assertEquals(expected, result);
    }
}
