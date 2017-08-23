package com.awifi.np.biz.common.menu.dao.sql;

import java.util.Map;

import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月7日 上午10:46:44
 * 创建作者：亢燕翔
 * 文件名称：MenuDaoSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class MenuSql {

    /**
     * 获取菜单
     * @param params 参数
     * @return sql
     * @author 亢燕翔  
     * @date 2017年2月7日 上午10:48:09
     */
    public String getListByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        Long roleId = (Long)params.get("roleId");//角色id
        Long[] roleIds = (Long[])params.get("roleIds");//角色ids
        Long parentId = CastUtil.toLong(params.get("parentId"));//上级菜单id
        sql.append("select m.id,m.name,m.url,m.target_id from np_biz_menu m where m.service_code=#{serviceCode} ");
        //上级菜单id
        if(parentId == null){
            sql.append("and m.parent_id=-1 ");
        } else {
            sql.append("and m.parent_id=#{parentId} ");
        }
        
        Boolean roleIdBool = roleId != null ? true :false;
        Boolean roleIdsBool = roleIds != null ? true : false;
        //角色id || 角色ids
        if(roleIdBool || roleIdsBool){
            sql.append("and exists (select rm.role_id from np_biz_role_menu rm where m.id=rm.menu_id ");
            //角色id
            if(roleIdBool){
                sql.append("and rm.role_id=#{roleId} ");
            }
            //角色ids
            if(roleIdsBool){
                SqlUtil.in("rm.role_id","roleIds",roleIds,sql);//in条件拼接
            }
            sql.append(") ");
        }
        sql.append("group by m.name order by m.order_no,m.id");
        return sql.toString();
    }
    
    /**
     * 角色-菜单关系表  批量更新的sql生成
     * @param params 参数
     * @return sql
     * @author 许小满  
     * @date 2017年2月16日 下午3:12:26
     */
    public String batchAddRoleMenu(Map<String,Object> params){
        StringBuffer sql = new StringBuffer();//用于拼接sql
        sql.append("insert into np_biz_role_menu(role_id, menu_id) values ");
        Long[] menuIds = (Long[])params.get("menuIds");//菜单表主键id数组
        int maxLength = menuIds.length;
        for(int i=0; i<maxLength; i++){
            sql.append("(#{roleId},#{menuIds["+i+"]})");
            if(i < (maxLength-1)){
                sql.append(",");
            }
        }
        return sql.toString();//返回sql
    }
    
}
