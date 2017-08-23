package com.awifi.np.biz.toe.admin.project.dao.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月28日 上午9:05:51
 * 创建作者：亢燕翔
 * 文件名称：ProjectSqltEST.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ProjectSqlTest {

    /**被测试类*/
    @InjectMocks
    private ProjectSql projectSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 查询项目总记录数
     * @author 亢燕翔  
     * @date 2017年2月28日 上午9:10:06
     */
    @Test
    public void testGetCountByParam(){
        Map<String, Object> params = getParams();
        String sql = projectSql.getCountByParam(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 测试查询项目列表
     * @author 亢燕翔  
     * @date 2017年2月28日 上午9:13:22
     */
    @Test
    public void testGetListByParam(){
        Map<String, Object> params = getParams();
        String sql = projectSql.getListByParam(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 测试
     * @author 亢燕翔  
     * @date 2017年2月28日 上午9:13:22
     */
    @Test
    public void testGetNumByProjectName(){
        Map<String, Object> params = getParams();
        String sql = projectSql.getNumByProjectName(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 测试通过项目名称/项目ID查询项目数量
     * @author 亢燕翔  
     * @date 2017年2月28日 上午9:13:22
     */
    @Test
    public void testGetIdAndNameByIds(){
        Map<String, Object> params = getParams();
        String sql = projectSql.getIdAndNameByIds(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 入参通过项目id获取名称
     * @return 入参
     * @author 亢燕翔  
     * @date 2017年2月28日 上午9:08:01
     */
    public Map getParams(){
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("projectName", "projectName");
        params.put("provinceId", 1L);
        params.put("cityId", 1L);
        params.put("areaId", 1L);
        params.put("projectId", 1L);
        params.put("id", 1L);
        return params;
    }
    
}
