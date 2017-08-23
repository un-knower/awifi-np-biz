/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月28日 上午10:55:54
* 创建作者：周颖
* 文件名称：AuthControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.auth.auth.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.api.client.auth.util.AuthClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;
import com.awifi.np.biz.api.client.sms.util.SmsClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.smsconfig.service.SmsConfigService;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdauth.util.ThirdAuthClient;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.service.CustomerConfigService;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.util.CustomerConfigUtil;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.service.StaticUserService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RegexUtil.class,MerchantClient.class,RedisUtil.class,CustomerConfigUtil.class,
        MessageUtil.class,ValidUtil.class,ExceptionUtil.class,SmsClient.class,AuthClient.class,UserBaseClient.class,
        ThirdAuthClient.class})
public class AuthControllerTest {

    /**被测试类*/
    @InjectMocks
    private AuthController authController;
    
    /** 短信配置 */
    @Mock(name = "smsConfigService")
    private SmsConfigService smsConfigService;
    
    /** 短信配置 */
    @Mock(name = "customerConfigService")
    private CustomerConfigService customerConfigService;
    
    /** 静态用户 */
    @Mock(name = "staticUserService")
    private StaticUserService staticUserService;
    
    /**mock httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(AuthClient.class);
        PowerMockito.mockStatic(UserBaseClient.class);
        PowerMockito.mockStatic(SmsClient.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(RegexUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(ThirdAuthClient.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(CustomerConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(ExceptionUtil.class);
    }
    
    /**
     * get请求方式
     * @author 周颖  
     * @date 2017年6月28日 上午11:01:59
     */
    @Test
    public void testSendSmsCodeGET() {
        httpRequest.setMethod("GET");
        authController.sendSmsCode(httpRequest);
    }
    
    /**
     * post请求方式
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月28日 上午11:01:59
     */
    @Test
    public void testSendSmsCodePOST() throws Exception {
        httpRequest.setMethod("POST");
        PowerMockito.when(SmsClient.sendSmsCode(anyLong(),anyString(),anyString(),anyString())).thenReturn(null);
        
        authController.sendSmsCode(httpRequest);
        PowerMockito.verifyStatic();
        SmsClient.sendSmsCode(anyLong(),anyString(),anyString(),anyString());
    }
    
    /**
     * Exception
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月28日 上午11:01:59
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSendSmsCodeException() throws Exception {
        httpRequest.setMethod("POST");
        PowerMockito.when(SmsClient.sendSmsCode(anyLong(),anyString(),anyString(),anyString())).thenThrow(Exception.class);
        
        authController.sendSmsCode(httpRequest);
        PowerMockito.verifyStatic();
        SmsClient.sendSmsCode(anyLong(),anyString(),anyString(),anyString());
    }
    

    /**
     * get
     * @author 周颖  
     * @date 2017年6月28日 下午5:12:24
     */
    @Test
    public void testPhoneAuthGET() {
        httpRequest.setMethod("GET");
        authController.phoneAuth(httpRequest);
    }
    
    /**
     * post
     * @author 周颖  
     * @date 2017年6月28日 下午5:17:38
     */
    @Test
    public void testPhoneAuthPOST() {
        httpRequest.setMethod("POST");
        authController.phoneAuth(httpRequest);
    }
    
    /**
     * post 免认证
     * @author 周颖  
     * @date 2017年6月28日 下午5:17:38
     */
    @Test
    public void testPhoneAuthType4() {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("type", "4");
        
        Map<String, Object> authResultMap = new HashMap<String, Object>();
        authResultMap.put("data", "data");
        PowerMockito.when(AuthClient.auth(anyObject(),anyObject(),anyObject())).thenReturn(authResultMap);
        PowerMockito.when(UserBaseClient.getUserIdByPhone(anyString())).thenReturn(1L);
        
        authController.phoneAuth(httpRequest);
        PowerMockito.verifyStatic();
        AuthClient.auth(anyObject(),anyObject(),anyObject());
        UserBaseClient.getUserIdByPhone(anyString());
    }
    
    /**
     * 手机号+验证码认证
     * @author 周颖  
     * @date 2017年6月28日 下午5:17:38
     */
    @Test
    public void testPhoneAuthType1() {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("type", "1");
        
        authController.phoneAuth(httpRequest);
    }
    
    /**
     * 手机号+验证码认证 验证码过期或未发送
     * @author 周颖  
     * @date 2017年6月28日 下午5:17:38
     */
    @Test
    public void testPhoneAuthType1Expired() {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("type", "1");
        List<String> smsCodeList = new ArrayList<String>();
        smsCodeList.add("");
        smsCodeList.add("a");
        PowerMockito.when(RedisUtil.hmget(anyString(),anyString())).thenReturn(smsCodeList);
        
        authController.phoneAuth(httpRequest);
        PowerMockito.verifyStatic();
        RedisUtil.hmget(anyString(),anyString());
    }
    
    /**
     * 手机号+验证码认证 验证码过期或未发送
     * @author 周颖  
     * @date 2017年6月28日 下午5:17:38
     */
    @Test
    public void testPhoneAuthType1Equal() {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("type", "1");
        httpRequest.setParameter("smsCode", "123456");
        List<String> smsCodeList = new ArrayList<String>();
        smsCodeList.add("123456");
        PowerMockito.when(RedisUtil.hmget(anyString(),anyString())).thenReturn(smsCodeList);
        
        authController.phoneAuth(httpRequest);
        PowerMockito.verifyStatic();
        RedisUtil.hmget(anyString(),anyString());
    }
    
    /**
     * 手机号+验证码认证 验证码过期或未发送
     * @author 周颖  
     * @date 2017年6月28日 下午5:17:38
     */
    @Test
    public void testPhoneAuthType1NotEqual() {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("type", "1");
        httpRequest.setParameter("smsCode", "1234567");
        List<String> smsCodeList = new ArrayList<String>();
        smsCodeList.add("123456");
        PowerMockito.when(RedisUtil.hmget(anyString(),anyString())).thenReturn(smsCodeList);
        
        authController.phoneAuth(httpRequest);
        PowerMockito.verifyStatic();
        RedisUtil.hmget(anyString(),anyString());
    }

    /**
     * get请求方式
     * @author 周颖  
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testUserAuthGET() {
        httpRequest.setMethod("GET");
        authController.userAuth(httpRequest);
    }
    
    /**
     * post请求方式 特通账号
     * @author 周颖  
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testUserAuthPOSTTT() {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(true);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
    }
    
    /**
    * post请求方式 特通账号
    * @author 周颖  
    * @date 2017年6月29日 上午9:28:52
    */
    @Test
    public void testUserAuthPOSTTTTAIL() {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(true);
        StaticUser staticUser = new StaticUser();
        staticUser.setUserName("sc");
        when(staticUserService.getStaticUser(anyLong(),anyString(),anyString())).thenReturn(staticUser);
       
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        Mockito.verify(staticUserService).getStaticUser(anyLong(),anyString(),anyString());
    }
    
    /**
     * post请求方式  第三方认证失败
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testUserAuthRemoteAuthFail() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("staticUserRemoteAuthUrl");
        Map<String, Object> interfaceReturnValueMap = new HashMap<String, Object>();
        interfaceReturnValueMap.put("result", "FAIL");
        PowerMockito.when(ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString())).thenReturn(interfaceReturnValueMap);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString());
    }
    

    /**
     * post请求方式  第三方认证traceType为空
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testRemoteAuthTypeNull() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("staticUserRemoteAuthUrl");
        Map<String, Object> interfaceReturnValueMap = new HashMap<String, Object>();
        interfaceReturnValueMap.put("result", "OK");
        PowerMockito.when(ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString())).thenReturn(interfaceReturnValueMap);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString());
    }
    
    /**
     * post请求方式  第三方认证traceValue为空
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testRemoteAuthValueNull() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("staticUserRemoteAuthUrl");
        Map<String, Object> interfaceReturnValueMap = new HashMap<String, Object>();
        interfaceReturnValueMap.put("result", "OK");
        interfaceReturnValueMap.put("traceType", "phone");
        PowerMockito.when(ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString())).thenReturn(interfaceReturnValueMap);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString());
    }
    
    /**
     * post请求方式  第三方认证手机号认证
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testRemoteAuthTypePhone() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("staticUserRemoteAuthUrl");
        Map<String, Object> interfaceReturnValueMap = new HashMap<String, Object>();
        interfaceReturnValueMap.put("result", "OK");
        interfaceReturnValueMap.put("traceType", "phone");
        interfaceReturnValueMap.put("traceValue", "13456567654");
        PowerMockito.when(ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString())).thenReturn(interfaceReturnValueMap);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString());
    }
    
    /**
     * post请求方式  第三方认证traceType类型错误
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testRemoteAuthTypeError() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("staticUserRemoteAuthUrl");
        Map<String, Object> interfaceReturnValueMap = new HashMap<String, Object>();
        interfaceReturnValueMap.put("result", "OK");
        interfaceReturnValueMap.put("traceType", "cellphone");
        interfaceReturnValueMap.put("traceValue", "13456567654");
        PowerMockito.when(ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString())).thenReturn(interfaceReturnValueMap);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString());
    }
    
    /**
     * post请求方式  第三方认证traceType类型错误
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testRemoteAuthTypePassport() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("staticUserRemoteAuthUrl");
        Map<String, Object> interfaceReturnValueMap = new HashMap<String, Object>();
        interfaceReturnValueMap.put("result", "OK");
        interfaceReturnValueMap.put("traceType", "passport");
        interfaceReturnValueMap.put("traceValue", "13456567654");
        PowerMockito.when(ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString())).thenReturn(interfaceReturnValueMap);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        ThirdAuthClient.staticUserAuth(anyString(),anyString(),anyString());
    }
    
    /**
     * post请求方式  本地静态用户认证 为空
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testLocalAuthNull() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("");
        when(staticUserService.getStaticUser(anyLong(),anyString(),anyString())).thenReturn(null);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        Mockito.verify(staticUserService).getStaticUser(anyLong(),anyString(),anyString());
    }
    
    /**
     * post请求方式  本地静态用户认证 1手机号
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testLocalAuth1() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("");
        StaticUser staticUser = new StaticUser();
        staticUser.setUserInfoType(1);
        when(staticUserService.getStaticUser(anyLong(),anyString(),anyString())).thenReturn(staticUser);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        Mockito.verify(staticUserService).getStaticUser(anyLong(),anyString(),anyString());
    }
    
    /**
     * post请求方式  本地静态用户认证 2 护照
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testLocalAuth2() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("");
        StaticUser staticUser = new StaticUser();
        staticUser.setUserInfoType(2);
        when(staticUserService.getStaticUser(anyLong(),anyString(),anyString())).thenReturn(staticUser);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        Mockito.verify(staticUserService).getStaticUser(anyLong(),anyString(),anyString());
    }
    
    /**
     * post请求方式  本地静态用户认证 3身份证号
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testLocalAuth3() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("");
        StaticUser staticUser = new StaticUser();
        staticUser.setUserInfoType(3);
        when(staticUserService.getStaticUser(anyLong(),anyString(),anyString())).thenReturn(staticUser);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        Mockito.verify(staticUserService).getStaticUser(anyLong(),anyString(),anyString());
    }
    
    /**
     * post请求方式  本地静态用户认证 4错误
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年6月29日 上午9:28:52
     */
    @Test
    public void testLocalAuth4() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenReturn(false);
        PowerMockito.when(CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong())).thenReturn("");
        StaticUser staticUser = new StaticUser();
        staticUser.setUserInfoType(4);
        when(staticUserService.getStaticUser(anyLong(),anyString(),anyString())).thenReturn(staticUser);
        Map<String, Object> authResultMap = new HashMap<String, Object>();
        authResultMap.put("data", "data");
        PowerMockito.when(AuthClient.auth(anyObject(),anyString(),anyString())).thenReturn(authResultMap);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
        CustomerConfigUtil.getStaticUserAuthUrlCache(anyLong());
        Mockito.verify(staticUserService).getStaticUser(anyLong(),anyString(),anyString());
        AuthClient.auth(anyObject(),anyString(),anyString());
    }

    /**
     * 异常
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月29日 下午2:36:19
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testLocalAuthError() throws Exception {
        httpRequest.setMethod("POST");
        httpRequest.setParameter("customerId", "1");
        PowerMockito.when(RegexUtil.match(anyString(),anyString(),anyInt())).thenThrow(Exception.class);
        
        authController.userAuth(httpRequest);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyString(),anyString(),anyInt());
    }
}
