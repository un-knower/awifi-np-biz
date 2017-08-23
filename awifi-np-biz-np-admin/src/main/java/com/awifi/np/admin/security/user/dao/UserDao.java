package com.awifi.np.admin.security.user.dao;

import java.util.List;

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

import com.awifi.np.admin.security.user.dao.sql.UserSql;
import com.awifi.np.admin.security.user.model.User;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:53:44
 * 创建作者：周颖
 * 文件名称：UserDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("userDao")
public interface UserDao {

    /**
     * 根据用户名和密码查找用户信息
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     * @author 周颖  
     * @date 2017年1月11日 下午7:04:30
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "projectIds", column = "project_ids", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "filterProjectIds", column = "filter_project_ids", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantIds", column = "merchant_ids", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "suitCode", column = "suit_code", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select id,user_name,province_id,city_id,area_id,project_ids,filter_project_ids,merchant_ids,merchant_id,suit_code from np_biz_user "
            + "where user_name=#{userName} and password=#{password} and status=1")
    User getByNameAndPwd(@Param("userName")String userName,@Param("password")String password);
    
    /**
     * 用户详情
     * @param id 主键
     * @return 用户详情
     * @author 周颖  
     * @date 2017年2月23日 下午3:38:37
     */
    @Results(value = {
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "realname", column = "realname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "nickname", column = "nickname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactPerson", column = "contact_person", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contact_way", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select user_name,password,province_id,city_id,area_id,realname,nickname,email,contact_person,contact_way,remark from np_biz_user where id=#{id} and status=1")
    User getById(@Param("id")Long id);

    /**
     * 更新用户信息
     * @param user 用户
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午7:01:18
     */
    @Update("update np_biz_user set realname=#{realname},nickname=#{nickname},email=#{email},contact_person=#{contactPerson},contact_way=#{contactWay},remark=#{remark} where id=#{id}")
    int updateById(User user);
    
    /**
     * 更新密码
     * @param id 用户id
     * @param password 密码
     * @return 记录数
     * @author 许小满  
     * @date 2017年2月23日 下午3:01:41
     */
    @Update("update np_biz_user set password=#{password} where id=#{id}")
    int updatePwdById(@Param("id")Long id, @Param("password")String password);

    /**
     * 管理员账号列表总数
     * @param roleId 角色id
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param userName 账号关键字
     * @return 总数
     * @author 周颖  
     * @date 2017年5月5日 上午10:38:36
     */
    @SelectProvider(type=UserSql.class,method="getCountByParams")
    int getCountByParams(@Param("roleId")Long roleId,@Param("provinceId") Long provinceId,
            @Param("cityId") Long cityId,@Param("areaId") Long areaId,@Param("userName") String userName);

    /**
     * 管理员账号列表
     * @param roleId 角色id
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param userName 账号关键字
     * @param begin 开始条数
     * @param pageSize 一页条数
     * @return 列表
     * @author 周颖  
     * @date 2017年5月5日 下午3:41:21
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "deptName", column = "dept_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactPerson", column = "contact_person", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contact_way", javaType = String.class, jdbcType = JdbcType.VARCHAR)  
            })
    @SelectProvider(type=UserSql.class,method="getListByParams")
    List<User> getListByParams(@Param("roleId")Long roleId,@Param("provinceId") Long provinceId,@Param("cityId") Long cityId,
            @Param("areaId") Long areaId,@Param("userName") String userName,@Param("begin") Integer begin,@Param("pageSize") Integer pageSize);

    /**
     * 获取用户名数量
     * @param userName 用户名
     * @return 总数
     * @author 周颖  
     * @date 2017年5月8日 下午1:56:32
     */
    @Select("select count(id) from np_biz_user where user_name=#{userName} and status!=9")
    int getNumByUserName(@Param("userName")String userName);

    /**
     * 保存管理员账号
     * @param user 账号
     * @author 周颖  
     * @date 2017年5月8日 下午2:12:01
     */
    @SelectKey(before=false,keyProperty="id",resultType=Long.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    @Insert("insert into np_biz_user(user_name,password,province_id,city_id,area_id,contact_person,contact_way,dept_name,project_ids,filter_project_ids,merchant_ids,remark) "
            + "values(#{userName},#{password},#{provinceId},#{cityId},#{areaId},#{contactPerson},#{contactWay},#{deptName},#{projectIds},#{filterProjectIds},#{merchantIds},#{remark})")
    void add(User user);

    /**
     * 更新管理员账号
     * @param user 账号
     * @author 周颖  
     * @date 2017年5月8日 下午2:40:48
     */
    @Update(" update np_biz_user set province_id=#{provinceId},city_id=#{cityId},area_id=#{areaId},contact_person=#{contactPerson},contact_way=#{contactWay},"
            + "dept_name=#{deptName},project_ids=#{projectIds},filter_project_ids=#{filterProjectIds},merchant_ids=#{merchantIds},remark=#{remark} where id=#{id}")
    void update(User user);

    /**
     * 管理员详情
     * @param id 主键id
     * @return 详情
     * @author 周颖  
     * @date 2017年5月8日 下午3:30:58
     */
    @Results(value = {
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "areaId", column = "area_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "contactPerson", column = "contact_person", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contact_way", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "deptName", column = "dept_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "projectIds", column = "project_ids", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "filterProjectIds", column = "filter_project_ids", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantIds", column = "merchant_ids", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select user_name,province_id,city_id,area_id,contact_person,contact_way,dept_name,project_ids,filter_project_ids,merchant_ids,remark "
            + "from np_biz_user where id=#{id}")
    User getUserById(@Param("id")Long id);

    /**
     * 逻辑删除管理员
     * @param id 主键id
     * @author 周颖  
     * @date 2017年5月8日 下午7:17:04
     */
    @Update("update np_biz_user set status=9 where id=#{id}")
    void delete(@Param("id")Long id);

    /**
     * 重置密码
     * @param id 主键id
     * @param password 密码
     * @author 周颖  
     * @date 2017年5月8日 下午7:22:28
     */
    @Update("update np_biz_user set password=#{password} where id=#{id}")
    void resetPassword(@Param("id")Long id,@Param("password") String password);

    /**
     * 修改用户默认套码
     * @param id 用户id
     * @param suitCode 套码
     * @author 周颖  
     * @date 2017年5月9日 下午3:06:28
     */
    @Update("update np_biz_user set suit_code=#{suitCode} where id=#{id}")
    void updateSuitById(@Param("id")Long id,@Param("suitCode") String suitCode);
}