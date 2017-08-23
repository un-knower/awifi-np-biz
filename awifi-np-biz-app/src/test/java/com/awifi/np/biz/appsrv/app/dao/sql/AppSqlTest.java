/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jul 17, 2017 9:09:19 AM
* 创建作者：季振宇
* 文件名称：AppSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.appsrv.app.dao.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class AppSqlTest {
    
    /**被测试类*/
    @InjectMocks
    private AppSql appSql;
    
    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 测试应用管理-分页查询总条数接口sql
     * 
     * @author 季振宇  
     * @date Jul 17, 2017 9:21:49 AM
     */
    @Test
    public void testGetCountByParam() {
        Map<String,Object> params = new HashMap<>();
        params.put("appName", "appName");
        params.put("status", 1);
        String result = appSql.getCountByParam(params);
        String expected = "select count(id) from np_biz_app where status!=9 and app_name like concat('%',#{appName},'%') and status=#{status}";
        Assert.assertEquals(expected, result);
    }
    
    /**
     * 测试应用管理-分页查询总条数接口sql
     * 
     * @author 季振宇  
     * @date Jul 17, 2017 9:21:49 AM
     */
    @Test
    public void testGetCountByParam1() {
        Map<String,Object> params = new HashMap<>();
        params.put("appName", "appName");
//        params.put("status", 1);
        String result = appSql.getCountByParam(params);
        String expected = "select count(id) from np_biz_app where status!=9 and app_name like concat('%',#{appName},'%')";
        Assert.assertEquals(expected, result);
    }
    
    /**
     * 测试应用管理-分页查询总条数接口sql
     * 
     * @author 季振宇  
     * @date Jul 17, 2017 9:21:49 AM
     */
    @Test
    public void testGetCountByParam2() {
        Map<String,Object> params = new HashMap<>();
//        params.put("appName", "appName");
        params.put("status", 1);
        String result = appSql.getCountByParam(params);
        String expected = "select count(id) from np_biz_app where status!=9 and status=#{status}";
        Assert.assertEquals(expected, result);
    }

    /**
     * 测试应用管理-分页查询接口sql
     * 
     * @author 季振宇  
     * @date Jul 17, 2017 9:22:22 AM
     */
    @Test
    public void testGetListByParam() {
        Map<String,Object> params = new HashMap<>();
        params.put("appName", "appName");
        params.put("status", 1);
        String result = appSql.getListByParam(params);
        String expected = "select id,app_id,app_name,status,date_format(create_date,'%Y-%m-%d %H:%i:%S') as create_date from np_biz_app where status!=9 and app_name like concat('%',#{appName},'%') and status=#{status} order by id desc limit #{begin},#{pageSize}";
        Assert.assertEquals(expected, result);
    }

    /**
     * 测试应用管理-分页查询接口sql
     * 
     * @author 季振宇  
     * @date Jul 17, 2017 9:22:22 AM
     */
    @Test
    public void testGetListByParam1() {
        Map<String,Object> params = new HashMap<>();
        params.put("appName", "appName");
//        params.put("status", 1);
        String result = appSql.getListByParam(params);
        String expected = "select id,app_id,app_name,status,date_format(create_date,'%Y-%m-%d %H:%i:%S') as create_date from np_biz_app where status!=9 and app_name like concat('%',#{appName},'%') order by id desc limit #{begin},#{pageSize}";
        Assert.assertEquals(expected, result);
    }
    
    /**
     * 测试应用管理-分页查询接口sql
     * 
     * @author 季振宇  
     * @date Jul 17, 2017 9:22:22 AM
     */
    @Test
    public void testGetListByParam2() {
        Map<String,Object> params = new HashMap<>();
//        params.put("appName", "appName");
        params.put("status", 1);
        String result = appSql.getListByParam(params);
        String expected = "select id,app_id,app_name,status,date_format(create_date,'%Y-%m-%d %H:%i:%S') as create_date from np_biz_app where status!=9 and status=#{status} order by id desc limit #{begin},#{pageSize}";
        Assert.assertEquals(expected, result);
    }
}
