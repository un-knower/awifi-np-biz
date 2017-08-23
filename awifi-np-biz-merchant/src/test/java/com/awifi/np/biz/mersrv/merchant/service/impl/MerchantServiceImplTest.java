package com.awifi.np.biz.mersrv.merchant.service.impl;

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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;
import com.awifi.np.biz.toe.admin.security.role.model.ToeRole;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月16日 上午11:21:49
 * 创建作者：周颖
 * 文件名称：MerchantServiceImplTest.java
 * 版本：  v1.0
 * 功能：商户实现类测试类
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,PermissionUtil.class,EncryUtil.class,IndustryClient.class,LocationClient.class,SessionUtil.class,MerchantClient.class})
public class MerchantServiceImplTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private MerchantServiceImpl merchantServiceImpl;
    
    /**项目服务*/
    @Mock(name = "projectService")
    private ProjectService projectService;
    
    /**toe用户服务*/
    @Mock(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**toe角色服务*/
    @Mock(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(IndustryClient.class);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(EncryUtil.class);
    }
    
    /**
     * 商户列表  无记录
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午2:07:53
     */
    @Test
    public void testGetListByParamNull() throws Exception {
        Map<String, Object> param = new HashMap<String,Object>();
        PowerMockito.when(PermissionUtil.dataPermission(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(),anyObject(),anyObject())).thenReturn(param);
        PowerMockito.when(MerchantClient.getCountByParam(anyObject())).thenReturn(0);
    
        SessionUser sessionUser = new SessionUser();
        Page page = new Page();
        page.setPageSize(15);
       // Map<String,Object> param = new HashMap<String,Object>();
        merchantServiceImpl.getListByParam( sessionUser, page, param);
        PowerMockito.verifyStatic();
        PermissionUtil.dataPermission(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
        MerchantClient.getCountByParam(anyObject());
    }

    /**
     * 商户列表  有记录
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午2:07:53
     */
    @Test
    public void testGetListByParam() throws Exception {
        Map<String, Object> param = new HashMap<String,Object>();
        PowerMockito.when(PermissionUtil.dataPermission(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())).thenReturn(param);
        PowerMockito.when(MerchantClient.getCountByParam(anyObject())).thenReturn(1);
        List<Merchant> merchantList = new ArrayList<Merchant>();
        Merchant merchant = new Merchant();
        merchant.setProvinceId(31L);
        merchant.setCityId(383L);
        merchant.setAreaId(3230L);
        merchant.setPriIndustryCode("OCAB20");
        merchant.setSecIndustryCode("OCAB2004");
        merchant.setProjectId(1L);
        merchant.setParentId(1L);
        merchantList.add(merchant);
        merchant = new Merchant();
        merchant.setProvinceId(31L);
        merchant.setCityId(383L);
        merchant.setAreaId(3230L);
        merchant.setPriIndustryCode("OCAB20");
        merchant.setSecIndustryCode("OCAB2004");
        merchant.setParentId(0L);
        merchantList.add(merchant);
        
        
        PowerMockito.when(IndustryClient.getNameByCode(anyObject())).thenReturn("industryName");
        PowerMockito.when(LocationClient.getByIdAndParam(anyObject(),anyObject())).thenReturn("locationName");
        PowerMockito.when(MerchantClient.getListByParam(anyObject())).thenReturn(merchantList);
       
        Map<Long, String> projectMap = new HashMap<Long,String>();
        projectMap.put(1L, "项目1");
        when(projectService.getIdAndNameByIds(anyObject())).thenReturn(projectMap);
        
    
        SessionUser sessionUser = new SessionUser();
        Page page = new Page();
        page.setPageSize(15);
        merchantServiceImpl.getListByParam(sessionUser, page, param);
        PowerMockito.verifyStatic();
        PermissionUtil.dataPermission(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
        MerchantClient.getCountByParam(anyObject());
        MerchantClient.getListByParam(anyObject());
        IndustryClient.getNameByCode(anyObject());
        LocationClient.getByIdAndParam(anyObject(),anyObject());
        
    }
    
    /**
     * 添加商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午2:43:17
     */
   /* @Test
    public void testAdd() throws Exception {
        PowerMockito.when(MerchantClient.add(anyObject(),anyObject())).thenReturn(1L);
        when(toeUserService.add(anyObject())).thenReturn(1L);
        Mockito.doNothing().when(toeRoleService).addUserRole(anyObject(), anyObject());
        Mockito.doNothing().when(toeUserService).addUserMerchant(anyObject(), anyObject());
        PowerMockito.when(SysConfigUtil.getParamValue("default_password")).thenReturn("123456");
        PowerMockito.when(EncryUtil.getMd5Str("123456")).thenReturn("123456");
        
        Merchant merchant = new Merchant();
        merchantServiceImpl.add(1L,merchant,"industryCode");
        PowerMockito.verifyStatic();
        MerchantClient.add(anyObject(), anyObject());
        SysConfigUtil.getParamValue("default_password");
        EncryUtil.getMd5Str("123456");
    }*/

    /**
     * 判断商户名称是否存在
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午2:57:25
     */
    @Test
    public void testIsMerchantNameExistTrue() throws Exception {
        PowerMockito.when(MerchantClient.getCountByParam(anyObject())).thenReturn(1);
        
        merchantServiceImpl.isMerchantNameExist("merchantName");
       
        PowerMockito.verifyStatic();
        MerchantClient.getCountByParam(anyObject());
    }
    
     /**
     * 判断商户名称是否存在
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午2:57:25
     */
    @Test
    public void testIsMerchantNameExistFalse() throws Exception {
        PowerMockito.when(MerchantClient.getCountByParam(anyObject())).thenReturn(0);
        
        merchantServiceImpl.isMerchantNameExist("merchantName");
       
        PowerMockito.verifyStatic();
        MerchantClient.getCountByParam(anyObject());
    }

    /**
     * 获取初始密码
     * @author 周颖  
     * @date 2017年2月16日 下午2:56:45
     *//*
    @Test
    public void testGetDefaultPassword() {
        PowerMockito.when(SysConfigUtil.getParamValue("default_password")).thenReturn(null);
        
        merchantServiceImpl.getDefaultPassword();
        
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue("default_password");
    }*/

    /**
     * 商户详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月17日 下午2:01:59
     */
    @Test
    public void testGetById() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setProvinceId(31L);
        merchant.setCityId(383L);
        merchant.setAreaId(3230L);
        merchant.setPriIndustryCode("OCAB20");
        merchant.setSecIndustryCode("OCAB2004");
        ToeUser user = new ToeUser();
        user.setId(1L);
        user.setUserName("userName");
        List<ToeRole> roleList = new ArrayList<ToeRole>();
        ToeRole toeRole = new ToeRole();
        toeRole.setId(1L);
        toeRole.setRoleName("admin");
        roleList.add(toeRole);
        toeRole = new ToeRole();
        toeRole.setId(2L);
        toeRole.setRoleName("merchant");
        roleList.add(toeRole);
        PowerMockito.when(MerchantClient.getById(anyObject())).thenReturn(merchant);
        PowerMockito.when(toeUserService.getByMerchantId(anyObject())).thenReturn(user);
        PowerMockito.when(toeRoleService.getNamesByUserId(anyObject())).thenReturn(roleList);
        PowerMockito.when(IndustryClient.getNameByCode(anyObject())).thenReturn("industryName");
        PowerMockito.when(LocationClient.getByIdAndParam(anyObject(),anyObject())).thenReturn("locationName");
        
        Merchant result = merchantServiceImpl.getById(1L);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        MerchantClient.getById(anyObject());
        IndustryClient.getNameByCode(anyObject());
        LocationClient.getByIdAndParam(anyObject(),anyObject());
    }

    /**
     * 编辑商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 下午2:56:30
     */
    @Test
    public void testUpdate() throws Exception {
        PowerMockito.doNothing().when(MerchantClient.class, "update",anyObject(), anyObject());
        when(toeUserService.update(anyObject(), anyObject())).thenReturn(1L);
        Mockito.doNothing().when(toeRoleService).updateUserRole(anyObject(), anyObject());
        
        
        Merchant merchant = new Merchant();
        merchantServiceImpl.update(merchant,"industryCode");
        PowerMockito.verifyStatic();
        MerchantClient.update(anyObject(), anyObject());
    }
}