/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 上午10:33:05
* 创建作者：周颖
* 文件名称：ComponentDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.component.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.portal.component.dao.sql.ComponentSql;
import com.awifi.np.biz.toe.portal.component.model.Component;

@Service("componentDao")
public interface ComponentDao {

    /**
     * 组件列表总数
     * @param keywords 组件名称关键字
     * @return 总数
     * @author 周颖  
     * @date 2017年4月12日 上午10:53:33
     */
    @SelectProvider(type=ComponentSql.class,method="getCountByParam")
    int getCountByParam(@Param("keywords")String keywords);

    /**
     * 组件列表
     * @param keywords 组件名关键字
     * @param begin 开始条数
     * @param pageSize 一页大小
     * @return 列表
     * @author 周颖  
     * @date 2017年4月12日 上午10:53:54
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "classify", column = "classify", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "version", column = "version", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=ComponentSql.class,method="getListByParam")
    List<Component> getListByParam(@Param("keywords")String keywords,@Param("begin") Integer begin,@Param("pageSize") Integer pageSize);
    
    /**
     * 通过code获取条总数
     * @param code code
     * @return 总数
     * @author 周颖  
     * @date 2017年4月12日 下午4:29:01
     */
    @Select("select count(pk_id) from toe_portal_component where code=#{code} and delete_flag=1")
    int getCountByCode(@Param("code")String code);
    
    /**
     * 通过setCode获取条总数
     * @param setCode setCode
     * @return 总数
     * @author 周颖  
     * @date 2017年4月12日 下午4:29:01
     */
    @Select("select count(pk_id) from toe_portal_component where set_code=#{setCode} and delete_flag=1")
    int getCountBySetCode(@Param("setCode")String setCode);

    /**
     * 保存组件
     * @param component 组件
     * @author 周颖  
     * @date 2017年4月13日 下午5:10:29
     */
    @Insert("insert into toe_portal_component(code,set_code,name,type,classify,can_unique,version,thumb,icon_path,component_path,project_ids,filter_project_ids,remark,create_date,update_date) "
            + "values(#{code},#{setCode},#{name},#{type},#{classify},#{canUnique},#{version},#{thumb},#{iconPath},#{componentPath},#{projectIds},#{filterProjectIds},#{remark},unix_timestamp(now()),unix_timestamp(now()))")
    void add(Component component);

    /**
     * 组件更新
     * @param component 组件
     * @author 周颖  
     * @date 2017年4月14日 下午1:36:43
     */
    @Update("update toe_portal_component set name=#{name},type=#{type},classify=#{classify},can_unique=#{canUnique},version=#{version},project_ids=#{projectIds},"
            + "filter_project_ids=#{filterProjectIds},remark=#{remark},update_date=unix_timestamp(now()) where pk_id=#{id}")
    void update(Component component);

    /**
     * 组件详情
     * @param id 组件id
     * @return 组件详情
     * @author 周颖  
     * @date 2017年4月14日 下午2:00:26
     */
    @Results(value = {
            @Result(property = "code", column = "code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "setCode", column = "set_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "classify", column = "classify", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "canUnique", column = "can_unique", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "version", column = "version", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "componentPath", column = "component_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "iconPath", column = "icon_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "projectIds", column = "project_ids", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "filterProjectIds", column = "filter_project_ids", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select code,set_code,name,type,classify,can_unique,version,component_path,icon_path,thumb,project_ids,filter_project_ids,remark from toe_portal_component where pk_id=#{id}")
    Component getById(@Param("id")Long id);

    /**
     * 通过组件类型获取组件列表
     * @param type 组件类型
     * @return 列表
     * @author 周颖  
     * @date 2017年5月3日 上午10:23:45
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "code", column = "code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "setCode", column = "set_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "classify", column = "classify", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "canUnique", column = "can_unique", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "version", column = "version", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "componentPath", column = "component_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "iconPath", column = "icon_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            })
    @Select("select pk_id,code,set_code,name,type,classify,can_unique,version,component_path,icon_path,thumb from toe_portal_component "
            + "where delete_flag=1 and type like concat('%',#{type},'%') and project_ids like ('{0}%') order by pk_id desc limit 100")
    List<Component> getListByType(@Param("type")String type);

    /**
     * 通过组件类型和项目id获取组件列表
     * @param type 组件类型
     * @param projectId 项目id
     * @return 列表
     * @author 周颖  
     * @date 2017年5月3日 上午10:30:23
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "code", column = "code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "setCode", column = "set_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "classify", column = "classify", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "canUnique", column = "can_unique", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "version", column = "version", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "componentPath", column = "component_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "iconPath", column = "icon_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,code,set_code,name,type,classify,can_unique,version,component_path,icon_path,thumb from toe_portal_component "
            + "where delete_flag=1 and type like concat('%',#{type},'%') "
            + "and (project_ids like ('{0}%') or project_ids like concat('%',#{projectId},'%')) "
            + "and filter_project_ids not like concat('%',#{projectId},'%')order by pk_id desc limit 100")
    List<Component> getListByTypeAndProjectId(@Param("type")String type,@Param("projectId") String projectId);
}
