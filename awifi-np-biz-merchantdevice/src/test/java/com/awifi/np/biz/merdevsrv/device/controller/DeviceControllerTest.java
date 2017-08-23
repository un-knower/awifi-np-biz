package com.awifi.np.biz.merdevsrv.device.controller;

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
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.merdevsrv.device.service.DeviceService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 下午3:23:07
 * 创建作者：亢燕翔
 * 文件名称：DeviceControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class,ValidUtil.class})
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
    
    /**请求*/
    private MockHttpServletResponse response;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
    }
    
    /**
     * 测试view接口
     * @author 亢燕翔  
     * @date 2017年2月14日 下午3:42:16
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
        request.setAttribute("userInfo",resultMap);
        Map<String, Object> map = deviceController.view(request, "001", "access_token");
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试列表接口
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月14日 下午3:42:33
     */
    @Test
    public void testGetListByParam() throws Exception{
        PowerMockito.mockStatic(SessionUtil.class);
        String accessToken = "xxx";
        Map<String, Object> map = deviceController.getListByParam(request, accessToken, anyString());
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试设备绑定（归属）
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 下午3:47:14
     */
    @Test(expected=Exception.class)
    public void testSetOwner() throws Exception{
        String accessToken = "xxx";
        Map<String,Object> fa=new HashMap<String,Object>();
        List<Map<String, Object>> list=new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectId", 10);
        dataMap.put("merchantId", 10);
        dataMap.put("entityType", "10");
        dataMap.put("isSF", 10);
        dataMap.put("provinceId", 10);
        list.add(dataMap);
        System.out.println(JsonUtil.toJson(dataMap));
        fa.put("list", list);
        deviceController.setOwner(request, response, accessToken, JsonUtil.toJson(fa));
    }
    
    /**
     * 测试设备过户
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月14日 下午4:07:44
     */
    @Test
    public void testTransfer() throws Exception{
        String accessToken = "xxx";
        Map<String, Object> map = deviceController.transfer(request, accessToken, anyObject());
        Assert.assertNotNull(map);
    }
    /**
     * 测试设备放通
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月12日 下午3:40:53
     */
    @Test
    public void testBatchEscape() throws Exception{
        List<Map<String, Object>> list=new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("merchantId", 10);
        dataMap.put("onekeySwitch", "on");
        list.add(dataMap);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Map<String, Object> map=deviceController.batchEscape(request, "xxx", list);
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试chinanet开关
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月12日 下午3:40:53
     */
    @Test
    public void testBatchChinanet() throws Exception{
        List<Map<String, Object>> list=new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("merchantId", 10);
        dataMap.put("chinaNetSwitch", "on");
        list.add(dataMap);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Map<String, Object> map=deviceController.batchChinanet(request, "xxx", list);
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试awifit开关
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月12日 下午3:40:53
     */
    @Test
    public void testBatchAwifi() throws Exception{
        List<Map<String, Object>> list=new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("merchantId", 10);
        dataMap.put("awifiSwitch", "on");
        list.add(dataMap);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Map<String, Object> map=deviceController.batchAwifi(request, "xxx", list);
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试lan口开关
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月12日 下午3:40:53
     */
    @Test
    public void testBatchLan() throws Exception{
        List<Map<String, Object>> list=new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("merchantId", 10);
        dataMap.put("lanSwitch", "on");
        list.add(dataMap);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Map<String, Object> map=deviceController.batchAwifi(request, "xxx", list);
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试闲时下线时间
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月12日 下午3:40:53
     */
    @Test
    public void testBatchTimeout() throws Exception{
        List<Map<String, Object>> list=new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("merchantId", 10);
        dataMap.put("offlineTime", "on");
        list.add(dataMap);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Map<String, Object> map=deviceController.batchTimeout(request, "xxx", list);
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试刷新开关
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月12日 下午3:40:53
     */
    @Test
    public void testGetSwitchStatus() throws Exception{
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("merchantId", 10L);
        dataMap.put("switchType", "a");
        PowerMockito.when(JsonUtil.fromJson("", Map.class)).thenReturn(dataMap);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        PowerMockito.when(deviceService.getSwitchStatus(anyObject(),anyObject())).thenReturn(dataMap);
        Map<String, Object> map=deviceController.getSwitchStatus(request, "access_token","10", "");
        Assert.assertNotNull(map);
        Assert.assertEquals(dataMap, map.get("data"));
    }
}
