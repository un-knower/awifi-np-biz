package com.awifi.np.biz.api.client.npadmin.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.FormatUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 下午3:09:33
 * 创建作者：周颖
 * 文件名称：NPAdminApiServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FormatUtil.class,SysConfigUtil.class,HttpRequest.class})
public class NPAdminApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private NPAdminApiServiceImpl npAdminApiServiceImpl;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(FormatUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(HttpRequest.class);
    }
    
    /**
     * 数据接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 下午3:02:25
     */
    @Test
    public void testDataInterface() throws Exception {
        npAdminApiServiceImpl.dataInterface("accessToken", "serviceCode", "interfaceCode", "params");
    }

    /**
     * 显示接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 下午3:05:49
     */
    @Test
    public void testViewInterface() throws Exception {
        npAdminApiServiceImpl.viewInterface("serviceCode", "suitCode", "templateCode", "accessToken");
    }

    /**
     * 接口推送
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 下午3:07:07
     */
    @Test
    public void testPushInterfaces() throws Exception {
        npAdminApiServiceImpl.pushInterfaces("params");
    }

}
