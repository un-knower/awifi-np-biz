/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月7日 上午11:23:23
 * 创建作者：尤小平
 * 文件名称：MerchantNoticeSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice;
import org.apache.log4j.Logger;

public class MerchantNoticeSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 插入.
     *
     * @param merchantNotice MerchantNotice
     * @return String
     * @author 尤小平
     * @date 2017年4月7日 下午3:54:42
     */
    public String insert(MerchantNotice merchantNotice) {
        logger.debug("params:" + JSON.toJSONString(merchantNotice));

        StringBuffer sql = new StringBuffer("insert into merchant_notice(");
        Object slot = merchantNotice.getSlot();
        Object content = merchantNotice.getContent();
        Object merid = merchantNotice.getMerid();

        if (content != null) {
            sql.append("content,");
        }
        if (slot != null) {
            sql.append("slot,");
        }
        if (merid != null) {
            sql.append("merid,");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(") values (");

        if (content != null) {
            sql.append("#{content},");
        }
        if (slot != null) {
            sql.append("#{slot},");
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
     * 更新.
     *
     * @param merchantNotice MerchantNotice
     * @return String
     * @author 尤小平
     * @date 2017年4月7日 下午3:54:52
     */
    public String update(MerchantNotice merchantNotice) {
        logger.debug("params:" + JSON.toJSONString(merchantNotice));

        StringBuffer sql = new StringBuffer("update merchant_notice set");
        StringBuffer condition = new StringBuffer("");

        Object id = merchantNotice.getId();
        Object slot = merchantNotice.getSlot();
        Object content = merchantNotice.getContent();
        Object merid = merchantNotice.getMerid();

        if (content != null) {
            condition.append(" content=#{content},");
        }
        if (slot != null) {
            condition.append(" slot=#{slot},");
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
