package com.awifi.np.admin.suit.dao.sql;

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
 * 创建日期:2017年2月22日 上午10:43:32
 * 创建作者：周颖
 * 文件名称：SuitSqlTest.java
 * 版本：  v1.0
 * 功能：SuitSqlTest
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SqlUtil.class)
@PowerMockIgnore({"javax.management.*"})
public class SuitSqlTest {

    /**被测试类*/
    @InjectMocks
    private SuitSql suitSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SqlUtil.class);
    }
    
    /**
     * 根据角色查找套码sql
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午11:12:24
     */
    @Test
    public void testGetCodeById() throws Exception {
        PowerMockito.doNothing().when(SqlUtil.class,"in",anyObject(),anyObject(),anyObject(),anyObject());
        
        Map<String, Object> params = new HashMap<String,Object>();
        String sql = suitSql.getCodeById(params);
        Assert.assertNotNull(sql);
        PowerMockito.verifyStatic();
        SqlUtil.in(anyObject(), anyObject(), anyObject(), anyObject());
    }
}