/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 上午10:51:40
* 创建作者：周颖
* 文件名称：ComponentSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.component.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ComponentSql {

    /**
     * 组件列表总数
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年4月12日 上午11:00:34
     */
    public String getCountByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_portal_component where delete_flag=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and name like concat('%',#{keywords},'%')");
        }
        return sql.toString();
    }
    
    /**
     * 组件列表
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年4月12日 上午11:00:34
     */
    public String getListByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,name,type,classify,version,thumb,from_unixtime(create_date, '%Y-%m-%d %H:%i:%S') as create_date from toe_portal_component where delete_flag=1 ");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            sql.append("and name like concat('%',#{keywords},'%') ");
        }
        sql.append("order by pk_id desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
}
