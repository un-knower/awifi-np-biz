package com.awifi.np.biz.toe.admin.security.role.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.toe.admin.security.org.util.OrgUtil;
import com.awifi.np.biz.toe.admin.security.role.dao.ToeRoleDao;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月20日 上午10:24:21
 * 创建作者：周颖
 * 文件名称：ToeRoleServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(OrgUtil.class)
public class ToeRoleServiceImplTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private ToeRoleServiceImpl toeRoleServiceImpl;
    
    /** 角色持久层  */
    @Mock(name = "toeRoleDao")
    private ToeRoleDao toeRoleDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(OrgUtil.class);
    }
    
    /**
     * 通过角色ID获取组织ID
     * @author 周颖  
     * @date 2017年2月20日 上午11:05:16
     */
    @Test
    public void testGetOrgIdByBizRoleIds() {
        toeRoleServiceImpl.getOrgIdByBizRoleIds("1,2,3");
    }

    /**
     * 维护账号角色关系
     * @author 周颖  
     * @date 2017年2月20日 上午11:00:44
     */
    @Test
    public void testAddUserRole() {
        toeRoleServiceImpl.addUserRole(1L, "1,2,3");
    }

    /**
     * 获取账号角色
     * @author 周颖  
     * @date 2017年2月20日 上午11:01:01
     */
    @Test
    public void testGetNamesByUserId() {
        toeRoleServiceImpl.getNamesByUserId(1L);
    }

    /**
     * 获取角色 组织是awifi
     * @author 周颖  
     * @date 2017年2月20日 上午11:01:32
     */
    @Test
    public void testGetListByOrgIdAwifi() {
        PowerMockito.when(OrgUtil.isAwifi(1L)).thenReturn(true);
        
        SessionUser user = new SessionUser();
        toeRoleServiceImpl.getListByOrgId(1L,user, 1L);
        
        PowerMockito.verifyStatic();
        OrgUtil.isAwifi(1L);
    }
    
    /**
     * 俩组织id不同
     * @author 周颖  
     * @date 2017年2月20日 上午11:02:17
     */
    @Test
    public void testGetListByOrgId() {
        SessionUser user = new SessionUser();
        toeRoleServiceImpl.getListByOrgId(2L,user, 3L);
        
    }
    
    /**
     * 俩组织id相同
     * @author 周颖  
     * @date 2017年2月20日 上午11:02:17
     */
    @Test
    public void testGetListByOrgIdEqual() {
        SessionUser user = new SessionUser();
        toeRoleServiceImpl.getListByOrgId(2L,user, 2L);
        
    }

    /**
     * 更新账号角色关系
     * @author 周颖  
     * @date 2017年2月20日 上午11:03:47
     */
    @Test
    public void testUpdateUserRole() {
        toeRoleServiceImpl.updateUserRole(1L, "1,2,3");
    }
    
    /**
     * 测试获取np角色列表
     * @author 方志伟  
     * @date 2017年6月21日 上午9:19:07
     */
    @Test
    public void testGetIdsById(){
        toeRoleServiceImpl.getIdsById(1L);
    }
    
    /**
     * 测试通过角色名称获取角色id
     * @author 方志伟  
     * @date 2017年6月21日 上午9:24:40
     */
    @Test
    public void testGetIdByName(){
        toeRoleServiceImpl.getIdByName("roleName"); 
    }
    
    /**
     * 测试通过角色名称获取角色id
     * @author 方志伟  
     * @date 2017年6月21日 上午9:24:42
     */
    @Test
    public void testGetIdByNameAndParentId(){
        toeRoleServiceImpl.getIdByName(1L, "roleName");
    }
}