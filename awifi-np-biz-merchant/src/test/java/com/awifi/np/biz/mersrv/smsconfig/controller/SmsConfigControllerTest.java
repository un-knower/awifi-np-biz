package com.awifi.np.biz.mersrv.smsconfig.controller;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
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

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.smsconfig.service.SmsConfigService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月5日 下午4:55:59
 * 创建作者：许尚敏
 * 文件名称：SmsConfigControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,JsonUtil.class,CastUtil.class,SessionUtil.class, 
        ValidUtil.class,PermissionUtil.class,MessageUtil.class,RegexUtil.class,MerchantClient.class,MessageUtil.class})
public class SmsConfigControllerTest {
	/**被测试类*/
    @InjectMocks
    private SmsConfigController smsConfigController;
    
    /**短信配置服务*/
    @Mock(name = "smsConfigService")
    private SmsConfigService smsConfigService;
    
    /**请求*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
    	MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RegexUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
    }

    /**
     * 测试短信配置列表
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月5日 下午4:57:39
     */
    @Test
    public void testGetListByParam() throws Exception{
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        Mockito.doNothing().when(smsConfigService).getListByParam(anyObject(), anyObject(), anyObject());
        
        Map<String,Object> result = smsConfigController.getListByParam("accessToken", "{'pageSize':2}", httpRequest);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        JsonUtil.fromJson(anyString(), anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        SysConfigUtil.getParamValue("page_maxsize");
        SessionUtil.getCurSessionUser(anyObject());
    }

	/**
     * 测试添加商户短信配置
     * @author 许尚敏
     * @throws Exception 
     * @date 2017年6月5日 下午4:57:39
     */
    @Test
	public void testAddSuccess() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("merchantId", "10");
        bodyParam.put("smsContent", "aaa${code}");
        bodyParam.put("codeLength", "4");
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(10L);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(4);
        Map<String,Object> actual = smsConfigController.add("access_token", bodyParam);
        Assert.assertNotNull(actual);
        PowerMockito.verifyStatic();
        CastUtil.toLong(anyObject());
        CastUtil.toInteger(anyObject());
    }

	/**
     * 测试添加商户短信配置
     * @author 许尚敏
	 * @throws Exception 
     * @date 2017年6月5日 下午4:57:39
     */
    @Test(expected=BizException.class)
	public void testAddForSmsContentException() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("smsContent", "aaa");
        Map<String,Object> actual = smsConfigController.add("access_token", bodyParam);
        Assert.assertNotNull(actual);
    }
	
	/**
     * 测试添加商户短信配置
     * @author 许尚敏
	 * @throws Exception 
     * @date 2017年6月5日 下午4:57:39
     */
    @Test(expected=BizException.class)
	public void testAddForCodeException() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("smsContent", "aaa${code}");
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(2);
        Map<String,Object> actual = smsConfigController.add("access_token", bodyParam);
        Assert.assertNotNull(actual);
        PowerMockito.verifyStatic();
        CastUtil.toInteger(anyObject());
    }

	/**
     * 测试添加商户短信配置
     * @author 许尚敏
	 * @throws Exception 
     * @date 2017年6月5日 下午4:57:39
     */
    @Test(expected=BizException.class)
	public void testAddForIsNotExist() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("merchantId", "10");
        bodyParam.put("smsContent", "aaa${code}");
        bodyParam.put("codeLength", "4");
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(10L);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(4);
        PowerMockito.when(smsConfigService.isExist(anyLong())).thenReturn(true);
        Map<String,Object> actual = smsConfigController.add("access_token", bodyParam);
        Assert.assertNotNull(actual);
        PowerMockito.verifyStatic();
        CastUtil.toLong(anyObject());
        CastUtil.toInteger(anyObject());
        smsConfigService.isExist(anyLong());
    }
	
	/**
     * 测试编辑商户短信配置
     * @author 许尚敏
     * @throws Exception 
     * @date 2017年6月5日 下午4:57:39
     */
    @Test(expected=BizException.class)
	public void testUpdate() throws Exception{
        Map<String,Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("smsContent", "aaa${code}");
        bodyParam.put("codeLength", 4);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(4);
        Map<String,Object> actual = smsConfigController.update("access_token", bodyParam, 1L);
        Assert.assertNotNull(actual);
        bodyParam.put("smsContent", "aaa");
        actual = smsConfigController.update("access_token", bodyParam, 1L);
        Assert.assertNotNull(actual);
    }
	
	/**
     * 测试编辑商户短信配置
     * @author 许尚敏
     * @throws Exception 
     * @date 2017年6月5日 下午4:57:39
     */
    @Test(expected=BizException.class)
	public void testUpdateForSmsContentExcep() throws Exception {
        Map<String,Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("smsContent", "aaa");
        Map<String,Object> actual = smsConfigController.update("access_token", bodyParam, 1L);
        Assert.assertNotNull(actual);
    }
	
	/**
     * 测试编辑商户短信配置
     * @author 许尚敏
     * @throws Exception 
     * @date 2017年6月5日 下午1:57:39
     */
    @Test(expected=BizException.class)
	public void testUpdateForCodeException() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("smsContent", "aaa${code}");
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(2);
        Map<String,Object> actual = smsConfigController.update("access_token", bodyParam, 1L);
        Assert.assertNotNull(actual);
        PowerMockito.verifyStatic();
        CastUtil.toInteger(anyObject());
    }
	
	/**
     * 测试短信配置详情
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月5日 下午4:57:39
     */
    @Test
	public void testGetById()  throws Exception{
        smsConfigController.getById("access_token", 1L);
    }

	/**
     * 测试短信配置删除
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月5日 下午4:57:39
     */
    @Test
	public void testDelete() throws Exception{
        smsConfigController.delete("access_token", 14L);
    }

}
