package com.awifi.np.biz.toe.admin.project.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.SqlUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月16日 下午3:35:43
 * 创建作者：亢燕翔
 * 文件名称：ProjectSql.java
 * 版本：  v1.0
 * 功能：  
 * 修改记录：
 */
public class ProjectSql {

    /**
     * 查询列表总记录数
     * @param params 参数
     * @return sql
     * @author 亢燕翔  
     * @date 2017年1月16日 下午5:04:44
     */
    public String getCountByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        String projectName = (String) params.get("projectName");
        Long provinceId = (Long) params.get("provinceId");
        Long cityId = (Long) params.get("cityId");
        Long areaId = (Long) params.get("areaId");
        Long projectId = (Long) params.get("projectId");
        sql.append("select count(pk_id) from toe_project where delete_flag = 1 ");
        if(StringUtils.isNotBlank(projectName)){
            sql.append("and project_name like concat('%',#{projectName},'%') ");
        }
        if(null != provinceId){
            sql.append("and province_id=#{provinceId} ");
        }
        if(null != cityId){
            sql.append("and city_id=#{cityId} ");
        }
        if(null != areaId){
            sql.append("and area_id=#{areaId} ");
        }
        if(null != projectId){
            sql.append("and pk_id=#{projectId} ");
        }else{
            Long[] projectIds = (Long[]) params.get("projectIds");
            if(projectIds != null){
                SqlUtil.in("pk_id", "projectIds", projectIds, sql);
            }
            Long[] filterProjectIds = (Long[]) params.get("filterProjectIds");
            if(filterProjectIds != null){
                SqlUtil.notIn("pk_id", "filterProjectIds", filterProjectIds, sql);
            }
        }
        return sql.toString();
    }
    
    /**
     * 查询项目列表
     * @param params 参数
     * @return sql
     * @author 亢燕翔  
     * @date 2017年1月16日 下午5:05:02
     */
    public String getListByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        String projectName = (String) params.get("projectName");
        Long provinceId = (Long) params.get("provinceId");
        Long cityId = (Long) params.get("cityId");
        Long areaId = (Long) params.get("areaId");
        Long projectId = (Long) params.get("projectId");
        sql.append("select pk_id,project_name,province_id,city_id,area_id,contact_person,contact_way,from_unixtime(create_date, '%Y-%m-%d %H:%i:%S') as create_date from toe_project where delete_flag = 1 ");
        if(StringUtils.isNotBlank(projectName)){
            sql.append("and project_name like concat('%',#{projectName},'%') ");
        }
        if(null != provinceId){
            sql.append("and province_id=#{provinceId} ");
        }
        if(null != cityId){
            sql.append("and city_id=#{cityId} ");
        }
        if(null != areaId){
            sql.append("and area_id=#{areaId} ");
        }
        if(null != projectId){
            sql.append("and pk_id=#{projectId} ");
        }else{
            Long[] projectIds = (Long[]) params.get("projectIds");
            if(projectIds != null){
                SqlUtil.in("pk_id", "projectIds", projectIds, sql);
            }
            Long[] filterProjectIds = (Long[]) params.get("filterProjectIds");
            if(filterProjectIds != null){
                SqlUtil.notIn("pk_id", "filterProjectIds", filterProjectIds, sql);
            }
        }
        sql.append("order by pk_id desc limit #{pageNo},#{pageSize} ");
        return sql.toString();
    }
    
    /**
     * 通过项目名称/项目ID查询项目数量
     * @param params 参数
     * @return sql
     * @author 亢燕翔  
     * @date 2017年1月17日 下午7:40:20
     */
    public String getNumByProjectName(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        Long id = (Long) params.get("id");
        sql.append("select count(pk_id) from toe_project where delete_flag = 1 and project_name = #{projectName} ");
        if(id != null){
            sql.append("and pk_id != #{id}");
        }
        return sql.toString();
    }
    
    /**
     * 通过项目id获取名称
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年2月4日 上午8:50:32
     */
    public String getIdAndNameByIds(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,project_name from toe_project where delete_flag=1 ");
        Long[] projectIds = (Long[])params.get("projectIds");
        SqlUtil.in("pk_id", "projectIds", projectIds, sql);
        return sql.toString();
    }
    
    /**
     * 获取项目名称
     * @param params 参数
     * @return sql
     * @author 周颖  
     * @date 2017年4月17日 上午10:54:12
     */
    public String getNamesByIds(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select project_name from toe_project where delete_flag=1 ");
        Long[] projectIds = (Long[])params.get("projectIds");
        SqlUtil.in("pk_id", "projectIds", projectIds, sql);
        return sql.toString();
    }
}