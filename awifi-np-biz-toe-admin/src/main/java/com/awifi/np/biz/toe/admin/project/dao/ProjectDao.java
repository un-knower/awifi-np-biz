package com.awifi.np.biz.toe.admin.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.admin.project.dao.sql.ProjectSql;
import com.awifi.np.biz.toe.admin.project.model.Project;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月16日 下午3:00:19
 * 创建作者：亢燕翔
 * 文件名称：ProjectDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("projectDao")
@SuppressWarnings("rawtypes")
public interface ProjectDao {

    /**
     * 通过商户ID查询对应的项目ID
     * @param merchantId 商户ID
     * @return 项目ID
     * @author 亢燕翔  
     * @date 2017年1月16日 下午3:16:35
     */
    @Select("select p.pk_id from toe_project p inner join toe_user u on p.pk_id = u.fk_project_id inner join toe_user_customer uc "
            + "on u.pk_id = uc.user_id where uc.customer_id=#{merchantId}")
    Long getProjectByMerchantId(Long merchantId);

    /**
     * 通过条件查询项目总数
     * @param projectName 项目名称
     * @param provinceId 省ID
     * @param cityId 市ID
     * @param areaId 区县ID
     * @param projectId 项目ID
     * @param projectIds 管理项目id
     * @param filterProjectIds 排除项目id
     * @return count
     * @author 亢燕翔  
     * @date 2017年1月16日 下午3:31:50
     */
    @SelectProvider(type=ProjectSql.class, method="getCountByParam")
    int getCountByParam(@Param("projectName")String projectName,@Param("provinceId")Object provinceId,@Param("cityId")Object cityId,@Param("areaId")Object areaId,
            @Param("projectId")Object projectId,@Param("projectIds")Long[] projectIds,@Param("filterProjectIds")Long[] filterProjectIds);

    /**
     * 通过条件查询项目列表
     * @param projectName 项目名称
     * @param provinceId 省ID
     * @param cityId 市ID
     * @param areaId 区县ID
     * @param projectId 项目ID
     * @param projectIds 管理项目id
     * @param filterProjectIds 排除项目id
     * @param pageNo 页码
     * @param pageSize 每页显示数
     * @return list
     * @author 亢燕翔  
     * @date 2017年1月16日 下午5:17:44
     */
    @Results(value = {  
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "projectName", column = "project_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "contact", column = "contact_person", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contact_way", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
    @SelectProvider(type=ProjectSql.class, method="getListByParam")
    List<Project> getListByParam(@Param("projectName")String projectName,@Param("provinceId")Long provinceId,@Param("cityId")Long cityId,@Param("areaId")Long areaId,
            @Param("projectId")Long projectId,@Param("projectIds")Long[] projectIds,@Param("filterProjectIds")Long[] filterProjectIds,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    
    /**
     * 通过项目名称获取项目数量
     * @param projectName 项目名称
     * @return 项目数量
     * @author 亢燕翔  
     * @param id 
     * @date 2017年1月17日 上午9:21:50
     */
    @SelectProvider(type=ProjectSql.class, method="getNumByProjectName")
    int getNumByProjectName(@Param("id")Long id,@Param("projectName")String projectName);

    /**
     * 添加项目
     * @param projectName 项目名称
     * @param contact 联系人
     * @param contactWay 联系方式
     * @param provinceId 省ID
     * @param cityId 市ID
     * @param areaId 区县ID
     * @author 亢燕翔  
     * @param platformId 
     * @param remark 
     * @date 2017年1月17日 上午9:40:23
     */
    @Insert("insert into toe_project(project_name,contact_person,contact_way,province_id,city_id,area_id,fk_platform_id,remark,create_date,update_date) values "
            + "(#{projectName},#{contact},#{contactWay},#{provinceId},#{cityId},#{areaId},#{platformId},#{remark},unix_timestamp(now()),unix_timestamp(now()))")
    void add(@Param("projectName")String projectName, @Param("contact")String contact, @Param("contactWay")String contactWay, @Param("provinceId")Integer provinceId,
             @Param("cityId")Integer cityId, @Param("areaId")Integer areaId, @Param("platformId")Integer platformId, @Param("remark")String remark);

    /**
     * 项目详情
     * @param id 项目ID
     * @return Project
     * @author 亢燕翔  
     * @date 2017年1月17日 上午9:55:55
     */
    @Results(value = {  
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "projectName", column = "project_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "contact", column = "contact_person", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contact_way", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
    @Select("select pk_id,project_name,contact_person,contact_way,province_id,city_id,area_id,remark,from_unixtime(create_date, '%Y-%m-%d %H:%i:%S') as create_date "
            + "from toe_project where delete_flag = 1 and pk_id=#{id}")
    Project getById(Long id);
    
    /**
     * 通过项目项目ID查询该项目下是否还有商户存在
     * @param id 项目ID
     * @return count
     * @author 亢燕翔  
     * @date 2017年1月17日 上午10:04:13
     */
    @Select("select count(t.pk_id) from toe_project t inner join toe_user u on t.pk_id = u.fk_project_id where t.pk_id=#{id}")
    int isCustomerExistByProjectId(Long id);

    /**
     * 删除项目
     * @param id 项目ID
     * @author 亢燕翔  
     * @date 2017年1月17日 上午10:19:58
     */
    @Update("update toe_project set delete_flag=-1 where pk_id=#{id}")
    void delete(Long id);
    
    /**
     * 编辑项目
     * @param id 项目ID
     * @param projectName 项目名称
     * @param contact 联系人
     * @param contactWay 联系方式
     * @param provinceId 省ID
     * @param cityId 市ID
     * @param areaId 区县ID
     * @param remark 备注
     * @author 亢燕翔  
     * @date 2017年1月17日 上午11:16:31
     */
    @Update("update toe_project set project_name=#{projectName},contact_person=#{contact},contact_way=#{contactWay},province_id=#{provinceId},city_id=#{cityId},"
            + "area_id=#{areaId},remark=#{remark},update_date=unix_timestamp(now()) where pk_id=#{id}")
    void update(@Param("id")Long id, @Param("projectName")String projectName, @Param("contact")String contact, @Param("contactWay")String contactWay,
            @Param("provinceId")Integer provinceId, @Param("cityId")Integer cityId, @Param("areaId")Integer areaId, @Param("remark")String remark);

    /**
     * 通过项目ids获取id和名称
     * @param projectIds 项目id数组
     * @return id+名称
     * @author 周颖  
     * @date 2017年2月4日 上午8:52:31
     */
    @SelectProvider(type=ProjectSql.class, method="getIdAndNameByIds")
    List<Map> getIdAndNameByIds(@Param("projectIds")Long[] projectIds);

    /**
     * 通过项目id获取项目名称
     * @param id 项目id
     * @return 项目名称
     * @author 周颖  
     * @date 2017年2月6日 上午9:56:14
     */
    @Select("select project_name from toe_project where pk_id = #{id} limit 1")
    String getNameById(@Param("id")Long id);

    /**
     * 通过项目名称获取项目id
     * @param projectName 项目名称
     * @return 项目id
     * @author 周颖  
     * @date 2017年4月10日 上午10:48:21
     */
    @Select("select pk_id from toe_project where project_name=#{projectName} and delete_flag=1 limit 1")
    Long getIdByName(String projectName);

    /**
     * 新建项目 返回项目名id
     * @param project 项目
     * @author 周颖  
     * @date 2017年4月10日 上午10:56:00
     */
    @SelectKey(before=false,keyProperty="id",resultType=Long.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    @Insert("insert into toe_project (project_name,fk_platform_id,contact_person,contact_way,delete_flag,province_id,city_id,area_id,create_date,update_date) "
            + "values (#{projectName},#{platformId},#{contact},#{contactWay},1,#{provinceId},#{cityId},#{areaId},unix_timestamp(now()),unix_timestamp(now()))")
    void insert(Project project);

    /**
     * 获取项目名称
     * @param projectIds 项目ids
     * @return 项目名称列表
     * @author 周颖  
     * @date 2017年4月17日 上午10:54:33
     */
    @SelectProvider(type=ProjectSql.class, method="getNamesByIds")
    List<String> getNamesByIds(@Param("projectIds")Long[] projectIds);
}