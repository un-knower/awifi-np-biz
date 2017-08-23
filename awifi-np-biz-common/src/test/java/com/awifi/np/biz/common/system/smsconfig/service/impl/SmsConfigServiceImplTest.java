/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 上午8:56:46
* 创建作者：周颖
* 文件名称：SmsConfigServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.system.smsconfig.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.smsconfig.dao.SmsConfigDao;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MerchantClient.class,PermissionUtil.class,CastUtil.class,MessageUtil.class})
public class SmsConfigServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private SmsConfigServiceImpl smsConfigServiceImpl;
    
    /**mock*/
    @Mock(name="smsConfigDao")
    private SmsConfigDao smsConfigDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
    
    /**
     * 超管登陆
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 上午9:18:43
     */
    @Test
    public void testGetListByParamSuperAdmin() throws Exception {
        PowerMockito.when(PermissionUtil.isSuperAdmin(anyObject())).thenReturn(true);
        when(smsConfigDao.getCountByParam(anyObject(), anyObject())).thenReturn(0);
        
        SessionUser user = new SessionUser();
        Page<Map<String, Object>> page = new Page<Map<String,Object>>();
        page.setPageNo(1);
        page.setPageSize(3);
        smsConfigServiceImpl.getListByParam(user, page, 1L);
        PowerMockito.verifyStatic();
        PermissionUtil.isSuperAdmin(anyObject());
        Mockito.verify(smsConfigDao).getCountByParam(anyObject(), anyObject());
    }
    
    /**
     * 商户登陆
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 上午9:26:40
     */
    @Test
    public void testGetListByParamMerchant() throws Exception {
        PowerMockito.when(PermissionUtil.isMerchant(anyObject())).thenReturn(true);
        when(smsConfigDao.getCountByParam(anyObject(), anyObject())).thenReturn(1);
        List<Map<String,Object>> smsConfigList = new ArrayList<Map<String,Object>>();
        Map<String,Object> smsConfig = new HashMap<String, Object>();
        smsConfig.put("id", 1L);
        smsConfig.put("smsContent", "test");
        smsConfig.put("codeLength", 4);
        smsConfig.put("merchantId", 23L);
        smsConfig.put("createDate", "2017-6-27 9:22:59");
        smsConfigList.add(smsConfig);
        when(smsConfigDao.getListByParam(anyObject(), anyObject(), anyObject(), anyObject())).thenReturn(smsConfigList);
        
        SessionUser user = new SessionUser();
        user.setMerchantId(1L);
        Page<Map<String, Object>> page = new Page<Map<String,Object>>();
        page.setPageNo(1);
        page.setPageSize(3);
        
        
        smsConfigServiceImpl.getListByParam(user, page, null);
        PowerMockito.verifyStatic();
        PermissionUtil.isMerchant(anyObject());
        Mockito.verify(smsConfigDao).getCountByParam(anyObject(), anyObject());
        Mockito.verify(smsConfigDao).getListByParam(anyObject(), anyObject(), anyObject(), anyObject());
    }
    
    /**
     * 商户管理员
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 上午9:28:17
     */
    @Test
    public void testGetListByParamMerchants() throws Exception {
        PowerMockito.when(PermissionUtil.isMerchants(anyObject())).thenReturn(true);
        when(smsConfigDao.getCountByParam(anyObject(), anyObject())).thenReturn(0);
        
        SessionUser user = new SessionUser();
        user.setMerchantIds("1,2,3");
        Page<Map<String, Object>> page = new Page<Map<String,Object>>();
        page.setPageNo(1);
        page.setPageSize(3);
        smsConfigServiceImpl.getListByParam(user, page, 1L);
        PowerMockito.verifyStatic();
        PermissionUtil.isMerchants(anyObject());
        Mockito.verify(smsConfigDao).getCountByParam(anyObject(), anyObject());
    }
    
    /**
     * 项目管理员
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 上午9:31:54
     */
    @Test
    public void testGetListByParamProject() throws Exception {
        PowerMockito.when(PermissionUtil.isProject(anyObject())).thenReturn(true);
        when(smsConfigDao.getCountByParam(anyObject(), anyObject())).thenReturn(0);
        
        SessionUser user = new SessionUser();
        Page<Map<String, Object>> page = new Page<Map<String,Object>>();
        page.setPageNo(1);
        page.setPageSize(3);
        smsConfigServiceImpl.getListByParam(user, page, 1L);
        PowerMockito.verifyStatic();
        PermissionUtil.isProject(anyObject());
        Mockito.verify(smsConfigDao).getCountByParam(anyObject(), anyObject());
    }
    
    /**
     * 地区管理员
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 上午9:33:24
     */
    @Test(expected=BizException.class)
    public void testGetListByParamLocation() throws Exception {
        PowerMockito.when(PermissionUtil.isLocation(anyObject())).thenReturn(true);
        when(smsConfigDao.getCountByParam(anyObject(), anyObject())).thenReturn(0);
        
        SessionUser user = new SessionUser();
        Page<Map<String, Object>> page = new Page<Map<String,Object>>();
        page.setPageNo(1);
        page.setPageSize(3);
        smsConfigServiceImpl.getListByParam(user, page, null);
        
        PowerMockito.verifyStatic();
        PermissionUtil.isLocation(anyObject());
        Mockito.verify(smsConfigDao).getCountByParam(anyObject(), anyObject());
    }

    /**
     * 商户短信配置存在
     * @author 周颖  
     * @date 2017年6月27日 上午9:37:00
     */
    @Test
    public void testIsExistTrue() {
        when(smsConfigDao.getNumByMerchantId(anyLong())).thenReturn(1);
        smsConfigServiceImpl.isExist(1L);
        Mockito.verify(smsConfigDao).getNumByMerchantId(anyLong());
    }
    
    /**
     * 商户短信配置不存在
     * @author 周颖  
     * @date 2017年6月27日 上午9:37:35
     */
    @Test
    public void testIsExistFalse() {
        when(smsConfigDao.getNumByMerchantId(anyLong())).thenReturn(0);
        smsConfigServiceImpl.isExist(1L);
        Mockito.verify(smsConfigDao).getNumByMerchantId(anyLong());
    }

    /**
     * 添加短信配置
     * @author 周颖  
     * @date 2017年6月27日 上午9:39:35
     */
    @Test
    public void testAdd() {
        Mockito.doNothing().when(smsConfigDao).add(anyLong(), anyString(), anyInt());
        smsConfigServiceImpl.add(1L, "短信内容", 4);
        Mockito.verify(smsConfigDao).add(anyLong(), anyString(), anyInt());
    }

    /**
     * 更新短信配置
     * @author 周颖  
     * @date 2017年6月27日 上午9:41:03
     */
    @Test
    public void testUpdate() {
        Mockito.doNothing().when(smsConfigDao).update(anyLong(), anyString(), anyInt());
        smsConfigServiceImpl.update(1L, "短信内容", 4);
        Mockito.verify(smsConfigDao).update(anyLong(), anyString(), anyInt());
    }

    /**
     * 短信配置详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 上午9:46:23
     */
    @Test
    public void testGetById() throws Exception {
        Map<String,Object> smsConfigMap = new HashMap<String, Object>();
        smsConfigMap.put("smsContent", "短信内容");
        smsConfigMap.put("codeLength", 4);
        smsConfigMap.put("merchantId", 1L);
        Mockito.when(smsConfigDao.getById(anyLong())).thenReturn(smsConfigMap);
        PowerMockito.when(MerchantClient.getNameByIdCache(anyLong())).thenReturn("商户名称");
        
        smsConfigServiceImpl.getById(1L);
        PowerMockito.verifyStatic();
        MerchantClient.getNameByIdCache(anyLong());
        Mockito.verify(smsConfigDao).getById(anyLong());
    }

    /**
     * 查看商户短信配置
     * @author 周颖  
     * @date 2017年6月27日 上午9:58:52
     */
    @Test
    public void testGetByCustomerIdNull() {
        smsConfigServiceImpl.getByCustomerId(null);
    }
    
    /**
     * 查看商户短信配置
     * @author 周颖  
     * @date 2017年6月27日 上午9:59:46
     */
    @Test
    public void testGetByCustomerId() {
        smsConfigServiceImpl.getByCustomerId(1L);
    }

    /**
     * 删除短信配置
     * @author 周颖  
     * @date 2017年6月27日 上午10:00:28
     */
    @Test
    public void testDelete() {
        smsConfigServiceImpl.delete(1L);
    }
}
