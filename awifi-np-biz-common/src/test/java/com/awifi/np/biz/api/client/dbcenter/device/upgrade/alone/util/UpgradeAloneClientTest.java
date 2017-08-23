/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月24日 下午4:58:28
* 创建作者：余红伟
* 文件名称：UpgradeAloneClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.util;
import static org.mockito.Matchers.any;

import java.util.HashMap;
import java.util.Map;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.service.UpgradeAloneService;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.service.impl.UpgradeAloneServiceImpl;
import com.awifi.np.biz.common.util.BeanUtil;
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtil.class})
public class UpgradeAloneClientTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private UpgradeAloneClient client;
    
    private static UpgradeAloneService upgradeAloneService;
    /**
     * 初始化
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月24日 下午5:02:17
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        upgradeAloneService = Mockito.mock(UpgradeAloneServiceImpl.class);
        PowerMockito.when(BeanUtil.getBean(any(String.class))).thenReturn(upgradeAloneService);
    }

    @After
    public void tearDown() throws Exception {
        client = null;
        upgradeAloneService = null;
    }

    @Test
    public void testGetUpgradeAloneService() {
//        fail("Not yet implemented");
    
    }

    
    @Test
    public void testGetPersonalizedUpgradeTaskList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(upgradeAloneService.getPersonalizedUpgradeTaskList(params)).thenReturn(null);
        client.getPersonalizedUpgradeTaskList(params);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.getPersonalizedUpgradeTaskList(params);
    }

    @Test
    public void testGetPersonalizedUpgradeTaskCount() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(upgradeAloneService.getPersonalizedUpgradeTaskCount(params)).thenReturn(null);
        client.getPersonalizedUpgradeTaskCount(params);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.getPersonalizedUpgradeTaskCount(params);
    }

    @Test
    public void testAddPersonalizedUpgradeTask() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.doNothing().when(upgradeAloneService).addPersonalizedUpgradeTask(params);
        client.addPersonalizedUpgradeTask(params);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.addPersonalizedUpgradeTask(params);
    }

    @Test
    public void testDeletePersonalizedUpgradeTask() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        PowerMockito.doNothing().when(upgradeAloneService).deletePersonalizedUpgradeTask(id);
        client.deletePersonalizedUpgradePatch(id);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.deletePersonalizedUpgradePatch(id);
    }

    @Test
    public void testGetPersonalizUpgradePatchById() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        PowerMockito.when(upgradeAloneService.getPersonalizUpgradePatchById(id)).thenReturn(null);
        client.getPersonalizUpgradePatchById(id);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.getPersonalizUpgradePatchById(id);
    }

    @Test
    public void testGetDeviceByMac() {
//        fail("Not yet implemented");
    }

    @Test
    public void testGetPersonalizUpgradePatchList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(upgradeAloneService.getPersonalizUpgradePatchList(params)).thenReturn(null);
        client.getPersonalizUpgradePatchList(params);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.getPersonalizUpgradePatchList(params);
    }

    @Test
    public void testGetPersonalizUpgradePatchCount() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(upgradeAloneService.getPersonalizUpgradePatchCount(params)).thenReturn(null);
        client.getPersonalizUpgradePatchCount(params);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.getPersonalizUpgradePatchCount(params);
    }

    @Test
    public void testDeletePersonalizedUpgradePatch() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        PowerMockito.doNothing().when(upgradeAloneService).deletePersonalizedUpgradePatch(id);
        client.deletePersonalizedUpgradePatch(id);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.deletePersonalizedUpgradePatch(id);
    }

    @Test
    public void testGetPersonalizedUpgradeTaskById() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        PowerMockito.when(upgradeAloneService.getPersonalizedUpgradeTaskById(id)).thenReturn(null);
        client.getPersonalizedUpgradeTaskById(id);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.getPersonalizedUpgradeTaskById(id);
    }

    @Test
    public void testAddPersonalizedUpgradePatch() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.doNothing().when(upgradeAloneService).addPersonalizedUpgradePatch(params);
        client.addPersonalizedUpgradePatch(params);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.addPersonalizedUpgradePatch(params);
    }

    @Test
    public void testGetUpgradeDeviceList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(upgradeAloneService.getUpgradeDeviceList(params)).thenReturn(null);
        client.getUpgradeDeviceList(params);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.getUpgradeDeviceList(params);
    }

    @Test
    public void testQueryDeviceCountByParam() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        PowerMockito.when(upgradeAloneService.queryDeviceCountByParam(params)).thenReturn(null);
        client.queryDeviceCountByParam(params);
        
        PowerMockito.verifyStatic();
        upgradeAloneService.queryDeviceCountByParam(params);
    }

}
