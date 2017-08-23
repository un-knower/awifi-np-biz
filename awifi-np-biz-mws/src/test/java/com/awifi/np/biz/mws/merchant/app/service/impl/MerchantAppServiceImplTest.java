/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月26日 上午11:19:55
* 创建作者：尤小平
* 文件名称：MerchantAppServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.app.service.impl;

import com.awifi.np.biz.mws.merchant.app.dao.StationAppDao;
import com.awifi.np.biz.mws.merchant.app.model.StationApp;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ StationAppDao.class })
public class MerchantAppServiceImplTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private MerchantAppServiceImpl service;

    /**
     * StationAppDao
     */
    @Mock
    private StationAppDao stationAppDao;

    /**
     * init.
     *
     * @throws Exception 异常
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(StationAppDao.class);
    }

    /**
     * destroy.
     *
     * @throws Exception 异常
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试根据Id获取应用.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 下午3:21:13
     */
    @Test
    public void testGetAppById() throws Exception {
        StationApp stationApp = new StationApp();
        stationApp.setAppName("appName");
        PowerMockito.when(stationAppDao.selectByPrimaryKey(anyInt())).thenReturn(stationApp);

        StationApp actual = service.getAppById(2);

        Assert.assertEquals("appName", actual.getAppName());
        PowerMockito.verifyStatic();
        stationAppDao.selectByPrimaryKey(anyInt());
    }

    /**
     * 测试删除商户下某类型的所有应用.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 下午3:21:28
     */
    @Test
    public void testDeleteMerchantAppByType() throws Exception {
        PowerMockito.when(stationAppDao.deleteRelationsByParam(anyObject())).thenReturn(1);
        Boolean actual = service.deleteMerchantAppByType(1L, "4");

        Assert.assertTrue(actual);
        PowerMockito.verifyStatic();
        stationAppDao.deleteRelationsByParam(anyObject());
    }

    /**
     * 测试根据授权类型查询商户的应用.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 下午3:21:41
     */
    @Test
    public void testSelectMerchantAppByType() throws Exception {
        List<StationApp> list = new ArrayList<StationApp>();
        PowerMockito.when(stationAppDao.selectRelationsByParam(anyObject())).thenReturn(list);

        List<StationApp> actual = service.selectMerchantAppByType(1L, "4", 10);

        Assert.assertEquals(0, actual.size());
        PowerMockito.verifyStatic();
        stationAppDao.selectRelationsByParam(anyObject());
    }

    /**
     * 测试查询商户已发布和已配置的应用.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 下午3:21:53
     */
    @Test
    public void testGetAppListByMerchantId() throws Exception {
        PowerMockito.when(stationAppDao.getAppListByMerchantId(anyLong())).thenReturn(null);

        List<StationApp> actual = service.getAppListByMerchantId(10L);

        Assert.assertNull(actual);
        PowerMockito.verifyStatic();
        stationAppDao.getAppListByMerchantId(anyLong());
    }

    /**
     * 测试获取应用商城应用列表（商户未获取的应用列表）.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 下午3:22:05
     */
    @Test
    public void testGetMerchantAppStoreList() throws Exception {
        PowerMockito.when(stationAppDao.getMerchantAppStoreList(anyLong())).thenReturn(null);

        List<StationApp> actual = service.getMerchantAppStoreList(10L);

        Assert.assertNull(actual);
        PowerMockito.verifyStatic();
        stationAppDao.getMerchantAppStoreList(anyLong());
    }

    /**
     * 测试获取应用商城应用列表（商户已购应用列表）.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 下午3:22:19
     */
    @Test
    public void testGetMerchantAppBuyList() throws Exception {
        PowerMockito.when(stationAppDao.getMerchantAppBuyList(anyLong())).thenReturn(null);

        List<StationApp> actual = service.getMerchantAppBuyList(10L);

        Assert.assertNull(actual);
        PowerMockito.verifyStatic();
        stationAppDao.getMerchantAppBuyList(anyLong());
    }
}