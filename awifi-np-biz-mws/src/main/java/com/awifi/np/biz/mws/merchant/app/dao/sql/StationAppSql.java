/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年6月12日 下午5:16:24
 * 创建作者：尤小平
 * 文件名称：StationAppSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.mws.merchant.app.dao.sql;

import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.mws.merchant.app.model.StationApp;
import org.apache.log4j.Logger;

public class StationAppSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 根据授权类型查询商户的应用.
     *
     * @param record StationApp
     * @return sql
     * @author 尤小平
     * @date 2017年6月13日 下午4:06:25
     */
    public String selectRelationsByParam(StationApp record) {
        logger.debug("params:" + JsonUtil.toJson(record));

        StringBuffer sql = new StringBuffer(
                "select t1.Id, t1.AppId, t1.AppSecret, t1.AppName, t1.GrantType, IndustryCode,LinkUrl from station_app t1, station_app_merchant_relation t2 where t1.Id=t2.AppId ");

        Object merchantId = record.getMerchantId();
        Object grantType = record.getGrantType();
        Object limitNum = record.getLimitNum();

        if (merchantId != null) {
            sql.append("and t2.MerchantId=#{merchantId} ");
        }
        if (grantType != null) {
            sql.append("and grantType = #{grantType} ");
        }
        if (limitNum != null) {
            sql.append("limit #{limitNum} ");
        }

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
}
