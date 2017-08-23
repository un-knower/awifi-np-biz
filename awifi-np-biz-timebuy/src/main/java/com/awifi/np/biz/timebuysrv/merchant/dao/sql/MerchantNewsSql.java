/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月1日 下午2:11:23
 * 创建作者：尤小平
 * 文件名称：MerchantNewsSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import org.apache.log4j.Logger;

import java.util.Map;

public class MerchantNewsSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 查询MerchantNews列表.
     *
     * @param params Map<String, Object>
     * @return String
     * @author 尤小平
     * @date 2017年4月5日 下午5:35:30
     */
    public String getListByParam(Map<String, Object> params) {
        logger.debug("params:" + JSON.toJSONString(params));

        StringBuffer sql = new StringBuffer("select m.id, m.content, m.merid from merchant_news m where 1=1 ");

        MerchantNews merchantNews = (MerchantNews) params.get("merchantNews");
        Object id = merchantNews.getId();
        Object content = merchantNews.getContent();
        Object merid = merchantNews.getMerid();

        if (id != null) {
            sql.append("and m.id = #{merchantNews.id} ");
        }
        if (content != null) {
            sql.append("and m.content like #{merchantNews.content} ");
        }
        if (merid != null) {
            sql.append("and m.merid = #{merchantNews.merid} ");
        }
        sql.append("order by id desc limit #{page.begin}, #{page.pageSize}");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * 插入一条MerchantNews.
     *
     * @param merchantNews MerchantNews
     * @return String
     * @author 尤小平
     * @date 2017年4月5日 下午5:35:38
     */
    public String insert(MerchantNews merchantNews) {
        logger.debug("params:" + JSON.toJSONString(merchantNews));

        StringBuffer sql = new StringBuffer("insert into merchant_news(");
        Object content = merchantNews.getContent();
        Object merid = merchantNews.getMerid();

        if (content != null) {
            sql.append("content,");
        }
        if (merid != null) {
            sql.append("merid,");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(") values (");

        if (content != null) {
            sql.append("#{content},");
        }
        if (merid != null) {
            sql.append("#{merid},");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(")");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * 按主键更新MerchantNews.
     *
     * @param merchantNews MerchantNews
     * @return String
     * @author 尤小平
     * @date 2017年4月5日 下午5:35:43
     */
    public String updateByPrimaryKey(MerchantNews merchantNews) {
        logger.debug("params:" + JSON.toJSONString(merchantNews));

        StringBuffer sql = new StringBuffer("update merchant_news set");
        StringBuffer condition = new StringBuffer("");

        Object id = merchantNews.getId();
        Object content = merchantNews.getContent();
        Object merid = merchantNews.getMerid();

        if (content != null) {
            condition.append(" content=#{content},");
        }
        if (merid != null) {
            condition.append(" merid=#{merid},");
        }

        if (id != null && condition.toString().length() > 0) {
            sql.append(condition.deleteCharAt(condition.length() - 1));
            sql.append(" where id=#{id}");
        }

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
}
