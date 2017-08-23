/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月15日 下午2:14:10
* 创建作者：尤小平
* 文件名称：WifiRecordServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.service;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.WifiRecordDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class WifiRecordServiceTest {
    /**
     * 被测试类
     */
    private WifiRecordService service;

    /**
     * WifiRecordDao
     */
    private WifiRecordDao dao;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:47:45
     */
    @Before
    public void setUp() throws Exception {
        service = new WifiRecordService();
        dao = Mockito.mock(WifiRecordDao.class);
        service.setWifiRecordDao(dao);
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:47:51
     */
    @After
    public void tearDown() throws Exception {
        service = null;
        dao = null;
    }

    /**
     * 测试根据id获取网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:47:55
     */
    @Test
    public void testSelectById() throws Exception {
        Long id = 10L;
        String deviceId = "deviceId";
        WifiRecord record = new WifiRecord();
        record.setId(id);
        record.setDeviceId(deviceId);

        Mockito.when(dao.selectByPrimaryKey(any(Long.class))).thenReturn(record);

        WifiRecord actual = service.selectById(id);
        Assert.assertNotNull(actual);
        Assert.assertEquals(id, actual.getId());
        Assert.assertEquals(deviceId, actual.getDeviceId());
        Mockito.verify(dao).selectByPrimaryKey(any(Long.class));
    }

    /**
     * 测试根据deviceId和token获取网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:47:59
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSelectByDevIdAndToken() throws Exception {

        String deviceId = "deviceId";
        WifiRecord record = new WifiRecord();
        record.setDeviceId(deviceId);
        Mockito.when(dao.selectByDevIdAndToken(any(Map.class))).thenReturn(record);

        Map<String, Object> params = new HashMap<String, Object>();
        WifiRecord actual = service.selectByDevIdAndToken(params);
        Assert.assertNotNull(actual);
        Assert.assertEquals(deviceId, actual.getDeviceId());
        Mockito.verify(dao).selectByDevIdAndToken(any(Map.class));
    }

    /**
     * 测试添加网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:48:03
     */
    @Test
    public void testSaveSuccess() throws Exception {
        Long id = 1L;
        WifiRecord record = new WifiRecord();
        record.setId(id);
        Mockito.when(dao.insert(any(WifiRecord.class))).thenReturn(1);

        Long actual = service.save(record);
        Assert.assertEquals(id, actual);
        Mockito.verify(dao).insert(any(WifiRecord.class));
    }

    /**
     * 测试添加网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:48:07
     */
    @Test
    public void testSaveIsNull() throws Exception {
        WifiRecord record = null;

        Long actual = service.save(record);
        Assert.assertEquals((long) 0, (long) actual);
    }

    /**
     * 测试添加网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:48:11
     */
    @Test
    public void testSaveForInsertFail() throws Exception {
        WifiRecord record = new WifiRecord();
        Mockito.when(dao.insert(any(WifiRecord.class))).thenReturn(1);

        Long actual = service.save(record);
        Assert.assertEquals((long) 0, (long) actual);
        Mockito.verify(dao).insert(any(WifiRecord.class));
    }

    /**
     * 测试更新网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:48:15
     */
    @Test
    public void testUpdateSuccess() throws Exception {
        WifiRecord record = new WifiRecord();
        Mockito.when(dao.updateByPrimaryKey(any(WifiRecord.class))).thenReturn(1);

        boolean actual = service.update(record);
        Assert.assertTrue(actual);
        Mockito.verify(dao).updateByPrimaryKey(any(WifiRecord.class));
    }

    /**
     * 测试更新网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月15日 下午2:48:19
     */
    @Test
    public void testUpdateFail() throws Exception {
        WifiRecord record = null;

        boolean actual = service.update(record);
        Assert.assertFalse(actual);
    }
}
