/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月5日 下午5:04:05
* 创建作者：尤小平
* 文件名称：UserBaseServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.user.service.impl;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;

import com.awifi.np.biz.timebuysrv.user.model.SysUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.awifi.np.biz.timebuysrv.user.dao.SysUserDao;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserBaseClient.class})
public class UserBaseServiceImplTest {
    @InjectMocks
    private UserBaseServiceImpl service;

    @Mock(name = "sysUserDao")
    private SysUserDao sysUserDao;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(UserBaseClient.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetByUseId() throws Exception {
        PubUser pubUser = new PubUser();
        pubUser.setBirthday(new Date());
        PowerMockito.when(UserBaseClient.queryByUserId(any(Long.class))).thenReturn(pubUser);

        PubUser actual = service.getByUseId(2L);

        Assert.assertNotNull(actual);
        PowerMockito.verifyStatic();
        UserBaseClient.queryByUserId(any(Long.class));
    }

    @Test
    public void testAddOne() throws Exception{
        PubUser pubUser = new PubUser();
        pubUser.setFaceInfo("faceInfo");
        pubUser.setUserNick("userNick");
        pubUser.setSex("2");
        pubUser.setBirthday(new Date());
        pubUser.setAddress("address");
        pubUser.setTelphone("1896991234");
        PowerMockito.when(UserBaseClient.add(any(PubUser.class))).thenReturn(2L);

        SysUser sysUser = new SysUser();
        PowerMockito.when(sysUserDao.selectByUserId(any(Long.class))).thenReturn(sysUser);
        PowerMockito.when(sysUserDao.updateByUserId(any(SysUser.class))).thenReturn(1);

        Long actual = service.add(pubUser);

        Assert.assertEquals(2l, (long) actual);

        PowerMockito.verifyStatic();
        UserBaseClient.add(any(PubUser.class));
        sysUserDao.selectByUserId(any(Long.class));
        sysUserDao.updateByUserId(any(SysUser.class));
    }
    
    @Test
    public void testAddTwo() throws Exception{
        PubUser pubUser = new PubUser();
        pubUser.setFaceInfo("faceInfo");
        pubUser.setUserNick("userNick");
        pubUser.setSex("2");
        pubUser.setBirthday(new Date());
        pubUser.setAddress("address");
        pubUser.setTelphone("1896991234");
        PowerMockito.when(UserBaseClient.add(any(PubUser.class))).thenReturn(2L);

        PowerMockito.when(sysUserDao.selectByUserId(any(Long.class))).thenReturn(null);
        PowerMockito.when(sysUserDao.insert(any(SysUser.class))).thenReturn(1);

        Long actual = service.add(pubUser);

        Assert.assertEquals(2l, (long) actual);

        PowerMockito.verifyStatic();
        UserBaseClient.add(any(PubUser.class));
        sysUserDao.selectByUserId(any(Long.class));
        sysUserDao.insert(any(SysUser.class));
    }
    
    @Test
    public void testUpdateSuccess() throws Exception{
        PubUser pubUser = new PubUser();
        pubUser.setFaceInfo("faceInfo");
        SysUser sysUser = new SysUser();
        PowerMockito.when(sysUserDao.selectByUserId(any(Long.class))).thenReturn(sysUser);
        PowerMockito.when(sysUserDao.updateByUserId(any(SysUser.class))).thenReturn(2);

        service.update(pubUser);

        PowerMockito.verifyStatic();
        sysUserDao.selectByUserId(any(Long.class));
        sysUserDao.updateByUserId(any(SysUser.class));
    }

    @Test
    public void testUpdateFail() throws Exception{
        PubUser pubUser = new PubUser();
        pubUser.setFaceInfo("faceInfo");
        PowerMockito.when(sysUserDao.selectByUserId(any(Long.class))).thenReturn(null);

        service.update(pubUser);

        PowerMockito.verifyStatic();
        sysUserDao.selectByUserId(any(Long.class));
    }
    
    @Test
    public void testAddSysUser() throws Exception{
        PowerMockito.when(sysUserDao.insert(any(SysUser.class))).thenReturn(1);

        SysUser sysUser = new SysUser();
        int actual = service.addSysUser(sysUser);

        Assert.assertEquals(1, actual);

        PowerMockito.verifyStatic();
        sysUserDao.insert(any(SysUser.class));
    }

}
