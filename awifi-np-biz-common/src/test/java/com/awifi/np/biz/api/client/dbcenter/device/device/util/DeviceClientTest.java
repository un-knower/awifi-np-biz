package com.awifi.np.biz.api.client.dbcenter.device.device.util;

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

import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceApiService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 上午11:05:38
 * 创建作者：亢燕翔
 * 文件名称：DeviceClientTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class})
public class DeviceClientTest {

    /**被测试类*/
    @InjectMocks
    private DeviceClient deviceClient;
    
    /**设备业务层*/
    @Mock(name = "deviceApiService")
    private DeviceApiService deviceApiService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(deviceApiService);
    }
    
    /**
     * 获取虚拟设备总记录数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:10:11
     */
    @Test
    public void testGetCountByParam() throws Exception{
        deviceClient.getCountByParam("xxx");
    }
    
    /**
     * 获取虚拟设备列表
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:11:00
     */
    @Test
    public void testGetListByParam() throws Exception{
        deviceClient.getListByParam("xxx");
    }

    /**
     *  设备绑定（归属）
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:13:10
     */
    @Test
    public void testSetOwner() throws Exception{
        deviceClient.setOwner("xxx");
    }

    /**
     * 设备过户
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:14:21
     */
    @Test
    public void testTransfer() throws Exception{
        deviceClient.transfer("xxx", 50L, "xxx");
    }

    /**
     * 修改ssid
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:14:53
     */
    @Test
    public void testUpdateSSID() throws Exception{
//        deviceClient.updateSSID(anyString(), anyString());
    }

    /**
     * 设备详情
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:15:45
     */
    @Test
    public void testGetByDevId() throws Exception{
        deviceClient.getByDevId(anyString());
    }

    /**
     * 设备解绑
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:18:02
     */
    @Test
    public void testUnbind() throws Exception{
        deviceClient.unbind(anyString());
    }

}
