/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月15日 下午3:01:39
* 创建作者：尤小平
* 文件名称：WifiRecordSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.dao.sql;

import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WifiRecordSqlTest {
    /**
     * 被测试类
     */
    private WifiRecordSql sql;

    /**
     * WifiRecord
     */
    private WifiRecord record;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午3:11:40
     */
    @Before
    public void setUp() throws Exception {
        sql = new WifiRecordSql();
        record = new WifiRecord();
        record.setUserId(10L);
        record.setMerchantId(2L);
        record.setPortalUrl("url");
        record.setDeviceId("devId");
        record.setTelphone("13612345678");
        record.setDevType("ac");
        record.setToken("token");
        record.setErrorInfo("eror");
        record.setId(1L);
        record.setWifiUrl("wifiUrl");
        record.setWifiResult("result");
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午3:11:45
     */
    @After
    public void tearDown() throws Exception {
        sql = null;
    }

    /**
     * test insert.
     * 
     * @author 尤小平  
     * @date 2017年5月15日 下午3:11:53
     */
    @Test
    public void testInsert() {
        String actual = sql.insert(record);

        Assert.assertNotNull(actual);
    }

    /**
     * test update.
     * 
     * @author 尤小平  
     * @date 2017年5月15日 下午3:11:59
     */
    @Test
    public void testUpdate() {
        String actual = sql.update(record);

        Assert.assertNotNull(actual);
    }
}
