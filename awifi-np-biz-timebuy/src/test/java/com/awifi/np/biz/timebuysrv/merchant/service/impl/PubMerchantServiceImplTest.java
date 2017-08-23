/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月9日 下午5:32:34
* 创建作者：尤小平
* 文件名称：PubMerchantServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import com.awifi.np.biz.timebuysrv.merchant.dao.PubMerchantDao;
import com.awifi.np.biz.timebuysrv.merchant.model.PubMerchant;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class PubMerchantServiceImplTest {
    /**
     * 被测试类
     */
    private PubMerchantServiceImpl service;

    /**
     * PubMerchantDao
     */
    private PubMerchantDao mockDao;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午8:09:28
     */
    @Before
    public void setUp() throws Exception {
        service = new PubMerchantServiceImpl();
        mockDao = Mockito.mock(PubMerchantDao.class);
        service.setPubMerchantDao(mockDao);
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午8:09:35
     */
    @After
    public void tearDown() throws Exception {
        service = null;
        mockDao = null;
    }

    /**
     * 测试保存商户数据到本地库.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午8:09:46
     */
    @Test
    public void testSaveMerchant() throws Exception {
        PubMerchant merchant = new PubMerchant();
        merchant.setId(10L);

        Mockito.when(mockDao.selectByPrimaryKey(any(Long.class))).thenReturn(null);
        Mockito.when(mockDao.insertSelective(any(PubMerchant.class))).thenReturn(1);

        boolean actual = service.saveMerchant(merchant);

        Assert.assertEquals(true, actual);
        Mockito.verify(mockDao).selectByPrimaryKey(any(Long.class));
        Mockito.verify(mockDao).insertSelective(any(PubMerchant.class));
    }

    /**
     * 测试保存商户数据到本地库.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午8:09:53
     */
    @Test
    public void testSaveMerchantWhileExist() throws Exception {
        PubMerchant merchant = new PubMerchant();
        merchant.setId(10L);

        Mockito.when(mockDao.selectByPrimaryKey(any(Long.class))).thenReturn(merchant);

        boolean actual = service.saveMerchant(merchant);

        Assert.assertEquals(false, actual);
        Mockito.verify(mockDao).selectByPrimaryKey(any(Long.class));
    }

    /**
     * 测试保存商户数据到本地库.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午8:09:58
     */
    @Test
    public void testSaveMerchantWhileIsNull() throws Exception {
        PubMerchant merchant = new PubMerchant();
        service.saveMerchant(merchant);
    }

    /**
     * 测试根据id获取本地库商户数据.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午8:10:05
     */
    @Test
    public void testGetMerchantById() throws Exception {
        Long id = 10L;
        PubMerchant merchant = new PubMerchant();
        merchant.setId(id);

        Mockito.when(mockDao.selectByPrimaryKey(any(Long.class))).thenReturn(merchant);

        PubMerchant actual = service.getMerchantById(id);

        Assert.assertEquals(10L, (long) actual.getId());
        Mockito.verify(mockDao).selectByPrimaryKey(any(Long.class));
    }
    
    /**
     * 测试根据id获取本地库商户数据.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午8:10:05
     */
    @Test
    public void testGetMerchantByIdFull() throws Exception {
        Long id = 10L;
        PubMerchant merchant = new PubMerchant();
        merchant.setId(id);
        merchant.setUserId(2L);
        merchant.setMerchantName("");
        merchant.setMerchantType(1);
        merchant.setCascadeLabel("");
        merchant.setCascadeLevel(1);
        merchant.setParentId(1L);
        merchant.setParentName("");
        merchant.setAccount("");
        merchant.setRoleIds("");
        merchant.setRoleNames("");
        merchant.setContact("");
        merchant.setContactWay("");
        merchant.setProjectId(1L);
        merchant.setProjectName("");
        merchant.setPriIndustryCode("");
        merchant.setPriIndustry("");
        merchant.setSecIndustry("");
        merchant.setSecIndustryCode("");
        merchant.setProvinceId(1L);
        merchant.setProvince("");
        merchant.setCityId(1L);
        merchant.setCity("");
        merchant.setArea("");
        merchant.setAreaId(1L);
        merchant.setLocationFullName("");
        merchant.setAddress("");
        merchant.setStoreLevel(1);
        merchant.setStoreScope(1);
        merchant.setStoreStar(1);
        merchant.setStoreType(1);
        merchant.setConnectType("");
        merchant.setRemark("");
        merchant.setCreateDate("");
        merchant.setUpdateDate("");
        merchant.setStatus(1);

        Mockito.when(mockDao.selectByPrimaryKey(any(Long.class))).thenReturn(merchant);

        PubMerchant actual = service.getMerchantById(id);

        Assert.assertEquals(10L, (long) actual.getId());
        Mockito.verify(mockDao).selectByPrimaryKey(any(Long.class));
    }
}
