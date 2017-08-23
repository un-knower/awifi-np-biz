package com.awifi.np.biz.devsrv.device.service.impl;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.device.service.SfTerminalConfigService;

/**
 * 版权所有： 爱WiFi无线运营中心 
 * 创建日期:2017年2月24日 下午3:28:01 
 * 创建作者：亢燕翔
 * 文件名称：DeviceServiceImplTest.java 
 * 版本： v1.0 
 * 功能： 
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, SysConfigUtil.class,EncryUtil.class,DeviceClient.class, DeviceBusClient.class })
public class DeviceServiceImplTest {

    /** 被测试类 */
    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    /** 省分平台业务层 */
    @Mock(name = "projectService")
    private SfTerminalConfigService sfTerminalConfigService;

    /** 初始化 */
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(EncryUtil.class);
        PowerMockito.mockStatic(DeviceClient.class);
        PowerMockito.mockStatic(DeviceBusClient.class);
    }

    /**
     * 测试更新ssid错误
     * @throws Exception 异常
     * @author 亢燕翔
     * @date 2017年2月24日 下午3:37:54
     */
    @Test(expected = Exception.class)
    public void testFailBatchUpdateSSID() throws Exception {
        deviceServiceImpl.batchUpdateSSID("xxx", anyObject());
    }

    /**
     * 测试更新ssid正确
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午3:39:49
     */
    @Test
    public void testOkBatchUpdateSSID() throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", 31);
        dataMap.put("devMac", "1C184A0F8DDD");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(dataMap);
        deviceServiceImpl.batchUpdateSSID("aWiFi-xxx", list);
    }
    
    /**
     * 设备详情
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午3:45:02
     */
    @Test
    public void testGetByDevId() throws Exception{
        deviceServiceImpl.getByDevId("xxx");
    }
    
    /**
     * 测试设备放通
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午3:46:40
     */
    @Test
    public void testBatchEscape() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
        
        List<Map<String, Object>> bodyParam = new ArrayList<Map<String,Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", 31);
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        bodyParam.add(dataMap);
        deviceServiceImpl.batchEscape(bodyParam);
    }
    
    /**
     * 测试Chinanet开关接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午3:48:58
     */
    @Test
    public void testBatchChinanetSsidSwitch() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
        
        List<Map<String, Object>> bodyParam = new ArrayList<Map<String,Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", 31);
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        bodyParam.add(dataMap);
        deviceServiceImpl.batchChinanetSsidSwitch(bodyParam);
    }
    
    /**
     * 测试aWiFi开关接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午3:51:12
     */
    @Test
    public void testBatchAwifiSsidSwitch() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
        
        List<Map<String, Object>> bodyParam = new ArrayList<Map<String,Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", 31);
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        bodyParam.add(dataMap);
        deviceServiceImpl.batchAwifiSsidSwitch(bodyParam);
    }
    
    /**
     * 测试LAN口认证开关接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午3:52:00
     */
    @Test
    public void testBatchLanSwitch() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
        
        List<Map<String, Object>> bodyParam = new ArrayList<Map<String,Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", 31);
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        bodyParam.add(dataMap);
        deviceServiceImpl.batchLanSwitch(bodyParam);
    }
    
    /**
     * 测试闲时下线接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午3:56:09
     */
    @Test
    public void testBatchClientTimeout() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
        
        List<Map<String, Object>> bodyParam = new ArrayList<Map<String,Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", 31);
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        dataMap.put("time", 11);
        bodyParam.add(dataMap);
        deviceServiceImpl.batchClientTimeout(bodyParam);
    }
    
    /**
     * 测试设备解绑
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午3:56:32
     */
    @Test
    public void testunbind() throws Exception{
        List<String> list = new ArrayList<String>();
        list.add("list");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceIds", list);
        deviceServiceImpl.unbind(dataMap);
    }
    
}
