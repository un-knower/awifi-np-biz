package com.awifi.np.admin.security.user.service.impl;

import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.awifi.np.admin.security.role.service.RoleService;
import com.awifi.np.admin.security.user.dao.UserDao;
import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.admin.suit.service.SuitService;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午5:19:45
 * 创建作者：周颖
 * 文件名称：UserServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,CastUtil.class,BeanUtil.class,SysConfigUtil.class,RedisUtil.class,MerchantClient.class,LocationClient.class})
@PowerMockIgnore({"javax.management.*"})
public class UserServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    
    /**mock用户dao*/
    @Mock(name = "userDao")
    private UserDao userDao;
   
    /**mock角色服务层*/
    @Mock(name = "roleService")
    private RoleService roleService;
    
    /**mock套服务层*/
    @Mock(name = "suitService")
    private SuitService suitService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(LocationClient.class);
    }
    
    /**
     * 根据用户名密码未找到用户信息
     * @author 周颖  
     * @date 2017年1月16日 上午9:48:50
     */
    @Test
    public void testGetByNameAndPwdNotFound() {
        when(userDao.getByNameAndPwd(anyString(), anyString())).thenReturn(null);
        
        User user = userServiceImpl.getByNameAndPwd("userName", "password");
       
        Assert.assertNull(user);
        PowerMockito.verifyStatic();
    }
   
    /**
     * 没有套码
     * @author 周颖  
     * @date 2017年1月16日 上午9:48:57
     */
    @Test(expected=BizException.class)
    public void testGetByNameAndPwdNull() {
        User user = new User();
        user.setId(1L);
        List<Long> roleList = new ArrayList<Long>();
        roleList.add(1L);
        PowerMockito.when(CastUtil.listToString(anyList(),anyChar())).thenReturn("1");
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        when(userDao.getByNameAndPwd(anyString(), anyString())).thenReturn(user);
        when(roleService.getIdsById(anyLong())).thenReturn(roleList);
        when(suitService.getCodeById(anyObject())).thenReturn(null);
        user.getId();
        user.getUserName();
        user.getPassword();
        user.getProvinceId();
        user.getCityId();
        user.getAreaId();
        user.getContactPerson();
        user.getContactWay();
        user.getCreateUserId();
        user.getEmail();
        user.getCreateDate();
        user.getUpdateDate();
        user.getMerchantId();
        user.getRealname();
        user.getNickname();
        user.getRemark();
        user.getStatus();
        user.getSuitCode();
        user.getRoleIds();
        
        User resultUser = userServiceImpl.getByNameAndPwd("userName", "password");
        Assert.assertNotNull(resultUser);
        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyString());
        CastUtil.listToString(anyList(),anyChar());
    }
    
   /**
    * 根据角色找到套码
    * @author 周颖  
    * @date 2017年1月16日 上午9:49:39
    */
    @Test
    public void testGetByNameAndPwdNotNull() {
        User user = new User();
        user.setId(1L);
        user.setUserName("userName");
        user.setPassword("password");
        user.setProvinceId(31L);
        user.setCityId(383L);
        user.setAreaId(3220L);
        user.setRealname("realname");
        user.setNickname("nickname");
        user.setEmail("email");
        user.setContactPerson("contactPersion");
        user.setContactWay("contactWay");
        user.setRemark("remark");
        user.setStatus(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setMerchantId(3220L);
        user.setCreateUserId(1L);
        List<Long> roleList = new ArrayList<Long>();
        roleList.add(1L);
        PowerMockito.when(CastUtil.listToString(anyList(),anyChar())).thenReturn("1");
        when(userDao.getByNameAndPwd(anyString(), anyString())).thenReturn(user);
        when(roleService.getIdsById(anyLong())).thenReturn(roleList);
        when(suitService.getCodeById(anyObject())).thenReturn("suitCode");
        
        User resultUser = userServiceImpl.getByNameAndPwd("userName", "password");
        Assert.assertNotNull(resultUser);
        PowerMockito.verifyStatic();
        CastUtil.listToString(anyList(),anyChar());
    }
    
    /**
     * 更新密码
     * @author 许小满  
     * @date 2017年2月24日 上午10:07:33
     */
    @Test
    public void testUpdatePwdById(){
        Long id = 1L;
        String password = "e10adc3949ba59abbe56e057f20f883e";
        userServiceImpl.updatePwdById(id, password);
    }
 
    /**
     * 用户id为空
     * @author 周颖  
     * @date 2017年2月24日 下午2:11:42
     */
    @Test
    public void testGetByIdNull(){
        userServiceImpl.getById(null);
    }
    
    /**
     * 用户id不为空
     * @author 周颖  
     * @date 2017年2月24日 下午2:12:37
     */
    @Test
    public void testGetById(){
        userServiceImpl.getById(1L);
    }
    
    /***
     * 更新用户信息
     * @author 周颖  
     * @date 2017年2月24日 下午2:13:39
     */
    @Test
    public void testUpdateById(){
        userServiceImpl.updateById(null);
    }
    
    /**
     * 管理员账号列表
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月23日 下午4:12:11
     */
    @Test
    public void testGetListByParams() throws Exception {
        User user = new User();
        user.setProvinceId(1L);
        user.setCityId(1L);
        user.setAreaId(2L);
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        when(userDao.getCountByParams(anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(1);
        SessionUser curUser = new SessionUser();
        Page<User> page = new Page<User>();
        page.setPageSize(15);
        curUser.setProvinceId(1L);
        curUser.setCityId(1L);
        curUser.setAreaId(3L);
        userServiceImpl.getListByParams(curUser, page, 1L, 2L, 3L, 2L, "userName");
    }
    
    /**
     * 判断管理员账号是否存在
     * @author 方志伟  
     * @date 2017年6月23日 下午4:12:08
     */
    @Test
    public void testIsUserNameExist(){
        userServiceImpl.isUserNameExist("userName");
    }
    
    /**
     * 添加管理员
     * @author 方志伟  
     * @date 2017年6月23日 下午4:12:05
     */
    @Test
    public void testAdd(){
        User user = new User();
        userServiceImpl.add(user);
    }
    
    /**
     * 修改管理员
     * @author 方志伟  
     * @date 2017年6月23日 下午4:12:02
     */
    @Test
    public void testUpdate(){
        User user = new User();
        userServiceImpl.update(user);
    }
    
    /**
     * 管理员详情
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月23日 下午4:11:59
     */
    @Test
    public void testGetUserById() throws Exception{
        User user = new User();
        user.setMerchantIds("1,2,3");
        user.setProvinceId(1L);
        user.setCityId(2L);
        user.setAreaId(3L);
        Long[] merchantIdsArray = {1L,2L,3L};
        when(CastUtil.toLongArray(anyString().split(","))).thenReturn(merchantIdsArray);
        user.setId(1L);
        when(userDao.getUserById(anyObject())).thenReturn(user);
        userServiceImpl.getUserById(1L);
    }
    
    /**
     * 删除管理员
     * @author 方志伟  
     * @date 2017年6月23日 下午4:11:55
     */
    @Test
    public void testDelete() {
        userServiceImpl.delete(1L);
    }
    
    /**
     * 重置密码
     * @author 方志伟  
     * @date 2017年6月23日 下午4:11:53
     */
    @Test
    public void testResetPassword() {
        userServiceImpl.resetPassword(1L);
    }
    
    /**
     * 更新用户默认套码
     * @author 方志伟  
     * @date 2017年6月23日 下午4:11:50
     */
    @Test
    public void testUpdateSuitById() {
        userServiceImpl.updateSuitById(1L, "suitCode");
    }
}