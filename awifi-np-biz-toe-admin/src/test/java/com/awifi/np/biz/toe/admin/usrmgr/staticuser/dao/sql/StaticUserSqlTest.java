package com.awifi.np.biz.toe.admin.usrmgr.staticuser.dao.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月20日 上午11:26:27
 * 创建作者：周颖
 * 文件名称：StaticUserSqlTest.java
 * 版本：  v1.0
 * 功能：静态用户sql测试类
 * 修改记录：
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class StaticUserSqlTest {

    /**被测试类*/
    @InjectMocks
    private StaticUserSql staticUserSql;
    
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
        String sql = staticUserSql.getCountByParam(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 列表总数的sql
     * merchantId == null
     * @author 方志伟  
     * @date 2017年6月21日 上午10:25:27
     */
    @Test
    public void testGetCountByParamAndMIdIsN(){
        Map<String, Object> params = getParamsAndMerchantIdIsNull();
        staticUserSql.getCountByParam(params);
    }

    /**
     * 列表
     * @author 周颖  
     * @date 2017年2月20日 上午11:17:27
     */
    @Test
    public void testGetListByParam() {
        Map<String, Object> params = getParams();
        String sql = staticUserSql.getListByParam(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * 列表
     * merchantId == null
     * @author 方志伟  
     * @date 2017年6月21日 上午10:25:27
     */
    @Test
    public void testGetListByParamAndMIdIsN() {
        Map<String, Object> params = getParamsAndMerchantIdIsNull();
        staticUserSql.getListByParam(params);
    }
    
    /**
     * 批量删除
     * @author 周颖  
     * @date 2017年2月20日 下午2:03:08
     */
    @Test
    public void testBatchDelete() {
        Map<String, Object> params = getParams();
        String sql = staticUserSql.batchDelete(params);
        Assert.assertNotNull(sql);
    }
    
    /**
     * @author 方志伟  
     * @date 2017年6月21日 上午10:22:55
     */
    @Test
    public void testGetListByMerchantIdACellP(){
        Map<String, Object> params = getParams();
        staticUserSql.getListByMerchantIdAndCellPhone(params);
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
        params.put("userType", 1);
        return params;
    }
    
    /**
     * 入参
     * @return 入参
     * @author 方志伟  
     * @date 2017年6月21日 上午10:22:37
     */
    public Map getParamsAndMerchantIdIsNull(){
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("keywords", "keywords");
        params.put("cascadeLabel", "cascadeLabel");
        params.put("userType", 1);
        return params;
    }
}