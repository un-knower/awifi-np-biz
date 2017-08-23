package com.awifi.np.biz.common.security.permission.dao.sql;

import java.util.Map;

import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午8:51:33
 * 创建作者：许小满
 * 文件名称：PermissionSql.java
 * 版本：  v1.0
 * 功能：权限--模型层--sql
 * 修改记录：
 */
public class PermissionSql {

    /**
     * 通过角色和编号获取记录数量的sql生成
     * @param params 参数
     * @return sql
     * @author 许小满  
     * @date 2017年2月9日 上午9:01:14
     */
    public String getNumByRoleAndCode(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();//用于拼接sql
        sql.append("select count(p.id) ")//sql拼接
           .append("from np_biz_permission p inner join np_biz_role_permission rp ")//sql拼接
               .append("on p.id=rp.permission_id ")//sql拼接
            .append("where p.service_code=#{serviceCode} ")//sql拼接--服务代码[外键]
            .append("and p.code=#{code} ");//sql拼接--接口编号
        Long[] roleIds = (Long[])params.get("roleIds");//角色ids
        SqlUtil.in("rp.role_id","roleIds",roleIds,sql);//in条件拼接
        return sql.toString();//返回sql
    }
    
    /**
     * 角色-权限关系表  批量更新的sql生成
     * @param params 参数
     * @return sql
     * @author 许小满  
     * @date 2017年2月15日 下午4:30:58
     */
    public String batchAddRolePermission(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();//用于拼接sql
        sql.append("insert into np_biz_role_permission(role_id, permission_id) values ");
        Long[] permissionIds = (Long[])params.get("permissionIds");//权限表主键id数组
        int maxLength = permissionIds.length;
        for(int i=0; i<maxLength; i++){
            sql.append("(#{roleId},#{permissionIds["+i+"]})");
            if(i < (maxLength-1)){
                sql.append(",");
            }
        }
        return sql.toString();//返回sql
    }
    
    /**
     * 角色所有权限
     * @param params 角色ids
     * @return 权限
     * @author 周颖  
     * @date 2017年5月9日 上午10:15:58
     */
    public String getPermissionsByRoleIds(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select p.id,p.code,p.name from np_biz_permission p inner join np_biz_role_permission rp on p.id=rp.permission_id where ");
        Long[] roleIds = (Long[])params.get("roleIds");//角色ids
        int maxLength = roleIds != null ? roleIds.length : 0;
        sql.append("rp.role_id").append(" in(");
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
