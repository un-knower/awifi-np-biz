/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月11日 下午5:11:38
* 创建作者：王冬冬
* 文件名称：WhiteUserSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.SqlUtil;

public class WhiteUserSql {
	
    /**
     * 白名单列表查询
     * @param params 参数
     * @return String
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:13:46
     */
    public String getListByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select id,MerchantId,Mobile,Mac,from_unixtime(CreateTime,'%Y-%m-%d %h:%i:%s') as CreateTime,AccountId from station_merchant_namelist where status=0 and Type=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append(" and concat(ifnull(Mac,''),ifnull(Mobile,'')) like concat('%',#{keywords},'%') ");
        }
        Long merchantId = (Long) params.get("merchantId");
        if(merchantId != null){
            sql.append("and MerchantId=#{merchantId} ");
        }
        sql.append("order by CreateTime desc limit #{begin},#{pageSize}");
        return sql.toString();
    }

    /**
     * 根据参数获得记录数
     * @param params 参数
     * @return string
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:20:46
     */
    public String getCountByParam(Map<String, Object> params) {
    	StringBuffer sql = new StringBuffer();
        sql.append("select count(id) from station_merchant_namelist where status=0 and Type=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append(" and concat(ifnull(Mac,''),ifnull(Mobile,'')) like concat('%',#{keywords},'%') ");
        }
        Long merchantId = (Long) params.get("merchantId");
        if(merchantId != null){
            sql.append("and MerchantId=#{merchantId} ");
        }
        sql.append("order by CreateTime desc");
        return sql.toString();
    }
    
    /**
     * 批量删除
     * @param params 参数
     * @return string
     * @author 王冬冬  
     * @date 2017年4月21日 下午5:28:02
     */
    public String batchDelete(Map<String, Object> params){
        Long [] idArr=(Long[]) params.get("idArr");
    	StringBuffer sql = new StringBuffer();
        sql.append("update station_merchant_namelist set status=9 where Type=1 ");
        SqlUtil.in("id", "idArr", idArr, sql);
    	return sql.toString();
    }
    
    
    /**
     * 根据ids查询白名单
     * @param params 参数
     * @return String
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:13:46
     */
    public String getListByIds(Map<String, Object> params){
        Long [] idArr=(Long[]) params.get("idArr");
        StringBuffer sql = new StringBuffer();
        sql.append("select id,MerchantId,Mobile,Mac,from_unixtime(CreateTime,'%Y-%m-%d %h:%i:%s') as CreateTime,AccountId from station_merchant_namelist where status=0 and Type=1 ");
        SqlUtil.in("id", "idArr", idArr, sql);
        return sql.toString();
    }
    
    
    /**
     * 根据参数获得记录数
     * @param params 参数
     * @return string
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:20:46
     */
    public String getCountByParams(Map<String, Object> params) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id) from station_merchant_namelist where status=0 and Type=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append(" and concat(ifnull(Mac,''),ifnull(Mobile,'')) like concat('%',#{keywords},'%') ");
        }
        Long[] merchantIds = (Long[]) params.get("merchantIds");
        SqlUtil.in("MerchantId", "merchantIds", merchantIds, sql);
        sql.append("order by CreateTime desc");
        return sql.toString();
    }
    
    /**
     * 白名单列表查询（多商户）
     * @param params 参数
     * @return String
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:13:46
     */
    public String getListByParams(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select id,MerchantId,Mobile,Mac,from_unixtime(CreateTime,'%Y-%m-%d %h:%i:%s') as CreateTime,AccountId from station_merchant_namelist where status=0 and Type=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append(" and concat(ifnull(Mac,''),ifnull(Mobile,'')) like concat('%',#{keywords},'%') ");
        }
        Long[] merchantIds = (Long[]) params.get("merchantIds");
        SqlUtil.in("MerchantId", "merchantIds", merchantIds, sql);
        sql.append("order by CreateTime desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
    
    
    
}
