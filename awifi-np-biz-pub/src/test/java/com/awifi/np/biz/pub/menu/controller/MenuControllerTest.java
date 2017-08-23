package com.awifi.np.biz.pub.menu.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.admin.service.service.ServiceService;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.menu.model.Menu;
import com.awifi.np.biz.common.menu.service.MenuService;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午2:31:20
 * 创建作者：周颖
 * 文件名称：MenuControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisAdminUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class MenuControllerTest {

    /**被测试类*/
    @InjectMocks
    private MenuController menuController;
    
    /**mock*/
    @Mock(name = "menuService")
    private MenuService menuService;
    
    /**mock服务业务层*/
    @Mock(name="serviceService")
    private ServiceService serviceService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RedisAdminUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
    }
    
    /**
     * 获取一级菜单抛异常
     * @author 周颖  
     * @date 2017年1月13日 下午2:49:18
     */
    @Test(expected=BizException.class)
    public void testGetTopMenusBizException() {
        
        PowerMockito.when(RedisAdminUtil.get(anyString())).thenReturn(null);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        
        Map<String,Object> result = menuController.getTopMenus("result");
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        RedisAdminUtil.get(anyString());
        MessageUtil.getMessage(anyString());
        
    }
    
    /**
     * 获取一级菜单成功
     * @author 周颖  
     * @date 2017年1月13日 下午2:50:14
     */
    @Test
    public void testGetTopMenusOK() {
        Map<String,Object> value = new HashMap<String,Object>();
        Map<String, Object> userInfo = new HashMap<String,Object>();
        userInfo.put("roleIds", "1,2,3");
        value.put("userInfo", userInfo);
        value.put("appId", "PUB");
        List<Map<String,Object>> menusList = new ArrayList<Map<String,Object>>();
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("menuName", "商户管理");//保存菜单名称
        dataMap.put("menuUrl", "/merchant");//保存菜单url
        dataMap.put("targetId", "1");//保存显示区域
        dataMap.put("has_submenu",true);//保存是否有下级菜单
        menusList.add(dataMap);
        
        PowerMockito.when(RedisAdminUtil.get(anyString())).thenReturn("result");
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(value);
        when(serviceService.getTopMenus(anyString(), anyObject())).thenReturn(menusList);
        
        Map<String,Object> result = menuController.getTopMenus("result");
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        RedisAdminUtil.get(anyString());
        JsonUtil.fromJson(anyString(),anyObject());
    }
    
    /**
     * 获取二级菜单接口
     * @author 周颖  
     * @date 2017年2月15日 上午9:02:01
     */
    @Test
    public void testGetListByParam() {
        PowerMockito.when(SysConfigUtil.getParamValue("servicecode_pub")).thenReturn("user");
        
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        
        List<Menu> menuList = new ArrayList<Menu>();
        when(menuService.getListByParam(anyObject(), anyString())).thenReturn(menuList);
        
        Map<String,Object> result = menuController.getListByParam("access_token", httpRequest);
        
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SessionUtil.getCurSessionUser(anyObject());
    }
    
}