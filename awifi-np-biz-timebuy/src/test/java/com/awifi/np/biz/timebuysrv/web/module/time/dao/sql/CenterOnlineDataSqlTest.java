/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 下午2:23:38
* 创建作者：余红伟
* 文件名称：CenterOnlineDataSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.dao.sql;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.awifi.np.biz.timebuysrv.web.module.time.model.CenterOnlineDataObject;

public class CenterOnlineDataSqlTest {
    
    private CenterOnlineDataSql centerOnlineDateSql;
    
    @Before
    public void Before(){
        centerOnlineDateSql = new CenterOnlineDataSql();
    }
    
    @Test
    public void testAdd() {
        CenterOnlineDataObject dataObject = new CenterOnlineDataObject();
        dataObject.setBroadbandAccount("ds");
        dataObject.setMerid("34");
        dataObject.setObjectId("ds");
        dataObject.setMobile("ds");
        dataObject.setGoodsCode("advxc");
        dataObject.setPkgPrice("dsd");
        dataObject.setAmount("dsd");
        dataObject.setAdddays("100");
        dataObject.setPkgDetail("ddd");
        dataObject.setInputTime("10000000s");
        dataObject.setRemark1("r1");
        dataObject.setRemark2("r2");
        dataObject.setRemark3("r3");
        dataObject.setSign("d");
        dataObject.setIdentityCode("dddd");
        dataObject.setNowTime("2017");
        dataObject.setProcessFlg("2018");
        dataObject.setTel("dddd");
        dataObject.setStartTime("dd");
        dataObject.setEndTime("ddd");
        dataObject.setCreateDate(new Date());
        dataObject.setPkgNum("dd");
        
        String actual = centerOnlineDateSql.add(dataObject);
        
        assertNotNull(actual);
    }

}
