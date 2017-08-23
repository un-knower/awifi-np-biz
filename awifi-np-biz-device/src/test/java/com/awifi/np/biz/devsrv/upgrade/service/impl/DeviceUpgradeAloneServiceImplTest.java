/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月19日 下午2:29:48
* 创建作者：余红伟
* 文件名称：DeviceUpgradeAloneServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.util.UpgradeAloneClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    UpgradeAloneClient.class, JsonUtil.class,MessageUtil.class
})
public class DeviceUpgradeAloneServiceImplTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private DeviceUpgradeAloneServiceImpl service;
    
    /**
     * init
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月19日 下午2:42:02
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(UpgradeAloneClient.class);
//        PowerMockito.mockStatic(ValidUtil.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetPersonalizedUpgradeTaskList() throws Exception {
//        fail("Not yet implemented");
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        Page<Map<String, Object>> page = new Page<>(1, 10);
        PowerMockito.when(UpgradeAloneClient.getPersonalizedUpgradeTaskList(any(Map.class))).thenReturn(list);
        PowerMockito.when(UpgradeAloneClient.getPersonalizedUpgradeTaskCount(any(Map.class))).thenReturn(1);
        service.getPersonalizedUpgradeTaskList(params, page);
    }

    @Test
    public void testGetPersonalizedUpgradeTaskById() throws Exception {
//        fail("Not yet implemented");
        Long id = 3l;
        Map<String, Object> map = new HashMap<>();
        PowerMockito.when(UpgradeAloneClient.getPersonalizedUpgradeTaskById(any(Long.class))).thenReturn(map);
        service.getPersonalizedUpgradeTaskById(id);
        
        PowerMockito.verifyStatic();
        UpgradeAloneClient.getPersonalizedUpgradeTaskById(any(Long.class));
    }

    @Test
    public void testAddPersonalizedUpgradeTask() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.doNothing().when(UpgradeAloneClient.class,"addPersonalizedUpgradeTask",any(Map.class));
        service.addPersonalizedUpgradeTask(params);
    }

    @Test
    public void testDeletePersonalizedUpgradeTask() throws Exception {
//        fail("Not yet implemented");
        Long[] ids = new Long[]{10L,11L};
        PowerMockito.doNothing().when(UpgradeAloneClient.class,"deletePersonalizedUpgradeTask",any(Long.class));
        service.deletePersonalizedUpgradeTask(ids);
    }

    @Test
    public void testGetPersonalizedUpgradeTaskCount() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(UpgradeAloneClient.class,"getPersonalizedUpgradeTaskCount",any(Map.class)).thenReturn(1);
        service.getPersonalizedUpgradeTaskCount(params);
    }

    @Test
    public void testGetDeviceByMac() {
//        fail("Not yet implemented");
    }

    @Test
    public void testGetPersonalizUpgradePatchList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        Page<Map<String, Object>> page = new Page<>(1,15);
        PowerMockito.when(UpgradeAloneClient.class,"getPersonalizUpgradePatchCount",any(Map.class)).thenReturn(10);
        List<Map<String, Object>> list = new ArrayList<>();
        PowerMockito.when(UpgradeAloneClient.class,"getPersonalizUpgradePatchList",any(Map.class)).thenReturn(list);
        service.getPersonalizUpgradePatchList(params, page);
    }

    @Test
    public void testGetPersonalizUpgradePatchCount() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(UpgradeAloneClient.class,"getPersonalizUpgradePatchCount",any(Map.class)).thenReturn(10);
        service.getPersonalizUpgradePatchCount(params);
    }

    @Test
    public void testDeletePersonalizedUpgradePatch() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        PowerMockito.doNothing().when(UpgradeAloneClient.class,"deletePersonalizedUpgradePatch",any(Long.class));
        service.deletePersonalizedUpgradePatch(id);
    }

    @Test
    public void testAddPersonalizedUpgradePatch() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.doNothing().when(UpgradeAloneClient.class,"addPersonalizedUpgradePatch",any(Map.class));
        service.addPersonalizedUpgradePatch(params);
    }

    @Test
    public void testGetPersonalizUpgradePatchById() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        Map<String, Object> result = new HashMap<>();
        PowerMockito.when(UpgradeAloneClient.class,"getPersonalizUpgradePatchById",any(Long.class)).thenReturn(result);
        service.getPersonalizUpgradePatchById(id);
    }

    @Test
    public void testGetUpgradeDeviceList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        Page<Map<String, Object>> page = new Page(1,15);
        List<Map<String, Object>> list = new ArrayList<>();
        PowerMockito.when(UpgradeAloneClient.class,"queryDeviceCountByParam",any(Map.class)).thenReturn(10);
        PowerMockito.when(UpgradeAloneClient.class,"getUpgradeDeviceList",any(Map.class)).thenReturn(list);
        service.getUpgradeDeviceList(params, page);
        
    }

    @Test
    public void testQueryDeviceCountByParam() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(UpgradeAloneClient.class,"queryDeviceCountByParam",any(Map.class)).thenReturn(10);
        service.queryDeviceCountByParam(params);
    }

}
