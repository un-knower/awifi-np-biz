/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月2日 下午2:59:14
* 创建作者：尤小平
* 文件名称：MerchantManagerSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MerchantManagerSqlTest {
    /**
     * MerchantManagerSql
     */
    private MerchantManagerSql merchantManagerSql;

    /**
     * MerchantManager
     */
    private MerchantManager merchantManager;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午3:17:54
     */
    @Before
    public void setUp() throws Exception {
        merchantManagerSql = new MerchantManagerSql();
        merchantManager = new MerchantManager();
        merchantManager.setId(1L);
        merchantManager.setUid("uid");
        merchantManager.setMid("mid");
        merchantManager.setUname("uname");
        merchantManager.setType("type");
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 下午3:18:05
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试查询MerchantManager列表.
     * 
     * @author 尤小平  
     * @date 2017年5月2日 下午3:18:18
     */
    @Test
    public void testGetListByMerchantManager() {
        String actual = merchantManagerSql.getListByMerchantManager(merchantManager);

        Assert.assertNotNull(actual);
        String expected = "select id, uid, mid, uname, type from merchant_manager where 1=1 and id = #{id} and uid = #{uid} and mid = #{mid} and uname like concat('%',#{uname},'%') and type = #{type} ";
        Assert.assertEquals(expected, actual);
    }

    /**
     * 测试查询MerchantManager列表，分页.
     * 
     * @author 尤小平  
     * @date 2017年5月2日 下午3:18:33
     */
    @Test
    public void testGetListByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        Page<MerchantManager> page = new Page<MerchantManager>();
        page.setBegin(1);
        page.setPageSize(15);
        params.put("page", page);
        params.put("merchantManager", merchantManager);

        String actual = merchantManagerSql.getListByParam(params);

        Assert.assertNotNull(actual);
    }

    /**
     * 测试查询MerchantManager列表总条数.
     * 
     * @author 尤小平  
     * @date 2017年5月2日 下午3:18:48
     */
    @Test
    public void testGetCountByParams() {
        String actual = merchantManagerSql.getCountByParams(merchantManager);

        Assert.assertNotNull(actual);
    }

    /**
     * 测试插入.
     * 
     * @author 尤小平  
     * @date 2017年5月2日 下午3:19:01
     */
    @Test
    public void testInsert() {
        String actual = merchantManagerSql.insert(merchantManager);

        Assert.assertNotNull(actual);
    }

    /**
     * 测试修改.
     * 
     * @author 尤小平  
     * @date 2017年5月2日 下午3:19:12
     */
    @Test
    public void testUpdate() {
        String actual = merchantManagerSql.update(merchantManager);

        Assert.assertNotNull(actual);
    }
}
