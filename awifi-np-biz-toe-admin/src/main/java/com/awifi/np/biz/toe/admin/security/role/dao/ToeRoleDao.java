package com.awifi.np.biz.toe.admin.security.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.admin.security.role.model.ToeRole;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午3:23:49
 * 创建作者：亢燕翔
 * 文件名称：RoleDao.java
 * 版本：  v1.0
 * 功能：  角色持久层
 * 修改记录：
 */
@Service("toeRoleDao")
public interface ToeRoleDao {
    
    /**
     * 通过角色ID获取组织ID
     * @param roleIds 角色ID
     * @return 组织ID
     * @author 亢燕翔  
     * @date 2017年1月17日 下午7:58:51
     */
    @Select("select org_id from toe_biz_role_match where biz_role_id in (#{roleIds}) order by org_id limit 1")
    String getOrgIdByBizRoleIds(String roleIds);

    /**
     * 维护账号角色关系
     * @param userId 账号id
     * @param roleId 角色id
     * @author 周颖  
     * @date 2017年2月4日 下午2:21:38
     */
    @Insert("insert into toe_user_role(user_id,role_id) values(#{userId},#{roleId})")
    void addUserRole(@Param("userId")Long userId,@Param("roleId") Long roleId);

    /**
     * 通过账号id获取账号的角色信息
     * @param userId 账号id
     * @return 角色信息
     * @author 周颖  
     * @date 2017年2月6日 上午9:42:49
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "roleName", column = "alias_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select r.pk_id,r.alias_name from toe_role r inner join toe_user_role ur on r.pk_id=ur.role_id where ur.user_id=#{userId}")
    List<ToeRole> getNamesByUserId(@Param("userId")Long userId);

    /***
     * 获取组织下的所有角色
     * @param orgId 组织id
     * @return 角色列表
     * @author 周颖  
     * @date 2017年2月6日 下午2:33:28
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "roleName", column = "alias_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,alias_name from toe_role where fk_org_id=#{orgId} and pk_id!=1")
    List<ToeRole> getListByOrgId(@Param("orgId")Long orgId);

    /**
     * 获取账号的角色列表
     * @param userId 账号id
     * @param orgId 组织id
     * @return 角色列表
     * @author 周颖  
     * @date 2017年2月6日 下午2:39:19
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "roleName", column = "alias_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select r.pk_id,r.alias_name from toe_role r inner join toe_user_role ur on r.pk_id=ur.role_id where ur.user_id=#{userId} and r.fk_org_id=#{orgId}")
    List<ToeRole> getListByUserAndOrgId(@Param("userId")Long userId,@Param("orgId") Long orgId);

    /**
     * 删除账号角色关系
     * @param userId 账号id
     * @author 周颖  
     * @date 2017年2月7日 下午2:22:41
     */
    @Delete("delete from toe_user_role where user_id=#{userId}")
    void delete(@Param("userId")Long userId);

    /**
     * 获取np角色列表
     * @param id 用户id
     * @return 角色列表
     * @author 周颖  
     * @date 2017年4月5日 上午9:19:16
     */
    @Select("select brm.biz_role_id from toe_biz_role_match brm inner join toe_user_role ur on brm.role_id=ur.role_id where ur.user_id=#{id}")
    List<Long> getIdsById(@Param("id")Long id);

    /**
     * 通过角色名称获取角色id（只针对商户）
     * @param roleName 角色名称
     * @return 角色id
     * @author 周颖  
     * @date 2017年4月7日 下午4:20:09
     */
    @Select("select pk_id from toe_role where fk_org_id=3 and alias_name=#{roleName}")
    Long getIdByName(@Param("roleName")String roleName);

    /**
     * 通过父商户id+角色名称获取角色id（只针对商户）
     * @param parentId 父商户id
     * @param roleName 角色名称
     * @return 角色id
     * @author 周颖  
     * @date 2017年4月10日 下午1:35:47
     */
    @Select("select r.pk_id from toe_user u inner join toe_user_customer uc on u.pk_id = uc.user_id inner join toe_user_role ur on u.pk_id = ur.user_id "
            + "inner join toe_role r on ur.role_id = r.pk_id where r.alias_name=#{roleName} and uc.customer_id=#{customerId}")
    Long getIdByNameAndMerchantId(@Param("customerId")Long parentId,@Param("roleName")String roleName);
}