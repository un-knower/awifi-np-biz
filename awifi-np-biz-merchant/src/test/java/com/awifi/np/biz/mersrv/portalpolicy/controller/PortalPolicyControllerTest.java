package com.awifi.np.biz.mersrv.portalpolicy.controller;

import static org.mockito.Matchers.anyObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.portalpolicy.util.PortalPolicyClient;
import com.awifi.np.biz.common.util.CastUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月20日 下午3:29:36
 * 创建作者：许尚敏
 * 文件名称：PortalPolicyControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PortalPolicyClient.class,CastUtil.class})
public class PortalPolicyControllerTest {

    /**被测试类*/
    @InjectMocks
    private PortalPolicyController portalPolicyController;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(PortalPolicyClient.class);
        PowerMockito.mockStatic(CastUtil.class);
    }
    
    /**
     * 测试刷新商户无感知配置
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月20日 下午3:32:39
     */
    @Test
    public void testRefresh() throws Exception{
        PowerMockito.when(PortalPolicyClient.getByMerchantId(anyObject())).thenReturn(null);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(0);
        portalPolicyController.refresh("", "{\"merchantId\":71287}");
    }
    
    /**
     * 测试刷新商户无感知配置
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月21日 下午7:32:39
     */
    @Test
    public void testRefreshByUnit() throws Exception{
        Map<String, Object> portalPolicy = new HashMap<String, Object>();
        portalPolicy.put("unit", 0);
        portalPolicy.put("times", 0);
        PowerMockito.when(PortalPolicyClient.getByMerchantId(anyObject())).thenReturn(portalPolicy);
        portalPolicyController.refresh("", "{\"merchantId\":71287}");
    }
    
    /**
     * 测试刷新商户无感知配置
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月21日 下午7:32:39
     */
    @Test
    public void testRefreshByUnit2() throws Exception{
        Map<String, Object> portalPolicy = new HashMap<String, Object>();
        portalPolicy.put("unit", 3);
        portalPolicy.put("times", 0);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(3);
        PowerMockito.when(PortalPolicyClient.getByMerchantId(anyObject())).thenReturn(portalPolicy);
        portalPolicyController.refresh("", "{\"merchantId\":71287}");
    }
    
    /**
     * 测试刷新商户无感知配置
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月21日 下午7:32:39
     */
    @Test
    public void testRefreshByUnit3() throws Exception{
        Map<String, Object> portalPolicy = new HashMap<String, Object>();
        portalPolicy.put("unit", 2);
        portalPolicy.put("times", 0);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(2);
        PowerMockito.when(PortalPolicyClient.getByMerchantId(anyObject())).thenReturn(portalPolicy);
        portalPolicyController.refresh("", "{\"merchantId\":71287}");
    }

    /**
     * 测试无感知配置
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月20日 下午3:32:39
     */
    @Test
    public void testBatchSwitch() throws Exception{
        List<Map<String,Object>> bodyParam = new ArrayList<Map<String,Object>>();
        Map<String,Object> maps = new HashMap<String,Object>();
        maps.put("merchantId", "62");
        maps.put("policySwitch", "ON");
        maps.put("time", 10);
        bodyParam.add(maps);
        PowerMockito.when(CastUtil.toString(anyObject())).thenReturn("ON");
        portalPolicyController.batchSwitch("", bodyParam);
    }
    
    /**
     * 测试无感知配置
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月20日 下午3:32:39
     */
    @Test
    public void testBatchSwitchOff() throws Exception{
        List<Map<String,Object>> bodyParam = new ArrayList<Map<String,Object>>();
        Map<String,Object> maps = new HashMap<String,Object>();
        maps.put("merchantId", "62");
        maps.put("policySwitch", "OFF");
        maps.put("time", 10);
        bodyParam.add(maps);
        PowerMockito.when(CastUtil.toString(anyObject())).thenReturn("OFF");
        portalPolicyController.batchSwitch("", bodyParam);
    }
}
