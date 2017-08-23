/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 27, 2017 9:23:09 AM
* 创建作者：季振宇
* 文件名称：MsMerchantDeviceServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.tob.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.tob.member.dao.MsMerchantDeviceDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DeviceClient.class})
public class MsMerchantDeviceServiceImplTest {
    
    /**被测试类*/
    @InjectMocks
    private MsMerchantDeviceServiceImpl msMerchantDeviceServiceImpl;
    
    /**
     * 微站商户设备dao
     */
    @Mock(name="msMerchantDeviceDao")
    private MsMerchantDeviceDao msMerchantDeviceDao;
    
    /**
     * 初始化
     * @author 季振宇  
     * @date Jun 21, 2017 3:01:29 PM
     */
    @Before
    public void before () {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(DeviceClient.class);
    }
    
    /**
     * 测试防蹭网开关
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 27, 2017 9:37:19 AM
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateAntiRobberSwitch() throws Exception {
        List<Device> list = new ArrayList<>();
        Device device = new Device();
        device.setDeviceId("123");
        list.add(device);
        
        PowerMockito.when(DeviceClient.getListByParam(Mockito.anyString())).thenReturn(list);
        PowerMockito.doNothing().when(msMerchantDeviceDao).updateSwitchStatusAll(Mockito.anyMap());
        
        msMerchantDeviceServiceImpl.updateAntiRobberSwitch(62L, new Byte("1"));
    }

    /**
     * 测试防蹭网开关
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 27, 2017 9:37:19 AM
     */
    @Test
    public void testUpdateAntiRobberSwitch1() throws Exception {
        msMerchantDeviceServiceImpl.updateAntiRobberSwitch(null, null);
    }
    
    /**
     * 测试防蹭网开关
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 27, 2017 9:37:19 AM
     */
    @Test
    public void testUpdateAntiRobberSwitch2() throws Exception {
        PowerMockito.when(DeviceClient.getListByParam(Mockito.anyString())).thenReturn(new ArrayList<>());
        msMerchantDeviceServiceImpl.updateAntiRobberSwitch(62L, new Byte("1"));
    }

    @Test
    public void testPutAntiRobberCodesToRedis() {
    }

}
