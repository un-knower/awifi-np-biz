/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午3:11:27
* 创建作者：周颖
* 文件名称：ToeUserSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.security.user.dao.sql;

import java.util.Map;

import com.awifi.np.biz.common.util.SqlUtil;

public class ToeUserSql {

    /**
     * 批量获取商户的账号 
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年4月10日 下午3:17:57
     */
    public String getNameByMerchantIds(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select u.user_name,uc.customer_id from toe_user_customer uc inner join toe_user u on uc.user_id=u.pk_id where u.delete_flag=1 ");
        Long[] merchantIds = (Long[])params.get("merchantIds");
        SqlUtil.in("uc.customer_id", "merchantIds", merchantIds, sql);
        return sql.toString();
    }
    
    /**
     * 根据username获取id和商户账号
     * @param params 参数
     * @return string
     * @author 王冬冬  
     * @date 2017年5月15日 下午2:49:18
     */
    public String getIdAndUserNameByUsernames(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select u.user_name,uc.customer_id from toe_user u left join toe_user_customer uc on u.pk_id=uc.user_id where u.delete_flag=1 ");
        String[] userNames = (String[])params.get("userNames");
        SqlUtil.inStringArr("u.user_name", "userNames", userNames, sql);
        return sql.toString();
    }
}
