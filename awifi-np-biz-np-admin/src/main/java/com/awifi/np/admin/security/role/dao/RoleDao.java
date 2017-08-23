package com.awifi.np.admin.security.role.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.security.role.dao.sql.RoleSql;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午7:24:04
 * 创建作者：周颖
 * 文件名称：RoleDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("roleDao")
public interface RoleDao {

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return 角色
     * @author 周颖  
     * @date 2017年1月11日 下午7:26:38
     */
    @Select("select role_id from np_biz_user_role where user_id=#{userId}")
    List<Long> getIdsById(Long userId);
    
    /**
     * 获取角色名称
     * @param roleIds 角色ids
     * @return 角色名称
     * @author 周颖  
     * @date 2017年2月23日 下午2:48:09
     */
    @SelectProvider(type=RoleSql.class,method="getNamesByIds")
    List<String> getNamesByIds(@Param("roleIds")Long[] roleIds);

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return 用户角色
     * @author 周颖  
     * @date 2017年5月5日 下午4:07:15
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "roleName", column = "role_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select r.id,r.role_name from np_biz_role r inner join np_biz_user_role ur on r.id=ur.role_id where ur.user_id=#{userId}")
    List<Map<String, Object>> getNamesByUserId(@Param("userId")Long userId);

    /**
     * 维护账号角色
     * @param userId 账号id
     * @param roleId 角色id
     * @author 周颖  
     * @date 2017年5月8日 下午2:18:47
     */
    @Insert("insert into np_biz_user_role(user_id,role_id) values(#{userId},#{roleId})")
    void addUserRole(@Param("userId")Long userId,@Param("roleId") Long roleId);

    /**
     * 删除账号旧角色关系
     * @param userId 账号id
     * @author 周颖  
     * @date 2017年5月8日 下午2:44:32
     */
    @Delete("delete from np_biz_user_role where user_id=#{userId}")
    void deleteUserRole(@Param("userId")Long userId);

    /**
     * 全部角色
     * @author 周颖  
     * @return 角色
     * @date 2017年5月8日 下午6:21:00
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "roleName", column = "role_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select id,role_name from np_biz_role where id!=1")
    List<Map<String, Object>> getAllRole();

    /**
     * 角色列表
     * @param roleIds 角色ids
     * @return 角色列表
     * @author 周颖  
     * @date 2017年5月8日 下午6:29:02
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "roleName", column = "role_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=RoleSql.class,method="getIdsAndNamesByRoleIds")
    List<Map<String, Object>> getIdsAndNamesByRoleIds(@Param("roleIds")Long[] roleIds);
}