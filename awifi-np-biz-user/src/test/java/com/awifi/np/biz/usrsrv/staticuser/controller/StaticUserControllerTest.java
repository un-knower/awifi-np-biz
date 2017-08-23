package com.awifi.np.biz.usrsrv.staticuser.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
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
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.service.StaticUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月15日 上午9:05:16
 * 创建作者：周颖
 * 文件名称：StaticUserControllerTest.java
 * 版本：  v1.0
 * 功能：静态用户测试类
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,SessionUtil.class,JsonUtil.class,CastUtil.class,SessionUtil.class, 
        ValidUtil.class,PermissionUtil.class,MessageUtil.class,RegexUtil.class,RegexUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class StaticUserControllerTest {

    /**被测试类*/
    @InjectMocks
    private StaticUserController staticUserController;
    
    /**mock*/
    @Mock(name = "templateService")
    private TemplateService templateService;
    
    /**mock*/
    @Mock(name = "staticUserService")
    private StaticUserService staticUserService;
    
    /**httpRequest*/
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
    }
    
    /**
     * 显示接口抛异常
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午9:21:31
     */
    @Test(expected=BizException.class)
    public void testViewBizException() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue("servicecode_user")).thenReturn("user");
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        
        Map<String,Object> result = staticUserController.view("accessToken", "templateCode", httpRequest);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue("servicecode_user");
        SessionUtil.getCurSessionUser(anyObject());
        MessageUtil.getMessage(anyString());
    }
    
    /**
     * 显示接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午9:27:27
     */
    @Test
    public void testViewBizOk() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue("servicecode_user")).thenReturn("user");
        SessionUser sessionUser = new SessionUser();
        sessionUser.setSuitCode("suitCode");
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        when(templateService.getByCode(anyObject(), anyObject(), anyObject())).thenReturn("template");
        
        Map<String,Object> result = staticUserController.view("accessToken", "templateCode", httpRequest);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue("servicecode_user");
        SessionUtil.getCurSessionUser(anyObject());
        MessageUtil.getMessage(anyString());
        verify(templateService).getByCode(anyObject(), anyObject(), anyObject());
    }

    /**
     * 静态用户列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午10:10:01
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
        Mockito.doNothing().when(staticUserService).getListByParam(anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
        
        Map<String,Object> result = staticUserController.getListByParam("access_token", "{'pageSize':2}", httpRequest);
        
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        JsonUtil.fromJson(anyString(), anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        SysConfigUtil.getParamValue("page_maxsize");
        SessionUtil.getCurSessionUser(anyObject());
        verify(staticUserService).getListByParam(anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
    }
    
    /**
     * 添加静态用户 用户名已存在
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午10:10:41
     */
    @Test(expected=ValidException.class)
    public void testAddUserNameExist() throws Exception {
        Map<String,Object> bodyParam = getParam();
        PowerMockito.when(RegexUtil.match(anyObject(), anyObject(),anyObject())).thenReturn(false);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(1);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(MessageUtil.getMessage(anyObject(),anyObject())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        when(staticUserService.isUserNameExist(anyObject(), anyObject())).thenReturn(true);
        
        Map<String,Object> result = staticUserController.add("accessToken", bodyParam);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyObject(), anyObject(),anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        MessageUtil.getMessage(anyObject(),anyObject());
        verify(staticUserService).isUserNameExist(anyObject(), anyObject());
    }
    
    /**
     * 添加静态用户 手机号已存在
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午10:11:04
     */
    @Test(expected=ValidException.class)
    public void testAddCellphoneExist() throws Exception {
        Map<String,Object> bodyParam = getParam();
        PowerMockito.when(RegexUtil.match(anyObject(), anyObject(),anyObject())).thenReturn(false);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(2);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(MessageUtil.getMessage(anyObject(),anyObject())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        when(staticUserService.isUserNameExist(anyObject(), anyObject())).thenReturn(false);
        when(staticUserService.isCellphoneExist(anyObject(), anyObject())).thenReturn(true);
        
        Map<String,Object> result = staticUserController.add("accessToken", bodyParam);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyObject(), anyObject(),anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        MessageUtil.getMessage(anyObject(),anyObject());
        verify(staticUserService).isUserNameExist(anyObject(), anyObject());
        verify(staticUserService).isCellphoneExist(anyObject(), anyObject());
    }
    
    /**
     * 成功添加静态用户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午10:18:42
     */
    @Test
    public void testAddOK() throws Exception {
        Map<String,Object> bodyParam = getParam();
        PowerMockito.when(RegexUtil.match(anyObject(), anyObject(),anyObject())).thenReturn(false);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(3);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(MessageUtil.getMessage(anyObject(),anyObject())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        when(staticUserService.isUserNameExist(anyObject(), anyObject())).thenReturn(false);
        when(staticUserService.isCellphoneExist(anyObject(), anyObject())).thenReturn(false);
        Mockito.doNothing().when(staticUserService).add(anyObject());
        
        Map<String,Object> result = staticUserController.add("accessToken", bodyParam);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyObject(), anyObject(),anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        MessageUtil.getMessage(anyObject(),anyObject());
        verify(staticUserService).isUserNameExist(anyObject(), anyObject());
        verify(staticUserService).isCellphoneExist(anyObject(), anyObject());
        verify(staticUserService).add(anyObject());
    }

    /**
     * 静态用户模拟数据
     * @return map
     * @author 周颖  
     * @date 2017年2月15日 上午10:20:27
     */
    public Map<String,Object> getParam(){
        Map<String,Object> bodyParam = new HashMap<String,Object>();
        bodyParam.put("cellphone", "cellphone");
        bodyParam.put("passport", "passport");
        bodyParam.put("identityCard", "identityCard");
        bodyParam.put("realName", "realName");
        bodyParam.put("deptName", "deptName");
        return bodyParam;
    }
    
    /**
     * 更新静态用户 手机号已存在
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午10:29:26
     */
    @Test(expected=ValidException.class)
    public void testUpdateValidException() throws Exception {
        Map<String,Object> bodyParam = getParam();
        PowerMockito.when(RegexUtil.match(anyObject(), anyObject(),anyObject())).thenReturn(false);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(1);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(MessageUtil.getMessage(anyObject(),anyObject())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        when(staticUserService.isCellphoneExist(anyObject(),anyObject(), anyObject())).thenReturn(true);
        
        Map<String,Object> result = staticUserController.update("accessToken", bodyParam,1L);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyObject(), anyObject(),anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        MessageUtil.getMessage(anyObject(),anyObject());
        verify(staticUserService).isCellphoneExist(anyObject(),anyObject(), anyObject());
    }
    
    /**
     * userInfoType=3
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午11:10:47
     */
    @Test
    public void testUpdate() throws Exception {
        Map<String,Object> bodyParam = getParam();
        PowerMockito.when(RegexUtil.match(anyObject(), anyObject(),anyObject())).thenReturn(false);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(3);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(MessageUtil.getMessage(anyObject(),anyObject())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        when(staticUserService.isCellphoneExist(anyObject(),anyObject(), anyObject())).thenReturn(false);
        Mockito.doNothing().when(staticUserService).update(anyObject());
        
        Map<String,Object> result = staticUserController.update("accessToken", bodyParam,1L);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyObject(), anyObject(),anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        MessageUtil.getMessage(anyObject(),anyObject());
        verify(staticUserService).isCellphoneExist(anyObject(),anyObject(), anyObject());
        verify(staticUserService).update(anyObject());
    }

    /**
     * 成功更新静态用户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午10:41:18
     */
    @Test
    public void testUpdateOK() throws Exception {
        Map<String,Object> bodyParam = getParam();
        PowerMockito.when(RegexUtil.match(anyObject(), anyObject(),anyObject())).thenReturn(false);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(2);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(MessageUtil.getMessage(anyObject(),anyObject())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        when(staticUserService.isCellphoneExist(anyObject(),anyObject(), anyObject())).thenReturn(false);
        Mockito.doNothing().when(staticUserService).update(anyObject());
        
        Map<String,Object> result = staticUserController.update("accessToken", bodyParam,1L);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RegexUtil.match(anyObject(), anyObject(),anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        MessageUtil.getMessage(anyObject(),anyObject());
        verify(staticUserService).isCellphoneExist(anyObject(),anyObject(), anyObject());
        verify(staticUserService).update(anyObject());
    }
    
    /**
     * 静态用户详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午11:13:34
     */
    @Test
    public void testGetById() throws Exception {
        StaticUser staticUser = new StaticUser();
        when(staticUserService.getById(anyObject())).thenReturn(staticUser);
        
        Map<String,Object> result = staticUserController.getById("accessToken",1L);
        Assert.assertNotNull(result);
        verify(staticUserService).getById(anyObject());
    }

    /**
     * 删除单条静态用户
     * @author 周颖  
     * @date 2017年2月15日 上午11:15:58
     */
    @Test
    public void testDelete() {
        Mockito.doNothing().when(staticUserService).delete(anyObject());
        
        Map<String,Object> result = staticUserController.delete("accessToken",1L);
        Assert.assertNotNull(result);
        verify(staticUserService).delete(anyObject());
    }

    /**
     * 批量删除
     * @author 周颖  
     * @date 2017年2月15日 上午11:16:50
     */
    @Test
    public void testBatchDelete() {
        Mockito.doNothing().when(staticUserService).batchDelete(anyObject());
        
        Map<String,Object> result = staticUserController.batchDelete("accessToken","1,2");
        Assert.assertNotNull(result);
        verify(staticUserService).batchDelete(anyObject());
    }

    /**
     * 删除商户下的所有用户
     * @author 周颖  
     * @date 2017年2月15日 上午11:17:43
     */
    @Test
    public void testDeleteByMerchantId() {
        Mockito.doNothing().when(staticUserService).deleteByMerchantId(anyObject());
        
        Map<String,Object> result = staticUserController.deleteByMerchantId("accessToken",1L);
        Assert.assertNotNull(result);
        verify(staticUserService).deleteByMerchantId(anyObject());
    }
}