package com.awifi.np.biz.mersrv.menu.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.awifi.np.biz.common.menu.model.Menu;
import com.awifi.np.biz.common.menu.service.MenuService;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 上午10:53:43
 * 创建作者：周颖
 * 文件名称：MenuControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class MenuControllerTest {

    /**被测试类*/
    @InjectMocks
    private MenuController menuController;
    
    /**mock*/
    @Mock(name = "menuService")
    private MenuService menuService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
    }
    
    /**
     * 获取二级菜单接口
     * @author 周颖  
     * @date 2017年2月15日 上午9:02:01
     */
    @Test
    public void testGetListByParam() {
        PowerMockito.when(SysConfigUtil.getParamValue("servicecode_merchant")).thenReturn("merchant");
        
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        
        List<Menu> menuList = new ArrayList<Menu>();
        when(menuService.getListByParam(anyObject(), anyString())).thenReturn(menuList);
        
        Map<String,Object> result = menuController.getListByParam("access_token", httpRequest);
        
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue("servicecode_merchant");
        SessionUtil.getCurSessionUser(anyObject());
    }
    
    /**
     * 获取所有菜单接口--管理系统
     * @author 许小满  
     * @date 2017年2月16日 下午4:00:12
     */
    @Test
    public void getListByServiceCodeForAm(){
        Map<String,Object> resultMap = menuController.getListByServiceCodeForAm();
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
    
    /**
     * 获取某一角色关联的菜单接口--管理系统
     * @author 许小满  
     * @date 2017年2月16日 下午4:03:08
     */
    @Test
    public void getListByRoleIdForAm(){
        String roleId = "1";//角色id
        Map<String,Object> resultMap = menuController.getListByRoleIdForAm(roleId);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
    
    /**
     * 菜单批量更新接口 -- 管理系统
     * @author 许小满  
     * @date 2017年2月16日 下午4:27:38
     */
    @Test
    public void batchAddRoleMenuForAm(){
        String roleId = "1";//角色id
        Long[] menuIds = {1L,2L};//菜单id数组
        Map<String,Object> resultMap = menuController.batchAddRoleMenuForAm(roleId, menuIds);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
}
