/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月26日 上午9:40:42
* 创建作者：周颖
* 文件名称：PortalPolicyClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.portalpolicy.util;

import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.portalpolicy.service.PortalPolicyService;
import com.awifi.np.biz.common.util.BeanUtil;

@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtil.class)
public class PortalPolicyClientTest {

    /**被测试类*/
    @InjectMocks
    private PortalPolicyClient portalPolicyClient;
    
    /**无感知策略*/
    @Mock(name="portalPolicyService")
    private PortalPolicyService portalPolicyService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年6月26日 上午9:48:41
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(portalPolicyService);
    }
    
    /**
     * 商户无感知
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月26日 上午9:52:47
     */
    @Test
    public void testGetByMerchantId() throws Exception {
        portalPolicyClient.getByMerchantId(1L);
    }

    /**
     * 商户无感知添加
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月26日 上午9:52:58
     */
    @Test
    public void testAdd() throws Exception {
        Map<String,Object> params =  new HashMap<String, Object>();
        params.put("unit", 3);
        params.put("times", 1);
        params.put("merchantId", 700000109277L);
        portalPolicyClient.add(params);
    }

    /**
     * 编辑无感知
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月26日 上午9:53:09
     */
    @Test
    public void testUpdate() throws Exception {
        Map<String,Object> params =  new HashMap<String, Object>();
        params.put("id", 12);
        params.put("unit", 3);
        params.put("times", 1);
        params.put("merchantId", 700000109277L);
        portalPolicyClient.update(params);
    }

    /**
     * 无感知删除
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月26日 上午9:53:20
     */
    @Test
    public void testDelete() throws Exception {
        portalPolicyClient.delete(12L);
    }
}
