/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 22, 2017 11:14:43 AM
* 创建作者：季振宇
* 文件名称：DeviceInstallerServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.device.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.devicebindsrv.device.dao.DeviceInstallerServiceDao;

@RunWith(PowerMockRunner.class)
public class DeviceInstallerServiceImplTest {
    
    /**被测试类*/
    @InjectMocks
    private DeviceInstallerServiceImpl deviceInstallerServiceImpl;
    
    /** 设备-装维人员 dao */
    @Mock(name = "deviceInstallerServiceDao")
    private DeviceInstallerServiceDao deviceInstallerServiceDao;
    
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
     * 测试新增 设备-装维人员关系表记录
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 11:16:24 AM
     */
    @Test
    public void testAdd() throws Exception{
        PowerMockito.when(deviceInstallerServiceDao.count(Mockito.anyString(), Mockito.anyString())).thenReturn(0L);
        PowerMockito.doNothing().when(deviceInstallerServiceDao).add(Mockito.anyString(), Mockito.anyString());
        
        deviceInstallerServiceImpl.add("123", "456");
    }

    /**
     * 测试新增 设备-装维人员关系表记录
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 11:16:24 AM
     */
    @Test
    public void testAdd1() throws Exception{
        PowerMockito.when(deviceInstallerServiceDao.count(Mockito.anyString(), Mockito.anyString())).thenReturn(-1L);
        PowerMockito.doNothing().when(deviceInstallerServiceDao).add(Mockito.anyString(), Mockito.anyString());
        
        deviceInstallerServiceImpl.add("123", "456");
    }
}
