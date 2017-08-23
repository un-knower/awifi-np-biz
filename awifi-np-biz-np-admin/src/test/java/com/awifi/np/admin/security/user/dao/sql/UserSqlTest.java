/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月23日 下午4:29:47
* 创建作者：方志伟
* 文件名称：UserSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.admin.security.user.dao.sql;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest(SqlUtil.class)
@PowerMockIgnore({"javax.management.*"})
public class UserSqlTest {
    /**被测试类*/
    @InjectMocks
    private UserSql userSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SqlUtil.class);
    }
    
    /**
     * 管理员账号列表总数
     * @author 方志伟  
     * @date 2017年6月23日 下午4:41:19
     */
    @Test
    public void testGetCountByParams() {
        Map<String, Object> paramMap = getParams();
        String sql = userSql.getCountByParams(paramMap);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 管理员账号列表
     * @author 方志伟  
     * @date 2017年6月23日 下午4:44:06
     */
    @Test
    public void testGetListByParams() {
        Map<String, Object> paramMap = getParams();
        String sql = userSql.getListByParams(paramMap);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 参数
     * @return 参数
     * @author 方志伟  
     * @date 2017年6月23日 下午4:40:34
     */
    public Map<String, Object> getParams(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("roleId", 2L);
        paramMap.put("provinceId", 1L);
        paramMap.put("cityId", 1L);
        paramMap.put("areaId", 1L);
        paramMap.put("userName", "userName");
        return paramMap;
    }
}
