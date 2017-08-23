/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年5月11日 上午11:32:17
 * 创建作者：尤小平
 * 文件名称：WifiRecordSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.web.module.time.dao.sql;

import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;
import org.apache.log4j.Logger;

public class WifiRecordSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * insert.
     * 
     * @param record WifiRecord
     * @return String
     * @author 尤小平
     * @date 2017年5月11日 下午9:09:12
     */
    public String insert(WifiRecord record) {
        logger.debug("insert record: " + JsonUtil.toJson(record));

        StringBuffer sql = new StringBuffer("insert into wifi_record(");
        Object userId = record.getUserId();
        Object merchantId = record.getMerchantId();
        Object portalUrl = record.getPortalUrl();
        Object telphone = record.getTelphone();
        Object deviceId = record.getDeviceId();
        Object devType = record.getDevType();
        Object token = record.getToken();
        Object errorInfo = record.getErrorInfo();

        if (userId != null) {
            sql.append(" user_id,");
        }
        if (merchantId != null) {
            sql.append(" merchant_id,");
        }
        if (portalUrl != null) {
            sql.append(" portal_url,");
        }
        if (telphone != null) {
            sql.append(" telphone,");
        }
        if (deviceId != null) {
            sql.append(" device_id,");
        }
        if (devType != null) {
            sql.append(" dev_type,");
        }
        if (token != null) {
            sql.append(" token,");
        }
        sql.append(" token_date,");
        if (errorInfo != null) {
            sql.append(" error_info,");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") values (");

        if (userId != null) {
            sql.append(" #{userId},");
        }
        if (merchantId != null) {
            sql.append(" #{merchantId},");
        }
        if (portalUrl != null) {
            sql.append(" #{portalUrl},");
        }
        if (telphone != null) {
            sql.append(" #{telphone},");
        }
        if (deviceId != null) {
            sql.append(" #{deviceId},");
        }
        if (devType != null) {
            sql.append(" #{devType},");
        }
        if (token != null) {
            sql.append(" #{token},");
        }
        sql.append(" now(),");
        if (errorInfo != null) {
            sql.append(" #{errorInfo},");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * update.
     * 
     * @param record WifiRecord
     * @return String
     * @author 尤小平  
     * @date 2017年5月11日 下午9:09:27
     */
    public String update(WifiRecord record) {
        logger.debug("update record: " + JsonUtil.toJson(record));

        StringBuffer sql = new StringBuffer("update wifi_record set ");
        StringBuffer condition = new StringBuffer("");

        Object id = record.getId();
        Object wifiUrl = record.getWifiUrl();
        Object wifiResult = record.getWifiResult();
        Object errorInfo = record.getErrorInfo();

        if (wifiUrl != null) {
            condition.append(" wifi_url = #{wifiUrl},");
        }
        if (wifiResult != null) {
            condition.append(" wifi_result = #{wifiResult},");
        }
        if (errorInfo != null) {
            sql.append(" error_info = #{errorInfo},");
        }

        if (id != null && condition.toString().length() > 0) {
            condition.append(" wifi_date = now()");
            sql.append(condition).append(" where id = #{id}");
        }

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
}
