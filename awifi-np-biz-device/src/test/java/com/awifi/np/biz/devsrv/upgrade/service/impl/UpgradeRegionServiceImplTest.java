/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月18日 上午9:05:30
* 创建作者：尤小平
* 文件名称：DeviceUpgradeRegionServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.service.impl;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.util.UpgradeRegionClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.common.base.model.Page;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UpgradeRegionClient.class, CorporationClient.class, LocationClient.class, DateUtil.class,
        JsonUtil.class, MessageUtil.class, SysConfigUtil.class })
public class UpgradeRegionServiceImplTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private DeviceUpgradeRegionServiceImpl service;

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:52:07
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(UpgradeRegionClient.class);
        PowerMockito.mockStatic(CorporationClient.class);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(DateUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }

    /**
     * destroy.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:52:21
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试定制终端-获取区域默认升级包列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:53:26
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGetListByParam() throws Exception {
        List<DeviceUpgradeRegion> list = new ArrayList<DeviceUpgradeRegion>();
        Page<DeviceUpgradeRegion> page = new Page(1, 15);
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setModelId(4L);
        region.setProvince(20L);
        region.setCity(30L);
        region.setCounty(40L);
        region.setCorporationId(3L);
        region.setStartTime(new Date());
        region.setUserName("userName");
        region.setState(1L);
        region.setType("module");
        list.add(region);

        PowerMockito.when(UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class))).thenReturn(list);
        PowerMockito.when(UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class))).thenReturn(1);

        Map<String, Object> corp = new HashMap<String, Object>();
        corp.put("corpName", "huawei");
        PowerMockito.when(CorporationClient.queryCorpById(any(Long.class))).thenReturn(corp);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("modelName", "G20");
        PowerMockito.when(CorporationClient.queryModelById(any(Long.class))).thenReturn(model);

        service.getListByParam(region, page);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class));
        UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class));
        CorporationClient.queryCorpById(any(Long.class));
        CorporationClient.queryModelById(any(Long.class));
    }
    
    /**
     * 测试定制终端-获取区域默认升级包列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:53:26
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGetListByParam4StateClose() throws Exception {
        List<DeviceUpgradeRegion> list = new ArrayList<DeviceUpgradeRegion>();
        Page<DeviceUpgradeRegion> page = new Page(1, 15);
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setModelId(4L);
        region.setProvince(20L);
        region.setCity(30L);
        region.setCounty(40L);
        region.setCorporationId(3L);
        region.setStartTime(new Date());
        region.setUserName("userName");
        region.setState(0L);
        region.setType("firmware");
        region.setCorporationName("HUAWEI");
        region.setModelName("AS001");
        list.add(region);

        PowerMockito.when(UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class))).thenReturn(list);
        PowerMockito.when(UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class))).thenReturn(1);

        service.getListByParam(region, page);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class));
        UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class));
    }

    /**
     * 测试定制终端-获取区域默认升级包列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:53:26
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGetListByParam4StateOthers() throws Exception {
        List<DeviceUpgradeRegion> list = new ArrayList<DeviceUpgradeRegion>();
        Page<DeviceUpgradeRegion> page = new Page(1, 15);
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setModelId(4L);
        region.setState(2L);
        region.setType("12");
        list.add(region);

        PowerMockito.when(UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class))).thenReturn(list);
        PowerMockito.when(UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class))).thenReturn(1);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("modelName", "G20");
        PowerMockito.when(CorporationClient.queryModelById(any(Long.class))).thenReturn(model);
        
        service.getListByParam(region, page);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class));
        UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class));
        CorporationClient.queryModelById(any(Long.class));
    }

    /**
     * 测试定制终端-获取区域默认升级包列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:53:26
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGetListByParam4StateNull() throws Exception {
        List<DeviceUpgradeRegion> list = new ArrayList<DeviceUpgradeRegion>();
        Page<DeviceUpgradeRegion> page = new Page(1, 15);
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setModelId(4L);
        region.setModelName("AS001");
        list.add(region);

        PowerMockito.when(UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class))).thenReturn(list);
        PowerMockito.when(UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class))).thenReturn(1);

        service.getListByParam(region, page);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class));
        UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class));
    }
    
    /**
     * 测试新增终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:55:38
     */
    @Test
    public void testAdd() throws Exception {
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        PowerMockito.doNothing().when(UpgradeRegionClient.class, "add", any(DeviceUpgradeRegion.class));
        service.add(region);
    }

    /**
     * 测试查看升级情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:55:57
     */
    @Test
    public void testGetById() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setModelId(4L);
        region.setProvince(20L);
        region.setCity(30L);
        region.setCounty(40L);
        region.setCorporationId(3L);
        region.setIssueNum(100L);
        region.setSuccessNum(90L);
        region.setStartTime(new Date());
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(region);

        service.getById(id);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
    }
    
    /**
     * 测试查看升级情况.
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:56:29
     */
    @Test
    public void testGetByIdForSuccessRate() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(region);

        service.getById(id);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
    }
    
    /**
     * 测试删除终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:57:10
     */
    @Test
    public void testDelete() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setModelId(4L);
        region.setProvince(20L);
        region.setCity(30L);
        region.setCounty(40L);
        region.setCorporationId(3L);
        region.setIssueNum(100L);
        region.setSuccessNum(90L);
        region.setStartTime(new Date());
        region.setPath("path");
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(region);
        PowerMockito.doNothing().when(UpgradeRegionClient.class, "delete", any(Long.class));
        PowerMockito.when(SysConfigUtil.getParamValue(any(String.class))).thenReturn("folderPath");

        service.delete(id);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
        SysConfigUtil.getParamValue(any(String.class));
    }
    
    /**
     * 测试删除终端地区升级, 升级包不存在的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:57:30
     */
    @Test(expected = BizException.class)
    public void testDeleteForException() throws Exception {
        Long id = 10L;
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(null);
        PowerMockito.when(MessageUtil.getMessage(any(String.class), any(Object.class))).thenReturn("value");
        service.delete(id);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
        MessageUtil.getMessage(any(String.class), any(Object.class));
    }
    
    /**
     * 测试判断是否已经有启用的升级包.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:58:05
     */
    @Test
    public void testExistStartUpgrade() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setType("firmware");
        region.setModelId(4L);
        region.setProvince(20L);
        region.setCity(30L);
        region.setCounty(40L);
        region.setCorporationId(3L);
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(region);
        PowerMockito.when(UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class))).thenReturn(2);

        boolean actual = service.existStartUpgrade(id);

        Assert.assertEquals(true, actual);
        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
        UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class));
    }

    /**
     * 测试判断是否已经有启用的升级包, 升级包不存在的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:58:37
     */
    @Test(expected = BizException.class)
    public void testExistStartUpgForRegNull() throws Exception {
        Long id = 10L;
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(null);

        boolean actual = service.existStartUpgrade(id);

        Assert.assertEquals(true, actual);
        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
    }

    /**
     * 测试判断是否已经有启用的升级包, 升级包类型为空的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:59:07
     */
    @Test(expected = BizException.class)
    public void testExistStartUpgForTypeNull() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(region);

        boolean actual = service.existStartUpgrade(id);

        Assert.assertEquals(true, actual);
        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
    }

    /**
     * 测试判断是否已经有启用的升级包, 升级包类型不存在的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午2:59:34
     */
    @Test(expected = BizException.class)
    public void testExistStartUpgForTypeError() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setType("test");
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(region);

        boolean actual = service.existStartUpgrade(id);

        Assert.assertEquals(true, actual);
        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
    }

    /**
     * 测试启用升级包-启用固件升级包.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午3:00:05
     */
    @Test
    public void testStartForFwUpgrade() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setType("firmware");
        List<DeviceUpgradeRegion> needCloseList = new ArrayList<DeviceUpgradeRegion>();
        needCloseList.add(region);
        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(region);
        PowerMockito.when(UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class))).thenReturn(1);
        PowerMockito.when(UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class),
                any(Integer.class))).thenReturn(needCloseList);

        service.start(id);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
        UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class));
        UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class));
    }

    /**
     * 测试启用升级包-启用组件升级包.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月18日 下午3:00:43
     */
    @Test
    public void testStartForCpUpgrade() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        region.setType("module");
        region.setVersions("12.34.5");

        PowerMockito.when(UpgradeRegionClient.queryById(any(Long.class))).thenReturn(region);

        List<DeviceUpgradeRegion> needCloseList = new ArrayList<DeviceUpgradeRegion>();
        DeviceUpgradeRegion newRegion = new DeviceUpgradeRegion();
        newRegion.setVersions("12.34.7");
        needCloseList.add(newRegion);
        PowerMockito.when(UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class))).thenReturn(1);
        PowerMockito.when(UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class),
                any(Integer.class))).thenReturn(needCloseList);

        service.start(id);

        PowerMockito.verifyStatic();
        UpgradeRegionClient.queryById(any(Long.class));
        UpgradeRegionClient.queryCountByParam(any(DeviceUpgradeRegion.class));
        UpgradeRegionClient.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class));
    }
}
