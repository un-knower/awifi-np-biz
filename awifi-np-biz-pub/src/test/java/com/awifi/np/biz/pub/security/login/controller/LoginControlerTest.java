package com.awifi.np.biz.pub.security.login.controller;

import static org.mockito.Matchers.anyInt;
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
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.admin.security.user.service.UserService;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午2:51:55
 * 创建作者：周颖
 * 文件名称：LoginControlerTest.java
 * 版本：  v1.0
 * 功能：登陆测试类
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonUtil.class,RedisAdminUtil.class,SysConfigUtil.class,KeyUtil.class,IPUtil.class,MessageUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class LoginControlerTest {

    /**被测试类*/
    @InjectMocks
    private LoginController loginController;
    
    /**mock模板服务*/
    @Mock(name="templateService")
    private TemplateService templateService;
    
    /**mock用户服务*/
    @Mock(name="userService")
    private UserService userService;
    
    /**mock*/
    private  MockHttpServletResponse response;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        //PowerMockito.mockStatic(EncryUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(RedisAdminUtil.class);
        PowerMockito.mockStatic(KeyUtil.class);
        PowerMockito.mockStatic(IPUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        response = new MockHttpServletResponse();
    }
    
    /**
     * 获取登录页
     * @author 周颖  
     * @date 2017年1月13日 下午3:46:14
     */
    @Test
    public void testLoginView() {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("servicecode"); 
        when(templateService.getByCode("suitcode", "servicecode", "templatecode")).thenReturn("result");
        
        Map<String,Object> result = loginController.loginView("templatecode", "suitCode");
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
    }
    
    /**
     * 获取验证码--校验错误
     * @author 许小满  
     * @date 2017年3月15日 下午7:49:12
     */
    @Test(expected=ValidException.class)
    public void testGetAuthCodeValidError(){
        loginController.getAuthCode("xxxx", null);
    }
    
    /**
     * 获取验证码
     * @author 许小满  
     * @date 2017年3月15日 下午7:49:12
     */
    @Test()
    public void testGetAuthCode(){
        loginController.getAuthCode("C6739B7AAC574C55ABF734BEEE6F458E", response);
    }
    
    /**
     * 登录--验证码错误
     * @author 许小满  
     * @throws Exception 
     * @date 2017年3月15日 下午7:46:04
     */
    @Test(expected=ValidException.class)
    public void testLoginAuthCodeError() throws Exception {
        User user = new User();
        PowerMockito.when(SysConfigUtil.getParamValue("appid_pub")).thenReturn("appid");
        PowerMockito.when(KeyUtil.generateAccessToken(anyString())).thenReturn("access_token");
        PowerMockito.when(RedisAdminUtil.get(anyString())).thenReturn("abc");
        PowerMockito.when(RedisAdminUtil.set(anyString(), anyString(), anyInt())).thenReturn("ok");
        PowerMockito.when(IPUtil.getIpAddr(anyObject())).thenReturn("192.168.10.1");
        PowerMockito.when(SysConfigUtil.getParamValue("access_token_time")).thenReturn("123");
        when(userService.getByNameAndPwd(anyString(), anyString())).thenReturn(user);
        
        loginController.login("username", "password", "abcd", "xxx", null);
    }
    
    /**
     * 登陆
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月13日 下午3:46:33
     */
    @Test
    public void testLogin() throws Exception {
        User user = new User();
        //PowerMockito.when(EncryUtil.getMd5Str(anyString())).thenReturn("");
        PowerMockito.when(SysConfigUtil.getParamValue("appid_pub")).thenReturn("appid");
        PowerMockito.when(KeyUtil.generateAccessToken(anyString())).thenReturn("access_token");
        PowerMockito.when(RedisAdminUtil.get(anyString())).thenReturn("abc");
        PowerMockito.when(RedisAdminUtil.set(anyString(), anyString(), anyInt())).thenReturn("ok");
        PowerMockito.when(IPUtil.getIpAddr(anyObject())).thenReturn("192.168.10.1");
        PowerMockito.when(SysConfigUtil.getParamValue("access_token_time")).thenReturn("123");
        
        when(userService.getByNameAndPwd(anyString(), anyString())).thenReturn(user);
        
        Map<String,Object> result = loginController.login("username", "password", "abc", "xxx", null);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        //EncryUtil.getMd5Str(anyString());
        SysConfigUtil.getParamValue("appid_pub");
        SysConfigUtil.getParamValue("access_token_time");
        KeyUtil.generateAccessToken(anyString());
        RedisAdminUtil.set(anyString(), anyString(), anyInt());
        IPUtil.getIpAddr(anyObject());
    }
}