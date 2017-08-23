package com.awifi.np.biz.common.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 下午2:14:48
 * 创建作者：周颖
 * 文件名称：IPUtilTest.java
 * 版本：  v1.0
 * 功能：iputil
 * 修改记录：
 */
public class IPUtilTest {

    /**
     * 测试类
     */
    @InjectMocks
    private IPUtil ipUtil;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
    }
    
    /**
     * 
     * 获取客户端真实ip地址
     * @author 周颖  
     * @date 2017年3月23日 下午2:23:16
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetIpAddr() {
        ipUtil.getIpAddr(httpRequest);
    }
}
