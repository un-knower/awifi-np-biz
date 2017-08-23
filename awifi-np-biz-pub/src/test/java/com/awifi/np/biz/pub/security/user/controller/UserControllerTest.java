package com.awifi.np.biz.pub.security.user.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
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

import com.awifi.np.admin.security.role.service.RoleService;
import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.admin.security.user.service.UserService;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月22日 下午2:21:08
 * 创建作者：周颖
 * 文件名称：UserControllerTest.java
 * 版本：  v1.0
 * 功能：UserControllerTest
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,RedisAdminUtil.class,LocationClient.class,CastUtil.class,EncryUtil.class,FormatUtil.class,JsonUtil.class,ValidUtil.class})
@PowerMockIgnore({"javax.management.*"})
@SuppressWarnings("rawtypes")
public class UserControllerTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private UserController userController;
    
    /**角色服务层*/
    @Mock(name= "userService")
    private UserService userService;
    
    /**角色服务层*/
    @Mock(name = "roleService")
    private RoleService roleService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisAdminUtil.class);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(EncryUtil.class);
        PowerMockito.mockStatic(FormatUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
    }
    
    /**
     * redis为空
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月22日 下午2:31:11
     */
    @Test(expected=BizException.class)
    public void testGetByAccessTokenNull() throws Exception {
        PowerMockito.when(RedisAdminUtil.get(anyObject())).thenReturn(null);
        PowerMockito.when(MessageUtil.getMessage(anyObject())).thenReturn("error");
        
        Map result = userController.getByAccessToken("access_token");
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RedisAdminUtil.get(anyObject());
        MessageUtil.getMessage(anyObject());
    }
    
    /**
     * 通过access_token获取用户详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月24日 上午9:41:46
     */
//    @Test
//    public void testGetByAccessToken() throws Exception {
//        PowerMockito.when(RedisAdminUtil.get(anyObject())).thenReturn("value");
//        Map<String,Object> value = new HashMap<String,Object>();
//        Map<String,Object> userInfo = new HashMap<String,Object>();
//        userInfo.put("roleIds", "1,2,3");
//        userInfo.put("id", 1L);
//        userInfo.put("areaId", 3230L);
//        userInfo.put("city", 383L);
//        userInfo.put("provinceId", 31L);
//        value.put("userInfo", userInfo);
//        PowerMockito.when(JsonUtil.fromJson(anyObject(),anyObject())).thenReturn(value);
//        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(1L);
//        PowerMockito.when(LocationClient.getByIdAndParam(anyObject(),anyObject())).thenReturn("locationName");
//        PowerMockito.when(FormatUtil.locationFullName(anyObject(),anyObject(),anyObject(),anyObject())).thenReturn("fullname");
//        User user = new User();
//        user.setRealname("realname");
//        when(userService.getById(anyObject())).thenReturn(user);
//        
//        Map result = userController.getByAccessToken("access_token");
//        Assert.assertNotNull(result);
//        PowerMockito.verifyStatic();
//        RedisAdminUtil.get(anyObject());
//        MessageUtil.getMessage(anyObject());
//        JsonUtil.fromJson(anyObject(),anyObject());
//        CastUtil.toLong(anyObject());
//        LocationClient.getByIdAndParam(anyObject(),anyObject());
//        FormatUtil.locationFullName(anyObject(),anyObject(),anyObject(),anyObject());
//        verify(userService).getById(anyObject());
//    }
    
    /**
     * 更新用户  access_token失效
     * @author 周颖  
     * @date 2017年2月24日 上午9:45:13
     */
    @Test(expected=BizException.class)
    public void testUpdateByAccessTokenNull(){
        PowerMockito.when(RedisAdminUtil.get(anyObject())).thenReturn(null);
        PowerMockito.when(MessageUtil.getMessage(anyObject())).thenReturn("error");
        
        Map<String,Object> bodyParam = new HashMap<String,Object>();
        Map result = userController.updateByAccessToken("accessToken", bodyParam);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RedisAdminUtil.get(anyObject());
        MessageUtil.getMessage(anyObject());
    }
    
    /**
     * 更新用户 缓存没有用户id
     * @author 周颖  
     * @date 2017年2月24日 上午9:54:35
     */
    @Test(expected=BizException.class)
    public void testUpdateByAccessTokenIdNull(){
        PowerMockito.when(RedisAdminUtil.get(anyObject())).thenReturn("value");
        Map<String,Object> value = new HashMap<String,Object>();
        Map<String,Object> userInfo = new HashMap<String,Object>();
        userInfo.put("roleIds", "1,2,3");
        userInfo.put("id", 1L);
        userInfo.put("areaId", 3230L);
        userInfo.put("city", 383L);
        userInfo.put("provinceId", 31L);
        value.put("userInfo", userInfo);
        PowerMockito.when(JsonUtil.fromJson(anyObject(),anyObject())).thenReturn(value);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(null);
        PowerMockito.when(MessageUtil.getMessage(anyObject())).thenReturn("error");
        
        Map<String,Object> bodyParam = new HashMap<String,Object>();
        Map result = userController.updateByAccessToken("accessToken", bodyParam);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RedisAdminUtil.get(anyObject());
        JsonUtil.fromJson(anyObject(),anyObject());
        CastUtil.toLong(anyObject());
        MessageUtil.getMessage(anyObject());
    }
    
    /**
     * 更新用户 失败
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月24日 上午10:04:55
     */
    @Test(expected=ValidException.class)
    public void testUpdateByAccessTokenCount() throws Exception{
        PowerMockito.when(RedisAdminUtil.get(anyObject())).thenReturn("value");
        Map<String,Object> value = new HashMap<String,Object>();
        Map<String,Object> userInfo = new HashMap<String,Object>();
        userInfo.put("roleIds", "1,2,3");
        userInfo.put("id", 1L);
        userInfo.put("areaId", 3230L);
        userInfo.put("city", 383L);
        userInfo.put("provinceId", 31L);
        value.put("userInfo", userInfo);
        PowerMockito.when(JsonUtil.fromJson(anyObject(),anyObject())).thenReturn(value);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(1L);
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyObject(),anyObject(),anyObject());
        when(userService.updateById(anyObject())).thenReturn(0);
        PowerMockito.when(MessageUtil.getMessage(anyObject())).thenReturn("error");
        
        Map<String,Object> bodyParam = new HashMap<String,Object>();
        bodyParam.put("email", "email");
        Map result = userController.updateByAccessToken("accessToken", bodyParam);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RedisAdminUtil.get(anyObject());
        JsonUtil.fromJson(anyObject(),anyObject());
        CastUtil.toLong(anyObject());
        ValidUtil.valid(anyObject(),anyObject(),anyObject());
        verify(userService).updateById(anyObject());
        MessageUtil.getMessage(anyObject());
    }
   
    /**
     * 更新用户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月24日 上午10:04:41
     */
    /*@Test
    public void testUpdateByAccessTokenOk() throws Exception{
        PowerMockito.when(RedisAdminUtil.get(anyObject())).thenReturn("value");
        Map<String,Object> value = new HashMap<String,Object>();
        Map<String,Object> userInfo = new HashMap<String,Object>();
        userInfo.put("roleIds", "1,2,3");
        userInfo.put("id", 1L);
        userInfo.put("areaId", 3230L);
        userInfo.put("city", 383L);
        userInfo.put("provinceId", 31L);
        value.put("userInfo", userInfo);
        PowerMockito.when(JsonUtil.fromJson(anyObject(),anyObject())).thenReturn(value);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(1L);
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyObject(),anyObject(),anyObject());
        when(userService.updateById(anyObject())).thenReturn(1);
        
        Map<String,Object> bodyParam = new HashMap<String,Object>();
        bodyParam.put("email", "173943166@qq.com");
        Map result = userController.updateByAccessToken("accessToken", bodyParam);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RedisAdminUtil.get(anyObject());
        JsonUtil.fromJson(anyObject(),anyObject());
        CastUtil.toLong(anyObject());
        ValidUtil.valid(anyObject(),anyObject(),anyObject());
        verify(userService).updateById(anyObject());
    }*/
    
    /**
     * 通过access_token更新当前登录用户密码接口
     * @author 许小满  
     * @date 2017年2月24日 上午8:49:01
     */
    @Test(expected = ValidException.class)
    public void testUpdatePwdValidEx1(){
        String accessToken = "x";
        Map<String,String> bodyParams = new HashMap<String,String>();
        bodyParams.put("oldPassword", "123456");//旧密码
        bodyParams.put("newPassword", "123456");//新密码
        bodyParams.put("confirmPassword", "1234567");//确认密码
        userController.updatePwdByAccessToken(accessToken, bodyParams);
    }
    
    /**
     * 通过access_token更新当前登录用户密码接口
     * @author 许小满  
     * @date 2017年2月24日 上午8:49:01
     */
    @Test(expected = ValidException.class)
    public void testUpdatePwdValidEx2(){
        String accessToken = "x";
        Map<String,String> bodyParams = new HashMap<String,String>();
        bodyParams.put("oldPassword", "123456");//旧密码
        bodyParams.put("newPassword", "1234567");//新密码
        bodyParams.put("confirmPassword", "12345678");//确认密码
        userController.updatePwdByAccessToken(accessToken, bodyParams);
    }
    
    /**
     * 通过access_token更新当前登录用户密码接口
     * @author 许小满  
     * @date 2017年2月24日 上午8:49:01
     */
    @Test(expected = BizException.class)
    public void testUpdatePwdUserInfoNull(){
        String userInfo = "";
        when(RedisAdminUtil.get(anyObject())).thenReturn(userInfo);
        
        String accessToken = "x";
        Map<String,String> bodyParams = new HashMap<String,String>();
        bodyParams.put("oldPassword", "123456");//旧密码
        bodyParams.put("newPassword", "1234567");//新密码
        bodyParams.put("confirmPassword", "1234567");//确认密码
        
        userController.updatePwdByAccessToken(accessToken, bodyParams);
    }
    
    /**
     * 通过access_token更新当前登录用户密码接口
     * @author 许小满  
     * @date 2017年2月24日 上午8:49:01
     */
    @Test(expected = BizException.class)
    public void testUpdatePwdUserNull(){
        String userInfo = "{}";
        when(RedisAdminUtil.get(anyObject())).thenReturn(userInfo);
        
        Map<String,Object> userInfoRedisMap = new HashMap<String,Object>();
        Map<String,Object> userInfoMap = new HashMap<String,Object>();
        userInfoMap.put("id", 1L);
        userInfoRedisMap.put("userInfo", userInfoMap);
        when(JsonUtil.fromJson(anyObject(), anyObject())).thenReturn(userInfoRedisMap);
        
        User user = null;
        when(userService.getById(anyObject())).thenReturn(user);
        
        String accessToken = "x";
        Map<String,String> bodyParams = new HashMap<String,String>();
        bodyParams.put("oldPassword", "123456");//旧密码
        bodyParams.put("newPassword", "1234567");//新密码
        bodyParams.put("confirmPassword", "1234567");//确认密码
        
        userController.updatePwdByAccessToken(accessToken, bodyParams);
    }
    
    /**
     * 通过access_token更新当前登录用户密码接口
     * @author 许小满  
     * @date 2017年2月24日 上午8:49:01
     */
    @Test(expected = BizException.class)
    public void testUpdatePwdPwdError(){
        String userInfo = "{}";
        when(RedisAdminUtil.get(anyObject())).thenReturn(userInfo);
        
        Map<String,Object> userInfoRedisMap = new HashMap<String,Object>();
        Map<String,Object> userInfoMap = new HashMap<String,Object>();
        userInfoMap.put("id", 1L);
        userInfoRedisMap.put("userInfo", userInfoMap);
        when(JsonUtil.fromJson(anyObject(), anyObject())).thenReturn(userInfoRedisMap);
        
        User user = new User();
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        when(userService.getById(anyObject())).thenReturn(user);
        
        String oldPasswordMd5 = "aaa";
        when(EncryUtil.getMd5Str(anyObject())).thenReturn(oldPasswordMd5);
        
        String accessToken = "x";
        Map<String,String> bodyParams = new HashMap<String,String>();
        bodyParams.put("oldPassword", "12345");//旧密码
        bodyParams.put("newPassword", "1234567");//新密码
        bodyParams.put("confirmPassword", "1234567");//确认密码
        
        userController.updatePwdByAccessToken(accessToken, bodyParams);
    }
    
    /**
     * 通过access_token更新当前登录用户密码接口
     * @author 许小满  
     * @date 2017年2月24日 上午8:49:01
     */
    /*@Test
    public void testUpdatePwd(){
        String userInfo = "{}";
        when(RedisAdminUtil.get(anyObject())).thenReturn(userInfo);
        
        Map<String,Object> userInfoRedisMap = new HashMap<String,Object>();
        Map<String,Object> userInfoMap = new HashMap<String,Object>();
        userInfoMap.put("id", 1L);
        userInfoRedisMap.put("userInfo", userInfoMap);
        when(JsonUtil.fromJson(anyObject(), anyObject())).thenReturn(userInfoRedisMap);
        
        User user = new User();
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        when(userService.getById(anyObject())).thenReturn(user);
        
        String oldPasswordMd5 = "e10adc3949ba59abbe56e057f20f883e";
        when(EncryUtil.getMd5Str(anyObject())).thenReturn(oldPasswordMd5);
        
        String accessToken = "x";
        Map<String,String> bodyParams = new HashMap<String,String>();
        bodyParams.put("oldPassword", "123456");//旧密码
        bodyParams.put("newPassword", "1234567");//新密码
        bodyParams.put("confirmPassword", "1234567");//确认密码
        
        userController.updatePwdByAccessToken(accessToken, bodyParams);
    }    */
}