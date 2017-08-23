package com.awifi.np.admin.platform.service.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.awifi.np.admin.platform.dao.PlatformDao;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月16日 下午2:13:04
 * 创建作者：周颖
 * 文件名称：PlatformServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class PlatformServiceImplTest {

    /**注入被测试类*/
    @InjectMocks
    private PlatformServiceImpl platformServiceImpl;
    
    /**mock*/
    @Mock(name = "platformDao")
    private PlatformDao platformDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 平台id为空
     * @author 周颖  
     * @date 2017年1月16日 下午2:30:22
     */
    @Test
    public void testGetKeyByAppIdNull() {
        String appId = null;
        String result = platformServiceImpl.getKeyByAppId(appId);
        Assert.assertNotNull(result);
    }
    
    /**
     * 平台id不为空
     * @author 周颖  
     * @date 2017年1月16日 下午2:30:50
     */
    @Test
    public void testGetKeyByAppIdOK() {
        when(platformDao.getKeyByAppId(anyString())).thenReturn("appKey");
        
        String result = platformServiceImpl.getKeyByAppId("appId");
        Assert.assertNotNull(result);
    }
}