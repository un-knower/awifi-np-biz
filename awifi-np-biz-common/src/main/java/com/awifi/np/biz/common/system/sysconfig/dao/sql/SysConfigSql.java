/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年3月24日 下午7:26:05
* 创建作者：许小满
* 文件名称：SysConfigSql.java
* 版本：  v1.0
* 功能：复杂sql封装类
* 修改记录：
*/
package com.awifi.np.biz.common.system.sysconfig.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class SysConfigSql {

    /**
     * 系统参数配置--记录总数
     * @param params 参数
     * @return sql
     * @author 许小满  
     * @date 2017年3月24日 下午7:34:48
     */
    public String getCountByParam(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(id) from np_biz_system_config ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("where concat(ifnull(alias_name,''),ifnull(param_key,''),ifnull(param_value,''),ifnull(remark,'')) like concat('%',#{keywords},'%')");
        }
        return sql.toString();
    }
    
    /**
     * 系统参数配置--分页查询sql
     * @param params 参数
     * @return sql
     * @author 许小满  
     * @date 2017年3月24日 下午7:35:32
     */
    public String getListByParam(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select id,alias_name,param_key,param_value,remark,order_no,")
           .append("date_format(create_date,'%Y-%m-%d %H:%i:%S') as create_date,")
           .append("date_format(update_date,'%Y-%m-%d %H:%i:%S') as update_date ")
            .append("from np_biz_system_config ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("where concat(ifnull(alias_name,''),ifnull(param_key,''),ifnull(param_value,''),ifnull(remark,'')) like concat('%',#{keywords},'%') ");
        }
        sql.append("group by param_key,id limit #{begin},#{pageSize}");
        return sql.toString();
    }
    
    /**
     * 通过key查询记录数量sql
     * @param params 参数
     * @return sql
     * @author 许小满  
     * @date 2017年5月18日 上午12:07:17
     */
    public String getNumByParamKey(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(id) ")
            .append("from np_biz_system_config where param_key=#{paramKey} ");
        Long id = (Long) params.get("id");
        if(id != null){
            sql.append("and id!=#{id}");
        }
        return sql.toString();
    }
    
}
