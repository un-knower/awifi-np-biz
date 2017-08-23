package com.awifi.np.admin.security.role.dao.sql;

import java.util.Map;

import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月23日 下午2:40:31
 * 创建作者：周颖
 * 文件名称：RoleSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class RoleSql {

    /**
     * 获取角色名称
     * @param params 参数
     * @return 角色名称
     * @author 周颖  
     * @date 2017年2月23日 下午2:53:30
     */
    public String getNamesByIds(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select role_name from np_biz_role where 1=1 ");
        Long[] roleIds = (Long[]) params.get("roleIds");
        SqlUtil.in("id", "roleIds", roleIds, sql);
        return sql.toString();
    }
    
    /**
     * 获取角色列表
     * @param params 角色ids
     * @return 列表
     * @author 周颖  
     * @date 2017年5月8日 下午6:28:31
     */
    public String getIdsAndNamesByRoleIds(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select id,role_name from np_biz_role where ");
        Long[] roleIds = (Long[]) params.get("roleIds");
        int maxLength = roleIds != null ? roleIds.length : 0;
        sql.append("id").append(" in(");
        for(int i=0; i<maxLength; i++){
            sql.append("#{").append("roleIds").append("[").append(i).append("]}");
            if(i < (maxLength - 1)){
                sql.append(",");
            }
        }
        sql.append(") ");
        return sql.toString();
    }
}