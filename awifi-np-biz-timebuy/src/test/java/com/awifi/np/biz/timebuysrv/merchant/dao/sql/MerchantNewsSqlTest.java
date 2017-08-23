/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月6日 上午10:00:56
 * 创建作者：尤小平
 * 文件名称：MerchantNewsSqlTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class MerchantNewsSqlTest {

	/**
	 * 被测试类
	 */
	private MerchantNewsSql merchantNewsSql;

	/**
	 * 初始化
	 */
	@Before
	public void before() {
		merchantNewsSql = new MerchantNewsSql();
	}

	/**
	 * 测试查询MerchantNews列表.
	 *
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月6日 上午10:02:42
	 */
	@Test
	public void testGetListByParam() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		MerchantNews merchantNews = new MerchantNews();

		merchantNews.setId(1);
		merchantNews.setContent("content");
		merchantNews.setMerid(10L);
		params.put("merchantNews", merchantNews);

		String actual = merchantNewsSql.getListByParam(params);

		assertNotNull(actual);
		String expected = "select m.id, m.content, m.merid from merchant_news m where 1=1 and m.id = #{merchantNews.id} and m.content like #{merchantNews.content} and m.merid = #{merchantNews.merid} order by id desc limit #{page.begin}, #{page.pageSize}";
		assertEquals(expected, actual);

		merchantNews = new MerchantNews();

		merchantNews.setId(1);
		params.put("merchantNews", merchantNews);

		actual = merchantNewsSql.getListByParam(params);

		assertNotNull(actual);
		expected = "select m.id, m.content, m.merid from merchant_news m where 1=1 and m.id = #{merchantNews.id} order by id desc limit #{page.begin}, #{page.pageSize}";
		assertEquals(expected, actual);
	}

	/**
	 * 测试插入一条MerchantNews.
	 *
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月6日 上午10:03:07
	 */
	@Test
	public void testInsert() throws Exception {
		MerchantNews merchantNews = new MerchantNews();

		merchantNews.setContent("content");
		merchantNews.setMerid(10L);

		String actual = merchantNewsSql.insert(merchantNews);

		assertNotNull(actual);
	}

	/**
	 * 测试按主键更新MerchantNews.
	 *
	 * @throws Exception
	 * @author 尤小平
	 * @date 2017年4月6日 上午10:03:28
	 */
	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		MerchantNews merchantNews = new MerchantNews();

		merchantNews.setId(1);
		merchantNews.setContent("content");
		merchantNews.setMerid(10L);

		String actual = merchantNewsSql.updateByPrimaryKey(merchantNews);

		assertNotNull(actual);
	}
}
