package com.awifi.np.biz.api.client.devicebus.util;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.devicebus.service.DeviceBusService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午1:17:24
 * 创建作者：亢燕翔
 * 文件名称：DeviceBusClientUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class})
public class DeviceBusClientTest {

    /**被测试类*/
    @InjectMocks
    private DeviceBusClient deviceBusClient;

    /**设备业务层*/
    @Mock(name = "deviceBusService")
    private DeviceBusService deviceBusService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(deviceBusService);
    }
    
    /**
     * 设备激活
     * @author 亢燕翔  
     * @throws Exception 异常 异常
     * @date 2017年3月22日 下午1:19:32
     */
    @Test
    public void testSetFatAPRegister() throws Exception{
        deviceBusClient.setFatAPRegister("xxx");
    }

    /**
     * 设置ssid
     * @author 亢燕翔  
     * @throws Exception 异常 异常
     * @date 2017年3月22日 下午1:20:20
     */
    @Test
    public void testSetFatAPSSID() throws Exception{
        deviceBusClient.setFatAPSSID("xxx", "xxx");
    }
    
    /**
     * 设备放通
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:21:06
     */
    @Test
    public void testSetFatAPEscape() throws Exception{
        deviceBusClient.setFatAPEscape("xxx");
    }
    
    /**
     * Chinanet开关接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:21:44
     */
    @Test
    public void testSetFatAPSSIDSwitch() throws Exception{
        deviceBusClient.setFatAPSSIDSwitch("xxx");
    }
    
    /**
     * aWiFi开关接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:22:18
     */
    @Test
    public void testSetFatAPAWiFiSwitch() throws Exception{
        deviceBusClient.setFatAPAWiFiSwitch("xxx");
    }
    
    /**
     * LAN口认证开关接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:22:50
     */
    @Test
    public void testSetFatAPLANSwitch() throws Exception{
        deviceBusClient.setFatAPLANSwitch("xxx");
    }
    
    /**
     * 闲时下线接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:23:30
     */
    @Test
    public void testSetFatAPClientTimeout() throws Exception{
        deviceBusClient.setFatAPClientTimeout("xxx");
    }
    
    
}
