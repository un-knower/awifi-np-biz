package com.awifi.np.biz.pub.token.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.admin.platform.service.PlatformService;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午4:08:07
 * 创建作者：周颖
 * 文件名称：TokenControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,EncryUtil.class,KeyUtil.class,RedisAdminUtil.class,IPUtil.class,SysConfigUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class TokenControllerTest {

    /**被测试类*/
    @InjectMocks
    private TokenController tokenController;
    
    /**平台服务层*/
    @Mock(name = "platformService")
    private PlatformService platformService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(EncryUtil.class);
        PowerMockito.mockStatic(KeyUtil.class);
        PowerMockito.mockStatic(RedisAdminUtil.class);
        PowerMockito.mockStatic(IPUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /**
     * 测试平台id无效
     * @author 周颖  
     * @date 2017年1月13日 下午4:32:34
     */
    @Test(expected = BizException.class)
    public void testGetAccessTokenError() {
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        when(platformService.getKeyByAppId(anyString())).thenReturn(null);

        Map<String,Object> result = tokenController.getAccessToken("appId", "timestamp", "token","userInfo", null);
        Assert.assertNotNull(result);

        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyString());
    }
    
    /**
     * 测试token失效
     * @author 周颖  
     * @date 2017年1月13日 下午4:32:51
     */
    @Test(expected = BizException.class)
    public void testGetAccessTokenTimeOut() {
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        PowerMockito.when(EncryUtil.isTimeout(anyString(),anyLong())).thenReturn(true);
        when(platformService.getKeyByAppId(anyString())).thenReturn("appKey");

        Map<String,Object> result = tokenController.getAccessToken("appId", "timestamp", "token","userInfo", null);
        Assert.assertNotNull(result);

        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyString());
        EncryUtil.isTimeout(anyString(),anyLong());
    }
    
    /**
     * 测试token无效
     * @author 周颖  
     * @date 2017年1月13日 下午4:33:10
     */
    @Test(expected = BizException.class)
    public void testGetAccessTokenFail() {
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        PowerMockito.when(EncryUtil.getMd5Str(anyString())).thenReturn("tokenA");
        when(platformService.getKeyByAppId(anyString())).thenReturn("appKey");

        Map<String,Object> result = tokenController.getAccessToken("appId", "timestamp", "token","userInfo", null);
        Assert.assertNotNull(result);

        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyString());
        EncryUtil.getMd5Str(anyString());
    }
    
    /**
     * 测试成功
     * @author 周颖  
     * @date 2017年1月13日 下午4:33:45
     */
    @Test
    public void testGetAccessTokenOK() {
        PowerMockito.when(EncryUtil.getMd5Str(anyString())).thenReturn("token");
        PowerMockito.when(KeyUtil.generateAccessToken(anyString())).thenReturn("access_token");
        PowerMockito.when(IPUtil.getIpAddr(anyObject())).thenReturn("192.168.10.1");
        PowerMockito.when(RedisAdminUtil.set(anyString(), anyString(), anyInt())).thenReturn("ok");
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("123");
        when(platformService.getKeyByAppId(anyString())).thenReturn("appKey");

        Map<String,Object> result = tokenController.getAccessToken("appId", "timestamp", "token","userInfo", null);
        Assert.assertNotNull(result);

        PowerMockito.verifyStatic();
        EncryUtil.getMd5Str(anyString());
        KeyUtil.generateAccessToken(anyString());
        IPUtil.getIpAddr(anyObject());
        RedisAdminUtil.set(anyString(), anyString(), anyInt());
        SysConfigUtil.getParamValue(anyString());
    }
}