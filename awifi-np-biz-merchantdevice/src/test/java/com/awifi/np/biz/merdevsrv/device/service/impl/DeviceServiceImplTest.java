package com.awifi.np.biz.merdevsrv.device.service.impl;

import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.device.service.SfTerminalConfigService;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月24日 下午4:16:54
 * 创建作者：亢燕翔
 * 文件名称：DeviceServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, SysConfigUtil.class,EncryUtil.class,DeviceClient.class,ValidUtil.class,PermissionUtil.class,DeviceBusClient.class})
public class DeviceServiceImplTest {

    /** 被测试类 */
    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;
    
    /** 省分平台业务层 */
    @Mock(name = "projectService")
    private SfTerminalConfigService sfTerminalConfigService;
   
    /**
     * toeUser
     */
    @Mock(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /** 初始化 */
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(EncryUtil.class);
        PowerMockito.mockStatic(DeviceClient.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(DeviceBusClient.class);
    }
    
    /**
     * 测试获取虚拟设备count
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午4:18:04
     */
    @Test
    public void testCountGetListByParam() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(0);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(1L);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", "fat");
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        dataMap.put("pageSize", "10");
        Page<Device> page = new Page<Device>();
        page.setPageSize(10);
        deviceServiceImpl.getListByParam(sessionUser, JsonUtil.toJson(dataMap), page);
    }
    
    /**
     * 测试获取虚拟设备列表
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午4:41:48
     */
    @Test
    public void testListGetListByParam() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(100);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(1L);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", "fat");
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        dataMap.put("pageSize", "10");
        Page<Device> page = new Page<Device>();
        page.setPageSize(10);
        deviceServiceImpl.getListByParam(sessionUser, JsonUtil.toJson(dataMap), page);
    }
    
    /**
     * 测试获取虚拟设备列表
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午4:41:48
     */
    @Test
    public void testListGetListByParam_1() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(100);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(1L);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", "fat");
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        dataMap.put("pageSize", "10");
        dataMap.put("userNames", "13000000000");
        dataMap.put("projectId","11");//项目id//（Long）
        
        dataMap.put("projectIds", "10");
        dataMap.put("excludeProjectIds", "11");
        dataMap.put("merchantIds","11");//项目id//（Long）
        
        dataMap.put("devMacs", "ffffffffffff");
        dataMap.put("chinaNetSwitch", "ON");
        dataMap.put("awifiSwitch","ON");//项目id//（Long）
        dataMap.put("onekeySwitch", "ON");
        dataMap.put("lanSwitch", "ON");
        dataMap.put("offlineTime","11");//项目id//（Long）
        
        Map<Long,String> idNameMap = new HashMap<Long,String>();
        idNameMap.put(1L, "13000000000");
        PowerMockito.when(toeUserService.getIdAndUserNameByUsernames("13000000000")).thenReturn(idNameMap);
        Page<Device> page = new Page<Device>();
        page.setPageSize(10);
        List<Device> devices=new ArrayList<Device>();
        Device device=new Device();
        device.setMerchantId(1L);
        device.setSsid("xxxx");
        devices.add(device);
        
        PowerMockito.when(PermissionUtil.dataPermission(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())).thenReturn(dataMap);
        PowerMockito.when(DeviceClient.getListByParam(anyObject())).thenReturn(devices);
        deviceServiceImpl.getListByParam(sessionUser, JsonUtil.toJson(dataMap), page);
    }
    /**
     * 测试获取虚拟设备列表1
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午4:41:48
     */
    @Test
    public void testListGetListByParam1() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(100);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(2L);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", "fat");
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        dataMap.put("pageSize", "10");
        Page<Device> page = new Page<Device>();
        page.setPageSize(10);
        deviceServiceImpl.getListByParam(sessionUser, JsonUtil.toJson(dataMap), page);
    }
    
    /**
     * 测试设备过户
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午4:41:48
     */
    @Test
    public void testTransfer() throws Exception{
        List<String> list = new ArrayList<String>();
        list.add("12");
        list.add("13");
        list.add("14");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("merchantId", 31);
        dataMap.put("projectId", 31);
        dataMap.put("deviceIds", list);
        deviceServiceImpl.transfer(dataMap);
    }

    /**
     *  测试批量设备放通
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月15日 上午9:04:04
     */
    @Test
    public void testBatchEscape() throws Exception{
        Map<String, Object> deviceParamMap = new HashMap<String, Object>(4);
        deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
        deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
        deviceParamMap.put("merchantId", 11L);
        deviceParamMap.put("devSwitch","ON");//取相反
        List<Device> devices=new ArrayList<Device>();
        Device device=new Device();
        device.setMerchantId(1L);
        device.setSsid("xxxx");
        device.setDevMac("xxx");
        device.setDeviceId("111");
        devices.add(device);
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(100);
        PowerMockito.when(DeviceClient.getListByParam(JsonUtil.toJson(anyObject()))).thenReturn(devices);
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        list.add(deviceParamMap);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.doNothing().when(DeviceBusClient.class,"setFatAPEscape",anyObject());
        deviceServiceImpl.batchEscape(list);
        PowerMockito.verifyStatic();
    }
    
    
    /**
     * 测试chinanet开关
     * @throws Exception 异常
     * @author 王冬冬
     * @date 2017年5月15日 上午9:04:04
     */
    @Test
    public void testBatchChinanetSsidSwitch() throws Exception{
        Map<String, Object> deviceParamMap = new HashMap<String, Object>(4);
        deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
        deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
        deviceParamMap.put("merchantId", 11L);
        deviceParamMap.put("devSwitch","ON");//取相反
        
        List<Device> devices=new ArrayList<Device>();
        Device device=new Device();
        device.setMerchantId(1L);
        device.setSsid("xxxx");
        device.setDevMac("xxx");
        device.setDeviceId("111");
        devices.add(device);
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(100);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.doNothing().when(DeviceBusClient.class,"setFatAPSSIDSwitch",anyObject());
        PowerMockito.when(DeviceClient.getListByParam(JsonUtil.toJson(anyObject()))).thenReturn(devices);
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        list.add(deviceParamMap);
        deviceServiceImpl.batchChinanetSsidSwitch(list);
        PowerMockito.verifyStatic();
    }
    
    /**
     * 测试awifi开关
     * @throws Exception 异常
     * @author 王冬冬
     * @date 2017年5月15日 上午9:04:04
     */
    @Test
    public void testBatchAwifiSsidSwitch() throws Exception{
        Map<String, Object> deviceParamMap = new HashMap<String, Object>(4);
        deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
        deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
        deviceParamMap.put("merchantId", 11L);
        deviceParamMap.put("devSwitch","ON");//取相反
        List<Device> devices=new ArrayList<Device>();
        Device device=new Device();
        device.setMerchantId(1L);
        device.setSsid("xxxx");
        device.setDevMac("xxx");
        device.setDeviceId("111");
        devices.add(device);
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(100);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.doNothing().when(DeviceBusClient.class,"setFatAPAWiFiSwitch",anyObject());
        PowerMockito.when(DeviceClient.getListByParam(JsonUtil.toJson(anyObject()))).thenReturn(devices);
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        list.add(deviceParamMap);
        deviceServiceImpl.batchAwifiSsidSwitch(list);
        PowerMockito.verifyStatic();
    }
    
    /**
     * 测试lan口开关
     * @throws Exception 异常
     * @author 王冬冬
     * @date 2017年5月15日 上午9:04:04
     */
    @Test
    public void testBatchLanSwitch() throws Exception{
        Map<String, Object> deviceParamMap = new HashMap<String, Object>(4);
        deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
        deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
        deviceParamMap.put("merchantId",11L);//设备类型            
        deviceParamMap.put("devSwitch","ON");//设备开关          
        
        List<Device> devices=new ArrayList<Device>();
        Device device=new Device();
        device.setMerchantId(1L);
        device.setSsid("xxxx");
        device.setDevMac("xxx");
        device.setDeviceId("111");
        devices.add(device);
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(100);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.doNothing().when(DeviceBusClient.class,"setFatAPLANSwitch",anyObject());
        PowerMockito.when(DeviceClient.getListByParam(JsonUtil.toJson(anyObject()))).thenReturn(devices);
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        list.add(deviceParamMap);
        deviceServiceImpl.batchLanSwitch(list);
        PowerMockito.verifyStatic();
    }
    
    /**
     * 测试闲时下线开关
     * @throws Exception 异常
     * @author 王冬冬
     * @date 2017年5月15日 上午9:04:04
     */
    @Test
    public void testBatchClientTimeout() throws Exception{
        Map<String, Object> deviceParamMap = new HashMap<String, Object>(4);
        deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
        deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
        deviceParamMap.put("merchantId",11L);//设备类型            
        deviceParamMap.put("offlineTime",12);//闲时下线时间  
        
        List<Device> devices=new ArrayList<Device>();
        Device device=new Device();
        device.setMerchantId(1L);
        device.setSsid("xxxx");
        device.setDevMac("xxx");
        device.setDeviceId("111");
        devices.add(device);
        PowerMockito.when(DeviceClient.getCountByParam(anyObject())).thenReturn(100);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.doNothing().when(DeviceBusClient.class,"setFatAPClientTimeout",anyObject());
        PowerMockito.when(DeviceClient.getListByParam(JsonUtil.toJson(anyObject()))).thenReturn(devices);
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        list.add(deviceParamMap);
        deviceServiceImpl.batchClientTimeout(list);
        PowerMockito.verifyStatic();
    }
    
    /**
     * 测试开关刷新
     * @throws Exception 异常
     * @author 王冬冬
     * @date 2017年5月15日 上午9:04:04
     */
    @Test
    public void testGetSwitchStatus() throws Exception{
        Map<String,Object>  map=new HashMap<String,Object>();
        PowerMockito.when(DeviceClient.statSwitchByParam(11L,"a")).thenReturn(map);
        Map<String,Object>  result=deviceServiceImpl.getSwitchStatus(11L,"a");
        Assert.assertSame(map, result);
        PowerMockito.verifyStatic();
    }
}
