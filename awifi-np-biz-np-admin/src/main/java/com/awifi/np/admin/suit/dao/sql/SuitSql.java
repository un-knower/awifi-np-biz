package com.awifi.np.admin.suit.dao.sql;

import java.util.Map;

import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午7:50:04 
 * 创建作者：周颖 
 * 文件名称：SuitSql.java 
 * 版本：v1.0 
 * 功能：SuitSql
 * 修改记录：
 */
public class SuitSql {

    /**
     * 根据角色查找套码sql
     * @param params 角色参数
     * @return sql
     * @author 周颖  
     * @date 2017年1月11日 下午8:00:22
     */
    public String getCodeById(Map<String, Object> params) {
        StringBuffer sql = new StringBuffer();
        sql.append("select suit_code from np_biz_suit_role where 1=1 ");
        Long[] roleIds = (Long[]) params.get("roleIds");
        SqlUtil.in("role_id", "roleIds", roleIds, sql);
        sql.append("order by show_level limit 1");
        return sql.toString();
    }
}