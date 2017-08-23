/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月26日 上午10:06:24
* 创建作者：尤小平
* 文件名称：MsMerchantServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.ms.merchant.service.impl;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.ms.util.TimeTag;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MerchantClient.class, TimeTag.class })
public class MsMerchantServiceImplTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private MsMerchantServiceImpl service;

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 上午11:06:22
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(TimeTag.class);
    }

    /**
     * destroy.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 上午11:07:19
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试根据merchantId查找商户，排除已撤销的商户.
     * 测试正常的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 上午11:07:49
     */
    @Test
    public void testFindMerchantInfoExclude9() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setProvince("浙江");
        merchant.setCity("杭州");
        merchant.setArea("拱墅");
        merchant.setStatus(1);
        merchant.setOpenTime("1465706917");
        merchant.setCloseTime("1465707928");
        merchant.setContactWay("18912345678");
        PowerMockito.when(MerchantClient.getById(anyLong())).thenReturn(merchant);
        PowerMockito.when(TimeTag.getTimeFromInt(anyInt())).thenReturn("07:00");

        Merchant actual = service.findMerchantInfoExclude9(1L);

        Assert.assertEquals("18912345678", actual.getContactWay());
        PowerMockito.verifyStatic();
        MerchantClient.getById(anyLong());
        TimeTag.getTimeFromInt(anyInt());
    }

    /**
     * 测试根据merchantId查找商户，排除已撤销的商户.
     * 测试商户已撤销的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 上午11:07:57
     */
    @Test
    public void testFindMerchantInfoForStatus() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setStatus(9);
        PowerMockito.when(MerchantClient.getById(anyLong())).thenReturn(merchant);

        Merchant actual = service.findMerchantInfoExclude9(1L);

        Assert.assertNull(actual);
        PowerMockito.verifyStatic();
        MerchantClient.getById(anyLong());
    }

    /**
     * 测试根据merchantId查找商户，排除已撤销的商户.
     * 测试其他分支的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 上午11:08:11
     */
    @Test
    public void testFindMerchantInfoForOthers() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setAddress("莫干山路118号");
        merchant.setStatus(1);
        PowerMockito.when(MerchantClient.getById(anyLong())).thenReturn(merchant);

        Merchant actual = service.findMerchantInfoExclude9(1L);

        Assert.assertEquals("9:00", actual.getOpenTime());
        PowerMockito.verifyStatic();
        MerchantClient.getById(anyLong());
    }

    /**
     * 测试根据merchantId查找商户，排除已撤销的商户.
     * 测试发生异常的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月26日 上午11:08:19
     */
    @Test
    public void testFindMerchantInfoForEx() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setAddress("莫干山路118号");
        merchant.setStatus(1);
        PowerMockito.when(MerchantClient.getById(anyLong())).thenThrow(new Exception());

        Merchant actual = service.findMerchantInfoExclude9(1L);

        Assert.assertNull(actual);
        PowerMockito.verifyStatic();
        MerchantClient.getById(anyLong());
    }
}