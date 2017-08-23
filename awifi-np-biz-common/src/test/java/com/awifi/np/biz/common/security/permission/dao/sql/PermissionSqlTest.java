package com.awifi.np.biz.common.security.permission.dao.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午8:16:01
 * 创建作者：亢燕翔
 * 文件名称：PermissionSqlTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class})
public class PermissionSqlTest {

    /**被测试类*/
    @InjectMocks
    private PermissionSql permissionSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
    
    /**
     * 通过角色和编号获取记录数量的sql生成
     * @author 亢燕翔  
     * @date 2017年3月22日 下午8:19:33
     */
    @Test
    public void testGetNumByRoleAndCode(){
        Map<String, Object> params = new HashMap<String, Object>();
        permissionSql.getNumByRoleAndCode(params);
    }
    
    /**
     * 角色-权限关系表  批量更新的sql生成
     * @author 亢燕翔  
     * @date 2017年3月22日 下午8:19:19
     */
    @Test
    public void testBatchAddRolePermission(){
        Map<String, Object> params = new HashMap<String, Object>();
        Long[] permissionIds = {10L,15L};
        params.put("permissionIds", permissionIds);
        permissionSql.batchAddRolePermission(params);
    }
    
}
