package com.awifi.np.biz.api.client.dbcenter.device.device.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.net.URLEncoder;
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

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.DeviceOwner;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月20日 上午9:30:47
 * 创建作者：亢燕翔
 * 文件名称：DeviceApiServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,JsonUtil.class,URLEncoder.class,RedisUtil.class})
public class DeviceApiServiceImplTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private DeviceApiServiceImpl deviceApiServiceImpl;
    
    /**
     * 初始化
     * @author 亢燕翔  
     * @date 2017年3月23日 上午10:44:41
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);   
        PowerMockito.mockStatic(URLEncoder.class);   
        PowerMockito.mockStatic(RedisUtil.class);   
    }
    
    /**
     * 测试设备实体
     * @author 亢燕翔  
     * @date 2017年3月22日 下午4:24:47
     */
    @Test
    public void testDevice(){
        Device device = new Device();
        device.toString();
        device.getStatusDsp();
        device.getEntityTypeDsp();
        device.getCorporationDsp();
        device.getMerchantId();
        device.getMerchantName();
        device.getBroadbandAccount();
        device.getDeviceId();
        device.getEntityName();
        device.getDevMac();
        device.getDevIp();
        device.getSsid();
        device.getEntityType();
        device.getCorporation();
        device.getModel();
        device.getModelDsp();
        device.getFwVersion();
        device.getPinCode();
        device.getCvlan();
        device.getPvlan();
        device.getProvinceId();
        device.getProvince();
        device.getCityId();
        device.getCity();
        device.getAreaId();
        device.getArea();
        device.getLocationFullName();
        device.getAddress();
        device.getBindTime();
        device.getOnlineNum();
        device.getRemark();
        device.getStatus();
        DeviceOwner deviceOwner = new DeviceOwner();
        deviceOwner.toString();
        deviceOwner.setDeviceName("xxx");
        deviceOwner.getDeviceName();
        deviceOwner.setDeviceType("xxx");
        deviceOwner.getDeviceType();
        deviceOwner.setMac("xxx");
        deviceOwner.getMac();
        deviceOwner.setMerchantId("xxx");
        deviceOwner.getMerchantId();
        deviceOwner.setProjectId("xxx");
        deviceOwner.getProjectId();
        deviceOwner.setSsid("xxx");
        deviceOwner.getSsid();
    }
    
    /**
     * 测试获取虚拟设备总记录数
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月20日 上午9:39:12
     */
    @Test
    public void testGetCountByParam() throws Exception{
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", 10);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        deviceApiServiceImpl.getCountByParam(anyObject());
    }
    
    /**
     * 测试获取虚拟设备列表
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testGetListByParam() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        deviceApiServiceImpl.getListByParam(anyObject());
    }
    
    /**
     * 测试设备绑定（归属）
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:54:38
     */
    @Test
    public void testSetOwner() throws Exception{
        deviceApiServiceImpl.setOwner(anyObject());
    }
    
    /**
     * 测试设备过户
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午10:02:16
     */
    @Test
    public void testTransfer() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        PowerMockito.when(JsonUtil.toJson(anyString())).thenReturn("abc");
        deviceApiServiceImpl.transfer("deviceId", 100L, "ToE");
    }
    
    /**
     * 测试修改ssid
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月20日 上午10:06:23
     */
    @Test
    public void testUpdateSSID() throws Exception{
        deviceApiServiceImpl.updateSSID("deviceId", "ssid");
    }
    
    /**
     * 测试设备详情
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午10:06:07
     */
    @Test
    public void testGetByDevId() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("merchantId", 15);
        map.put("merchantName", "merchantName");
        map.put("broadbandAccount", "broadbandAccount");
        map.put("deviceId", "deviceId");
        map.put("entityName", "entityName");
        map.put("macAddr", "macAddr");
        map.put("ipAddr", "ipAddr");
        map.put("ssid", "ssid");
        map.put("entityType", "15");
        map.put("bindDate", 15);
        map.put("province", 15);
        map.put("provinceText", "provinceText");
        map.put("city", 15);
        map.put("cityText", "cityText");
        map.put("county", 15);
        map.put("countyText", "countyText");
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        deviceApiServiceImpl.getByDevId("deviceid");
    }
    
}
