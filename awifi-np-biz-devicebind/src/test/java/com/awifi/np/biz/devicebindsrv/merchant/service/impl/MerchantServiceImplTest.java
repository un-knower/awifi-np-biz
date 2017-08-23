/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 22, 2017 11:23:06 AM
* 创建作者：季振宇
* 文件名称：MerchantServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.merchant.service.impl;

import org.apache.commons.lang3.StringUtils;
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

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.util.FormatUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MerchantClient.class,IndustryClient.class,StringUtils.class,LocationClient.class,FormatUtil.class})
public class MerchantServiceImplTest {
    
    /**被测试类*/
    @InjectMocks
    private MerchantServiceImpl merchantServiceImpl;
    
    /**
     * 初始化
     * @author 季振宇  
     * @date Jun 21, 2017 3:01:29 PM
     */
    @Before
    public void before () {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(FormatUtil.class);
        PowerMockito.mockStatic(IndustryClient.class);
    }

    /**
     * 测试获取商户详情
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 2:10:37 PM
     */
    @Test
    public void testGetById() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setProvince("Province");
        merchant.setCity("City");
        merchant.setArea("Area");
        merchant.setPriIndustry("industryId");
        
        PowerMockito.when(MerchantClient.getById(Mockito.anyLong())).thenReturn(merchant);
        PowerMockito.when(IndustryClient.getNameByCode(Mockito.anyString())).thenReturn("industryId");
        PowerMockito.when(StringUtils.isBlank(Mockito.anyString())).thenReturn(true);
        PowerMockito.when(LocationClient.getByIdAndParam(Mockito.anyLong(), Mockito.anyString())).thenReturn("province");
        PowerMockito.when(FormatUtil.locationFullName(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn("LocationFullName");
        
        merchantServiceImpl.getById(1L);
        
        PowerMockito.verifyStatic();
        MerchantClient.getById(1L);
        StringUtils.isNotBlank("industryId");
        StringUtils.isNotBlank("industryId");
        IndustryClient.getNameByCode("industryId");
        LocationClient.getByIdAndParam(38L, "nameParam");
        LocationClient.getByIdAndParam(383L, "nameParam");
        LocationClient.getByIdAndParam(3883L, "nameParam");
        FormatUtil.locationFullName("province", "city", "area");
    }
    
    /**
     * 测试获取商户详情
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 2:10:37 PM
     */
    @Test
    public void testGetById1() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setProvince("Province");
        merchant.setCity("City");
        merchant.setArea("Area");
        merchant.setPriIndustry("");
        
        PowerMockito.when(MerchantClient.getById(Mockito.anyLong())).thenReturn(merchant);
        PowerMockito.when(StringUtils.isBlank(Mockito.anyString())).thenReturn(true);
        PowerMockito.when(IndustryClient.getNameByCode(Mockito.anyString())).thenReturn("industryId");
        PowerMockito.when(LocationClient.getByIdAndParam(Mockito.anyLong(), Mockito.anyString())).thenReturn("province");
        PowerMockito.when(FormatUtil.locationFullName(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn("LocationFullName");
        
        merchantServiceImpl.getById(1L);
        
        PowerMockito.verifyStatic();
        MerchantClient.getById(1L);
        StringUtils.isNotBlank("industryId");
        StringUtils.isNotBlank("industryId");
        IndustryClient.getNameByCode("industryId");
        IndustryClient.getNameByCode("industryId");
        LocationClient.getByIdAndParam(38L, "nameParam");
        LocationClient.getByIdAndParam(383L, "nameParam");
        LocationClient.getByIdAndParam(3883L, "nameParam");
        FormatUtil.locationFullName("province", "city", "area");
    }
    
    /**
     * 测试获取商户详情
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 2:10:37 PM
     */
    @Test
    public void testGetById2() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setProvince("Province");
        merchant.setCity("City");
        merchant.setArea("Area");
        merchant.setPriIndustry("");
        merchant.setSecIndustry("");
        
        PowerMockito.when(MerchantClient.getById(Mockito.anyLong())).thenReturn(merchant);
        PowerMockito.when(StringUtils.isBlank(Mockito.anyString())).thenReturn(false);
        PowerMockito.when(IndustryClient.getNameByCode(Mockito.anyString())).thenReturn("industryId");
        PowerMockito.when(LocationClient.getByIdAndParam(Mockito.anyLong(), Mockito.anyString())).thenReturn("province");
        PowerMockito.when(FormatUtil.locationFullName(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn("LocationFullName");
        
        merchantServiceImpl.getById(1L);
        
        PowerMockito.verifyStatic();
        MerchantClient.getById(1L);
        StringUtils.isNotBlank("industryId");
        StringUtils.isNotBlank("industryId");
        IndustryClient.getNameByCode("industryId1");
        IndustryClient.getNameByCode("industryId2");
        LocationClient.getByIdAndParam(38L, "nameParam");
        LocationClient.getByIdAndParam(383L, "nameParam");
        LocationClient.getByIdAndParam(3883L, "nameParam");
        FormatUtil.locationFullName("province", "city", "area");
    }

    
    /**
     * 测试判断商户名称是否存在
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 11:27:48 AM
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsMerchantNameExist() throws Exception {
        PowerMockito.when(MerchantClient.getCountByParam(Mockito.anyMap())).thenReturn(1);
        
        boolean result = merchantServiceImpl.isMerchantNameExist("merchantName");
        Assert.assertTrue(result);
    }

}
