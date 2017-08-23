/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jul 13, 2017 5:03:00 PM
* 创建作者：季振宇
* 文件名称：AppSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.appsrv.app.dao.sql;

import java.util.Map;

public class AppSql {
    /**
     * 应用管理-分页查询总条数接口sql条件组装
     * @param params 参数
     * @return 符合条件的app数量
     * @author 季振宇  
     * @date Jul 13, 2017 5:56:37 PM
     */
    public String getCountByParam(Map<String,Object> params) {
        StringBuffer sql = new StringBuffer();//创建sql的StringBuffer
        
        sql.append("select count(id) from np_biz_app where status!=9");//添加不含appName和status条件的sql语句
        String appName = (String)params.get("appName");//获取appName参数
        if (appName != null) {//判断appName是否为空
            sql.append(" and app_name like concat('%',#{appName},'%')");//不为空的时候添加appName的条件
        }
        Integer status = (Integer)params.get("status");//获取status参数
        if (status != null) {//判断status是否为空
            sql.append(" and status=#{status}");//不为空的时候添加status的条件
        }
        
        return sql.toString();//返回完整的sql语句
    }
    
    /**
     * 应用管理-分页查询接口sql条件组装
     * @param params 参数
     * @return 分页查询接口sql
     * @author 季振宇  
     * @date Jul 13, 2017 10:08:19 AM
     */
    public String getListByParam(Map<String,Object> params) {
        StringBuffer sql = new StringBuffer();//创建sql的StringBuffer
        
        sql.append("select id,app_id,app_name,status,date_format(create_date,'%Y-%m-%d %H:%i:%S') as create_date from np_biz_app where status!=9");//添加不含appName和status条件的sql语句
        String appName = (String)params.get("appName");//获取appName参数
        if (appName != null) {//判断appName是否为空
            sql.append(" and app_name like concat('%',#{appName},'%')");//不为空的时候添加appName的条件
        }
        Integer status = (Integer)params.get("status");//获取status参数
        if (status != null) {//判断status是否为空
            sql.append(" and status=#{status}");//不为空的时候添加status的条件
        }
        
        sql.append(" order by id desc limit #{begin},#{pageSize}");
        
        return sql.toString();//返回完整的sql语句
    }

}
