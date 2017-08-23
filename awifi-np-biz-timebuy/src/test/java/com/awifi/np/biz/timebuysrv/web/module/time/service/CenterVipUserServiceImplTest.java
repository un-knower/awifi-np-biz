/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月21日 上午9:14:25
* 创建作者：余红伟
* 文件名称：CenterOnlineUserServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey.On;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.CenterVipUserDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;

public class CenterVipUserServiceImplTest {
    
    /**
     * 被测试类
     */
    private CenterVipUserServiceImpl centerOnlineUserService;
    
    private CenterVipUserDao mockCenterOnlineUserDao;
    
    @Before
    public void setUp() throws Exception {
        centerOnlineUserService = new CenterVipUserServiceImpl();
        mockCenterOnlineUserDao = Mockito.mock(CenterVipUserDao.class);
    }

    @After
    public void tearDown() throws Exception {
        centerOnlineUserService = null;
        mockCenterOnlineUserDao = null;
    }

    @Test
    public void testAdd() {
        VipUserObject object = new VipUserObject();
        object.setProcessFlg("亚玖璃");
        Mockito.when(mockCenterOnlineUserDao.add(any(VipUserObject.class))).thenReturn(1);
        centerOnlineUserService.setCenterOnlineUserDao(mockCenterOnlineUserDao);
        long actual = centerOnlineUserService.add(any(VipUserObject.class));
        Assert.assertEquals(1L, actual);
        Mockito.verify(mockCenterOnlineUserDao).add(any(VipUserObject.class));
    }

    @Test
    public void testUpdate() {
        VipUserObject object = new VipUserObject();
        object.setId(2L);
        
        Mockito.when(mockCenterOnlineUserDao.update(any(VipUserObject.class))).thenReturn(1);
        centerOnlineUserService.setCenterOnlineUserDao(mockCenterOnlineUserDao);
        
        int actual = centerOnlineUserService.update(object);
        Assert.assertEquals(1, actual);
        Mockito.verify(mockCenterOnlineUserDao).update(any(VipUserObject.class));
        
    }

    @Test
    public void testQueryOnlineUserCount() {
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", "1");
        Mockito.when(mockCenterOnlineUserDao.queryOnlineUserCount(any(Map.class))).thenReturn(1);
        centerOnlineUserService.setCenterOnlineUserDao(mockCenterOnlineUserDao);
        
        int actual = centerOnlineUserService.queryOnlineUserCount(params);
        Assert.assertEquals(1, actual);
        Mockito.verify(mockCenterOnlineUserDao).queryOnlineUserCount(any(Map.class));
    }

    @Test
    public void testQueryLastOnlineUser() {
        Map<String,Object> params = new HashMap<>();
        params.put("telephone", "1");
        VipUserObject object = new VipUserObject(); 
        Mockito.when(mockCenterOnlineUserDao.queryLastOnlineUser(any(Map.class))).thenReturn(object);
        centerOnlineUserService.setCenterOnlineUserDao(mockCenterOnlineUserDao);
        VipUserObject actual = centerOnlineUserService.queryLastOnlineUser(params);
        assertEquals(object, actual);
        Mockito.verify(mockCenterOnlineUserDao).queryLastOnlineUser(any(Map.class));
    }

    @Test
    public void testQueryVipUserList() {
        List<VipUserObject> list = new ArrayList<>();
        Mockito.when(mockCenterOnlineUserDao.queryListByMerArea(any(Map.class))).thenReturn(list);
        centerOnlineUserService.setCenterOnlineUserDao(mockCenterOnlineUserDao);
        List<VipUserObject> actual = centerOnlineUserService.queryVipUserList(any(Map.class));
        assertEquals(list, actual);
        Mockito.verify(mockCenterOnlineUserDao).queryListByMerArea(any(Map.class));
    }

    @Test
    public void testQueryVipUserCount() {
//        Mockito.when(mockCenterOnlineUserDao.queryCountByMerArea(any(Map.class))).thenReturn(1);
        centerOnlineUserService.setCenterOnlineUserDao(mockCenterOnlineUserDao);
        Map<String, Object> map = new HashMap<String, Object>();
        int actual = centerOnlineUserService.queryVipUserCount(map);
//        assertEquals(0, actual);
//        Mockito.verify(mockCenterOnlineUserDao).queryCountByMerArea(any(Map.class));
    }

    @Test
    public void testUpdateVipUser() {
        Mockito.when(mockCenterOnlineUserDao.updateVipUser(any(VipUserObject.class))).thenReturn(1);
        centerOnlineUserService.setCenterOnlineUserDao(mockCenterOnlineUserDao);
        int actual = centerOnlineUserService.updateVipUser(any(VipUserObject.class));
        assertEquals(1, actual);
        Mockito.verify(mockCenterOnlineUserDao).updateVipUser(any(VipUserObject.class));
    }

}
