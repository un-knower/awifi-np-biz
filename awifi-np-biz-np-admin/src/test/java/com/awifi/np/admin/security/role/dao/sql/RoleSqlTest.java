package com.awifi.np.admin.security.role.dao.sql;

import static org.mockito.Matchers.anyObject;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午4:26:56
 * 创建作者：周颖
 * 文件名称：RoleSqlTest.java
 * 版本：  v1.0
 * 功能：角色sql单元测试
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SqlUtil.class) @PowerMockIgnore({"javax.management.*"})
public class RoleSqlTest {

    /**被测试类*/
    @InjectMocks
    private RoleSql roleSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SqlUtil.class);
    }
     
    /**
     * 角色sql单元测试
     * @throws Exception 
     * @author 周颖  
     * @date 2017年3月22日 下午4:29:53
     */
    @Test
    public void testGetNamesByIds() throws Exception {
        PowerMockito.doNothing().when(SqlUtil.class,"in",anyObject(),anyObject(),anyObject(),anyObject());
        
        Map<String, Object> params = new HashMap<String,Object>();
        String sql = roleSql.getNamesByIds(params);
        Assert.assertNotNull(sql);
        PowerMockito.verifyStatic();
        SqlUtil.in(anyObject(), anyObject(), anyObject(), anyObject());
    }
    
    /**
     * 获取角色列表
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月22日 下午2:04:36
     */
    @Test
    public void testGetIdsAndNamesByRoleIds() throws Exception{
        PowerMockito.doNothing().when(SqlUtil.class,"in",anyObject(),anyObject(),anyObject(),anyObject());
        Map<String, Object> params = new HashMap<String,Object>();
        String sql = roleSql.getIdsAndNamesByRoleIds(params);
        Assert.assertNotNull(sql);
        PowerMockito.verifyStatic();
    }
}
