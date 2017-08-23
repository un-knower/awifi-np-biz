package com.awifi.np.admin.service.dao.sql;

import java.util.Map;

import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午8:05:55
 * 创建作者：周颖
 * 文件名称：ServiceSql.java
 * 版本：  v1.0
 * 功能：服务sql
 * 修改记录：
 */
public class ServiceSql {

    /**
     * 获取一级菜单sql
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年1月12日 下午8:15:59
     */
    public String getTopMenus(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select s.menu_name,s.menu_url,s.has_submenu,s.target_id from np_service s "
                + "inner join np_platform_service ps on s.service_code = ps.service_code "
                + "inner join np_biz_role_service rs on s.service_code=rs.service_code "
                + "where s.is_menu=1 and s.publish_status=1 and ps.app_id=#{appId} ");
        Long[] roleIds = (Long[]) params.get("roleIds");
        SqlUtil.in("rs.biz_role_id", "roleIds", roleIds, sql);
        sql.append("order by ps.list_order");
        return sql.toString();
    }
}