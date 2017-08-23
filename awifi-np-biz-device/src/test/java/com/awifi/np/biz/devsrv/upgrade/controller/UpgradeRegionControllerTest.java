/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年7月18日 下午3:09:22
 * 创建作者：尤小平
 * 文件名称：UpgradeRegionControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.devsrv.upgrade.controller;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.devsrv.upgrade.service.impl.DeviceUpgradeRegionServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SysConfigUtil.class, MessageUtil.class, IOUtil.class })
public class UpgradeRegionControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private DeviceUpgradeRegionController controller;

    /**
     * DeviceUpgradeRegionServiceImpl
     */
    private DeviceUpgradeRegionServiceImpl service;

    /**
     * request
     */
    private MockHttpServletRequest request;

    /**
     * accessToken
     */
    private String accessToken = "token";

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午7:57:19
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = Mockito.mock(DeviceUpgradeRegionServiceImpl.class);
        controller.setDeviceUpgradeRegionService(service);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(IOUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        request = new MockHttpServletRequest();
    }

    /**
     * destroy.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午7:57:32
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试定制终端-获取区域默认升级包列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午7:58:25
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetListByParam() throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pageSize", "15");
        paramMap.put("id", "1");
        paramMap.put("corporationId", "2");
        paramMap.put("modelId", "3");
        paramMap.put("versions", "12.34.5");
        paramMap.put("hdVersions", "hdversion");
        paramMap.put("type", "type");
        paramMap.put("path", "path");
        paramMap.put("province", "4");
        paramMap.put("city", "5");
        paramMap.put("county", "6");
        paramMap.put("state", "1");

        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(2L);
        request.setAttribute("sessionUser", sessionUser);

        PowerMockito.doNothing().when(service).getListByParam(any(DeviceUpgradeRegion.class), any(Page.class));

        Map<String, Object> actual = controller.getListByParam(accessToken, JsonUtil.toJson(paramMap), request);

        Assert.assertEquals("0", actual.get("code").toString());
        PowerMockito.verifyStatic();
        service.getListByParam(any(DeviceUpgradeRegion.class), any(Page.class));
    }

    /**
     * 测试定制终端-新增终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午7:58:42
     */
    @Test
    public void testAdd() throws Exception {
        controller = new DeviceUpgradeRegionController() {
            @Override
            protected void uploadRegion(MultipartFile regionZip, String path) throws IOException {
                return;
            }
        };
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.setParameter("corporationId", "1");
        request.setParameter("modelId", "2");
        request.setParameter("type", "3");
        request.setParameter("versions", "versions");
        request.setParameter("hdVersions", "hdVersions");
        request.setParameter("province", "4");
        request.setParameter("city", "5");
        request.setParameter("county", "6");
        request.setParameter("userName", "userName");
        MockMultipartFile multipartFile = new MockMultipartFile("regionZip", "yxp", "application/x-www-form-urlencoded",
                "ddd".getBytes());
        request.addFile(multipartFile);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(2L);
        sessionUser.setUserName("userName");
        request.setAttribute("sessionUser", sessionUser);

        service = Mockito.mock(DeviceUpgradeRegionServiceImpl.class);
        controller.setDeviceUpgradeRegionService(service);

        PowerMockito.when(SysConfigUtil.class, "getParamValue", any(String.class)).thenReturn("e://tmp");
        PowerMockito.doNothing().when(IOUtil.class, "mkDirsByFilePath", any(String.class));

        Map<String, Object> actual = controller.add(accessToken, request);

        Assert.assertEquals("0", actual.get("code").toString());
    }

    /**
     * 测试定制终端-新增终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午7:58:54
     */
    @Test(expected = ValidException.class)
    public void testAddForFileNull() throws Exception {
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(2L);
        sessionUser.setUserName("userName");
        request.setAttribute("sessionUser", sessionUser);

        Map<String, Object> actual = controller.add(accessToken, request);

        Assert.assertEquals("E2000052", actual.get("code").toString());
    }

    /**
     * 测试定制终端-查看升级情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午7:59:14
     */
    @Test
    public void testGetById() throws Exception {
        String id = "2";
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setIssueNum(100L);
        region.setSuccessNum(90L);
        region.setSuccessRate("90%");
        region.setAreaName("浙江杭州拱墅区");
        PowerMockito.when(service.getById(any(Long.class))).thenReturn(region);

        Map<String, Object> actual = controller.getById(accessToken, id);

        Assert.assertNotNull(actual.get("data"));
        PowerMockito.verifyStatic();
        service.getById(any(Long.class));
    }

    /**
     * 测试定制终端-删除终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午7:59:31
     */
    @Test
    public void testDelete() throws Exception {
        String id = "2";
        PowerMockito.doNothing().when(service).delete(any(Long.class));

        Map<String, Object> actual = controller.delete(accessToken, id);

        Assert.assertEquals("0", actual.get("code").toString());
        PowerMockito.verifyStatic();
        service.delete(any(Long.class));
    }

    /**
     * 测试定制终端-判断是否已经有启用的升级包.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午7:59:45
     */
    @Test
    public void testExistUpgrade() throws Exception {
        String id = "2";
        PowerMockito.when(service.existStartUpgrade(any(Long.class))).thenReturn(true);

        Map<String, Object> actual = controller.existUpgrade(accessToken, id);

        Assert.assertEquals(true, (boolean) actual.get("data"));
        PowerMockito.verifyStatic();
        service.existStartUpgrade(any(Long.class));
    }

    /**
     * 测试定制终端-启用升级包.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午8:00:08
     */
    @Test
    public void testStart() throws Exception {
        String id = "2";
        PowerMockito.doNothing().when(service).start(any(Long.class));

        Map<String, Object> actual = controller.start(accessToken, id);

        Assert.assertEquals("0", actual.get("code").toString());
        PowerMockito.verifyStatic();
        service.start(any(Long.class));
    }
}
