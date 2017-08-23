/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月4日 上午8:58:35
* 创建作者：周颖
* 文件名称：SmsConfigSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.system.smsconfig.dao.sql;

import java.util.Map;

import com.awifi.np.biz.common.util.SqlUtil;

public class SmsConfigSql {

    /**
     * 短信配置总数
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年5月4日 上午9:24:53
     */
    public String getCountByParam(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id) from np_biz_sms_code_config where 1=1 ");
        Long merchantId = (Long)params.get("merchantId");
        if(merchantId != null){
            sql.append("and merchant_id=#{merchantId} ");
        }
        Long[] merchantIds = (Long[])params.get("merchantIds");
        if(merchantIds != null){
            SqlUtil.in("merchant_id", "merchantIds", merchantIds, sql);
        }
        return sql.toString();
    }
    
    /**
     * 短信配置列表sql
     * @param params 参数
     * @return 列表sql
     * @author 周颖  
     * @date 2017年5月4日 上午9:35:32
     */
    public String getListByParam(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select id,sms_content,code_length,merchant_id,date_format(create_date,'%Y-%m-%d %H:%i:%S') as create_date from np_biz_sms_code_config "
                + "where 1=1 ");
        Long merchantId = (Long)params.get("merchantId");
        if(merchantId != null){
            sql.append("and merchant_id=#{merchantId} ");
        }
        Long[] merchantIds = (Long[])params.get("merchantIds");
        if(merchantIds != null){
            SqlUtil.in("merchant_id", "merchantIds", merchantIds, sql);
        }
        sql.append("order by id limit #{begin},#{pageSize}");
        return sql.toString();
    }
}
