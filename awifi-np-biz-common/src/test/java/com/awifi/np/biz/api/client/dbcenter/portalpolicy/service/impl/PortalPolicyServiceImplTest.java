/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月26日 上午8:48:37
* 创建作者：周颖
* 文件名称：PortalPolicyServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.portalpolicy.service.impl;

import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;







import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,JsonUtil.class})
public class PortalPolicyServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private PortalPolicyServiceImpl portalPolicyServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
    }
    
    /**
     * 查看商户策略
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月26日 上午9:17:31
     */
    @Test
    public void testGetByMerchantId() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("http://192.168.41.49:28850/merchant-device/portalpolicy/querybymerchantid");
        PowerMockito.when(JsonUtil.toJson(anyString())).thenReturn("'merchantId':1");
        Map<String, Object> returnMap = new HashMap<String,Object>();
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        Map<String, Object> result = portalPolicyServiceImpl.getByMerchantId(1L);
        Assert.assertNull(result);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        CenterHttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.toJson(anyString());
    }

    /**
     * 商户添加无感知
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月26日 上午9:31:41
     */
    @Test
    public void testAdd() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("http://192.168.41.49:28850/merchant-device/portalpolicy/add");
        PowerMockito.when(JsonUtil.toJson(anyString())).thenReturn("{'unit':3,'times':1,'merchantId':700000109277}");
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("rs", 12);
        PowerMockito.when(CenterHttpRequest.sendPostRequest(anyString(),anyString())).thenReturn(params);
        params = new HashMap<String,Object>();
        params.put("unit", 3);
        params.put("times", 1);
        params.put("merchantId", 700000109277L);
        portalPolicyServiceImpl.add(params);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        CenterHttpRequest.sendPostRequest(anyString(),anyString());
        JsonUtil.toJson(anyString());
    }

    /**
     * 商户无感知编辑
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月26日 上午9:32:02
     */
    @Test
    public void testUpdate() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("http://192.168.41.49:28850/merchant-device/portalpolicy/update");
        PowerMockito.when(JsonUtil.toJson(anyString())).thenReturn("{'id':12,'unit':3,'times':1,'merchantId':700000109277}");
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("rs", 12);
        PowerMockito.when(CenterHttpRequest.sendPutRequest(anyString(),anyString())).thenReturn(params);
        params = new HashMap<String,Object>();
        params.put("id", 12);
        params.put("unit", 3);
        params.put("times", 1);
        params.put("merchantId", 700000109277L);
        portalPolicyServiceImpl.update(params);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        CenterHttpRequest.sendPutRequest(anyString(),anyString());
        JsonUtil.toJson(anyString());
    }

    /**
     * 删除无感知
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月26日 上午9:38:05
     */
    @Test
    public void testDelete() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("http://192.168.41.49:28850/merchant-device/portalpolicy/deletebyid");
        PowerMockito.when(JsonUtil.toJson(anyString())).thenReturn("{'id':12}");
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("rs", "");
        PowerMockito.when(CenterHttpRequest.sendDeleteRequest(anyString(), anyString())).thenReturn(params);
        portalPolicyServiceImpl.delete(12L);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        CenterHttpRequest.sendDeleteRequest(anyString(),anyString());
        JsonUtil.toJson(anyString());
    }
}
