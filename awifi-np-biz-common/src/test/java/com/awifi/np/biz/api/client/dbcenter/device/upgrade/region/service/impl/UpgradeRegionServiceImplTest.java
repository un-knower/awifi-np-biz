/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月18日 下午8:07:52
* 创建作者：尤小平
* 文件名称：UpgradeRegionServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.service.impl;

import static org.mockito.Matchers.any;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SysConfigUtil.class, CenterHttpRequest.class })
public class UpgradeRegionServiceImplTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private UpgradeRegionServiceImpl service;

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:32:28
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(CenterHttpRequest.class);
    }

    /**
     * destroy.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:32:36
     */
    @After
    public void tearDown() throws Exception {
        service = null;
    }

    /**
     * 测试根据条件查询列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:33:11
     */
    @Test
    public void testQueryListByParam() throws Exception {
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setProvince(10L);
        int begin = 1;
        int pageSize = 15;

        Map<String, Object> resultMap = new HashMap<String, Object>();
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("province", 10);
        array.add(jsonObject);
        resultMap.put("rs", array);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(resultMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("queryListByParamUrl");

        List<DeviceUpgradeRegion> actual = service.queryListByParam(region, begin, pageSize);
        
        Assert.assertEquals(1, actual.size());
        SysConfigUtil.getParamValue(any(String.class));
        CenterHttpRequest.sendGetRequest(any(String.class), any(String.class));
    }

    /**
     * 测试根据条件统计条数.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:33:33
     */
    @Test
    public void testQueryCountByParam() throws Exception {
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setProvince(10L);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("rs", 1);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("queryCountByParamUrl");
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(resultMap);

        int actual = service.queryCountByParam(region);
        Assert.assertEquals(1, actual);
        CenterHttpRequest.sendGetRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    /**
     * 测试根据id查询.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:33:48
     */
    @Test
    public void testQueryById() throws Exception {
        Long id = 2L;

        Map<String, Object> resultMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("province", 10);
        resultMap.put("rs", jsonObject);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(any(String.class), any(String.class))).thenReturn(resultMap);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("queryByIdUrl");

        DeviceUpgradeRegion actual = service.queryById(id);

        Assert.assertEquals(10L, (long) actual.getProvince());
        CenterHttpRequest.sendGetRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    /**
     * 测试新增终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:34:02
     */
    @Test
    public void testAdd() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suc", true);
        map.put("msg", "成功");
        PowerMockito.when(CenterHttpRequest.sendPostRequest(any(String.class), any(String.class))).thenReturn(map);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("addUrl");

        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        service.add(region);

        PowerMockito.verifyStatic();
        CenterHttpRequest.sendPostRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    /**
     * 测试修改终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:34:16
     */
    @Test
    public void testUpdate() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("updateUrl");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suc", true);
        map.put("msg", "成功");
        PowerMockito.when(CenterHttpRequest.sendPutRequest(any(String.class), any(String.class))).thenReturn(map);

        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        service.update(region);

        PowerMockito.verifyStatic();
        CenterHttpRequest.sendPutRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    /**
     * 测试删除终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:34:28
     */
    @Test
    public void testDelete() throws Exception {
        Long id = 10L;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suc", true);
        map.put("msg", "成功");
        PowerMockito.when(CenterHttpRequest.sendDeleteRequest(any(String.class), any(String.class))).thenReturn(map);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("deleteUrl");

        service.delete(id);

        PowerMockito.verifyStatic();
        CenterHttpRequest.sendDeleteRequest(any(String.class), any(String.class));
        SysConfigUtil.getParamValue(any(String.class));
    }

    /**
     * 测试启用.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午9:34:42
     */
    @Test
    public void testStart() throws Exception {
        Long id = 1L;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suc", true);
        map.put("msg", "成功");
        PowerMockito.when(CenterHttpRequest.sendPostRequest(any(String.class), any(String.class))).thenReturn(map);
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("startUrl");

        service.start(id);

        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(any(String.class));
        CenterHttpRequest.sendPostRequest(any(String.class), any(String.class));
    }
}
