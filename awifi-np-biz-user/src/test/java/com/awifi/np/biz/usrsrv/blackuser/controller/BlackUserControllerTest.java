package com.awifi.np.biz.usrsrv.blackuser.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.model.BlackUser;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.service.BlackUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 下午2:06:13
 * 创建作者：周颖
 * 文件名称：BlackUserControllerTest.java
 * 版本：  v1.0
 * 功能：黑名单测试类
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,JsonUtil.class,CastUtil.class,SessionUtil.class, ValidUtil.class,PermissionUtil.class,MessageUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class BlackUserControllerTest {

    /**被测试类*/
    @InjectMocks
    private BlackUserController blackUserController;
    
    /**mock*/
    @Mock(name = "blackUserService")
    private BlackUserService blackUserService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
   
    /**
     * 黑名单列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月14日 下午4:16:26
     */
    @Test
    public void testGetListByParam() throws Exception {
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        Mockito.doNothing().when(blackUserService).getListByParam(anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
        
        Map<String,Object> result = blackUserController.getListByParam("access_token", "{'pageSize':2}", httpRequest);
        
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        JsonUtil.fromJson(anyString(), anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        SysConfigUtil.getParamValue("page_maxsize");
        SessionUtil.getCurSessionUser(anyObject());
    }
    
    /**
     * 成功添加黑名单 手机号模糊匹配
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月14日 下午4:28:28
     */
    @Test
    public void testAddOk() throws Exception {
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(2);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        
        when(blackUserService.isCellphoneExist(anyObject(), anyObject())).thenReturn(false);
        BlackUser blackUser = new BlackUser();
        Mockito.doNothing().when(blackUserService).add(blackUser);
        
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        Map<String,Object> result = blackUserController.add("access_token", paramsMap);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());       
    }
    
    /**
     * 成功添加黑名单 手机号精确匹配
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月14日 下午4:28:28
     */
    @Test
    public void testAddOkT() throws Exception {
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(1);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        
        when(blackUserService.isCellphoneExist(anyObject(), anyObject())).thenReturn(false);
        BlackUser blackUser = new BlackUser();
        Mockito.doNothing().when(blackUserService).add(blackUser);
        
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        Map<String,Object> result = blackUserController.add("access_token", paramsMap);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());       
    }
    
    /**
     * 添加黑名单失败 
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月14日 下午4:28:28
     */
    @Test(expected = ValidException.class)
    public void testAddValidException() throws Exception {
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(3);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        Map<String,Object> result = blackUserController.add("access_token", paramsMap);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject()); 
        MessageUtil.getMessage(anyString());
    }
    
    
    /**
     * 添加黑名单失败 
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月14日 下午4:28:28
     */
    @Test(expected = BizException.class)
    public void testAddBizException() throws Exception {
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(1);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        
        when(blackUserService.isCellphoneExist(anyObject(), anyObject())).thenReturn(true);
        
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        Map<String,Object> result = blackUserController.add("access_token", paramsMap);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject()); 
        MessageUtil.getMessage(anyString());
    }

    /**
     * 删除黑名单
     * @author 周颖  
     * @date 2017年2月14日 下午4:28:11
     */
    @Test
    public void testDelete() {
        Mockito.doNothing().when(blackUserService).delete(1L);
        Map<String,Object> result = blackUserController.delete("acess_token", 1L);
        Assert.assertNotNull(result);
    }
}