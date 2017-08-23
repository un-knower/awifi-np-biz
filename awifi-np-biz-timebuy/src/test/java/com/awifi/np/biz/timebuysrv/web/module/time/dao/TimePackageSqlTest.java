package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNewsSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.TimePackageSql;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dozen.zhang on 2017/4/18.
 */
public class TimePackageSqlTest {

    /**
     * 被测试类
     */
    private TimePackageSql timePackageSql;

    /**
     * 初始化
     */
    @Before
    public void before() {
        timePackageSql = new TimePackageSql();
    }

    /**
     * 测试查询MerchantNews列表.
     *
     * @throws Exception
     * @author 尤小平
     * @date 2017年4月6日 上午10:02:42
     */
    //@Test
    public void testGetListByParam() throws Exception {
       /* Map<String, Object> params = new HashMap<String, Object>();
        MerchantNews merchantNews = new MerchantNews();

        merchantNews.setId(1);
        merchantNews.setContent("content");
        merchantNews.setMerid(10L);
        params.put("merchantNews", merchantNews);

        String actual = timePackageSql.getListByParam(params);

        assertNotNull(actual);
        String expected = "select m.id, m.content, m.merid from merchant_news m where 1=1 and m.id = #{merchantNews.id} and m.content like #{merchantNews.content} and m.merid = #{merchantNews.merid} order by id desc limit #{page.begin}, #{page.pageSize}";
        assertEquals(expected, actual);

        merchantNews = new MerchantNews();

        merchantNews.setId(1);
        params.put("merchantNews", merchantNews);

        actual = timePackageSql.getListByParam(params);

        assertNotNull(actual);
        expected = "select m.id, m.content, m.merid from merchant_news m where 1=1 and m.id = #{merchantNews.id} order by id desc limit #{page.begin}, #{page.pageSize}";
        assertEquals(expected, actual);*/
    }
}
