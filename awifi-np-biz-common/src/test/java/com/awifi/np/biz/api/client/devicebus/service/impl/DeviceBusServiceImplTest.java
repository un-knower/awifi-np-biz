package com.awifi.np.biz.api.client.devicebus.service.impl;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月20日 上午10:50:51
 * 创建作者：亢燕翔
 * 文件名称：DeviceBusServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class, SysConfigUtil.class, JsonUtil.class, FormatUtil.class})
public class DeviceBusServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private DeviceBusServiceImpl deviceBusServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);   
        PowerMockito.mockStatic(FormatUtil.class);   
    }
    
    /**
     * 设备激活
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午11:00:48
     */
    @Test
    public void testSetFatAPRegister() throws Exception{
        deviceBusServiceImpl.setFatAPRegister("XXX");
    }
    
    /**
     * 设置ssid
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午11:00:55
     */
    @Test
    public void testsetFatAPSSID() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("1");
        deviceBusServiceImpl.setFatAPSSID("XXX", "XXX");
    }
    
    /**
     * 设备放通
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午11:01:45
     */
    @Test
    public void testSetFatAPEscape() throws Exception{
        deviceBusServiceImpl.setFatAPEscape("xxx");
    }
    
    /**
     * Chinanet开关接口
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午11:02:31
     */
    @Test
    public void testSetFatAPSSIDSwitch() throws Exception{
        deviceBusServiceImpl.setFatAPSSIDSwitch("xxx");
    }
    
    /**
     * aWiFi开关接口
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午11:03:27
     */
    @Test
    public void testSetFatAPAWiFiSwitch() throws Exception{
        deviceBusServiceImpl.setFatAPAWiFiSwitch("xxx");
    }
    
    /**
     * LAN口认证开关接口
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午11:04:47
     */
    @Test
    public void testSetFatAPLANSwitch() throws Exception{
        deviceBusServiceImpl.setFatAPLANSwitch("xxx");
    }
    
    /**
     * 闲时下线接口
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午11:04:39
     */
    @Test
    public void testSetFatAPClientTimeout() throws Exception{
        deviceBusServiceImpl.setFatAPClientTimeout("xxx");
    }
    
}
