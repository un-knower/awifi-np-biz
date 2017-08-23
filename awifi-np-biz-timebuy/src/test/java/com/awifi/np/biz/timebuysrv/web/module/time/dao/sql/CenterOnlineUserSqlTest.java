/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 下午3:21:25
* 创建作者：余红伟
* 文件名称：CenterOnlineUserSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.dao.sql;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;

public class CenterOnlineUserSqlTest {
    
    private CenterVipUserSql centerOnlineUserSql;

    @Before
    public void Before(){
        centerOnlineUserSql = new CenterVipUserSql();
    }
    
    @Test
    public void testAdd() {
         VipUserObject object = new VipUserObject();
         object.setId(10L);
         object.setTelephone("17328878609");
         object.setProcessFlg("千秋");
         object.setStartTime(System.currentTimeMillis());
         object.setEndTime(System.currentTimeMillis());
         object.setCreateDate(new Date());
         object.setModifyDate(new Date());
         object.setMerchantId(79L);
         String actual = centerOnlineUserSql.add(object);
         
         assertNotNull(actual);
    }
    @Test
    public void testUpdate(){
        VipUserObject object = new VipUserObject();
        
        object.setId(10L);
        object.setTelephone("12345678910");
        object.setProcessFlg("千秋");
        object.setStartTime(System.currentTimeMillis());
        object.setEndTime(System.currentTimeMillis());
        object.setCreateDate(new Date());
        object.setModifyDate(new Date());
        object.setMerchantId(79L);
        
        String actual = centerOnlineUserSql.update(object);
        
        assertNotNull(actual);
        
    }
    
    @Test
    public void testQueryOnlineUserCount(){
        Map<String, Object> params = new HashMap<>();
        
        params.put("telephone", "12345678910");
        params.put("nowTime", System.currentTimeMillis());
        
        String actual = centerOnlineUserSql.queryOnlineUserCount(params);
        
        assertNotNull(actual);
    }
    
    @Test
    public void testQueryLastOnlineUser(){
        Map<String, Object> params = new HashMap<>();
        
        params.put("telephone", "12345678910");
        
        String actual = centerOnlineUserSql.queryLastOnlineUser(params);
        
        assertNotNull(actual);
    }
    
    @Test
    public void testQueryListByMerArea(){
        Map<String, Object> params = new HashMap<>();
        
        params.put("telephone", "12345678910");
        params.put("startTime", System.currentTimeMillis());
        params.put("endTime", System.currentTimeMillis());
        params.put("province", "浙江");
        params.put("city", "杭州");
        params.put("county", "西湖区");
        params.put("merchantId", "23L");
        params.put("merchantName", "商户N");
        params.put("start", 10);
        params.put("pageSize", 20);
        
        String actual = centerOnlineUserSql.queryListByMerArea(params);
        
        assertNotNull(actual);
    }
    
    @Test
    public void testQueryCountByMerArea(){
        Map<String, Object> params = new HashMap<>();
        
        params.put("telephone", "12345678910");
        params.put("startTime", System.currentTimeMillis());
        params.put("endTime", System.currentTimeMillis());
        params.put("province", "浙江");
        params.put("city", "杭州");
        params.put("county", "西湖区");
        params.put("merchantId", "23L");
        params.put("merchantName", "商户N");
        
        String actual = centerOnlineUserSql.queryCountByMerArea(params);
        assertNotNull(actual);
    }
    
    @Test
    public void testUpdateVipUser(){
        VipUserObject object = new VipUserObject();
        
        object.setMerchantId(55L);
        object.setTelephone("123445678912");
        
        String actual = centerOnlineUserSql.updateVipUser(object);
        
        assertNotNull(actual);
    }
    @Test
    public void testQueryListByParam(){
        Map<String, Object> map = new HashMap<>();
        map.put("telephone", 123456789176L);
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        String actual  = centerOnlineUserSql.queryListByParam(map);
        assertNotNull(actual);
    }
}
