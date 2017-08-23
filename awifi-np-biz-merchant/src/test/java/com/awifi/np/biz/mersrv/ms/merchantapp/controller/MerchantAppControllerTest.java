/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月23日 下午3:27:43
* 创建作者：尤小平
* 文件名称：MerchantAppControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.ms.merchantapp.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyInt;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.common.ms.util.MD5;

import com.awifi.np.biz.common.ms.util.MsCommonUtil;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mws.merchant.app.model.StationApp;
import com.awifi.np.biz.mws.merchant.app.service.MerchantAppService;
import org.junit.After;
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
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ StringUtil.class, SysConfigUtil.class, JsonUtil.class, ValidUtil.class, MD5.class,
        MsCommonUtil.class })
public class MerchantAppControllerTest {
    /**
     * MerchantAppController
     */
    @InjectMocks
    private MerchantAppController controller;

    /**
     * MerchantAppService
     */
    @Mock(name = "merchantAppService")
    private MerchantAppService merchantAppService;

    /**
     * MockHttpServletRequest
     */
    @Mock(name = "request")
    private MockHttpServletRequest request;

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月23日 下午4:39:46
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MsCommonUtil.class);
        PowerMockito.mockStatic(StringUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(MD5.class);
        request = new MockHttpServletRequest();
    }

    /**
     * destroy.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月23日 下午4:40:05
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试获取商户下的已发布和已配置应用列表.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月23日 下午4:40:50
     */
    @Test
    public void testGetAppList() throws Exception {
        request.setMethod("GET");
        request.setParameter("merchantId", "1");
        request.setParameter("userId", "10");

        List<StationApp> appList = new ArrayList<StationApp>();
        StationApp app = new StationApp();
        app.setAppSecret("secret");
        app.setAppIcon("icon");
        appList.add(app);
        PowerMockito.when(merchantAppService.getAppListByMerchantId(anyLong())).thenReturn(appList);

        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyString(), anyObject(), anyString());
        PowerMockito.when(MsCommonUtil.class, "getOpenId", anyObject()).thenReturn("10");
        PowerMockito.when(MsCommonUtil.class, "createToken", anyLong(), anyString(), anyString()).thenReturn("token");
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("msw-domain");

        Map<String, Object> actual = controller.getAppList(request);
        Assert.assertEquals(1, ((List<StationApp>) ((JSONObject) actual.get("data")).get("appList")).size());

        PowerMockito.verifyStatic();
        merchantAppService.getAppListByMerchantId(anyLong());
        SysConfigUtil.getParamValue(anyString());
    }

    /**
     * 测试应用查询.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月23日 下午4:41:03
     */
    @Test
    public void testGetAppById() throws Exception {
        int id = 2;
        request.setMethod("GET");
        request.setParameter("merchantId", "1");
        request.setParameter("userId", "10");

        StationApp app = new StationApp();
        app.setAppSecret("secret");
        app.setAppIcon("icon");
        PowerMockito.when(merchantAppService.getAppById(anyInt())).thenReturn(app);
        PowerMockito.when(MsCommonUtil.class, "createToken", anyLong(), anyString(), anyString()).thenReturn("token");
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("msw-domain");

        Map<String, Object> actual = controller.getAppById(id, request);
        Assert.assertEquals("secret",
                ((StationApp) ((Map<String, Object>) actual.get("data")).get("app")).getAppSecret());

        PowerMockito.verifyStatic();
        merchantAppService.getAppById(anyInt());
        SysConfigUtil.getParamValue(anyString());
    }

}
