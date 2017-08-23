package com.awifi.np.admin.service.dao.sql;

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
 * 创建日期:2017年1月16日 下午5:07:49
 * 创建作者：周颖
 * 文件名称：ServiceSqlTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SqlUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class ServiceSqlTest {

    /**注入被测试类*/
    @InjectMocks
    private ServiceSql serviceSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SqlUtil.class);
    }
    
    /**
     * 返回sql
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月16日 下午5:30:58
     */
    @Test
    public void testGetTopMenus() throws Exception {
        Map<String,Object> param = new HashMap<String,Object>(); 
        String result = serviceSql.getTopMenus(param);
        Assert.assertNotNull(result);
    }
}