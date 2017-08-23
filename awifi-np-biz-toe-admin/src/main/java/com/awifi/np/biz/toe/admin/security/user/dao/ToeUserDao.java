package com.awifi.np.biz.toe.admin.security.user.dao;

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

import com.awifi.np.biz.toe.admin.security.user.dao.sql.ToeUserSql;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 上午9:41:23
 * 创建作者：周颖
 * 文件名称：ToeUserDao.java
 * 版本：  v1.0
 * 功能：用户
 * 修改记录：
 */
public interface ToeUserDao {

    /**
     * 获取账号对应的商户id
     * @param userName 账号
     * @return 商户id
     * @author 周颖  
     * @date 2017年2月3日 上午9:54:42
     */
    @Select("select uc.customer_id from toe_user u left join toe_user_customer uc on u.pk_id=uc.user_id where u.user_name=#{userName} and u.delete_flag=1 limit 1")
    Long getMerIdByUserName(@Param("userName") String userName);

    /**
     * 查看是否存在该账号
     * @param userName 账号
     * @return 总数
     * @author 周颖  
     * @date 2017年2月4日 上午11:21:07
     */
    @Select("select count(pk_id) from toe_user where user_name=#{userName} and delete_flag=1")
    int getNumByUserName(String userName);

    /**
     * 创建用户
     * @param user 用户
     * @author 周颖  
     * @date 2017年2月4日 下午2:13:53
     */
    @SelectKey(before=false,keyProperty="id",resultType=Long.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    @Insert("insert into toe_user(user_name,password,province_id,city_id,area_id,contact_person,contact_way,dept_name,remark,create_date,update_date,fk_project_id,fk_create_user_id) "
            + "values(#{userName},#{password},#{provinceId},#{cityId},#{areaId},#{contactPerson},#{contactWay},#{deptName},#{remark},unix_timestamp(now()),unix_timestamp(now()),#{projectId},#{createUserId})")
    void add(ToeUser user);

    /**
     * 维护账号和商户的关系
     * @param userId 账号id
     * @param merchantId 商户id
     * @author 周颖  
     * @date 2017年2月4日 下午2:28:21
     */
    @Insert("insert into toe_user_customer(user_id,customer_id) values(#{userId},#{merchantId})")
    void addUserMerchant(@Param("userId")Long userId,@Param("merchantId") Long merchantId);

    /**
     * 通过商户id获取账号详情
     * @param merchantId 商户id
     * @return 账号详情
     * @author 周颖  
     * @date 2017年2月6日 上午9:12:17
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactPerson", column = "contact_person", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contact_way", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "projectId", column = "fk_project_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select u.pk_id,u.user_name,u.contact_person,u.contact_way,u.province_id,u.city_id,u.area_id,u.remark,u.fk_project_id,from_unixtime(u.create_date, '%Y-%m-%d %H:%i:%S') as create_date "
            + "from toe_user u inner join toe_user_customer uc on u.pk_id=uc.user_id where u.delete_flag=1 and uc.customer_id=#{merchantId} limit 1")
    ToeUser getByMerchantId(@Param("merchantId")Long merchantId);

    /**
     * 通过商户id获取账号id
     * @param merchantId 商户id
     * @return 账号id
     * @author 周颖  
     * @date 2017年2月7日 下午2:10:54
     */
    @Select("select u.pk_id from toe_user u inner join toe_user_customer uc on u.pk_id=uc.user_id where u.delete_flag=1 and uc.customer_id=#{merchantId} limit 1")
    Long getIdByMerchantId(Long merchantId);

    /**
     * 更新账号
     * @param user 账号
     * @author 周颖  
     * @date 2017年2月7日 下午2:17:44
     */
    @Update("update toe_user set contact_person=#{contactPerson},contact_way=#{contactWay},province_id=#{provinceId},city_id=#{cityId},area_id=#{areaId},fk_project_id=#{projectId} where pk_id=#{id}")
    void update(ToeUser user);

    /**
     * 根据用户名和密码查找用户信息
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     * @author 周颖  
     * @date 2017年4月5日 上午9:04:43
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            })
    @Select("select u.pk_id,u.user_name,u.province_id,u.city_id,u.area_id,uc.customer_id from toe_user u inner join toe_user_customer uc on u.pk_id = uc.user_id "
            + "where delete_flag=1 and user_name=#{userName} and password=#{password}")
    ToeUser getByNameAndPwd(@Param("userName")String userName, @Param("password")String password);

    /**
     * 通过用户id获取用户详情
     * @param id 用户主键id
     * @return 用户详情
     * @author 周颖  
     * @date 2017年4月5日 上午10:32:02
     */
    @Results(value = {
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "realname", column = "realname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "nickname", column = "nickname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "email", column = "email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactPerson", column = "contact_person", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contact_way", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select user_name,password,province_id,city_id,area_id,realname,nickname,email,contact_person,contact_way,remark from toe_user where pk_id=#{id} and delete_flag=1")
    ToeUser getById(Long id);

    /**
     * 更新用户信息
     * @param toeUser 用户
     * @return 条数
     * @author 周颖  
     * @date 2017年4月5日 上午11:28:00
     */
    @Update("update toe_user set realname=#{realname},nickname=#{nickname},email=#{email},contact_person=#{contactPerson},contact_way=#{contactWay},remark=#{remark} where pk_id=#{id}")
    int updateById(ToeUser toeUser);

    /**
     * 更新密码 
     * @param id 主键id
     * @param password 密码
     * @return 条数
     * @author 周颖  
     * @date 2017年4月5日 下午1:56:02
     */
    @Update("update toe_user set password=#{password} where pk_id=#{id}")
    int updatePwdById(@Param("id")Long id,@Param("password") String password);

    /**
     * 更新项目归属
     * @param id 主键id
     * @param projectId 项目id
     * @author 周颖  
     * @date 2017年4月6日 下午1:17:19
     */
    @Update("update toe_user set fk_project_id=#{projectId} where pk_id=#{id}")
    void updateProject(@Param("id")Long id,@Param("projectId") Long projectId);

    /**
     * 批量获取账号名称
     * @param merchantIds 商户ids
     * @return 账号名称
     * @author 周颖  
     * @date 2017年4月10日 下午7:04:51
     */
    @SuppressWarnings("rawtypes")
    @SelectProvider(type=ToeUserSql.class, method="getNameByMerchantIds")
    List<Map> getNameByMerchantIds(@Param("merchantIds")Long[] merchantIds);

    /**
     * @param userNames 用户名称
     * @return map
     * @author 王冬冬  
     * @date 2017年5月15日 下午2:47:42
     */
    @SuppressWarnings("rawtypes")
    @SelectProvider(type=ToeUserSql.class, method="getIdAndUserNameByUsernames")
    List<Map> getIdAndUserNameByUsernames(@Param("userNames") String[] userNames);
}