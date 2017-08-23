package com.awifi.np.biz.toe.admin.security.user.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;
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

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.dao.ToeUserDao;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月20日 上午9:54:17
 * 创建作者：周颖
 * 文件名称：ToeUserServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtil.class,SysConfigUtil.class,MessageUtil.class})
public class ToeUserServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private ToeUserServiceImpl toeUserServiceImpl;
    
    /**toe用户dao*/
    @Mock(name = "toeUserDao")
    private ToeUserDao toeUserDao;
    /**toe用户service*/
    @Mock(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
    
    /**
     * 获取商户id
     * @author 周颖  
     * @date 2017年2月20日 上午10:04:39
     */
    @Test
    public void testGetMerIdByUserName() {
        Long id = toeUserServiceImpl.getMerIdByUserName("userName");
        Assert.assertNotNull(id);
    }

    /**
     * 判断用户名是否存在
     * @author 周颖  
     * @date 2017年2月20日 上午10:10:28
     */
    @Test
    public void testIsUserNameExist() {
        toeUserServiceImpl.isUserNameExist("userName");
    }

    /**
     * 维护商户账号对应关系
     * @author 周颖  
     * @date 2017年2月20日 上午10:13:17
     */
    @Test
    public void testAddUserMerchant() {
        toeUserServiceImpl.addUserMerchant(1L, 1L);
    }

    /**
     * 通过商户id获取账号信息
     * @author 周颖  
     * @date 2017年2月20日 上午10:20:04
     */
    @Test
    public void testGetByMerchantId() {
        toeUserServiceImpl.getByMerchantId(1L);
    }

    /**
     * 通过商户id获取账号id
     * @author 周颖  
     * @date 2017年2月20日 上午10:21:38
     */
    @Test
    public void testGetIdByMerchantId() {
        toeUserServiceImpl.getIdByMerchantId(1L);
    }

    /**
     * 修改账号
     * @author 周颖  
     * @date 2017年2月20日 上午10:21:58
     */
    @Test
    public void testUpdate() {
        ToeUser user = new ToeUser();
        toeUserServiceImpl.update(1L, user);
    }
    
    /**
     * 新建账号
     * @author 方志伟  
     * @date 2017年6月21日 上午10:52:54
     */
    @Test
    public void testAdd(){
        ToeUser user = new ToeUser();
        toeUserServiceImpl.add(user);
    }
    
    /**
     * 根据用户名密码获取用户信息
     * @author 方志伟  
     * @date 2017年6月21日 上午10:54:43
     */
    @Test
    public void testGetByNameAndPwdNull(){
        toeUserServiceImpl.getByNameAndPwd("admin", "e10adc3949ba59abbe56e057f20f883e");
    }
    
    @Test
    public void testGetByNameAndPwd(){
        ToeUser user = new ToeUser();
        user.setId(1L);
        when(toeUserDao.getByNameAndPwd(anyObject(),anyString())).thenReturn(user);
        List<Long> list = new ArrayList<Long>();
        list.add(user.getId());
        when(toeRoleService.getIdsById(anyObject())).thenReturn(list);
        toeUserServiceImpl.getByNameAndPwd("userName", "password");
    }
    
    /**
     * 通过用户id获取用户信息 id != null
     * @author 方志伟  
     * @date 2017年6月21日 上午11:03:03
     */
    @Test
    public void testGetById(){
        toeUserServiceImpl.getById(1L);
    }
    
    /**
     * 通过用户id获取用户信息 id == null
     * @author 方志伟  
     * @date 2017年6月21日 上午11:03:08
     */
    @Test 
    public void testGetByIdIsNull(){
        toeUserServiceImpl.getById(null);
    }
    
    /**
     * 更新用户信息
     * @author 方志伟  
     * @date 2017年6月21日 上午11:05:05
     */
    @Test
    public void testUpdateById(){
        ToeUser user = new ToeUser();
        toeUserServiceImpl.updateById(user);
    }
    
    /**
     * 更新用户密码
     * @author 方志伟  
     * @date 2017年6月21日 上午11:06:12
     */
    @Test
    public void testUpdatePwdById(){
        toeUserServiceImpl.updatePwdById(1L, "123123");;
    }
    
    /**
     * 更新用户项目归属
     * @author 方志伟  
     * @date 2017年6月21日 上午11:07:45
     */
    @Test
    public void testUpdateProject(){
        toeUserServiceImpl.updateProject(1L, 2L);
    }
    
    /**
     * 重置商户账号密码
     * @author 方志伟  
     * @date 2017年6月21日 上午11:08:36
     */
    @Test(expected=BizException.class)
    public void testResetPassword(){
        toeUserServiceImpl.resetPassword(1L);
    }
    
    /**
     * 批量获取商户的账号
     * @author 方志伟  
     * @date 2017年6月21日 上午11:14:14
     */
    @Test
    public void testGetNameByMerchantIds(){
        Map<Long,String> resultMap = new HashMap<Long,String>();
        List<Map> list = new ArrayList<Map>();
        resultMap.put(1L, "userName");
        list.add(resultMap);
        when(toeUserDao.getNameByMerchantIds(anyObject())).thenReturn(list);
        List<Long> merchantIdList = new ArrayList<Long>();
        merchantIdList.add(1L);
        merchantIdList.add(2L);
        toeUserServiceImpl.getNameByMerchantIds(merchantIdList);
    }
    
    /**
     * 根据用户名查询id和username
     * @author 方志伟  
     * @date 2017年6月21日 上午11:19:40
     */
    @Test
    public void testGetIdAndUserNameByUser(){
        Map<Long,String> resultMap = new HashMap<Long,String>();
        List<Map> list = new ArrayList<Map>();
        resultMap.put(1L, "userName");
        list.add(resultMap);
        when(toeUserDao.getIdAndUserNameByUsernames(anyObject())).thenReturn(list);
        toeUserServiceImpl.getIdAndUserNameByUsernames("admin");
    }
    
    /**
     * 根据商户商户账号集合查询
     * @author 方志伟  
     * @date 2017年6月21日 上午11:22:01
     */
    @Test
    public void testGetUserNameAndIdByUser(){
        List<Map> list = new ArrayList<Map>();
        Map<String,Long> resultMap = new HashMap<String,Long>();
        resultMap.put("userName", 1L);
        list.add(resultMap);
        when(toeUserDao.getIdAndUserNameByUsernames(anyObject())).thenReturn(list);
        Set<String> set = new HashSet<String>();
        set.add("admin");
        set.add("superadmin");
        toeUserServiceImpl.getUserNameAndIdByUsernames(set);
    }
}