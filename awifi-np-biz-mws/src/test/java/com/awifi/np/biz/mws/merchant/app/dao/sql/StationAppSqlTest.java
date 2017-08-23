/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月26日 下午3:38:35
* 创建作者：尤小平
* 文件名称：StationAppSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.app.dao.sql;

import com.awifi.np.biz.mws.merchant.app.model.StationApp;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class StationAppSqlTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private StationAppSql sql;

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 下午5:15:59
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * destroy.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 下午5:16:05
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试根据授权类型查询商户的应用sql.
     * 
     * @author 尤小平  
     * @date 2017年6月26日 下午5:16:14
     */
    @Test
    public void testSelectRelationsByParam() {
        StationApp record = new StationApp();
        record.setMerchantId(10L);
        record.setGrantType("4");
        record.setLimitNum(10);

        String actual = sql.selectRelationsByParam(record);

        String expected = "select t1.Id, t1.AppId, t1.AppSecret, t1.AppName, t1.GrantType, IndustryCode,LinkUrl from station_app t1, station_app_merchant_relation t2 where t1.Id=t2.AppId and t2.MerchantId=#{merchantId} and grantType = #{grantType} limit #{limitNum} ";
        Assert.assertEquals(expected, actual);
    }

}
