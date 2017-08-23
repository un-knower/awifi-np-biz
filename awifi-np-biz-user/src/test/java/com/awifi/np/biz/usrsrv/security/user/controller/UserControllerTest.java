/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月5日 下午1:30:21
* 创建作者：季振宇
* 文件名称：UserControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.usrsrv.security.user.controller;

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

import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.admin.security.user.service.UserService;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;
import com.awifi.np.biz.toe.admin.security.org.util.OrgUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,SessionUtil.class,JsonUtil.class,CastUtil.class,SessionUtil.class, ValidUtil.class,OrgUtil.class,MessageUtil.class,RegexUtil.class,RegexUtil.class,BizException.class})
@PowerMockIgnore({"javax.management.*"})
public class UserControllerTest {
	/**用户服务*/
    @Mock(name = "userService")
    private UserService userService;
    
    /**工程服务*/
    @Mock(name = "projectService")
    private ProjectService projectService;
	
	/**被测试类*/
    @InjectMocks
    private UserController userController;
    
    /**请求*/
    private MockHttpServletRequest request;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(OrgUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RegexUtil.class);
        PowerMockito.mockStatic(BizException.class);
    }

    /**
     * 测试列表
     * @author 季振宇  
     * @throws Exception 
     * @date 2017年6月5日 下午1:53:23
     */
    @Test
	public void testGetListByParam() throws Exception{
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(paramsMap);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Mockito.doNothing().when(userService).getListByParams(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject());
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String,Object> result = userController.getListByParam("access_token", "{'pageSize':2}", request);
        Assert.assertNotNull(result);
    }

    /**
     * 测试列表
     * @author 季振宇  
     * @throws Exception 
     * @date 2017年6月5日 下午1:57:16
     */
    @Test
	public void testAdd() throws Exception{
    	Map<String,Object> bodyParam = getParam();
        Mockito.doNothing().when(userService).add(anyObject());
        Map<String,Object> result = userController.add("access_token", bodyParam);
        Assert.assertNotNull(result);
        verify(userService).add(anyObject());
    }
    
    /**
     * 用户模拟数据
     * @return map
     * @author 季振宇  
     * @date 2017年6月5日 下午2:11:26
     */
    private Map<String,Object> getParam() {
    	Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("userName", "jizhenyu");
        paramsMap.put("provinceId",31);
        paramsMap.put("cityId",383);
        paramsMap.put("areaId",3883);
        paramsMap.put("roleIds","roleIds");
        paramsMap.put("projectIds","projectIds");
        paramsMap.put("filterProjectIds","filterProjectIds");
        paramsMap.put("merchantIds","merchantIds");
        paramsMap.put("deptName","deptName");
        paramsMap.put("contactPerson","contactPerson");
        paramsMap.put("contactWay","contactWay");
        paramsMap.put("remark","remark");
        return paramsMap;
    }

    /**
     * 更新用户
     * @author 季振宇  
     * @throws Exception 
     * @date 2017年6月5日 下午2:11:26
     */
    @Test
	public void testUpdate() throws Exception{
        PowerMockito.when(CastUtil.toLong(31)).thenReturn(31L);
        PowerMockito.when(CastUtil.toLong(383)).thenReturn(383L);
        PowerMockito.when(CastUtil.toLong(3883)).thenReturn(3883L);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Mockito.doNothing().when(userService).update(anyObject());
        when(userService.isUserNameExist(anyObject())).thenReturn(false);
        
        Map<String,Object> bodyParam =  getParam();
        
        Map<String,Object> result = userController.update("access_token", bodyParam, 1L);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toLong(31);
        CastUtil.toLong(383);
        CastUtil.toLong(3883);
        ValidUtil.valid(anyObject(),anyObject(),anyObject());
        verify(userService).update(anyObject());
    }

    /**
     * 商户详情
     * @throws Exception 异常
     * @author 季振宇  
     * @date 2017年6月5日 下午2:26:54
     */
    @Test
	public void testGetById() throws Exception{
        User user = new User();
        user.setProjectIds("ProjectIds");
        user.setFilterProjectIds("FilterProjectIds");
        when(userService.getUserById(anyObject())).thenReturn(user);
        when(projectService.getNamesByIds(anyObject())).thenReturn("projectNames");
        
        Map<String,Object> result = userController.getById("access_token", 1L);
        Assert.assertNotNull(result);
        verify(userService).getUserById(anyObject());
    }

	/**
     * 测试删除
     * @throws Exception 异常
     * @author 季振宇  
     * @date 2017年6月5日 下午2:07:31
     */
    @Test
	public void testDelete() throws Exception{
        Mockito.doNothing().when(userService).delete(1L);
        Map<String,Object> result = userController.delete("access_token", 1L);
        Assert.assertNotNull(result);
        verify(userService).delete(1L);
    }

    /**
     * 测试重置密码
     * @throws Exception 异常
     * @author 季振宇  
     * @date 2017年6月5日 下午2:35:22
     */
    @Test
	public void testResetPassword() throws Exception{
        Mockito.doNothing().when(userService).resetPassword(1L);;
        Map<String,Object> result = userController.resetPassword("access_token", 1L);
        Assert.assertNotNull(result);
        verify(userService).resetPassword(1L);
    }

}
