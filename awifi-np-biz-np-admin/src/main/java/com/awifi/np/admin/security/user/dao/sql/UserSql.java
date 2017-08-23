/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月5日 上午10:36:52
* 创建作者：周颖
* 文件名称：UserSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.admin.security.user.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class UserSql {

    /**
     * 管理员账号列表总数
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年5月5日 下午3:26:29
     */
    public String getCountByParams(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(u.id) from np_biz_user u where u.status!=9 and u.id!=1 and merchant_id is null ");
        Long roleId = (Long)params.get("roleId");
        if(roleId != null){
            sql.append("and exists (select ur.role_id from np_biz_user_role ur where ur.user_id=u.id and ur.role_id=#{roleId}) ");
        }
        Long provinceId = (Long)params.get("provinceId");
        if(provinceId != null){
            sql.append("and u.province_id=#{provinceId} ");
        }
        Long cityId = (Long)params.get("cityId");
        if(cityId != null){
            sql.append("and u.city_id=#{cityId} ");
        }
        Long areaId = (Long)params.get("areaId");
        if(areaId != null){
            sql.append("and u.area_id=#{areaId} ");
        }
        String userName = (String)params.get("userName");
        if(StringUtils.isNotBlank(userName)){
            sql.append("and u.user_name like concat('%',#{userName},'%')");
        }
        return sql.toString();
    }
    
    /**
     * 管理员账号列表
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年5月5日 下午3:26:29
     */
    public String getListByParams(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select u.id,u.user_name,u.province_id,u.city_id,u.area_id,u.dept_name,u.contact_person,u.contact_way ")
               .append("from np_biz_user u where u.status!=9 and u.id!=1 and merchant_id is null ");
        Long roleId = (Long)params.get("roleId");
        if(roleId != null){
            sql.append("and exists (select ur.role_id from np_biz_user_role ur where ur.user_id=u.id and ur.role_id=#{roleId}) ");
        }
        Long provinceId = (Long)params.get("provinceId");
        if(provinceId != null){
            sql.append("and u.province_id=#{provinceId} ");
        }
        Long cityId = (Long)params.get("cityId");
        if(cityId != null){
            sql.append("and u.city_id=#{cityId} ");
        }
        Long areaId = (Long)params.get("areaId");
        if(areaId != null){
            sql.append("and u.area_id=#{areaId} ");
        }
        String userName = (String)params.get("userName");
        if(StringUtils.isNotBlank(userName)){
            sql.append("and u.user_name like concat('%',#{userName},'%') ");
        }
        sql.append("order by u.id desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
}
