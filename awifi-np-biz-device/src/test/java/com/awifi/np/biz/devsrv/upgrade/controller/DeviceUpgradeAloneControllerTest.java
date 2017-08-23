/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月19日 下午4:18:07
* 创建作者：余红伟
* 文件名称：DeviceUpgradeAloneControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.upgrade.service.impl.DeviceUpgradeAloneServiceImpl;
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    SysConfigUtil.class, MessageUtil.class, SessionUtil.class
})
public class DeviceUpgradeAloneControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private DeviceUpgradeAloneController controller;
    
    private DeviceUpgradeAloneServiceImpl service;
    
    private MockHttpServletRequest request;
    
    private String accessToken = "token";
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = Mockito.mock(DeviceUpgradeAloneServiceImpl.class);
        controller.setDeviceUpgradeAloneServiceImpl(service);
        PowerMockito.mockStatic(SysConfigUtil.class,MessageUtil.class,SessionUtil.class);
        request = new MockHttpServletRequest();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetPersonalizedUpgradeTaskList() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> map = new HashMap<>();
        PowerMockito.when(SysConfigUtil.class, "getParamValue",any(String.class)).thenReturn("100");
        Map<String, Object> actual = controller.getPersonalizedUpgradeTaskList(JsonUtil.toJson(map));
       System.out.println(actual);
        Assert.assertEquals("0", actual.get("code").toString());
        PowerMockito.verifyStatic();
        service.getPersonalizedUpgradeTaskList(any(Map.class), any(Page.class));
    }

    @Test
    public void testAddPersonalizedUpgradeTask() throws Exception {
//        fail("Not yet implemented");
        Map<String, Object> map = new HashMap<>();
//        String[] arr = new String[]{"a","b"};
        List<String> mList = new ArrayList<>();
        mList.add("1C1840013160");
        mList.add("5CE3B608863C");
        mList.add("5CE3B6088F8C");
        map.put("macArr", mList);
        map.put("upgradeId", 44L);
        
//        SessionUser sessionUser = Mockito.mock(SessionUser.class);
//        SessionUtil sessionMock = PowerMockito.mock(SessionUtil.class);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(30L);
        sessionUser.setUserName("HoShi");
//        request.setAttribute("sessionUser", sessionUser);
        PowerMockito.when(SessionUtil.class,"getCurSessionUser",any(HttpServletRequest.class)).thenReturn(sessionUser);
//        PowerMockito.when(sessionMock.getCurSessionUser(any(MockHttpServletRequest.class))).thenReturn(sessionUser);
        PowerMockito.when(SysConfigUtil.class,"getParamValue",any(String.class)).thenReturn("100");
        Map<String, Object> actual = controller.addPersonalizedUpgradeTask(map, request);
        Assert.assertEquals("0", actual.get("code").toString());
    }

    @Test
    public void testDeletePersonalizedUpgradeTask() throws Exception {
//        fail("Not yet implemented");
//        Map<String, Object> params = new HashMap<>();
//        params.put("", new ArrayList<Long>(Arrays.asList(10L,50L)));
        String ids = "71,78";
//        params.put("idArr", new ArrayList<String>(Arrays.asList("10L","50L")));
        PowerMockito.doNothing().when(service).deletePersonalizedUpgradePatch(any(Long.class));
        Map<String, Object> actual = controller.deletePersonalizedUpgradeTask(ids);
        Assert.assertEquals("0", actual.get("code").toString());
    }

    @Test
    public void testGetUpgradeDeviceList() throws Exception {
//        fail("Not yet implemented");
        String params = "{'corporationId':10,'modelId':101,'mac':'1C1840013160','merchantId':100}";
        Page<Map<String, Object>> page = new Page<>();
        PowerMockito.doNothing().when(service).getUpgradeDeviceList(any(Map.class), any(Page.class));
        PowerMockito.when(SysConfigUtil.class,"getParamValue",any(String.class)).thenReturn("100");
        Map<String, Object> actual = controller.getUpgradeDeviceList(params);
        System.out.println(actual);
        Assert.assertEquals("0", actual.get("code"));
    }

    @Test
    public void testAddPersonalizedUpgradepatch() throws Exception {
//        fail("Not yet implemented");
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
//        request.setParameter("aloneFile", );
        MockMultipartFile multipartFile = new MockMultipartFile("aloneFile", ".zip", "application/x-www-form-urlencoded", "alone".getBytes());
        request.addFile(multipartFile);
        request.setParameter("patch_name", "升级包111");
        request.setParameter("patch_type", "firmware");
        request.setParameter("corporationId", "100");
        request.setParameter("modelId", "30");
        request.setParameter("versions", "V1.11.11");
        request.setParameter("hdVersions", "dsddd");
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(30L);
        sessionUser.setUserName("HoShi");
        PowerMockito.when(SessionUtil.class,"getCurSessionUser",any(HttpServletRequest.class)).thenReturn(sessionUser);
        PowerMockito.when(SysConfigUtil.class,"getParamValue",any(String.class)).thenReturn("100");
        Map<String, Object> actual = controller.addPersonalizedUpgradepatch(request);
        Assert.assertEquals("0", actual.get("code").toString());
    }

    @Test
    public void testDeletePersonalizedUpgradePatch() throws Exception {
//        fail("Not yet implemented");
        String id = "10";
        Map<String, Object> actual = controller.deletePersonalizedUpgradePatch(id);
        Assert.assertEquals("0", actual.get("code").toString());
    }

    @Test
    public void testGetPersonalizedUpgradePatchList() throws Exception {
//        fail("Not yet implemented");
        String params = "{'corporationId':100,'modelId':10}";
        PowerMockito.when(SysConfigUtil.class,"getParamValue",any(String.class)).thenReturn("100");
        Map<String, Object> actual = controller.getPersonalizedUpgradePatchList(params);
        Assert.assertEquals("0", actual.get("code").toString());
    }

    @Test
    public void testGetPersonalizUpgradePatchById() throws Exception {
//        fail("Not yet implemented");
        String id = "99";
        Map<String, Object> actual = controller.getPersonalizUpgradePatchById(id);
        Assert.assertEquals("0", actual.get("code").toString());
    }

}
