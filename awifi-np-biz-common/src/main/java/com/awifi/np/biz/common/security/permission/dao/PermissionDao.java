package com.awifi.np.biz.common.security.permission.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.security.permission.dao.sql.PermissionSql;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午8:49:31
 * 创建作者：许小满
 * 文件名称：PermissionDao.java
 * 版本：  v1.0
 * 功能：权限--模型层接口
 * 修改记录：
 */
@Service("permissionDao")
public interface PermissionDao {

    /**
     * 通过角色和编号获取记录数量
     * @param roleIds 角色ids
     * @param code 权限编号
     * @param serviceCode 服务代码[外键]
     * @return 记录数量
     * @author 许小满  
     * @date 2017年2月9日 上午9:03:29
     */
    @SelectProvider(type=PermissionSql.class, method="getNumByRoleAndCode")
    int getNumByRoleAndCode(@Param("roleIds") Long[] roleIds,@Param("code") String code, @Param("serviceCode")String serviceCode);

    /**
     * 通过服务代码[外键]、角色id获取权限编号集合
     * @param serviceCode 服务代码[外键]
     * @param roleId 角色id
     * @return 权限编号集合
     * @author 许小满  
     * @date 2017年2月13日 下午4:54:45
     */
    @Select("select p.code from np_biz_permission p inner join np_biz_role_permission rp on p.id=rp.permission_id where p.service_code=#{serviceCode} and rp.role_id=#{roleId} order by order_no")
    List<String> getCodesByRoleId(@Param("serviceCode")String serviceCode, @Param("roleId")Long roleId);
    
    /**
     * 通过角色id删除角色权限关系表的数据
     * @param roleId 角色id
     * @param serviceCode 服务编号
     * @return 更新的记录数
     * @author 许小满  
     * @date 2017年2月15日 下午4:14:09
     */
    @Delete("delete rp.* from np_biz_role_permission rp inner join np_biz_permission p on rp.permission_id=p.id where p.service_code=#{serviceCode} and rp.role_id=#{roleId}")
    int deleteRolePermissionByRoleId(@Param("serviceCode")String serviceCode, @Param("roleId")Long roleId);
    
    /**
     * 通过编号获取主键id
     * @param serviceCode 服务编号
     * @param code 权限（接口）编号
     * @return id
     * @author 许小满  
     * @date 2017年2月15日 下午4:25:03
     */
    @Select("select id from np_biz_permission where service_code=#{serviceCode} and code=#{code} limit 1")
    Long getIdByCode(@Param("serviceCode")String serviceCode, @Param("code")String code);
    
    /**
     * 角色-权限关系表  批量更新
     * @param roleId 角色id
     * @param permissionIds 权限表主键id数组
     * @return 新增的记录数
     * @author 许小满  
     * @date 2017年2月15日 下午4:31:02
     */
    @InsertProvider(type=PermissionSql.class, method="batchAddRolePermission")
    int batchAddRolePermission(@Param("roleId")Long roleId, @Param("permissionIds")Long[] permissionIds);

    /**
     * 通过服务编号获取接口信息
     * @param serviceCode 服务编号
     * @return list
     * @author 亢燕翔  
     * @date 2017年2月20日 上午10:19:13
     */
    @Select("select code,name from awifi_np_biz.np_biz_permission where service_code = #{serviceCode} order by order_no")
    List<Map<String, Object>> getListByServiceCode(@Param("serviceCode")String serviceCode);

    /**
     * 获取角色所有权限
     * @param roleIds 角色ids
     * @return 权限
     * @author 周颖  
     * @date 2017年5月9日 上午10:19:08
     */
    @Results(value = {
            @Result(property = "id", column = "_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "code", column = "code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=PermissionSql.class,method="getPermissionsByRoleIds")
    List<Map<String, Object>> getPermissionsByRoleIds(@Param("roleIds")Long[] roleIds);

    /**
     * 查看app是否有调用接口的权限
     * @param appId 第三方应用表主键id
     * @param code 接口编号
     * @return 总数
     * @author 周颖  
     * @date 2017年7月19日 下午4:23:31
     */
    @Select("select count(p.id) from np_biz_permission p inner join np_biz_app_permission ap on p.id=ap.permission_id where ap.app_id=#{appId} and p.code=#{code}")
    int getNumByAppIdAndCode(@Param("appId")Long appId,@Param("code") String code);
}
