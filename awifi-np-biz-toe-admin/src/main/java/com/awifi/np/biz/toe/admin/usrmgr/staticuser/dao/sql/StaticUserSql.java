package com.awifi.np.biz.toe.admin.usrmgr.staticuser.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午10:04:34
 * 创建作者：周颖
 * 文件名称：StaticUserSql.java
 * 版本：  v1.0
 * 功能：静态用户sql
 * 修改记录：
 */
public class StaticUserSql {

    /**
     * 静态用户名列表总数
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年2月9日 上午10:14:05
     */
    public String getCountByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_static_user where delete_flag=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and concat(ifnull(user_name,''),ifnull(cellphone,''),ifnull(passport,''),ifnull(identity_card,'')) like concat('%',ifnull(#{keywords},''),'%') ");
        }
        Long merchantId = (Long)params.get("merchantId");
        if(merchantId != null){
            sql.append("and fk_customer_id=#{merchantId} ");
        }else{
            String cascadeLabel = (String) params.get("cascadeLabel");
            if(StringUtils.isNotBlank(cascadeLabel)){
                sql.append("and cascade_label like concat(#{cascadeLabel},'%') ");
            }
            Long[] merchantIds = (Long[])params.get("merchantIds");
            if(merchantIds != null){
                SqlUtil.in("fk_customer_id", "merchantIds", merchantIds, sql);
            }
        }
        Integer userType = (Integer) params.get("userType");
        if(userType != null){
            sql.append("and user_type=#{userType}");
        }
        return sql.toString();
    }
    
    /**
     * 静态用户列表
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年2月9日 上午10:23:44
     */
    public String getListByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,user_name,user_type,cellphone,passport,identity_card,real_name ,dept_name, remark,fk_customer_id from toe_static_user where delete_flag=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and concat(ifnull(user_name,''),ifnull(cellphone,''),ifnull(passport,''),ifnull(identity_card,'')) like concat('%',ifnull(#{keywords},''),'%') ");
        }
        Long merchantId = (Long)params.get("merchantId");
        if(merchantId != null){
            sql.append("and fk_customer_id=#{merchantId} ");
        }else{
            String cascadeLabel = (String) params.get("cascadeLabel");
            if(StringUtils.isNotBlank(cascadeLabel)){
                sql.append("and cascade_label like concat(#{cascadeLabel},'%') ");
            }
            Long[] merchantIds = (Long[])params.get("merchantIds");
            if(merchantIds != null){
                SqlUtil.in("fk_customer_id", "merchantIds", merchantIds, sql);
            }
        }
        Integer userType = (Integer) params.get("userType");
        if(userType != null){
            sql.append("and user_type=#{userType} ");
        }
        sql.append("group by pk_id desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
    
    /**
     * 批量删除用户 逻辑删除
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年2月10日 上午9:51:20
     */
    public String batchDelete(Map<String, Object> params){
        Long[] ids = (Long[])params.get("ids");
        StringBuffer sql = new StringBuffer();
        sql.append("update toe_static_user set delete_flag=-1,update_date=unix_timestamp(now()) where 1=1 ");
        SqlUtil.in("pk_id", "ids", ids, sql);
        return sql.toString();
    }
    
    /**
     * @param params 参数
     * @return sql
     * @author 王冬冬  
     * @date 2017年4月24日 下午2:56:19
     */
    public String getListByMerchantIdAndCellPhone(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,user_name,user_type,cellphone,passport,identity_card,real_name ,dept_name, remark,fk_customer_id from toe_static_user where delete_flag=1 ");
        Long merchantId = (Long)params.get("merchantId");
        if(merchantId != null){
            sql.append("and fk_customer_id=#{merchantId} ");
        }
        String cellphone = (String) params.get("cellphone");
        if(cellphone!=null){
            sql.append("and cellphone in "+cellphone);
        }
        sql.append("group by pk_id desc");
        return sql.toString();
    }
}