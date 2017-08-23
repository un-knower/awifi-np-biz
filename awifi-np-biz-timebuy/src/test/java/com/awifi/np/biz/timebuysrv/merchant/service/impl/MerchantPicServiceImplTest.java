/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午5:29:56
* 创建作者：尤小平
* 文件名称：MerchantPicServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;

import java.util.ArrayList;
import java.util.List;

import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantPicDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantPic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MerchantPicServiceImplTest {
	/**
	 * MerchantPicServiceImpl
	 */
	private MerchantPicServiceImpl merchantPicService;

	/**
	 * MerchantPicDao
	 */
	private MerchantPicDao mockMerchantPicDao;

	/**
	 * 初始化.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:00:25
	 */
	@Before
	public void setUp() throws Exception {
		merchantPicService = new MerchantPicServiceImpl();
		mockMerchantPicDao = Mockito.mock(MerchantPicDao.class);
	}

	/**
	 * 回收.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:00:37
	 */
	@After
	public void tearDown() throws Exception {
		merchantPicService = null;
		mockMerchantPicDao = null;
	}

	/**
	 * 测试根据id获取单个商户滚动图片信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:18:35
	 */
	@Test
	public void testGetMerchantPicById() throws Exception {
		MerchantPic merchantPic = new MerchantPic();
		Mockito.when(mockMerchantPicDao.selectByPrimaryKey(anyInt())).thenReturn(merchantPic);
		merchantPicService.setMerchantPicDao(mockMerchantPicDao);

		MerchantPic actual = merchantPicService.getMerchantPicById(anyInt());

		assertEquals(merchantPic, actual);
		Mockito.verify(mockMerchantPicDao).selectByPrimaryKey(anyInt());
	}

	/**
	 * 测试根据商户id获取商户滚动图片列表.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:18:49
	 */
	@Test
	public void testGetListByMerid() throws Exception {
		List<MerchantPic> list = new ArrayList<MerchantPic>();
		Mockito.when(mockMerchantPicDao.selectListByMerid(anyLong())).thenReturn(list);
		merchantPicService.setMerchantPicDao(mockMerchantPicDao);

		List<MerchantPic> actual = merchantPicService.getListByMerid(anyLong());

		assertEquals(list, actual);
		Mockito.verify(mockMerchantPicDao).selectListByMerid(anyLong());
	}

	/**
	 * 测试添加单个商户滚动图片信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:19:04
	 */
	@Test
	public void testAddMerchantPic() throws Exception {
		Mockito.when(mockMerchantPicDao.insert(any(MerchantPic.class))).thenReturn(1);
		merchantPicService.setMerchantPicDao(mockMerchantPicDao);

		int actual = merchantPicService.addMerchantPic(any(MerchantPic.class));

		assertEquals(1, actual);
		Mockito.verify(mockMerchantPicDao).insert(any(MerchantPic.class));
	}

	/**
	 * 测试根据id更新单个商户滚动图片信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:19:13
	 */
	@Test
	public void testUpdateMerchantPicById() throws Exception {
		Mockito.when(mockMerchantPicDao.updateByPrimaryKey(any(MerchantPic.class))).thenReturn(1);
		merchantPicService.setMerchantPicDao(mockMerchantPicDao);

		int actual = merchantPicService.updateMerchantPicById(any(MerchantPic.class));

		assertEquals(1, actual);
		Mockito.verify(mockMerchantPicDao).updateByPrimaryKey(any(MerchantPic.class));
	}

	/**
	 * 测试根据id删除单个商户滚动图片信息.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午8:19:28
	 */
	@Test
	public void testDeleteMerchantPicById() throws Exception {
		Mockito.when(mockMerchantPicDao.delete(any(Integer.class))).thenReturn(1);
		merchantPicService.setMerchantPicDao(mockMerchantPicDao);

		int actual = merchantPicService.deleteMerchantPicById(any(Integer.class));

		assertEquals(1, actual);
		Mockito.verify(mockMerchantPicDao).delete(any(Integer.class));
	}
}
