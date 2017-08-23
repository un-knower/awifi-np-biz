package com.awifi.np.biz.toe.admin.usrmgr.staticuser.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.dao.StaticUserDao;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月16日 下午7:41:45
 * 创建作者：周颖
 * 文件名称：StaticUserServiceImplTest.java
 * 版本：  v1.0
 * 功能：静态用户测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,MerchantClient.class,CastUtil.class,PermissionUtil.class})
public class StaticUserServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private StaticUserServiceImpl staticUserServiceImpl;
    
    /**
     * 静态用户dao
     */
    @Mock(name = "staticUserDao")
    private StaticUserDao staticUserDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
    }
    
    /**
     * 静态用户列表 商户id为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午7:55:50
     */
    /*@Test(expected=BizException.class)
    public void testGetListByParamMerchantNull() throws Exception {
        PowerMockito.when(MessageUtil.getMessage(anyObject(), anyObject())).thenReturn("error");
        
        SessionUser sessionUser = new SessionUser();
        Page<StaticUser> page = new Page<StaticUser>();
        staticUserServiceImpl.getListByParam(page, "keywords", 1L, 2, sessionUser);;
        
        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyObject(), anyObject());
    }*/
    
    /**
     * 静态用户列表 商户层级为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午7:55:50
     */
    /*@Test(expected=BizException.class)
    public void testGetListByParamCascadeLabel() throws Exception {
        PowerMockito.when(MessageUtil.getMessage(anyObject(), anyObject())).thenReturn("error");
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(1L);
        Page<StaticUser> page = new Page<StaticUser>();
        staticUserServiceImpl.getListByParam(page, "keywords", 1L, 2, sessionUser);
        
        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyObject(), anyObject());
    }*/
    
    /**
     * 静态用户列表为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午7:31:52
     */
   /* @Test
    public void testGetListByParamNull() throws Exception {
        when(staticUserDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(0);
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(1L);
        sessionUser.setCascadeLabel("cascadeLabel");
        Page<StaticUser> page = new Page<StaticUser>();
        page.setPageSize(15);
        staticUserServiceImpl.getListByParam(page, "keywords", 1L, 2, sessionUser);
        
        PowerMockito.verifyStatic();
    }*/
    
    /**
     * 静态用户列表有数据
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午7:31:52
     */
    @Test
    public void testGetListByParam() throws Exception {
        when(staticUserDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(1);
        List<StaticUser> staticUserList = new ArrayList<StaticUser>(); 
        StaticUser staticUser = new StaticUser();
        staticUser.setMerchantId(1L);
        staticUserList.add(staticUser);
        when(staticUserDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(staticUserList);
        PowerMockito.when(MerchantClient.getNameByIdCache(anyObject())).thenReturn("merchantName");
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(1L);
        sessionUser.setCascadeLabel("cascadeLabel");
        Page<StaticUser> page = new Page<StaticUser>();
        page.setPageSize(15);
        staticUserServiceImpl.getListByParam(page, "keywords", 1L, 2, sessionUser);
        
        PowerMockito.verifyStatic();
        MerchantClient.getNameByIdCache(anyObject());
    }

    /**
     * 用户名存在
     * @author 周颖  
     * @date 2017年2月16日 下午8:16:20
     */
    @Test
    public void testIsUserNameExistTrue() {
        when(staticUserDao.getNumByUserName(anyObject(), anyObject())).thenReturn(1);
        
        staticUserServiceImpl.isUserNameExist(1L, "userName");
    }
    
    /**
     * 用户名不存在
     * @author 周颖  
     * @date 2017年2月16日 下午8:16:20
     */
    @Test
    public void testIsUserNameExistFalse() {
        when(staticUserDao.getNumByUserName(anyObject(), anyObject())).thenReturn(0);
        
        staticUserServiceImpl.isUserNameExist(1L, "userName");
    }

    /**
     * 手机号存在 新增
     * @author 周颖  
     * @date 2017年2月17日 上午8:48:26
     */
    @Test
    public void testIsCellphoneExistTrue() {
        when(staticUserDao.getNumByCellphone(anyObject(),anyObject())).thenReturn(1);
        staticUserServiceImpl.isCellphoneExist(1L, "13465765676");
    }

    /**
     * 手机号存在 编辑
     * @author 周颖  
     * @date 2017年2月17日 上午8:48:36
     */
    @Test
    public void testIsCellphoneExist() {
        when(staticUserDao.getNumByParam(anyObject(),anyObject(),anyObject())).thenReturn(1);
        staticUserServiceImpl.isCellphoneExist(1L, 1L, "13457877678");
    }

    /**
     * 新增静态用户
     * @author 周颖  
     * @date 2017年2月17日 上午8:49:09
     */
    @Test
    public void testAdd() {
        StaticUser staticUser = new StaticUser();
        Mockito.doNothing().when(staticUserDao).add(staticUser);
        
        staticUserServiceImpl.add(staticUser);
    }

    /**
     * 更新静态用户
     * @author 周颖  
     * @date 2017年2月17日 上午8:49:23
     */
    @Test
    public void testUpdate() {
        StaticUser staticUser = new StaticUser();
        Mockito.doNothing().when(staticUserDao).update(staticUser);
        
        staticUserServiceImpl.update(staticUser);
    }

    /**
     * 静态用户详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月17日 上午8:58:27
     */
    @Test
    public void testGetById() throws Exception {
        StaticUser staticUser = new StaticUser();
        staticUser.setMerchantId(1L);
        when(staticUserDao.getById(anyObject())).thenReturn(staticUser);
        PowerMockito.when(MerchantClient.getNameByIdCache(anyObject())).thenReturn("merchantName");
        
        StaticUser result = staticUserServiceImpl.getById(1L);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        MerchantClient.getNameByIdCache(anyObject());
    }

    /**
     * 删除
     * @author 周颖  
     * @date 2017年2月17日 上午8:58:43
     */
    @Test
    public void testDelete() {
        Mockito.doNothing().when(staticUserDao).delete(anyObject());
        
        staticUserServiceImpl.delete(1L);
    }

    /**
     * 批量删除
     * @author 周颖  
     * @date 2017年2月17日 上午9:02:13
     */
    @Test
    public void testBatchDelete() {
        Long[] idsLong = {1L,2L};
        String[] idsString = {"1","2","3"};
        PowerMockito.when(CastUtil.toLongArray(idsString)).thenReturn(idsLong);
        Mockito.doNothing().when(staticUserDao).batchDelete(anyObject());
        
        staticUserServiceImpl.batchDelete("1,2,3");
        
        PowerMockito.verifyStatic();
        CastUtil.toLongArray(idsString);
    }

    /**
     * 一键删除
     * @author 周颖  
     * @date 2017年2月17日 上午9:03:25
     */
    @Test
    public void testDeleteByMerchantId() {
        Mockito.doNothing().when(staticUserDao).deleteByMerchantId(anyObject());
        
        staticUserServiceImpl.deleteByMerchantId(1L);
    }
    
    /**
     * 通过 用户名、密码 获取用户id
     * @author 方志伟  
     * @date 2017年6月21日 下午5:18:00
     */
    @Test
    public void testGetIdByUserNameAndPwd() {
        staticUserServiceImpl.getIdByUserNameAndPwd(1L, "userName", "password");
    }
    
    /**
     * 更新密码
     * @author 方志伟  
     * @date 2017年6月21日 下午5:18:02
     */
    @Test
    public void testUpdatePwd() {
        staticUserServiceImpl.updatePwd(1L, "password");
    }
    
    /**
     * 通过手机号更新密码
     * @author 方志伟  
     * @date 2017年6月21日 下午5:18:04
     */
    @Test
    public void testUpdatePwdByCellphone() {
        staticUserServiceImpl.updatePwdByCellphone(1L, "18656988221", "password");
    }
    
    /**
     * 获取静态用户对象
     * @author 方志伟  
     * @date 2017年6月21日 下午5:18:06
     */
    @Test
    public void testGetStaticUser() {
        staticUserServiceImpl.getStaticUser(1L, "userName", "password");
    }
}