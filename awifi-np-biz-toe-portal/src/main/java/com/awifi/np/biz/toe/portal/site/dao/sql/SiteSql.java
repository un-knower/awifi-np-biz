/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:48:47
* 创建作者：周颖
* 文件名称：SiteSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.site.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.SqlUtil;

public class SiteSql {

    /**
     * 默认站点总数
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年4月18日 上午8:53:37
     */
    public String getDefaultCountByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and site_name like concat('%',#{keywords},'%')");
        }
        return sql.toString();
    }
    
    /**
     * 默认站点列表
     * @param params 参数
     * @return 列表
     * @author 周颖  
     * @date 2017年4月18日 上午8:59:09
     */
    public String getDefaultListByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,site_name,thumb,from_unixtime(create_date,'%Y-%m-%d %H:%i:%S') as create_date from toe_portal_site where delete_flag=1 and default_site=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and site_name like concat('%',#{keywords},'%') ");
        }
        sql.append("order by pk_id desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
    
    /**
     * 地区站点总数
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年4月18日 上午9:33:14
     */
    public String getLocationCountByParam(Map<String,Object>params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=3 ");
        String keywords = (String)params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and site_name like concat('%',#{keywords},'%') ");
        }
        Long provinceId = (Long)params.get("provinceId");
        if(provinceId != null){
            sql.append("and province_id=#{provinceId} ");
        }
        Long cityId = (Long)params.get("cityId");
        if(cityId != null){
            sql.append("and city_id=#{cityId}");
        }
        return sql.toString(); 
    }
    
    /**
     * 地区站点列表
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年4月18日 上午9:33:14
     */
    public String getLocationListByParam(Map<String,Object>params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,site_name,thumb,province_id,city_id,from_unixtime(create_date,'%Y-%m-%d %H:%i:%S') as create_date from toe_portal_site where delete_flag=1 and default_site=3 ");
        String keywords = (String)params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and site_name like concat('%',#{keywords},'%') ");
        }
        Long provinceId = (Long)params.get("provinceId");
        if(provinceId != null){
            sql.append("and province_id=#{provinceId} ");
        }
        Long cityId = (Long)params.get("cityId");
        if(cityId != null){
            sql.append("and city_id=#{cityId} ");
        }
        sql.append("order by pk_id desc limit #{begin},#{pageSize}");
        return sql.toString(); 
    }
    
    /**
     * 行业站点总数
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年4月18日 上午9:33:14
     */
    public String getIndustryCountByParam(Map<String,Object>params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=2 ");
        String keywords = (String)params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and site_name like concat('%',#{keywords},'%') ");
        }
        String priIndustryCode = (String)params.get("priIndustryCode");
        if(StringUtils.isNotBlank(priIndustryCode)){
            sql.append("and pri_industry_code=#{priIndustryCode} ");
        }
        String secIndustryCode = (String)params.get("secIndustryCode");
        if(StringUtils.isNotBlank(secIndustryCode)){
            sql.append("and sec_industry_code=#{secIndustryCode}");
        }
        return sql.toString(); 
    }
    
    /**
     * 行业站点列表
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年4月18日 上午9:33:14
     */
    public String getIndustryListByParam(Map<String,Object>params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,site_name,thumb,pri_industry_code,sec_industry_code,from_unixtime(create_date,'%Y-%m-%d %H:%i:%S') as create_date from toe_portal_site where delete_flag=1 and default_site=2 ");
        String keywords = (String)params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and site_name like concat('%',#{keywords},'%') ");
        }
        String priIndustryCode = (String)params.get("priIndustryCode");
        if(StringUtils.isNotBlank(priIndustryCode)){
            sql.append("and pri_industry_code=#{priIndustryCode} ");
        }
        String secIndustryCode = (String)params.get("secIndustryCode");
        if(StringUtils.isNotBlank(secIndustryCode)){
            sql.append("and sec_industry_code=#{secIndustryCode} ");
        }
        sql.append("order by pk_id desc limit #{begin},#{pageSize}");
        return sql.toString(); 
    }
    
    /**
     * 商户站点总数
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年4月19日 上午10:18:58
     */
    public String getCountByParam(Map<String,Object>params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=-1 ");
        String keywords = (String)params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and site_name like concat('%',#{keywords},'%') ");
        }
        Long siteId = (Long)params.get("siteId");
        if(siteId != null){
            sql.append("and pk_id=#{siteId} ");
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
        Integer status = (Integer) params.get("status");
        if(status != null){
            sql.append("and status=#{status}");
        }
        return sql.toString();
    }
    
    /**
     * 商户站点列表
     * @param params 参数
     * @return 列表sql
     * @author 周颖  
     * @date 2017年4月19日 上午10:38:49
     */
    public String getListByParam(Map<String,Object>params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,site_name,thumb,fk_customer_id,status,from_unixtime(create_date,'%Y-%m-%d %H:%i:%S') as create_date ")
                .append("from toe_portal_site where delete_flag=1 and default_site=-1 ");
        String keywords = (String)params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and site_name like concat('%',#{keywords},'%') ");
        }
        Long siteId = (Long)params.get("siteId");
        if(siteId != null){
            sql.append("and pk_id=#{siteId} ");
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
        Integer status = (Integer) params.get("status");
        if(status != null){
            sql.append("and status=#{status} ");
        }
        sql.append("order by pk_id desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
}
