package com.awifi.np.biz.toe.admin.usrmgr.blackuser.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

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

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.dao.BlackUserDao;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.model.BlackUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月16日 下午7:05:41
 * 创建作者：周颖
 * 文件名称：BlackUserServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,MerchantClient.class,PermissionUtil.class})
public class BlackUserServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private BlackUserServiceImpl blackUserServiceImpl;
    
    /**
     * 黑名单dao
     */
    @Mock(name = "blackUserDao")
    private BlackUserDao blackUserDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(PermissionUtil.class);
    }
    
    /**
     * 黑名单列表 商户id为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午7:19:24
     */
    /*@Test(expected=BizException.class)
    public void testGetListByParamMerchantNull() throws Exception {
        PowerMockito.when(MessageUtil.getMessage(anyObject(), anyObject())).thenReturn("error");
        
        SessionUser sessionUser = new SessionUser();
        Page<BlackUser> page = new Page<BlackUser>();
        blackUserServiceImpl.getListByParam(sessionUser, page, 1L, 1, "keywords");
        
        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyObject(), anyObject());
    }*/
    
    /**
     * 黑名单列表 商户层级为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午7:19:48
     */
    /*@Test(expected=BizException.class)
    public void testGetListByParamCascadeLabel() throws Exception {
        PowerMockito.when(MessageUtil.getMessage(anyObject(), anyObject())).thenReturn("error");
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(1L);
        Page<BlackUser> page = new Page<BlackUser>();
        blackUserServiceImpl.getListByParam(sessionUser, page, 1L, 1, "keywords");
        
        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyObject(), anyObject());
    }*/
    
    /**
     * 黑名单列表为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午7:31:52
     */
    @Test
    public void testGetListByParamNull() throws Exception {
        when(blackUserDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(0);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(1L);
        sessionUser.setCascadeLabel("cascadeLabel");
        Page<BlackUser> page = new Page<BlackUser>();
        page.setPageSize(15);
        blackUserServiceImpl.getListByParam(sessionUser, page, 1L, 1, "keywords");
        
        PowerMockito.verifyStatic();
    }
    
    /**
     * 黑名单列表有数据
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午7:32:08
     */
    @Test
    public void testGetListByParam() throws Exception {
        when(blackUserDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(1);
        List<BlackUser> blackUserList = new ArrayList<BlackUser>();
        BlackUser blackUser = new BlackUser();
        blackUser.setMerchantId(1L);
        blackUserList.add(blackUser);
        when(blackUserDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(), anyObject(), anyObject(),anyObject())).thenReturn(blackUserList);
        PowerMockito.when(MerchantClient.getNameByIdCache(anyObject())).thenReturn("merchantName");
        PowerMockito.when(PermissionUtil.isMerchant(anyObject())).thenReturn(true);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(1L);
        sessionUser.setCascadeLabel("cascadeLabel");
        Page<BlackUser> page = new Page<BlackUser>();
        page.setPageSize(15);
        blackUserServiceImpl.getListByParam(sessionUser, page, 1L, 1, "keywords");
        
        PowerMockito.verifyStatic();
        MerchantClient.getNameByIdCache(anyObject());
    }
    
    /**
     * 黑名单列表有数据且属性含有多个商户
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月21日 下午7:06:23
     */
    @Test
    public void testGetListByParamIsMerchants() throws Exception {
        when(blackUserDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(1);
        List<BlackUser> blackUserList = new ArrayList<BlackUser>();
        BlackUser blackUser = new BlackUser();
        blackUser.setMerchantId(1L);
        blackUserList.add(blackUser);
        when(blackUserDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(), anyObject(), anyObject(),anyObject())).thenReturn(blackUserList);
        PowerMockito.when(MerchantClient.getNameByIdCache(anyObject())).thenReturn("merchantName");
        PowerMockito.when(PermissionUtil.isMerchants(anyObject())).thenReturn(true);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setCascadeLabel("cascadeLabel");
        sessionUser.setMerchantIds("1");
        Page<BlackUser> page = new Page<BlackUser>();
        page.setPageSize(15);
        blackUserServiceImpl.getListByParam(sessionUser, page, 1L, 1, "keywords");
        
        PowerMockito.verifyStatic();
        MerchantClient.getNameByIdCache(anyObject());
    }
    
    /**
     * 手机号已加黑名单
     * @author 周颖  
     * @date 2017年2月16日 下午7:37:02
     */
    @Test
    public void testIsCellphoneExistTrue() {
        when(blackUserDao.getNumByCellphone(anyObject(),anyObject(),anyObject())).thenReturn(1);
        
        blackUserServiceImpl.isCellphoneExist(1L, "13465767876");
    }
    
    /**
     * 手机号未加黑名单
     * @author 周颖  
     * @date 2017年2月16日 下午7:37:02
     */
    @Test
    public void testIsCellphoneExistFalse() {
        when(blackUserDao.getNumByCellphone(anyObject(),anyObject(),anyObject())).thenReturn(0);
        
        blackUserServiceImpl.isCellphoneExist(1L, "13465767876");
    }

    /**
     * 添加黑名单
     * @author 周颖  
     * @date 2017年2月17日 下午2:04:50
     */
    @Test
    public void testAdd() {
        BlackUser blackUser = new BlackUser();
        blackUserServiceImpl.add(blackUser); 
    }

    /**
     * 删除黑名单
     * @author 周颖  
     * @date 2017年2月17日 下午2:04:59
     */
    @Test
    public void testDelete() {
        blackUserServiceImpl.delete(1L);
    }
}