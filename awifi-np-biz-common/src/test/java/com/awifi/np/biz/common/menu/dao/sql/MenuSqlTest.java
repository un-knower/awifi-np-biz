package com.awifi.np.biz.common.menu.dao.sql;

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
 * 创建日期:2017年3月22日 上午9:25:48
 * 创建作者：亢燕翔
 * 文件名称：MenuSqlTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class})
public class MenuSqlTest {

    /**被测试类*/
    @InjectMocks
    private MenuSql menuSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
    
    /**
     * 测试获取菜单
     * @author 亢燕翔  
     * @date 2017年3月22日 上午9:31:14
     */
    @Test
    public void testGetListByParam(){
        Long[] roleIds = {10L,15L};
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", 10L);
        params.put("roleIds", roleIds);
        menuSql.getListByParam(params);
    }
    
    /**
     * 测试角色-菜单关系表  批量更新的sql生成
     * @author 亢燕翔  
     * @date 2017年3月22日 上午9:45:47
     */
    @Test
    public void testBatchAddRoleMenu(){
        Long[] menuIds = {10L,15L};
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("menuIds", menuIds);
        menuSql.batchAddRoleMenu(params);
    }
    
}
