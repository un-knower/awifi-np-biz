/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月6日 下午3:03:56
 * 创建作者：尤小平
 * 文件名称：MerchantNewsServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantNewsDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MerchantNewsServiceImplTest {

    /**
     * MerchantNewsServiceImpl
     */
    private MerchantNewsServiceImpl merchantNewsService;
    /**
     * MerchantNewsDao
     */
    private MerchantNewsDao mockMerchantNewsDao;

    /**
     * before.
     *
     * @throws Exception
     * @author 尤小平
     * @date 2017年4月6日 下午4:46:18
     */
    @Before
    public void setUp() throws Exception {
        merchantNewsService = new MerchantNewsServiceImpl();
        mockMerchantNewsDao = Mockito.mock(MerchantNewsDao.class);
    }

    /**
     * after.
     *
     * @throws Exception
     * @author 尤小平
     * @date 2017年4月6日 下午4:46:27
     */
    @After
    public void tearDown() throws Exception {
        merchantNewsService = null;
        mockMerchantNewsDao = null;
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.service.impl.MerchantNewsServiceImpl#getMerchantNewsById(int)}.
     */
    @Test
    public void testGetMerchantNewsById() throws Exception {
        MerchantNews merchantNewsTemp = new MerchantNews();

        Mockito.when(mockMerchantNewsDao.selectByPrimaryKey(anyInt())).thenReturn(merchantNewsTemp);
        merchantNewsService.setMerchantNewsDao(mockMerchantNewsDao);

        MerchantNews merchantNews = merchantNewsService.getMerchantNewsById(anyInt());

        assertNotNull(merchantNews);

        Mockito.verify(mockMerchantNewsDao).selectByPrimaryKey(anyInt());
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.service.impl.MerchantNewsServiceImpl#getListByParam(com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews, com.awifi.np.biz.common.base.model.Page)}.
     */
    @Test
    public void testGetListByParam() throws Exception {

        List<MerchantNews> list = new ArrayList<MerchantNews>();

        MerchantNews merchantNews = new MerchantNews();
        merchantNews.setId(1);
        list.add(merchantNews);

        Page<MerchantNews> page = new Page<MerchantNews>();

        Mockito.when(mockMerchantNewsDao.getListByParam(merchantNews, page)).thenReturn(list);
        merchantNewsService.setMerchantNewsDao(mockMerchantNewsDao);

        merchantNewsService.getListByParam(merchantNews, page);

        Mockito.verify(mockMerchantNewsDao).getListByParam(merchantNews, page);
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.service.impl.MerchantNewsServiceImpl#addMerchantNews(com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews)}.
     */
    @Test
    public void testAddMerchantNews() throws Exception {
        MerchantNews merchantNews = new MerchantNews();
        merchantNews.setId(1);

        Mockito.when(mockMerchantNewsDao.insert(any(MerchantNews.class))).thenReturn(1);
        merchantNewsService.setMerchantNewsDao(mockMerchantNewsDao);

        int actual = merchantNewsService.addMerchantNews(any(MerchantNews.class));

        Assert.assertEquals(1, actual);
        Mockito.verify(mockMerchantNewsDao).insert(any(MerchantNews.class));
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.service.impl.MerchantNewsServiceImpl#updateMerchantNewsById(com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews)}.
     */
    @Test
    public void testUpdateMerchantNewsById() throws Exception {
        MerchantNews merchantNews = new MerchantNews();
        merchantNews.setId(1);

        Mockito.when(mockMerchantNewsDao.updateByPrimaryKey(any(MerchantNews.class))).thenReturn(1);
        merchantNewsService.setMerchantNewsDao(mockMerchantNewsDao);

        int actual = merchantNewsService.updateMerchantNewsById(any(MerchantNews.class));

        Assert.assertEquals(1, actual);
        Mockito.verify(mockMerchantNewsDao).updateByPrimaryKey(any(MerchantNews.class));
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.service.impl.MerchantNewsServiceImpl#deleteMerchantNewsById(int)}.
     */
    @Test
    public void testDeleteMerchantNewsById() throws Exception {
        Mockito.when(mockMerchantNewsDao.deleteByPrimaryKey(anyInt())).thenReturn(1);
        merchantNewsService.setMerchantNewsDao(mockMerchantNewsDao);

        int actual = merchantNewsService.deleteMerchantNewsById(anyInt());

        Assert.assertEquals(1, actual);
        Mockito.verify(mockMerchantNewsDao).deleteByPrimaryKey(anyInt());
    }

    /**
     * 测试根据商户Id获取商户滚动消息信息列表.
     * 
     * @throws Exception
     * @author 尤小平  
     * @date 2017年4月7日 上午10:29:56
     */
    @Test
    public void testGetListByMerid() throws Exception {
        List<MerchantNews> list = new ArrayList<MerchantNews>();
        MerchantNews merchantNews = new MerchantNews();
        list.add(merchantNews);

        Mockito.when(mockMerchantNewsDao.selectListByMerid(any(Long.class))).thenReturn(list);
        merchantNewsService.setMerchantNewsDao(mockMerchantNewsDao);

        List<MerchantNews> actual = merchantNewsService.getListByMerid(any(Long.class));

        Assert.assertEquals(list, actual);
        Mockito.verify(mockMerchantNewsDao).selectListByMerid(any(Long.class));
    }
}
