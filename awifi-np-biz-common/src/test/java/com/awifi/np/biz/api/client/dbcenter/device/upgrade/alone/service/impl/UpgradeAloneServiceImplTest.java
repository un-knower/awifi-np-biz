/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月24日 下午3:58:48
* 创建作者：余红伟
* 文件名称：UpgradeAloneServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,CenterHttpRequest.class})
public class UpgradeAloneServiceImplTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private UpgradeAloneServiceImpl service;
    
    /**
     * init
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月24日 下午4:05:08
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(CenterHttpRequest.class);
    }

    @After
    public void tearDown() throws Exception {
        service = null;
    }

    @Test
    public void testGetPersonalizedUpgradeTaskList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        params.put("mac", "5CE3B6088F8C");
        params.put("upgradeId", "10");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(new HashMap<>());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneQueryTaskListByParam_url");
        
        List<Map<String, Object>> actual = service.getPersonalizedUpgradeTaskList(params);
        Assert.assertEquals(1, actual.size());
    }

    @Test
    public void testGetPersonalizedUpgradeTaskCount() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        params.put("mac", "5CE3B6088F8C");
        params.put("upgradeId", "10");
    
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneQueryTaskCountByParam_url");
        int actual = service.getPersonalizedUpgradeTaskCount(params);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void testGetPersonalizedUpgradeTaskById() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        Map<String, Object> map = new HashMap<>();
        map.put("name", "个性化升级");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", map);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneQueryTaskById_url");
        Map<String, Object> actual = service.getPersonalizedUpgradeTaskById(id);
        Assert.assertEquals("个性化升级", actual.get("name").toString());
    }

    @Test
    public void testAddPersonalizedUpgradeTask() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suc", true);
        map.put("msg", "成功");
        PowerMockito.when(CenterHttpRequest.sendPostRequest(any(String.class), any(String.class))).thenReturn(map);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneAddTask_url");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("upgradeId", 10L);
        params.put("mac", "1C1840013160");
        params.put("userId", 10L);
        params.put("userName", "shiro");
        service.addPersonalizedUpgradeTask(params);
        
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendPostRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    @Test
    public void testDeletePersonalizedUpgradeTask() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        Map<String, Object> map = new HashMap<>();
        map.put("suc", true);
        map.put("msg", "成功");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", map);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneDeleteTask_url");
        service.deletePersonalizedUpgradeTask(id);
        
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendDeleteRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    @Test
    public void testGetPersonalizUpgradePatchById() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        Map<String, Object> map = new HashMap<>();
        map.put("name", "个性化升级包");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", map);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneQueryById_url");
        Map<String, Object> actual = service.getPersonalizUpgradePatchById(id);
        Assert.assertEquals("个性化升级包", actual.get("name").toString());
    }

    @Test
    public void testGetDeviceByMac() {
//        fail("Not yet implemented");
    }

    @Test
    public void testGetPersonalizUpgradePatchList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        params.put("corporationId", "100");
        params.put("modelId", "10");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(new HashMap<>());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneQueryListByParam_url");
        
        List<Map<String, Object>> actual = service.getPersonalizUpgradePatchList(params);
        Assert.assertEquals(1, actual.size());
    }

    @Test
    public void testGetPersonalizUpgradePatchCount() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        params.put("corporationId", "100");
        params.put("modelId", "10");
    
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneQueryCountByParam_url");
        int actual = service.getPersonalizUpgradePatchCount(params);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void testDeletePersonalizedUpgradePatch() throws Exception {
//        fail("Not yet implemented");
        Long id = 10L;
        Map<String, Object> map = new HashMap<>();
        map.put("suc", true);
        map.put("msg", "成功");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", map);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneDelete_url");
        service.deletePersonalizedUpgradePatch(id);;
        
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendDeleteRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    @Test
    public void testAddPersonalizedUpgradePatch() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suc", true);
        map.put("msg", "成功");
        PowerMockito.when(CenterHttpRequest.sendPostRequest(any(String.class), any(String.class))).thenReturn(map);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneAddTask_url");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "升级包111");
        params.put("type", "firmware");
        params.put("corporationId", "100");
        params.put("modelId", "30");
        params.put("versions", "V.1.1");
        params.put("hdVersions", "dsddd");
        params.put("path", "D:/patch");
        params.put("userId", 10L);
        params.put("userName", "管理员");
        service.addPersonalizedUpgradePatch(params);
        
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendPostRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    @Test
    public void testGetUpgradeDeviceList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        params.put("corporationId", "100");
        params.put("modelId", "10");
        params.put("mac", "1C1840013160");
        params.put("merchantId", "109");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(new HashMap<>());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneQueryDeviceListByParam_url");
        
        List<Map<String, Object>> actual = service.getUpgradeDeviceList(params);
        Assert.assertEquals(1, actual.size());
    }

    @Test
    public void testQueryDeviceCountByParam() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> params = new HashMap<>();
        params.put("corporationId", "100");
        params.put("modelId", "10");
        params.put("mac", "1C1840013160");
        params.put("merchantId", "109");
    
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(returnMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("dbc_UpgradeAloneQueryDeviceCountByParam_url");
        int actual = service.queryDeviceCountByParam(params);
        Assert.assertEquals(1, actual);
    }

}
