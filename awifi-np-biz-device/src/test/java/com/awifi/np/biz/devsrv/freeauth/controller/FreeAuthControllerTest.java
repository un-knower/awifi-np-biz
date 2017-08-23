/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 下午4:12:11
* 创建作者：范立松
* 文件名称：FreeAuthControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.freeauth.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.freeauth.service.FreeAuthService;

@RunWith(PowerMockRunner.class)
@SuppressWarnings("unchecked")
@PrepareForTest({ JsonUtil.class, SysConfigUtil.class, CastUtil.class, ValidUtil.class, MessageUtil.class })
public class FreeAuthControllerTest {

    /**被测试类*/
    @InjectMocks
    private FreeAuthController freeAuthController;

    /** MAC免认证业务层 */
    @Mock(name = "freeAuthService")
    private FreeAuthService freeAuthService;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }

    /**
     * 测试添加设备区域信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testAddDeviceArea() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("name", "name");
        bodyParam.put("macAuthHour", "2");
        bodyParam.put("refreshKey", 0);
        bodyParam.put("remarks", "remarks");
        Map<String, Object> resultMap = freeAuthController.addDeviceArea("accessToken", bodyParam);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试修改设备区域信息
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testUpdateDeviceArea() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("name", "name");
        bodyParam.put("macAuthHour", "2");
        bodyParam.put("refreshKey", 0);
        bodyParam.put("remarks", "remarks");
        Map<String, Object> resultMap = freeAuthController.updateDeviceArea("accessToken", bodyParam, "deviceAreaId");
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetDeviceAreaList() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("name", "name");
        paramsMap.put("deviceName", "deviceName");
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String, Object> resultMap = freeAuthController.getDeviceAreaList("accessToken", "params");
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试根据区域id删除设备区域信息以及设备与区域关联关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveDeviceAreaById() throws Exception {
        String ids = "1,2";
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyObject(), anyObject(), anyObject());
        Map<String, Object> resultMap = freeAuthController.removeDeviceAreaById("accessToken", ids);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试批量添加设备与区域关联信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testAddDeviceAreaRel() throws Exception {
        List<Map<String, Object>> bodyParam = new ArrayList<>();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("deviceAreaId", "deviceAreaId");
        paramsMap.put("deviceId", "deviceId");
        bodyParam.add(paramsMap);
        Map<String, Object> resultMap = freeAuthController.addDeviceAreaRel("accessToken", bodyParam);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试根据设备id删除设备与区域关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveRelByDevId() throws Exception {
        String ids = "1,2";
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyObject(), anyObject(), anyObject());
        Map<String, Object> resultMap = freeAuthController.removeRelByDevId("accessToken", ids);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetRelListByAreaId() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("mac", "mac");
        paramsMap.put("deviceName", "deviceName");
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String, Object> resultMap = freeAuthController.getRelListByAreaId("", "", "deviceAreaId");
        Assert.assertNotNull(resultMap);
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
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String, Object> resultMap = freeAuthController.getChoosableDeviceList("accessToken", "params");
        Assert.assertNotNull(resultMap);
    }

}
