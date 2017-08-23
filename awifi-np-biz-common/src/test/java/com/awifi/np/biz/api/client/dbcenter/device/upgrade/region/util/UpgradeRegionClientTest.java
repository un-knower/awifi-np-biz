/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月19日 上午9:35:51
* 创建作者：尤小平
* 文件名称：UpgradeRegionClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.util;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.service.UpgradeRegionService;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.service.impl.UpgradeRegionServiceImpl;
import com.awifi.np.biz.common.util.BeanUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BeanUtil.class })
public class UpgradeRegionClientTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private UpgradeRegionClient client;

    /**
     * UpgradeRegionService
     */
    private static UpgradeRegionService upgradeRegionService;

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:06:44
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        upgradeRegionService = Mockito.mock(UpgradeRegionServiceImpl.class);
        PowerMockito.when(BeanUtil.getBean(any(String.class))).thenReturn(upgradeRegionService);
    }

    /**
     * destroy.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:06:51
     */
    @After
    public void tearDown() throws Exception {
        client = null;
        upgradeRegionService = null;
    }

    /**
     * 测试新增终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:07:06
     */
    @Test
    public void testAdd() throws Exception {
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        client.add(region);

        PowerMockito.verifyStatic();
    }

    /**
     * 测试根据主键查询终端地区升级包.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:07:25
     */
    @Test
    public void testQueryById() throws Exception {
        Long id = 10L;
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        PowerMockito.when(upgradeRegionService.queryById(any(Long.class))).thenReturn(region);

        client.queryById(id);

        PowerMockito.verifyStatic();
        upgradeRegionService.queryById(any(Long.class));
    }

    /**
     * 测试查询终端地区升级列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:07:37
     */
    @Test
    public void testQueryListByParam() throws Exception {
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        int begin = 1;
        int pageSize = 15;
        PowerMockito.when(upgradeRegionService.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class),
                any(Integer.class))).thenReturn(null);

        client.queryListByParam(region, begin, pageSize);

        PowerMockito.verifyStatic();
        upgradeRegionService.queryListByParam(any(DeviceUpgradeRegion.class), any(Integer.class), any(Integer.class));
    }

    /**
     * 测试查询终端地区升级总数.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:07:49
     */
    @Test
    public void testQueryCountByParam() throws Exception {
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        PowerMockito.when(upgradeRegionService.queryCountByParam(any(DeviceUpgradeRegion.class))).thenReturn(1);

        client.queryCountByParam(region);

        PowerMockito.verifyStatic();
        upgradeRegionService.queryCountByParam(any(DeviceUpgradeRegion.class));
    }

    /**
     * 测试更新终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:08:00
     */
    @Test
    public void testUpdate() throws Exception {
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        PowerMockito.doNothing().when(upgradeRegionService).update(any(DeviceUpgradeRegion.class));

        client.update(region);

        PowerMockito.verifyStatic();
        upgradeRegionService.update(any(DeviceUpgradeRegion.class));
    }

    /**
     * 测试删除终端地区升级.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:08:10
     */
    @Test
    public void testDelete() throws Exception {
        Long id = 10L;
        PowerMockito.doNothing().when(upgradeRegionService).delete(any(Long.class));

        client.delete(id);

        PowerMockito.verifyStatic();
        upgradeRegionService.delete(any(Long.class));
    }

    /**
     * 测试启用.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月19日 上午10:32:22
     */
    @Test
    public void testStart() throws Exception {
        Long id = 10L;
        PowerMockito.doNothing().when(upgradeRegionService).start(any(Long.class));

        client.start(id);

        PowerMockito.verifyStatic();
        upgradeRegionService.start(any(Long.class));
    }

}
