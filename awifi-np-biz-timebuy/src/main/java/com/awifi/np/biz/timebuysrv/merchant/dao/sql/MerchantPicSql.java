/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午3:04:34
* 创建作者：尤小平
* 文件名称：MerchantPicSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantPic;

public class MerchantPicSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 插入.
     * 
     * @param merchantPic MerchantPic
     * @return String
     * @author 尤小平
     * @date 2017年4月10日 下午4:38:48
     */
    public String insert(MerchantPic merchantPic) {
        logger.debug("params:" + JSON.toJSONString(merchantPic));

        StringBuffer sql = new StringBuffer("insert into merchant_pic(");
        Object slot = merchantPic.getSlot();
        Object path = merchantPic.getPath();
        Object merid = merchantPic.getMerid();

        if (slot != null) {
            sql.append("slot,");
        }
        if (path != null) {
            sql.append("path,");
        }
        if (merid != null) {
            sql.append("merid,");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(") values (");

        if (slot != null) {
            sql.append("#{slot},");
        }
        if (path != null) {
            sql.append("#{path},");
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
     * @param merchantPic MerchantPic
     * @return String
     * @author 尤小平
     * @date 2017年4月10日 下午4:38:55
     */
    public String update(MerchantPic merchantPic) {
        logger.debug("params:" + JSON.toJSONString(merchantPic));

        StringBuffer sql = new StringBuffer("update merchant_pic set");
        StringBuffer condition = new StringBuffer("");

        Object slot = merchantPic.getSlot();
        Object path = merchantPic.getPath();
        Object merid = merchantPic.getMerid();

        if (slot != null) {
            condition.append(" slot=#{slot},");
        }
        if (path != null) {
            condition.append(" path=#{path},");
        }
        if (merid != null) {
            condition.append(" merid=#{merid},");
        }

        if (condition.toString().length() > 0) {
            sql.append(condition.deleteCharAt(condition.length() - 1));
            sql.append(" where id=#{id}");
        }

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
}
