package com.awifi.np.biz.devsrv.device.controller;

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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.device.service.DeviceService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 下午2:17:09
 * 创建作者：亢燕翔
 * 文件名称：DeviceControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,PermissionUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class DeviceControllerTest {
    
    /**被测试类*/
    @InjectMocks
    private DeviceController deviceController;
    
    /**设备服务*/
    @Mock(name = "deviceService")
    private DeviceService deviceService;
    
    /**模板服务*/
    @Mock(name = "templateService")
    private TemplateService templateService;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /**
     * 测试获取view
     * @author 亢燕翔  
     * @date 2017年2月14日 下午2:19:44
     */
    @Test
    public void testView(){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", 1);
        dataMap.put("userName", "superadmin");
        dataMap.put("roleIds", "1");
        dataMap.put("provinceId", 31);
        dataMap.put("cityId", 383);
        dataMap.put("areaId", 3232);
        dataMap.put("merchantId", 1110);
        dataMap.put("suitCode", "S001");
        resultMap.put("code", 0);
        resultMap.put("data", dataMap);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
        request.setAttribute("userInfo",resultMap);
        Map<String, Object> map = deviceController.view(request, "001", "access_token");
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试设备详情
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月14日 下午2:21:25
     */
    @Test
    public void testGetByDevId() throws Exception{
        String accessToken = "XXX";
        String deviceid = "XXX";
        Map<String, Object> resultMap = deviceController.getByDevId(accessToken, deviceid);
        Assert.assertNotNull(resultMap);
    }
    
    /**
     * 测试修改ssid
     * @throws Exception 
     * @author 亢燕翔  
     * @date 2017年2月14日 下午2:28:13
     */
    @Test
    public void testBatchUpdateSSID() throws Exception{
        String ssid = "ssid";
        String accessToken = "XXX";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", 1);
        dataMap.put("userName", "superadmin");
        dataMap.put("roleIds", "1");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        list.add(dataMap);
        Map<String, Object> resultMap = deviceController.batchUpdateSSID(ssid, accessToken, list);
        Assert.assertNotNull(resultMap);
    }
    
    /**
     * 测试设备放通
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 上午9:49:08
     */
    @Test
    public void testBatchEscape() throws Exception{
        String accessToken = "XXX";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "sssssss");
        dataMap.put("entityType", 13);
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        deviceController.batchEscape(accessToken, anyObject());
    }
    
    /**
     * 测试Chinanet开关接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 上午9:54:12
     */
    @Test
    public void testBatchChinanetSsidSwitch() throws Exception{
        String accessToken = "XXX";
        deviceController.batchChinanetSsidSwitch(accessToken,anyObject());
    }
    
    /**
     * 测试aWiFi开关接口
     * @throws Exception 异常
     * @author 亢燕翔   
     * @date 2017年2月24日 上午9:55:06
     */
    @Test
    public void testBatchAwifiSsidSwitch() throws Exception{
        String accessToken = "XXX";
        deviceController.batchAwifiSsidSwitch(accessToken, anyObject());
    }
    
    /**
     * 测试LAN口认证开关接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 上午9:56:04
     */
    @Test
    public void testBatchLanSwitch() throws Exception{
        String accessToken = "XXX";
        deviceController.batchLanSwitch(accessToken, anyObject());
    }
    
    /**
     * 测试闲时下线接口
     * @throws Exception 异常 
     * @author 亢燕翔  
     * @date 2017年2月24日 上午9:57:01
     */
    @Test
    public void testBatchClientTimeout() throws Exception{
        String accessToken = "XXX";
        deviceController.batchClientTimeout(accessToken, anyObject());
    }
    
    /**
     * 测试设备解绑
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 上午9:57:59
     */
    @Test
    public void testUnbind() throws Exception{
        String accessToken = "XXX";
        deviceController.unbind(accessToken, anyObject());
    }
    /**
     * 测试商户设备解绑
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 上午9:57:59
     */
    @Test
    public void testunbindMerchant() throws Exception{
        String accessToken = "XXX";
        deviceController.unbindMerchant(accessToken, anyObject());
    }
}
