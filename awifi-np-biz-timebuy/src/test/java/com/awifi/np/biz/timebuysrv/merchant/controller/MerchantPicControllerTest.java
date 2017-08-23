/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月10日 下午5:28:56
 * 创建作者：尤小平
 * 文件名称：MerchantPicControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantPic;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantPicService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

@SuppressWarnings("rawtypes")
public class MerchantPicControllerTest {
	/**
	 * 被测试类
	 */
	@InjectMocks
	private MerchantPicController merchantPicController;

	/**
	 * MerchantPicService
	 */
	@Mock
	private MerchantPicService merchantPicService;

	/**
	 * request
	 */
	private MockHttpServletRequest request;

	/**
	 * 初始化.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:44:14
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		request = new MockHttpServletRequest();
	}

	/**
	 * 回收.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:44:22
	 */
	@After
	public void tearDown() throws Exception {
		merchantPicController = null;
		request = null;
	}

	/**
	 * 测试根据商户id获取商户滚动图片列表.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:44:51
	 */
	@Test
	public void testGetMerchantPicList() throws Exception {
		List<MerchantPic> list = new ArrayList<MerchantPic>();
		Mockito.when(merchantPicService.getListByMerid(anyLong())).thenReturn(list);
		merchantPicController.setMerchantPicService(merchantPicService);

		Map actual = merchantPicController.getMerchantPicList("1");

		assertNotNull(actual);
		Mockito.verify(merchantPicService).getListByMerid(anyLong());
	}

	/**
	 * 测试添加单个商户滚动图片信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:45:01
	 */
	@Test
	public void testAdd() throws Exception {
		request.setParameter("slot", "11");
		request.setParameter("path", "http://path");
		request.setParameter("merid", "1");

		Mockito.when(merchantPicService.addMerchantPic(any(MerchantPic.class))).thenReturn(1);
		merchantPicController.setMerchantPicService(merchantPicService);

		Map actual = merchantPicController.add(request);

		assertNotNull(actual);
		Mockito.verify(merchantPicService).addMerchantPic(any(MerchantPic.class));
	}

	/**
	 * 测试根据id更新单个商户滚动图片信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:45:15
	 */
	@Test
	public void testUpdate() throws Exception {
		request.setParameter("slot", "11");
		request.setParameter("path", "http://path");
		request.setParameter("merid", "1");

		Mockito.when(merchantPicService.updateMerchantPicById(any(MerchantPic.class))).thenReturn(1);
		merchantPicController.setMerchantPicService(merchantPicService);

		Map actual = merchantPicController.update("5", request);

		assertNotNull(actual);
		Mockito.verify(merchantPicService).updateMerchantPicById(any(MerchantPic.class));
	}

	/**
	 * 测试根据id获取单个商户滚动图片信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:45:26
	 */
	@Test
	public void testView() throws Exception {
		MerchantPic merchantPic = new MerchantPic();
		Mockito.when(merchantPicService.getMerchantPicById(anyInt())).thenReturn(merchantPic);
		merchantPicController.setMerchantPicService(merchantPicService);

		Map actual = merchantPicController.view("5");

		assertNotNull(actual);
		Mockito.verify(merchantPicService).getMerchantPicById(anyInt());
	}

	/**
	 * 测试根据id删除单个商户滚动图片信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:45:37
	 */
	@Test
	public void testDelete() throws Exception {
		Mockito.when(merchantPicService.deleteMerchantPicById(anyInt())).thenReturn(1);
		merchantPicController.setMerchantPicService(merchantPicService);

		Map actual = merchantPicController.delete("5");

		assertNotNull(actual);
		Mockito.verify(merchantPicService).deleteMerchantPicById(anyInt());
	}
}
