/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午2:29:01
* 创建作者：尤小平
* 文件名称：MerchantNoticeSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
/**
 * 
 */
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice;

public class MerchantNoticeSqlTest {

	/**
	 * 被测试类
	 */
	private MerchantNoticeSql merchantNoticeSql;

	/**
	 * 初始化.
	 * 
	 * @throws java.lang.Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:29:01
	 */
	@Before
	public void setUp() throws Exception {
		merchantNoticeSql = new MerchantNoticeSql();
	}

	/**
	 * 回收.
	 * 
	 * @throws java.lang.Exception
	 * @author 尤小平
	 * @date 2017年4月10日 下午2:29:01
	 */
	@After
	public void tearDown() throws Exception {
		merchantNoticeSql = null;
	}

	/**
	 * Test method for
	 * {@link com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNoticeSql#insert(com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice)}.
	 */
	@Test
	public void testInsert() {
		MerchantNotice merchantNotice = new MerchantNotice();

		merchantNotice.setSlot(2);
		merchantNotice.setContent("content");
		merchantNotice.setMerid(10L);

		String actual = merchantNoticeSql.insert(merchantNotice);

		assertNotNull(actual);

		String expected = "insert into merchant_notice(content,slot,merid) values (#{content},#{slot},#{merid})";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for
	 * {@link com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNoticeSql#update(com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice)}.
	 */
	@Test
	public void testUpdate() {
		MerchantNotice merchantNotice = new MerchantNotice();

		merchantNotice.setId(1);
		merchantNotice.setSlot(2);
		merchantNotice.setContent("content");
		merchantNotice.setMerid(10L);

		String actual = merchantNoticeSql.update(merchantNotice);

		assertNotNull(actual);

		String expected = "update merchant_notice set content=#{content}, slot=#{slot}, merid=#{merid} where id=#{id}";
		assertEquals(expected, actual);
	}

}
