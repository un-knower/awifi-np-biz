package com.awifi.np.biz.merdevsrv.hotarea.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Workbook;

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

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;
import com.awifi.np.biz.api.client.dbcenter.device.hotarea.util.HotareaClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.merdevsrv.hotarea.service.HotareaService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 下午2:44:06
 * 创建作者：亢燕翔
 * 文件名称：HotareaController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, JsonUtil.class, RedisUtil.class, SysConfigUtil.class, SessionUtil.class, 
        HotareaClient.class, PermissionUtil.class, Workbook.class, ExcelUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class HotareaControllerTest {

    
    /**被测试类*/
    @InjectMocks
    private HotareaController hotareaController;
    
    /**热点服务*/
    @Mock(name = "hotareaService")
    private HotareaService hotareaService;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**响应求*/
    private MockHttpServletResponse response;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(HotareaClient.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(Workbook.class);
        PowerMockito.mockStatic(ExcelUtil.class);
    }
    
    /**
     * 测试热点列表
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月14日 下午4:54:37
     */
    @Test
    public void testGetListByParam() throws Exception{
        String accessToken = "xxx";
        Map<String, Object> map = hotareaController.getListByParam(request, accessToken, anyString());
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试导出
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月14日 下午5:04:26
     */
    @Test(expected=Exception.class)
    public void testExport() throws Exception{
        String accessToken = "xxx";
        String params = "{'merchantId':1,'hotareaName':'hotareaName','devMac':'devMac','status':1,'provinceId':15,'cityId':15,'areaId':15}";
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("10");
        hotareaController.export(request, response, accessToken, params);
    }
    
    /**
     * 测试获取导出文件的数据集合
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:21:39
     */
    @Test
    public void testGetListObj() throws Exception{
        List<Hotarea> hotareaList = new ArrayList<Hotarea>();
        Hotarea h = new Hotarea();
        hotareaList.add(h);
        PowerMockito.when(HotareaClient.getListByParam(anyObject())).thenReturn(hotareaList);
        hotareaController.getListObj(anyObject());
    }
    
    
}
