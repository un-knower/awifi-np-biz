package com.awifi.np.biz.api.client.npadmin.util;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.npadmin.service.NPAdminApiService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 下午2:53:56
 * 创建作者：周颖
 * 文件名称：NPAdminClientTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtil.class)
public class NPAdminClientTest {

    /**被测试类*/
    @InjectMocks
    private NPAdminClient npAdminClient;
    
    /**服务*/
    @Mock(name = "npAdminApiService")
    private NPAdminApiService npAdminApiService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(npAdminApiService);
    }
    
    /**
     * 数据接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 下午3:02:25
     */
    @Test
    public void testDataInterface() throws Exception {
        npAdminClient.dataInterface("accessToken", "serviceCode", "interfaceCode", "params");
    }

    /**
     * 显示接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 下午3:05:49
     */
    @Test
    public void testViewInterface() throws Exception {
        npAdminClient.viewInterface("serviceCode", "suitCode", "templateCode", "accessToken");
    }

    /**
     * 接口推送
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 下午3:07:07
     */
    @Test
    public void testPushInterfaces() throws Exception {
        npAdminClient.pushInterfaces("params");
    }
}
