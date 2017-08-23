package com.awifi.np.biz.appsrv.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;


import com.awifi.np.biz.appsrv.app.dao.sql.AppSql;
import com.awifi.np.biz.appsrv.app.model.App;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月10日 下午2:35:43
 * 创建作者：许尚敏
 * 文件名称：AppDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service(value = "appDao")
public interface AppDao {
    /**
     * 查询应用条数
     * @param appName 应用名称
     * @param status 状态
     * @return  应用数量
     * @author 季振宇  
     * @date Jul 10, 2017 3:38:39 PM
     */
    @SelectProvider(type=AppSql.class,method="getCountByParam")
    Integer getCountByParam(@Param("appName")String appName, @Param("status")Integer status);
    
    /**
     * 查询应用列表
     * @param appName 应用名称  
     * @param status  状态
     * @param begin 查询开始点
     * @param pageSize 查询条数
     * @return
     * @author 季振宇  
     * @date Jul 10, 2017 3:39:23 PM
     * @return 返回记录集
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appId", column = "app_id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "appName", column = "app_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
    @SelectProvider(type=AppSql.class,method="getListByParam")
    List<App> getListByParam(@Param("appName")String appName, @Param("status")Integer status, @Param("begin")Integer begin, @Param("pageSize")Integer pageSize);

    /**
     * 应用管理--应用列表-详情接口dao
     * @param id 应用id
     * @return 应用详情
     * @author 季振宇  
     * @date Jul 12, 2017 10:04:56 AM
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appId", column = "app_id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "appKey", column = "app_key", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "appName", column = "app_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appParam", column = "app_param", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "company", column = "company", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "businessLicense", column = "business_license", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactPerson", column = "contact_person", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contact_way", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
    @Select("select id,app_id,app_key,app_name,app_param,company,business_license,contact_person,contact_way,status,remark from np_biz_app where id=#{id}")
    App getById(@Param("id")Long id);
    
    /**
     * 应用管理—应用删除接口dao
     * @param id 主键id
     * @author 季振宇  
     * @date Jul 12, 2017 10:43:08 AM
     */
    @Update("update np_biz_app set status=9 where id=#{id}")
    void delete(@Param("id")Long id);
    
    /**
     * 应用管理—应用删除-查询状态
     * @param id 主键id
     * @return 状态
     * @author 季振宇  
     * @date Jul 12, 2017 11:03:54 AM
     */
    @Select("select status from np_biz_app where id=#{id}")
    Integer getStatusById(@Param("id")Long id);
    
    /**
     * 应用添加接口
     * @param record 应该实体类
     * @author 许尚敏  
     * @date 2017年7月11日 上午10:50:24
     */
    @Insert("insert into np_biz_app(app_id,app_key,app_name,app_param,company,business_license,contact_person,contact_way,status,remark) values (#{appId,jdbcType=VARCHAR}, #{appKey,jdbcType=VARCHAR}, #{appName,jdbcType=VARCHAR}, "
            + "#{appParam,jdbcType=VARCHAR}, #{company,jdbcType=VARCHAR}, #{businessLicense,jdbcType=VARCHAR}, #{contactPerson,jdbcType=VARCHAR}, #{contactWay,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})")
    void add(App record);

    /**
     * 应用编辑接口
     * @param record 应该实体类
     * @author 许尚敏
     * @date 2017年7月11日 上午10:50:24
     */
    @Update("update np_biz_app set app_name = #{appName,jdbcType=INTEGER}, app_param = #{appParam,jdbcType=VARCHAR},company = #{company,jdbcType=VARCHAR},business_license = #{businessLicense,jdbcType=VARCHAR},contact_person = #{contactPerson,jdbcType=VARCHAR},"
            + "contact_way = #{contactWay,jdbcType=VARCHAR},status = #{status,jdbcType=TINYINT},remark = #{remark,jdbcType=VARCHAR} where Id = #{id,jdbcType=INTEGER}")
    void update(App record);

    /**
     * 根据appid查询app信息
     * @param appId 应用
     * @return app信息
     * @author 王冬冬  
     * @date 2017年7月14日 下午1:54:54
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appKey", column = "app_key", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "appParam", column = "app_param", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
    @Select("select app_key,app_param from np_biz_app where status!=9 and app_id=#{appId}")
    App queryAppByAppId(@Param("appId") String appId);

    /**
     * 通过appId获取appKey
     * @param appId appId
     * @return appKey
     * @author 周颖  
     * @date 2017年7月14日 下午2:55:34
     */
    @Select("select app_key from np_biz_app where app_id=#{appId} and status=1 limit 1")
    String getKeyByAppId(@Param("appId")String appId);

    /**
     * 通过appId获取app详情
     * @param appId appId
     * @return 详情
     * @author 周颖  
     * @date 2017年7月17日 上午10:27:32
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appId", column = "app_id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "appKey", column = "app_key", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "appName", column = "app_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
    @Select("select id,app_id,app_key,app_name from np_biz_app where app_id=#{appId} limit 1")
    Map<String, Object> getByAppId(@Param("appId")String appId);
}
