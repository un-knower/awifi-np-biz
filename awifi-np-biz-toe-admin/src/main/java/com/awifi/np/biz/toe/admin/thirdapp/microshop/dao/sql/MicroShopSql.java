package com.awifi.np.biz.toe.admin.thirdapp.microshop.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;

/**   
 * @Description:  
 * @Title: MicroShopSql.java 
 * @Package com.awifi.toe.app.mapper.sql 
 * @author 王冬冬 
 * @date 2016年5月6日 上午9:01:47
 * @version V1.0   
 */
public class MicroShopSql {
    
    /**
     * 关联配置--客户列表（总记录数）
     * @param params 参数
     * @return 总记录数
     * @author 王冬冬  
     * @date 2016年5月6日 上午9:07:31
     */
    public String pageCount(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_app_micro_shop where delete_flag = 1 and app_id=#{appId} and shop_id is not null ");
        Long projectId = (Long)params.get("projectId");//项目id
        if(projectId != null){
            sql.append("and fk_project_id = #{projectId} ");
        }
        Long customerId = (Long)params.get("customerId");
        if(customerId!=null){
            sql.append("and fk_customer_id!=#{customerId} ");
        }
        Long relateCustomerId = (Long)params.get("relateCustomerId");
        if(relateCustomerId!=null){
            sql.append("and fk_customer_id != #{relateCustomerId} ");
        }
        Long cusId = (Long)params.get("cusId");
        if(cusId!=null){
            sql.append("and fk_customer_id = #{cusId}");
        }
        return sql.toString();
    }
    
    /**
     * 关联配置--客户总数（分页）
     * @param params 参数
     * @return 分页列表
     * @author 王冬冬  
     * @date 2016年5月6日 上午9:11:52
     */
    public String pageQuery(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,fk_customer_id from toe_app_micro_shop where delete_flag = 1 and app_id=#{appId} and shop_id is not null ");
        Long projectId = (Long)params.get("projectId");//项目id
        if(projectId != null){
            sql.append("and fk_project_id = #{projectId} ");
        }
        Long customerId = (Long)params.get("customerId");
        if(customerId != null){
            sql.append("and fk_customer_id!=#{customerId} ");
        }
        Long relateCustomerId = (Long)params.get("relateCustomerId");
        if(relateCustomerId!=null){
            sql.append("and fk_customer_id != #{relateCustomerId} ");
        }
        Long cusId = (Long)params.get("cusId");
        if(cusId!=null){
            sql.append("and fk_customer_id = #{cusId} ");
        }
        sql.append("order by create_date desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
    
    /**
     * 公众号解绑总数
     * @param params 参数
     * @return 总数
     * @author ZhouYing 
     * @date 2016年11月16日 上午10:20:05
     */
    public String count(Map<String,String> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_app_micro_shop where delete_flag=1 and spread_model=1 ");
        String customerId = params.get("customerId");
        if(StringUtils.isNotBlank(customerId)){
            sql.append("and fk_customer_id=#{customerId} ");
        }
        String wwpAppid = params.get("wwpAppid");
        if(StringUtils.isNotBlank(wwpAppid)){
            sql.append("and wwp_appid like concat('%',#{wwpAppid},'%') ");
        }
        String shopName = params.get("shopName");
        if(StringUtils.isNotBlank(shopName)){
            sql.append("and shop_name like concat('%',#{shopName},'%')");
        }
        return sql.toString();
    }
    
    /**
     * 公众号解绑列表
     * @param params 参数
     * @return 列表
     * @author ZhouYing 
     * @date 2016年11月16日 上午10:33:35
     */
    public String query(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,fk_customer_id,app_id,wwp_appid,wwp_secret,shop_id,shop_name,wifi_key,force_attention,from_unixtime(create_date,'%Y-%m-%d %H:%i:%S') as create_date ");
        sql.append("from toe_app_micro_shop where delete_flag=1 and spread_model=1 ");
        String customerId = (String)params.get("customerId");
        if(StringUtils.isNotBlank(customerId)){
            sql.append("and fk_customer_id=#{customerId} ");
        }
        String wwpAppid = (String)params.get("wwpAppid");
        if(StringUtils.isNotBlank(wwpAppid)){
            sql.append("and wwp_appid like concat('%',#{wwpAppid},'%') ");
        }
        String shopName = (String)params.get("shopName");
        if(StringUtils.isNotBlank(shopName)){
            sql.append("and shop_name like concat('%',#{shopName},'%') ");
        }
        sql.append("order by pk_id desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
    
    /**
     * 根据merchantId获取关联商户信息
     * @param params 参数
     * @return string
     * @author 王冬冬  
     * @date 2017年7月12日 下午3:45:59
     */
    public String getRelateMerIdByMerId(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,app_id,wwp_appid,wifi_key,shop_id,shop_name,spread_model,force_attention,cascade_label,fk_relate_customer_id from toe_app_micro_shop where delete_flag=1 and app_id=#{appId} ");
        Long customerId = (Long)params.get("customerId");
        if(customerId!=null){
            sql.append("and fk_customer_id=#{customerId} ");
        }
        return sql.toString();
    }
    
    /**
     * 关联配置商户列表
     * @param params 参数
     * @return 列表
     * @author 王冬冬 
     * @date 2016年11月16日 上午10:33:35
     */
    public String getMerchantByParam(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select fk_customer_id from toe_app_micro_shop where delete_flag=1 and shop_id is not null and app_id=#{appId} and fk_project_id=#{projectId} and fk_customer_id!=#{merchantId} ");
        Long queryMerchantId = (Long)params.get("queryMerchantId");
        if(queryMerchantId!=null){
            sql.append(" and fk_customer_id=#{queryMerchantId} ");
        }
        Long relateCustomerId = (Long)params.get("relateCustomerId");
        if(relateCustomerId!=null){
            sql.append(" and fk_customer_id!=#{relateCustomerId} ");
        }
        sql.append("order by pk_id desc limit #{pageNo},#{pageSize}");
        return sql.toString();
    }
    
    /**
     * 关联配置商户列表记录数
     * @param params 参数
     * @return 列表
     * @author 王冬冬 
     * @date 2016年11月16日 上午10:33:35
     */
    public String getMerchantCountByParam(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_app_micro_shop where delete_flag=1 and shop_id is not null and app_id=#{appId} and fk_project_id=#{projectId} and fk_customer_id!=#{merchantId} ");
        Long queryMerchantId = (Long)params.get("queryMerchantId");
        if(queryMerchantId!=null){
            sql.append(" and fk_customer_id=#{queryMerchantId} ");
        }
        Long relateCustomerId = (Long)params.get("relateCustomerId");
        if(relateCustomerId!=null){
            sql.append(" and fk_customer_id!=#{relateCustomerId} ");
        }
        return sql.toString();
    }
}