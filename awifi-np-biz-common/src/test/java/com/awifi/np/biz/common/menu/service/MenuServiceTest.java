package com.awifi.np.biz.common.menu.service;

import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.menu.dao.MenuDao;
import com.awifi.np.biz.common.menu.model.Menu;
import com.awifi.np.biz.common.menu.service.impl.MenuServiceImpl;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月16日 下午7:02:43
 * 创建作者：许小满
 * 文件名称：MenuServiceTest.java
 * 版本：  v1.0
 * 功能：菜单测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class})
public class MenuServiceTest {

    /**被测试类*/
    @InjectMocks
    private MenuServiceImpl menuService;
    
    /**mock模板服务*/
    @Mock(name = "menuDao")
    private MenuDao menuDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
    
    /**
     * 测试获取对应的菜单
     * @author 亢燕翔  
     * @date 2017年3月22日 上午9:21:24
     */
    @Test
    public void testGetListByParam(){
        SessionUser sessionUser = new SessionUser();
        sessionUser.setRoleIds("11");
        String serviceCode = "xxx";//服务编号
        menuService.getListByParam(sessionUser, serviceCode);
    }
    
    /**
     * 通过服务编号获取菜单集合
     * @author 许小满  
     * @date 2017年2月16日 下午7:06:11
     */
    @Test
    public void getListByParamWithCode(){
        String serviceCode = "xxx";//服务编号
        menuService.getListByParam(serviceCode);
    }
    
    /**
     * 通过服务编号、角色id获取菜单集合
     * @author 许小满  
     * @date 2017年2月16日 下午7:06:11
     */
    @Test
    public void getListByParamWithCodeRoleId(){
        String serviceCode = "xxx";//服务编号
        Long roleId = 1L;//角色id
        menuService.getListByParam(serviceCode, roleId);
    }
    
    /**
     * 角色-菜单关系表  批量更新
     * @author 许小满  
     * @date 2017年2月16日 下午7:06:11
     */
    @Test
    public void batchAddRoleMenu(){
        String serviceCode = "xxx";//服务编号
        Long roleId = 1L;//角色id
        Long[] menuIds = {1L, 2L};//菜单ids
        menuService.batchAddRoleMenu(serviceCode, roleId, menuIds);
    }
    
    /**
     * 通过参数获取菜单
     * @author 许小满  
     * @throws Exception 异常
     * @date 2017年2月16日 下午7:12:29
     */
    @Test
    public void getListByParamPrivate() throws Exception{
        Method targetMethod = menuService.getClass().getDeclaredMethod("getListByParam", String.class, Long.class, Long[].class);
        targetMethod.setAccessible(true);
        
        String serviceCode = "xxx";//服务编号
        Long roleId = 1L;//角色id
        Long[] menuIds = {1L, 2L};//菜单ids
        
        List<Menu> priMenuList = new ArrayList<Menu>();
        Menu priMenu = new Menu();
        priMenu.setId(1L);
        priMenu.setMenuName("xxx");
        priMenu.getMenuName();
        priMenu.setMenuUrl("xxx");
        priMenu.getMenuUrl();
        priMenu.setTargetId("xxx");
        priMenu.getTargetId();
        priMenu.getSubMenus();
        priMenuList.add(priMenu);
        when(menuDao.getListByParam(serviceCode, roleId, menuIds, null)).thenReturn(priMenuList);
        
        targetMethod.invoke(menuService, new Object[]{serviceCode, roleId, menuIds});
    }
    
}
