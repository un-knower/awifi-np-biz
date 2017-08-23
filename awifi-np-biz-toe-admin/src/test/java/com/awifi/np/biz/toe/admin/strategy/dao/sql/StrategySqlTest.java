/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 下午3:55:51
* 创建作者：方志伟
* 文件名称：StrategySqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.strategy.dao.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.Assert;
@RunWith(PowerMockRunner.class)
public class StrategySqlTest {
    /**被测试类*/
    @InjectMocks
    private StrategySql strategySql;
    /**初始化*/
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试批量获取站点下的策略数量
     * @author 方志伟  
     * @date 2017年6月20日 下午4:15:30
     */
    @Test
    public void testGetTotalBySiteIds(){
        Map<String, Object> params = getParams();
        strategySql.getTotalBySiteIds(params);
    }
    
    /**
     * 测试站点策略列表
     * @author 方志伟  
     * @date 2017年6月20日 下午4:17:30
     */
    @Test
    public void testGetCountByParam(){
        Map<String, Object> params = getParams();
        strategySql.getCountByParam(params);
    }
    
    /**
     * 测试策略列表
     * @author 方志伟  
     * @date 2017年6月20日 下午4:20:05
     */
    @Test
    public void testGetListByParam(){
        Map<String, Object> params = getParams();
        strategySql.getListByParam(params);
    }
    
    /**
     * 测试获取站点id -- sql
     * @author 方志伟  
     * @date 2017年6月20日 下午4:22:17
     */
    @Test
    public void testGetSiteId(){
        Map<String, Object> params = getParams();
        strategySql.getSiteId(params);
    }
    
    /**
     * 入参通过项目id获取名称
     * @return 结果
     * @author 方志伟  
     * @date 2017年6月20日 下午4:06:46
     */
    private Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", 1L);
        params.put("strategyName", "strategyName");
        params.put("merchantId", 2L);
        params.put("ssid", "ssid");
        params.put("devId", "devId");
        params.put("id", 1L);
        return params;
    }
}
