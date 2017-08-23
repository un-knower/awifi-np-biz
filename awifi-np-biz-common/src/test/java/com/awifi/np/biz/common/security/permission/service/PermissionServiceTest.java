package com.awifi.np.biz.common.security.permission.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
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

import com.awifi.np.biz.api.client.npadmin.util.NPAdminClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.permission.dao.PermissionDao;
import com.awifi.np.biz.common.security.permission.service.impl.PermissionServiceImpl;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 下午12:29:12
 * 创建作者：许小满
 * 文件名称：PermissionServiceTest.java
 * 版本：  v1.0
 * 功能：权限--业务层--测试
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class, NPAdminClient.class,RedisUtil.class})
public class PermissionServiceTest {

    /**被测试类*/
    @InjectMocks
    private PermissionServiceImpl permissionService;
    
    /**mock模板服务*/
    @Mock(name = "permissionDao")
    private PermissionDao permissionDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(NPAdminClient.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
    
    /**
     * 测试
     * @author 许小满  
     * @date 2017年2月9日 下午12:31:56
     */
    @Test
    public void testCheckTrue(){
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("S_XXX");
        when(permissionDao.getNumByRoleAndCode(anyObject(), anyString(), anyString())).thenReturn(1);
        Long[] roleIds = new Long[]{1L, 2L, 3L};
        String code = "/xxx:GET";
        String serviceCode = SysConfigUtil.getParamValue("xxx");
        boolean isPass = permissionService.check(roleIds, code, serviceCode);
        Assert.assertTrue(isPass);
    }
    
    /**
     * 测试
     * @author 许小满  
     * @date 2017年2月9日 下午12:31:56
     */
    @Test
    public void testCheckFalse(){
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("S_XXX");
        when(permissionDao.getNumByRoleAndCode(anyObject(), anyString(), anyString())).thenReturn(0);
        Long[] roleIds = new Long[]{1L, 2L, 3L};
        String code = "/xxx:GET";
        String serviceCode = SysConfigUtil.getParamValue("xxx");
        boolean isPass = permissionService.check(roleIds, code, serviceCode);
        Assert.assertFalse(isPass);
    }
    
    /**
     * 通过服务代码[外键]、角色id获取权限编号集合
     * @author 许小满  
     * @date 2017年2月16日 下午4:31:25
     */
    @Test
    public void getCodesByRoleId(){
        String serviceCode = "xxx";//服务编号
        Long roleId = 1L;//角色id
        
        permissionService.getCodesByRoleId(serviceCode, roleId);
    }
    
    /**
     * 角色-权限关系表  批量更新，抛出业务异常
     * @author 许小满  
     * @date 2017年2月16日 下午6:50:07
     */
    @Test(expected = BizException.class)
    public void batchAddRolePermissionBizEx(){
        String serviceCode = "xxx";//服务编号
        Long roleId = 1L;//角色id
        String[] codes = {"/xxx:GET", "/xxx:POST"};//编号数组
        
        PowerMockito.when(MessageUtil.getMessage(anyObject(), anyObject())).thenReturn("S_XXX");
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("S_XXX");
        PowerMockito.when(RedisUtil.del(anyString())).thenReturn(1L);
        when(permissionDao.getIdByCode(anyObject(), anyObject())).thenReturn(null);
        permissionService.batchAddRolePermission(serviceCode, roleId, codes);
    }
    
    /**
     * 角色-权限关系表  批量更新
     * @author 许小满  
     * @date 2017年2月16日 下午6:50:07
     */
    @Test
    public void batchAddRolePermission(){
        PowerMockito.when(RedisUtil.del(anyString())).thenReturn(1L);
        String serviceCode = "xxx";//服务编号
        Long roleId = 1L;//角色id
        String[] codes = null;//编号数组
        permissionService.batchAddRolePermission(serviceCode, roleId, codes);
        codes = new String[]{"/xxx:GET", "/xxx:POST"};//编号数组
        permissionService.batchAddRolePermission(serviceCode, roleId, codes);
    }
    
    /**
     * 推送接口注册信息
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午10:04:50
     */
    @Test
    public void testPushInterfaces() throws Exception{
        String serviceCode = "S_PROJ";//服务编号
        String serviceKey = "xxx";//服务密钥
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "code:xxx");
        map.put("name", "name");
        list.add(map);
        PowerMockito.when(permissionDao.getListByServiceCode(anyString())).thenReturn(list);
        permissionService.pushInterfaces(serviceCode, serviceKey);
    }
    
}
