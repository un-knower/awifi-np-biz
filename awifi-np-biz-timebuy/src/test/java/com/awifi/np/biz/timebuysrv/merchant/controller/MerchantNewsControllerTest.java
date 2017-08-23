/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月6日 下午4:58:25
 * 创建作者：尤小平
 * 文件名称：MerchantNewsControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.controller;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantNewsService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class MerchantNewsControllerTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private MerchantNewsController merchantNewsController;

    /**
     * MerchantNewsService
     */
    @Mock
    private MerchantNewsService merchantNewsService;

    /**
     * request
     */
    private MockHttpServletRequest request;

    /**
     * @throws java.lang.Exception
     * @author 尤小平
     * @date 2017年4月6日 下午4:58:25
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(MessageUtil.class);
    }

    /**
     * @throws java.lang.Exception
     * @author 尤小平
     * @date 2017年4月6日 下午4:58:25
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNewsController#add(javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public void testAdd() throws Exception {
        PowerMockito.when(merchantNewsService.addMerchantNews(any(MerchantNews.class))).thenReturn(1);
        merchantNewsController.setMerchantNewsService(merchantNewsService);

        request.setParameter("content", "content");
        request.setParameter("merid", "1");
        Map<String, Object> map = merchantNewsController.add(request);

        Assert.assertNotNull(map);
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNewsController#getListByParam(javax.servlet.http.HttpServletRequest)}.
     */
    /*@Test
    public void testGetListByParam() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("content", "content");
        request.setParameter("merid", "1");
        request.setParameter("pageSize", "15");
        request.setParameter("pageNo", "2");

        Map<String, Object> map = merchantNewsController.getListByParam(request);

        Assert.assertNotNull(map);
    }*/

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNewsController#view(java.lang.String)}.
     */
    @Test
    public void testView() throws Exception {
        MerchantNews merchantNews = new MerchantNews();

        PowerMockito.when(merchantNewsService.getMerchantNewsById(any(Integer.class))).thenReturn(merchantNews);
        merchantNewsController.setMerchantNewsService(merchantNewsService);

        Map<String, Object> map = merchantNewsController.view("1");

        Assert.assertNotNull(map);
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNewsController#update(java.lang.String, javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public void testUpdate() throws Exception {
        request.setParameter("content", "content");
        request.setParameter("merid", "1");

        PowerMockito.when(merchantNewsService.updateMerchantNewsById(any(MerchantNews.class))).thenReturn(1);
        merchantNewsController.setMerchantNewsService(merchantNewsService);

        Map<String, Object> map = merchantNewsController.update("1", request);

        Assert.assertNotNull(map);
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNewsController#delete(java.lang.String)}.
     */
    @Test
    public void testDelete() throws Exception {

        PowerMockito.when(merchantNewsService.deleteMerchantNewsById(any(Integer.class))).thenReturn(1);
        merchantNewsController.setMerchantNewsService(merchantNewsService);

        Map<String, Object> map = merchantNewsController.delete("1");

        Assert.assertNotNull(map);
    }

    /**
     * 测试根据商户id获取商户介绍信息列表.
     *
     * @throws Exception
     * @author 尤小平
     * @date 2017年4月7日 上午10:25:13
     */
    @Test
    public void testGetListByMerid() throws Exception {
        List<MerchantNews> list = new ArrayList<MerchantNews>();
        MerchantNews merchantNews = new MerchantNews();
        list.add(merchantNews);

        PowerMockito.when(merchantNewsService.getListByMerid(any(Long.class))).thenReturn(list);
        merchantNewsController.setMerchantNewsService(merchantNewsService);

        Map<String, Object> map = merchantNewsController.getListByMerid("1");

        Assert.assertNotNull(map);
    }
}
