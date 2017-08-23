/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 上午10:01:20
* 创建作者：周颖
* 文件名称：SmsConfigSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.system.smsconfig.dao.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.util.SqlUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SqlUtil.class)
public class SmsConfigSqlTest {
    
    /**被测试类*/
    @InjectMocks
    private SmsConfigSql smsConfigSql;

    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SqlUtil.class);
    }
    
    /**
     * 短信配置总数
     * @author 周颖  
     * @date 2017年6月27日 上午10:12:14
     */
    @Test
    public void testGetCountByParam() {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("merchantId", 1L);
        Long[] merchantIds = {1L,2L};
        params.put("merchantIds", merchantIds);
        smsConfigSql.getCountByParam(params);
    }

    /**
     * 短信配置列表
     * @author 周颖  
     * @date 2017年6月27日 上午10:12:25
     */
    @Test
    public void testGetListByParam() {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("merchantId", 1L);
        Long[] merchantIds = {1L,2L};
        params.put("merchantIds", merchantIds);
        smsConfigSql.getListByParam(params);
    }
}
