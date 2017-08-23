/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年6月14日 上午9:41:16
 * 创建作者：尤小平
 * 文件名称：StationAppMerchantRelationSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.mws.merchant.app.dao.sql;

import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.mws.merchant.app.model.StationAppMerchantRelation;
import org.apache.log4j.Logger;

public class StationAppMerchantRelationSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * select.
     * 
     * @param relation StationAppMerchantRelation
     * @return String
     * @author 尤小平  
     * @date 2017年6月15日 下午4:25:04
     */
    public String select(StationAppMerchantRelation relation) {
        logger.debug("params:" + JsonUtil.toJson(relation));
        if (relation == null) {
            return null;
        }

        StringBuffer sql = new StringBuffer(
                "select Id, MerchantId, AppId, ExpireTime, CreateTime, Status from station_app_merchant_relation where 1=1 ");

        Object appId = relation.getAppId();
        Object merchantId = relation.getMerchantId();

        if (appId != null) {
            sql.append("and AppId = #{appId} ");
        }
        if (merchantId != null) {
            sql.append("and MerchantId = #{merchantId} ");
        }

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * delete.
     * 
     * @param relation StationAppMerchantRelation
     * @return String
     * @author 尤小平  
     * @date 2017年6月15日 下午4:25:29
     */
    public String delete(StationAppMerchantRelation relation) {
        logger.debug("params:" + JsonUtil.toJson(relation));
        if (relation == null) {
            return null;
        }

        StringBuffer sql = new StringBuffer("delete from station_app_merchant_relation where 1=1 ");

        Object appId = relation.getAppId();
        Object merchantId = relation.getMerchantId();

        if (appId != null) {
            sql.append("and AppId = #{appId} ");
        }
        if (merchantId != null) {
            sql.append("and MerchantId = #{merchantId} ");
        }

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * update.
     * 
     * @param relation StationAppMerchantRelation
     * @return String
     * @author 尤小平  
     * @date 2017年6月15日 下午4:25:41
     */
    public String update(StationAppMerchantRelation relation) {
        logger.debug("params:" + JsonUtil.toJson(relation));

        if (relation.getId() == null || relation.getId() == 0) {
            return null;
        }

        StringBuffer sql = new StringBuffer("update station_app_merchant_relation set ");

        Object merchantId = relation.getMerchantId();
        Object appId = relation.getAppId();
        Object expireTime = relation.getExpireTime();
        Object createTime = relation.getCreateTime();
        Object status = relation.getStatus();

        if (merchantId != null) {
            sql.append("MerchantId = #{merchantId},");
        }
        if (appId != null) {
            sql.append("AppId = #{appId},");
        }
        if (expireTime != null) {
            sql.append("ExpireTime = #{expireTime},");
        }
        if (createTime != null) {
            sql.append("CreateTime = #{createTime},");
        }
        if (status != null) {
            sql.append("Status = #{status},");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" where Id = #{id}");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * insert.
     * 
     * @param record StationAppMerchantRelation
     * @return String
     * @author 尤小平  
     * @date 2017年6月15日 下午4:25:53
     */
    public String insert(StationAppMerchantRelation record) {
        logger.debug("params:" + JsonUtil.toJson(record));
        StringBuffer sql = new StringBuffer("insert into station_app_merchant_relation(");

        Object id = record.getId();
        Object merchantId = record.getMerchantId();
        Object appId = record.getAppId();
        Object expireTime = record.getExpireTime();
        Object createTime = record.getCreateTime();
        Object status = record.getStatus();

        if (id != null) {
            sql.append("Id,");
        }
        if (merchantId != null) {
            sql.append("MerchantId,");
        }
        if (appId != null) {
            sql.append("AppId,");
        }
        if (expireTime != null) {
            sql.append("ExpireTime,");
        }
        if (createTime != null) {
            sql.append("CreateTime,");
        }
        if (status != null) {
            sql.append("Status,");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") values (");

        if (id != null) {
            sql.append("#{id},");
        }
        if (merchantId != null) {
            sql.append("#{merchantId},");
        }
        if (appId != null) {
            sql.append("#{appId},");
        }
        if (expireTime != null) {
            sql.append("#{expireTime},");
        }
        if (createTime != null) {
            sql.append("#{createTime},");
        }
        if (status != null) {
            sql.append("#{status},");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
}