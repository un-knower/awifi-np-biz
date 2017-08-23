package com.awifi.np.biz.toe.admin.usrmgr.blackuser.dao.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月20日 上午11:09:49
 * 创建作者：周颖
 * 文件名称：BlackUserSqlTest.java
 * 版本：  v1.0
 * 功能：黑名单sql测试类
 * 修改记录：
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class BlackUserSqlTest {

    /**被测试类*/
    @InjectMocks
    private BlackUserSql blackUserSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 列表总数sql
     * @author 周颖  
     * @date 2017年2月20日 上午11:16:11
     */
    @Test
    public void testGetCountByParam() {
        Map<String, Object> params = getParams();
        String sql = blackUserSql.getCountByParam(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 列表总数sql
     * @author 方志伟  
     * @date 2017年6月21日 上午9:28:21
     */
    @Test
    public void testGetCountByParamNoMerchant() {
        Map<String, Object> params = getParamNoMerchantId();
        String sql = blackUserSql.getCountByParam(params);
        Assert.assertNotNull(sql);
    }

    /**
     * 列表
     * @author 周颖  
     * @date 2017年2月20日 上午11:17:27
     */
    @Test
    public void testGetListByParam() {
        Map<String, Object> params = getParams();
        String sql = blackUserSql.getListByParam(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 列表
     * @author 方志伟  
     * @date 2017年6月21日 上午9:29:24
     */
    @Test
    public void testGetListByParamNoMerchant() {
        Map<String, Object> params = getParamNoMerchantId();
        String sql = blackUserSql.getListByParam(params);
        Assert.assertNotNull(sql);
    }

    /**
     * 入参
     * @return 入参
     * @author 周颖  
     * @date 2017年2月20日 上午11:17:40
     */
    public Map getParams(){
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("keywords", "keywords");
        params.put("merchantId", 1L);
        params.put("cascadeLabel", "cascadeLabel");
        params.put("matchRule", 1);
        return params;
    }
    
    /**
     * 入参
     * @return 入参
     * @author 方志伟  
     * @date 2017年6月21日 上午9:27:52
     */
    public Map getParamNoMerchantId(){
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("keywords", "keywords");
        params.put("cascadeLabel", "cascadeLabel");
        params.put("matchRule", 1);
        return params;
    }
}