/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月7日 下午4:00:43
* 创建作者：尤小平
* 文件名称：MerchantNoticeControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantNoticeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class MerchantNoticeControllerTest {
	/**
	 * 被测试类
	 */
	@InjectMocks
	private MerchantNoticeController merchantNoticeController;

	/**
	 * MerchantNoticeService
	 */
	@Mock
	private MerchantNoticeService merchantNoticeService;

	/**
	 * request
	 */
	private MockHttpServletRequest request;

	/**
	 * @throws java.lang.Exception
	 * @author 尤小平
	 * @date 2017年4月7日 下午4:00:43
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		request = new MockHttpServletRequest();
	}

	/**
	 * @throws java.lang.Exception
	 * @author 尤小平
	 * @date 2017年4月7日 下午4:00:43
	 */
	@After
	public void tearDown() throws Exception {
		merchantNoticeController = null;
		request = null;
	}

	/**
	 * Test method for
	 * {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNoticeController#getListByMerid(java.lang.String)}.
	 */
	@Test
	public void testGetListByMerid() throws Exception {
		List<MerchantNotice> list = new ArrayList<MerchantNotice>();
		Mockito.when(merchantNoticeService.getListByMerid(any(Long.class))).thenReturn(list);
		merchantNoticeController.setMerchantNoticeService(merchantNoticeService);
		
		Map actual = merchantNoticeController.getListByMerid(String.valueOf(any(Long.class)));

		assertNotNull(actual);
		Mockito.verify(merchantNoticeService).getListByMerid(any(Long.class));
	}

	/**
	 * Test method for
	 * {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNoticeController#view(java.lang.String)}.
	 */
	@Test
	public void testView() throws Exception {
		MerchantNotice merchantNotice = new MerchantNotice();

		Mockito.when(merchantNoticeService.getMerchantNoticeById(any(Integer.class))).thenReturn(merchantNotice);
		merchantNoticeController.setMerchantNoticeService(merchantNoticeService);

		Map actual = merchantNoticeController.view("1");

		assertNotNull(actual);
		Mockito.verify(merchantNoticeService).getMerchantNoticeById(any(Integer.class));
	}

	/**
	 * Test method for
	 * {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNoticeController#add(javax.servlet.http.HttpServletRequest)}.
	 */
	@Test
	public void testAdd() throws Exception {
		request.setParameter("slot", "10");
		request.setParameter("content", "content");
		request.setParameter("merid", "1");

		Mockito.when(merchantNoticeService.addMerchantNotice(any(MerchantNotice.class))).thenReturn(1);
		merchantNoticeController.setMerchantNoticeService(merchantNoticeService);

		Map actual = merchantNoticeController.add(request);

		assertNotNull(actual);
		Mockito.verify(merchantNoticeService).addMerchantNotice(any(MerchantNotice.class));
	}

	/**
	 * Test method for
	 * {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNoticeController#update(java.lang.String, javax.servlet.http.HttpServletRequest)}.
	 */
	@Test
	public void testUpdate() throws Exception {
		request.setParameter("slot", "10");
		request.setParameter("content", "content");
		request.setParameter("merid", "1");

		Mockito.when(merchantNoticeService.updateMerchantNoticeById(any(MerchantNotice.class))).thenReturn(1);
		merchantNoticeController.setMerchantNoticeService(merchantNoticeService);

		Map actual = merchantNoticeController.update("1", request);

		assertNotNull(actual);
		Mockito.verify(merchantNoticeService).updateMerchantNoticeById(any(MerchantNotice.class));

	}

	/**
	 * Test method for
	 * {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNoticeController#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() throws Exception {
		Mockito.when(merchantNoticeService.deleteMerchantNoticeById(any(Integer.class))).thenReturn(1);
		merchantNoticeController.setMerchantNoticeService(merchantNoticeService);

		Map actual = merchantNoticeController.delete("1");

		assertNotNull(actual);
		Mockito.verify(merchantNoticeService).deleteMerchantNoticeById(any(Integer.class));

	}

}
