package com.awifi.np.biz.toe.admin.device.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.DeviceOwner;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.device.dao.SfTerminalConfigDao;
import com.awifi.np.biz.toe.admin.device.model.SfTerminalConfig;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月27日 上午9:22:38
 * 创建作者：亢燕翔
 * 文件名称：SfTerminalConfigServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,DeviceClient.class,DeviceBusClient.class})
@PowerMockIgnore({"javax.management.*"})
public class SfTerminalConfigServiceTest {

    /**被测试类*/
    @InjectMocks
    private SfTerminalConfigServiceImpl sfTerminalConfigServiceImpl;
    
    /**省分持久层*/
    @Mock(name = "sfTerminalConfigDao")
    private SfTerminalConfigDao sfTerminalConfigDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(DeviceClient.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(DeviceBusClient.class);
    }
    
    /**
     * 测试省分设备激活异常
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月27日 上午9:26:36
     */
    @Test(expected=Exception.class)
    public void testSetFatAPRegisterException() throws Exception{
        sfTerminalConfigServiceImpl.setFatAPRegister(4L, anyObject());
    }
    
    /**
     * 测试省分设备激活异常
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月27日 上午9:26:36
     */
    @Test(expected=Exception.class)
    public void testSetFatAPRegisterNull() throws Exception{
        List<DeviceOwner> deviceOwners = new ArrayList<DeviceOwner>();
        DeviceOwner device = new DeviceOwner();
        device.setMac("58252C123BC9");
        deviceOwners.add(device);
        SfTerminalConfig sfTerminal = new SfTerminalConfig();
        sfTerminal.setHostname("xxx");
        sfTerminal.setPlatformHostName("xxx");
        sfTerminal.setPortalHostName("xxx");
        sfTerminal.toString();
        when(sfTerminalConfigDao.getByProvinceId(anyObject())).thenReturn(sfTerminal);
        sfTerminalConfigServiceImpl.setFatAPRegister(4L, deviceOwners);
    }
    
    /**
     * 测试省分设备激活
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月27日 上午9:26:36
     */
    @Test
    public void testSetFatAPRegister() throws Exception{
        List<DeviceOwner> deviceOwnerList = new ArrayList<DeviceOwner>();
        DeviceOwner deviceOwner = new DeviceOwner();
        deviceOwner.setMac("58252C123BC9");
        deviceOwnerList.add(deviceOwner);
        SfTerminalConfig sfTerminal = new SfTerminalConfig();
        sfTerminal.setHostname("xxx");
        sfTerminal.setPlatformHostName("xxx");
        sfTerminal.setPortalHostName("xxx");
        when(sfTerminalConfigDao.getByProvinceId(anyObject())).thenReturn(sfTerminal);
        List<Device> deviceList = new ArrayList<Device>();
        Device device = new Device();
        device.setDeviceId("xxxxxxxxxxxx");
        deviceList.add(device);
        when(DeviceClient.getListByParam(anyObject())).thenReturn(deviceList);
        
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
        sfTerminalConfigServiceImpl.setFatAPRegister(4L, deviceOwnerList);
    }
    
}
