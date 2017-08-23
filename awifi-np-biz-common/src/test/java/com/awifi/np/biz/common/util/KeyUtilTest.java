package com.awifi.np.biz.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午10:56:36
 * 创建作者：周颖
 * 文件名称：KeyUtilTest.java
 * 版本：  v1.0
 * 功能：KeyUtil测试类
 * 修改记录：
 */
public class KeyUtilTest {

    /**被测试类*/
    @InjectMocks
    private KeyUtil keyUtil;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 生成access_token
     * @author 周颖  
     * @date 2017年1月13日 上午11:02:42
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGenerateAccessToken() {
        String appId = "PUB";
        String result = keyUtil.generateAccessToken(appId);
        Assert.assertNotNull(result);
    }
}