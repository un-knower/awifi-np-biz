package com.awifi.np.biz.devsrv.menu.controller;

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

import com.awifi.np.biz.common.menu.service.MenuService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 下午3:15:11
 * 创建作者：亢燕翔
 * 文件名称：MenuControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class MenuControllerTest {

    /**被测试类*/
    @InjectMocks
    private MenuController menuController;
 
    /**菜单业务层*/
    @Mock(name = "menuService")
    private MenuService menuService;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /**
     * 测试获取菜单
     * @author 亢燕翔  
     * @date 2017年2月14日 下午3:20:12
     */
    @Test
    public void testGetListByParam(){
        String accessToken = "XXX";
        Map<String, Object> resultMap = menuController.getListByParam(request, accessToken);
        Assert.assertNotNull(resultMap);
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
