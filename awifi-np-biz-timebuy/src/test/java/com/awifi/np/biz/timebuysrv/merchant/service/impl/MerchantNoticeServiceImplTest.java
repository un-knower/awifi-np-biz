/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午2:00:14
* 创建作者：尤小平
* 文件名称：MerchantNoticeServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantNoticeDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice;

public class MerchantNoticeServiceImplTest {

	/**
	 * merchantNoticeService
	 */
	private MerchantNoticeServiceImpl merchantNoticeService;

	/**
	 * mockMerchantNoticeDao
	 */
	private MerchantNoticeDao mockMerchantNoticeDao;

	/**
	 * 初始化.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:06:28
	 */
	@Before
	public void setUp() throws Exception {
		merchantNoticeService = new MerchantNoticeServiceImpl();
		mockMerchantNoticeDao = Mockito.mock(MerchantNoticeDao.class);
	}

	/**
	 * 回收.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:06:38
	 */
	@After
	public void tearDown() throws Exception {
		merchantNoticeService = null;
		mockMerchantNoticeDao = null;
	}

	/**
	 * 测试根据id获取单个商户滚动消息信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:26:34
	 */
	@Test
	public void testGetMerchantNoticeById() throws Exception {
		MerchantNotice merchantNoticeTemp = new MerchantNotice();

		Mockito.when(mockMerchantNoticeDao.selectByPrimaryKey(anyInt())).thenReturn(merchantNoticeTemp);
		merchantNoticeService.setMerchantNoticeDao(mockMerchantNoticeDao);

		MerchantNotice merchantNotice = merchantNoticeService.getMerchantNoticeById(anyInt());

		assertNotNull(merchantNotice);
		Mockito.verify(mockMerchantNoticeDao).selectByPrimaryKey(anyInt());
	}

	/**
	 * 测试根据商户id获取商户滚动消息信息列表.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:26:50
	 */
	@Test
	public void testGetListByMerid() throws Exception {
		List<MerchantNotice> list = new ArrayList<MerchantNotice>();

		Mockito.when(mockMerchantNoticeDao.selectListByMerid(1L)).thenReturn(list);
		merchantNoticeService.setMerchantNoticeDao(mockMerchantNoticeDao);

		List<MerchantNotice> merchantNoticeList = merchantNoticeService.getListByMerid(1L);

		assertNotNull(merchantNoticeList);
		Mockito.verify(mockMerchantNoticeDao).selectListByMerid(1L);
	}

	/**
	 * 测试添加单个商户滚动消息信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:27:07
	 */
	@Test
	public void testAddMerchantNotice() throws Exception {
		Mockito.when(mockMerchantNoticeDao.insert(any(MerchantNotice.class))).thenReturn(1);
		merchantNoticeService.setMerchantNoticeDao(mockMerchantNoticeDao);

		int actual = merchantNoticeService.addMerchantNotice(any(MerchantNotice.class));

		assertEquals(1, actual);
		Mockito.verify(mockMerchantNoticeDao).insert(any(MerchantNotice.class));
	}

	/**
	 * 测试根据id更新单个商户滚动消息信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:27:22
	 */
	@Test
	public void testUpdateMerchantNoticeById() throws Exception {
		Mockito.when(mockMerchantNoticeDao.updateByPrimaryKey(any(MerchantNotice.class))).thenReturn(1);
		merchantNoticeService.setMerchantNoticeDao(mockMerchantNoticeDao);

		int actual = merchantNoticeService.updateMerchantNoticeById(any(MerchantNotice.class));

		assertEquals(1, actual);
		Mockito.verify(mockMerchantNoticeDao).updateByPrimaryKey(any(MerchantNotice.class));
	}

	/**
	 * 测试根据id删除单个商户滚动消息信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:27:36
	 */
	@Test
	public void testDeleteMerchantNoticeById() throws Exception {
		Mockito.when(mockMerchantNoticeDao.delete(any(Integer.class))).thenReturn(1);
		merchantNoticeService.setMerchantNoticeDao(mockMerchantNoticeDao);

		int actual = merchantNoticeService.deleteMerchantNoticeById(any(Integer.class));

		assertEquals(1, actual);
		Mockito.verify(mockMerchantNoticeDao).delete(any(Integer.class));
	}
}
