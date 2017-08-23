package com.awifi.np.admin.security.role.service.impl;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.admin.security.role.dao.RoleDao;
import com.awifi.np.biz.api.client.npadmin.util.NPAdminClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月16日 下午2:37:03
 * 创建作者：周颖
 * 文件名称：RoleServiceImplTest.java
 * 版本：  v1.0
 * 功能：获取角色id测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtil.class,SysConfigUtil.class,NPAdminClient.class})
public class RoleServiceImplTest {

    /**注入被测试类*/
    @InjectMocks
    private RoleServiceImpl roleServiceImpl;
    
    /**mock*/
    @Mock(name="roleDao")
    private RoleDao roleDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(NPAdminClient.class);
    }
    
    /**
     * testGetIdsById
     * @author 周颖  
     * @date 2017年1月16日 下午2:49:04
     */
    @Test
    public void testGetIdsById() {
        List<Long> roleIds = new ArrayList<Long>();
        roleIds.add(1L);
        when(roleDao.getIdsById(anyLong())).thenReturn(roleIds);
        List<Long> result = roleServiceImpl.getIdsById(1L);
        Assert.assertNotNull(result);
    }
    
    /**
     * 根据角色ids获取角色名称 角色ids为空
     * @author 周颖  
     * @date 2017年2月24日 下午2:24:52
     */
    @Test
    public void testGetNamesByIdsNull(){
        String result = roleServiceImpl.getNamesByIds(null);
        Assert.assertNotNull(result);
    }
    
    /**
     * 根据角色ids获取角色名称 角色ids为空
     * @author 周颖  
     * @date 2017年2月24日 下午2:24:52
     */
    @Test
    public void testGetNamesByIds(){
        List<String> roleNames = new ArrayList<String>();
        roleNames.add("角色1");
        roleNames.add("角色2");
        when(roleDao.getNamesByIds(anyObject())).thenReturn(roleNames);
        
        String result = roleServiceImpl.getNamesByIds("1,2,3");
        Assert.assertNotNull(result);
    }
    
    /**
     * 根据角色ids获取角色名称 角色ids为空
     * @author 周颖  
     * @date 2017年2月24日 下午2:24:52
     */
    @Test
    public void testGetNamesByIdsEmpty(){
        List<String> roleNames = new ArrayList<String>();
        when(roleDao.getNamesByIds(anyObject())).thenReturn(roleNames);
        
        String result = roleServiceImpl.getNamesByIds("1,2,3");
        Assert.assertNotNull(result);
    }
    
    /**
     * 根据roleId获取权限
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月22日 下午2:48:53
     */
    @Test
    public void testGetPermisionByRoleId() throws Exception {
        roleServiceImpl.getPermisionByRoleId(1L);
    }
    
    /**
     * 根据roleId获取权限
     * @throws Exception 异常
     * @author 方志伟
     * @date 2017年6月22日 下午2:49:03
     */
    @Test
    public void testGetPermisionByRoleIdNull() throws Exception {
        roleServiceImpl.getPermisionByRoleId(null);
    }
    
    /**
     * 维护账号角色关系
     * @author 方志伟  
     * @date 2017年6月22日 下午3:12:21
     */
    @Test
    public void testAddUserRole() {
        roleServiceImpl.addUserRole(1L, "1,2,3");
    }
    
    /**
     * 删除账号角色
     * @author 方志伟  
     * @date 2017年6月22日 下午3:17:21
     */
    @Test
    public void testDeleteUserRole() {
        roleServiceImpl.deleteUserRole(1L);
    }
    
    /**
     * 获取全部角色
     * @author 方志伟  
     * @date 2017年6月22日 下午3:19:38
     */
    @Test
    public void testGetAllRole() {
        roleServiceImpl.getAllRole();
    }
    
    /**
     * 获取角色列表
     * @author 方志伟  
     * @date 2017年6月22日 下午3:21:23
     */
    @Test
    public void testGetIdsAndNamesByRoleIds(){
        roleServiceImpl.getIdsAndNamesByRoleIds("1,2,3");
    }
    
    /**
     * 获取用户角色名称
     * @author 方志伟  
     * @date 2017年6月22日 下午3:23:26
     */
    @Test
    public void testGetIdsAndNamesByUserId(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("roleIds", "roleIdsValue");
        List<Map<String,Object>> roleList = new ArrayList<Map<String, Object>>();
        roleList.add(paramMap);
        when(roleDao.getNamesByUserId(anyObject())).thenReturn(roleList);
        roleServiceImpl.getIdsAndNamesByUserId(1L);
    }
}