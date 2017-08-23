/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月1日 下午2:11:23
 * 创建作者：张智威
 * 文件名称：TimePackageSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.web.module.time.dao.sql;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;

import org.apache.log4j.Logger;

import java.util.Map;

public class TimePackageSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 查询center_pub_merchant_package列表.
     * @param params 查询参数
     * @return String
     * @author 张智威
     * @date 2017年4月5日 下午5:35:30
     */
    public String getListByParam(Map<String, Object> params) {
        logger.debug("params:" + JSON.toJSONString(params));

        StringBuffer sql = new StringBuffer("select * from center_pub_merchant_package m where status=1 ");

        //MerchantNews merchantNews = (MerchantNews) params.get("merchantNews");
       
        Object merchantId = params.get("merchantId");
        Object packageType = params.get("packageType");
        Object packageKey = params.get("packageKey");
       // Object createDateBegin = params.get("createDateBegin");
       // Object createDateEnd = params.get("createDateEnd");
        if (merchantId != null) {
            sql.append("and m.merchant_id = #{merchantId} ");
        }
        if (packageType != null) {
            sql.append("and m.package_type like #{packageType} ");
        }
        if (packageKey != null) {
            sql.append("and m.package_key = #{packageKey} ");
        }
        
      
      //  sql.append("");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
    /**
     * 根据主键修改
     * @param params
     * @return
     * @author 张智威  
     * @date 2017年4月19日 上午11:08:34
     */
    public String updateByPrimaryKey(Map<String, Object> params) {
        logger.debug("params:" + JSON.toJSONString(params));
        

        StringBuffer sql = new StringBuffer("update center_pub_merchant_package m set ");

        //MerchantNews merchantNews = (MerchantNews) params.get("merchantNews");
       
        Object packageType = params.get("packageType");
        Object packageKey = params.get("packageKey");
        Object effectDatetime = params.get("effectDatetime");
        Object expiredDatetime = params.get("expiredDatetime");
        
        if (packageType != null) {
            sql.append(" m.package_type = #{packageType}, ");
        }
        if (packageKey != null) {
            sql.append(" m.package_key = #{packageKey}, ");
        }
        if (effectDatetime != null) {
            sql.append("and m.package_key = #{packageKey}, ");
        }
        if (expiredDatetime != null) {
            sql.append("and m.package_key = #{packageKey}, ");
        }
        
        sql.append("where id = #{id} ");
        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
    /**
     * 查询center_pub_merchant_package列表.
     * @param params 查询参数
     * @return String
     * @author 张智威
     * @date 2017年4月5日 下午5:35:30
     */
    public String queryCountByParam(Map<String, Object> params) {
        logger.debug("params:" + JSON.toJSONString(params));

        StringBuffer sql = new StringBuffer("select count(*) from center_pub_merchant_package m where status=1 ");

        //MerchantNews merchantNews = (MerchantNews) params.get("merchantNews");
       
        Object merchantId = params.get("merchantId");
        Object packageType = params.get("packageType");
        Object packageKey = params.get("packageKey");
//        Object createDateBegin = params.get("createDateBegin");
//        Object createDateEnd = params.get("createDateEnd");
        if (merchantId != null) {
            sql.append("and m.merchant_id = #{merchantId} ");
        }
        if (packageType != null) {
            sql.append("and m.package_type like #{packageType} ");
        }
        if (packageKey != null) {
            sql.append("and m.package_key = #{packageKey} ");
        }
      //  sql.append("");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
//    /**
//     * 插入一条套餐信息
//     *
//     * @param merchantNews MerchantNews
//     * @return String
//     * @author 张智威
//     * @date 2017年4月5日 下午5:35:38
//     */
//    public String insert(TimePackage timePackage) {
//        logger.debug("params:" + JSON.toJSONString(timePackage));
//
//        StringBuffer sql = new StringBuffer("insert into c(");
//        Object content = merchantNews.getContent();
//        Object merid = merchantNews.getMerid();
//
//        if (content != null) {
//            sql.append("content, ");
//        }
//        if (merid != null) {
//            sql.append("merid");
//        }
//
//        sql.append(") values (");
//
//        if (content != null) {
//            sql.append("#{content}, ");
//        }
//        if (merid != null) {
//            sql.append("#{merid}");
//        }
//        sql.append(")");
//
//        logger.debug("sql.toString()= " + sql.toString());
//        return sql.toString();
//    }

}
