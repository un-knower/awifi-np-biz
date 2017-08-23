/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月15日 上午10:35:12
* 创建作者：尤小平
* 文件名称：WifiRecordControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiRecordService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.annotation.Resource;
import java.util.Map;

public class WifiRecordControllerTest {
    /**
     * 被测试类
     */
    private WifiRecordController controller;

    /**
     * request
     */
    private MockHttpServletRequest request;

    /**
     * WifiRecordService
     */
    @Resource
    private WifiRecordService mockWifiRecordService;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月15日 下午2:10:13
     */
    @Before
    public void setUp() throws Exception {
        controller = new WifiRecordController();
        mockWifiRecordService = Mockito.mock(WifiRecordService.class);
        controller.setWifiRecordService(mockWifiRecordService);
        request = new MockHttpServletRequest();
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月15日 下午2:10:23
     */
    @After
    public void tearDown() throws Exception {
        controller = null;
        mockWifiRecordService = null;
    }

    /**
     * 测试根据id获取网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月15日 下午2:10:32
     */
    @Test
    public void testGetById() throws Exception {
        String id = "2";

        WifiRecord record = new WifiRecord();
        record.setDeviceId("deviceId");

        Mockito.when(mockWifiRecordService.selectById(any(Long.class))).thenReturn(record);

        Map<String, Object> actual = controller.getById(id);
        Assert.assertNotNull(actual.get("data"));
        Assert.assertEquals("deviceId", ((WifiRecord) actual.get("data")).getDeviceId());
        Mockito.verify(mockWifiRecordService).selectById(any(Long.class));
    }

    /**
     * 测试根据deviceId和token获取网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月15日 下午2:10:42
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetByDevIdAndToken() throws Exception {

        request.setParameter("deviceId", "FAT_AP_123456");
        request.setParameter("token", "123");

        WifiRecord record = new WifiRecord();
        Mockito.when(mockWifiRecordService.selectByDevIdAndToken(any(Map.class))).thenReturn(record);

        Map<String, Object> actual = controller.getByDevIdAndToken(request);
        Assert.assertNotNull(actual);
        Mockito.verify(mockWifiRecordService).selectByDevIdAndToken(any(Map.class));
    }

    /**
     * 测试添加网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月15日 下午2:10:49
     */
    @Test
    public void testAdd() throws Exception {
        request.setParameter("userId", "10");
        request.setParameter("merchantId", "2");
        request.setParameter("portalUrl", "http://www.portal.51awifi.com");
        request.setParameter("telphone", "136");
        request.setParameter("devType", "FAT_AP");
        request.setParameter("deviceId", "FAT_AP_123456");
        request.setParameter("token", "123");

        Mockito.when(mockWifiRecordService.save(any(WifiRecord.class))).thenReturn(20L);

        Map<String, Object> actual = controller.add(request);
        Assert.assertEquals(20L, (actual.get("data")));
        Mockito.verify(mockWifiRecordService).save(any(WifiRecord.class));
    }

    /**
     * 测试更新网络放通日志信息.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月15日 下午2:10:55
     */
    @Test
    public void testEdit() throws Exception {
        request.setParameter("id", "10");
        request.setParameter("wifiUrl", "wifiUrl");
        request.setParameter("wifiResult", "wifiResult");

        Mockito.when(mockWifiRecordService.update(any(WifiRecord.class))).thenReturn(true);

        Map<String, Object> actual = controller.edit(request);
        Assert.assertNotNull(actual);
        Assert.assertTrue((boolean) actual.get("data"));
        Mockito.verify(mockWifiRecordService).update(any(WifiRecord.class));
    }

}
