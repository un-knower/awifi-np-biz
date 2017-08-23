package com.awifi.np.biz.toe.admin.usrmgr.blackuser.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午9:31:28
 * 创建作者：周颖
 * 文件名称：BlackUserSql.java
 * 版本：  v1.0
 * 功能：黑名单sql
 * 修改记录：
 */
public class BlackUserSql {

    /**
     * 黑名单总数
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年2月13日 上午9:51:50
     */
    public String getCountByParam(Map<String, Object> params) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(bw.pk_id) from toe_blackwhiteuser bw left join toe_static_user su on su.cellphone=bw.cellphone and su.fk_customer_id=bw.fk_customer_id ");
        sql.append("where bw.delete_flag=1 and bw.type=#{type} ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and concat(ifnull(su.user_name,''),ifnull(bw.cellphone,'')) like concat('%',#{keywords},'%') ");
        }
        Long merchantId = (Long) params.get("merchantId");
        if(merchantId != null){
            sql.append("and bw.fk_customer_id=#{merchantId} ");
        }else{
            String cascadeLabel = (String) params.get("cascadeLabel");
            if(StringUtils.isNotBlank(cascadeLabel)){
                sql.append("and bw.cascade_label like concat(#{cascadeLabel},'%') ");
            }
            Long[] merchantIds = (Long[])params.get("merchantIds");
            if(merchantIds != null){
                SqlUtil.in("bw.fk_customer_id", "merchantIds", merchantIds, sql);
            }
        }
        Integer matchRule = (Integer) params.get("matchRule");
        if(matchRule != null){
            sql.append("and bw.match_rule=#{matchRule}");
        }
        return sql.toString();
    }
    
    /**
     * 黑名单列表
     * @param params 参数
     * @return 列表sql
     * @author 周颖  
     * @date 2017年2月13日 上午10:07:01
     */
    public String getListByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select bw.pk_id,su.user_name,bw.match_rule,bw.cellphone,su.real_name,bw.fk_customer_id,su.user_type,from_unixtime(bw.create_date,'%Y-%m-%d %H:%i:%S') as create_date ");
        sql.append("from toe_blackwhiteuser bw left join toe_static_user su on su.cellphone=bw.cellphone and su.fk_customer_id=bw.fk_customer_id where bw.delete_flag=1 and bw.type=#{type} ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and concat(ifnull(su.user_name,''),ifnull(bw.cellphone,'')) like concat('%',#{keywords},'%') ");
        }
        Long merchantId = (Long) params.get("merchantId");
        if(merchantId != null){
            sql.append("and bw.fk_customer_id=#{merchantId} ");
        }else{
            String cascadeLabel = (String) params.get("cascadeLabel");
            if(StringUtils.isNotBlank(cascadeLabel)){
                sql.append("and bw.cascade_label like concat(#{cascadeLabel},'%') ");
            }
            Long[] merchantIds = (Long[])params.get("merchantIds");
            if(merchantIds != null){
                SqlUtil.in("bw.fk_customer_id", "merchantIds", merchantIds, sql);
            }
        }
        Integer matchRule = (Integer) params.get("matchRule");
        if(matchRule != null){
            sql.append("and bw.match_rule=#{matchRule} ");
        }
        sql.append("order by create_date desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
}