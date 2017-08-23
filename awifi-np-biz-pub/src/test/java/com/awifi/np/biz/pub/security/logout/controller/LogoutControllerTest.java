package com.awifi.np.biz.pub.security.logout.controller;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.redis.util.RedisAdminUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 上午9:34:04
 * 创建作者：周颖
 * 文件名称：LogoutControllerTest.java
 * 版本：  v1.0
 * 功能：注销控制层单元测试
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RedisAdminUtil.class)
@PowerMockIgnore({"javax.management.*"})
public class LogoutControllerTest {

    /**被测试类*/
    @InjectMocks
    private LogoutController logoutController;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RedisAdminUtil.class);
    }
    
    /**
     * 注销接口单元测试
     * @author 周颖  
     * @date 2017年3月22日 上午9:50:10
     */
    @Test
    public void testLogout() {
        PowerMockito.when(RedisAdminUtil.delete(anyString())).thenReturn(1L);
        logoutController.logout("access_token");
        PowerMockito.verifyStatic();
        RedisAdminUtil.delete(anyString());
    }
}
