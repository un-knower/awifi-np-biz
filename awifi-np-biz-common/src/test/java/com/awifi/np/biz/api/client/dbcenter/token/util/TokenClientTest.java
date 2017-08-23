package com.awifi.np.biz.api.client.dbcenter.token.util;

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

import com.awifi.np.biz.api.client.dbcenter.token.service.TokenService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 上午10:43:11
 * 创建作者：周颖
 * 文件名称：TokenClientTest.java
 * 版本：  v1.0
 * 功能：获取数据中心token测试类
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({RedisUtil.class, BeanUtil.class})
public class TokenClientTest {

    /**被测试类*/
    @InjectMocks
    private TokenClient tokenClient;
    
    /**
     * token
     */
    @Mock(name = "tokenService")
    private TokenService tokenService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(tokenService);
    }
    
    /**
     * 获取access_token 缓存中有
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午10:53:26
     */
    @Test
    public void testGetAccessToken() throws Exception {
        PowerMockito.when(RedisUtil.get(anyString())).thenReturn("access_token");
        tokenClient.getAccessToken();
    }
    
    /**
     * 获取access_token
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午10:53:51
     */
    @Test
    public void testGetAccessTokenNull() throws Exception {
        tokenClient.getAccessToken();
    }

    /**
     * 重置access_token
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午10:54:14
     */
    @Test
    public void testResetAccessToken() throws Exception {
        tokenClient.resetAccessToken();
    }
}
