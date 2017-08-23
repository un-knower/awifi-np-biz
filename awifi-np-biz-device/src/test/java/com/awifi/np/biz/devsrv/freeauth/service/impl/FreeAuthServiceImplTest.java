/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 下午4:12:41
* 创建作者：范立松
* 文件名称：FreeAuthServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.freeauth.service.impl;

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
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.freeauth.util.FreeAuthClient;
import com.awifi.np.biz.common.base.model.Page;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ FreeAuthClient.class, DeviceClient.class })
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FreeAuthServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private FreeAuthServiceImpl freeAuthServiceImpl;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(FreeAuthClient.class);
        PowerMockito.mockStatic(DeviceClient.class);
    }

    /**
     * 测试添加设备区域信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testAddDeviceArea() throws Exception {
        freeAuthServiceImpl.addDeviceArea(anyObject());
    }

    /**
     * 测试修改设备区域信息
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testUpdateDeviceArea() throws Exception {
        freeAuthServiceImpl.updateDeviceArea(anyObject());
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetDeviceAreaList() throws Exception {
        PowerMockito.when(FreeAuthClient.queryAreaCountByParam(anyObject())).thenReturn(1);
        Page page = new Page<>();
        page.setPageSize(10);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("deviceName", "deviceName");
        List<Device> devices = new ArrayList<>();
        Device device = new Device();
        device.setDeviceId("deviceId");
        devices.add(device);
        PowerMockito.when(DeviceClient.getCountByParam(anyString())).thenReturn(1);
        PowerMockito.when(DeviceClient.getListByParam(anyString())).thenReturn(devices);
        freeAuthServiceImpl.getDeviceAreaList(page, paramsMap);
    }

    /**
     * 测试根据区域id删除设备区域信息以及设备与区域关联关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveDeviceAreaById() throws Exception {
        String[] ids = {"1","2"};
        freeAuthServiceImpl.removeDeviceAreaById(ids);
    }

    /**
     * 测试批量添加设备与区域关联信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testAddDeviceAreaRel() throws Exception {
        freeAuthServiceImpl.addDeviceAreaRel(anyObject());
    }

    /**
     * 测试根据设备id删除设备与区域关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveRelByDevId() throws Exception {
        String[] ids = {"1","2"};
        freeAuthServiceImpl.removeRelByDevId(ids);
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetRelListByAreaId() throws Exception {
        PowerMockito.when(FreeAuthClient.queryRelCountByParam(anyObject())).thenReturn(1);
        Page page = new Page<>();
        page.setPageSize(10);
        freeAuthServiceImpl.getRelListByAreaId(page, anyObject());
    }
    
    /**
     * 测试分页查询可选择的设备
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetChoosableDeviceList() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("merchantProjectId", "merchantProjectId");
        paramsMap.put("merchantId", "merchantId");
        paramsMap.put("pageSize", 10);
        Page page = new Page<>();
        page.setPageSize(10);
        PowerMockito.when(FreeAuthClient.queryChooseableDeviceCount(anyObject())).thenReturn(1);
        PowerMockito.when(FreeAuthClient.getChooseableDeviceList(anyObject())).thenReturn(null);
        freeAuthServiceImpl.getChooseableDeviceList(page, paramsMap);
    }

}
