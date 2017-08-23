/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午5:29:31
* 创建作者：尤小平
* 文件名称：MerchantPicSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import static org.junit.Assert.*;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantPic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MerchantPicSqlTest {
	/**
	 * 被测试类
	 */
	private MerchantPicSql merchantPicSql;

	/**
	 * MerchantPic
	 */
	private MerchantPic merchantPic;

	/**
	 * 初始化.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午7:51:48
	 */
	@Before
	public void setUp() throws Exception {
		merchantPicSql = new MerchantPicSql();
		merchantPic = new MerchantPic();
		merchantPic.setSlot(12);
		merchantPic.setPath("http://path");
		merchantPic.setMerid(1L);
	}

	/**
	 * 回收.
	 * 
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午7:51:59
	 */
	@After
	public void tearDown() throws Exception {
		merchantPicSql = null;
	}

	/**
	 * 测试插入.
	 * 
	 * @author 尤小平
	 * @date 2017年4月10日 下午7:56:15
	 */
	@Test
	public void testInsert() {
		String actual = merchantPicSql.insert(merchantPic);

		assertNotNull(actual);
		String expected = "insert into merchant_pic(slot,path,merid) values (#{slot},#{path},#{merid})";
		assertEquals(expected, actual);
	}

	/**
	 * 测试更新.
	 * 
	 * @author 尤小平
	 * @date 2017年4月10日 下午7:56:28
	 */
	@Test
	public void testUpdate() {
		String actual = merchantPicSql.update(merchantPic);

		assertNotNull(actual);
		String expected = "update merchant_pic set slot=#{slot}, path=#{path}, merid=#{merid} where id=#{id}";
		assertEquals(expected, actual);
	}

}
