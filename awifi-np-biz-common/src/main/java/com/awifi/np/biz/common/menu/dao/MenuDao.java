package com.awifi.np.biz.common.menu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.menu.dao.sql.MenuSql;
import com.awifi.np.biz.common.menu.model.Menu;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月7日 上午10:12:07
 * 创建作者：亢燕翔
 * 文件名称：MenuDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */

@Service("menuDao")
public interface MenuDao {
    
    /**
     * 获取菜单
     * @param serviceCode 服务编号
     * @param roleId 角色id
     * @param roleIds 角色ids
     * @param parentId 上级菜单id
     * @return list
     * @author 亢燕翔
     * @date 2017年2月7日 下午12:22:30
     */
    @Results(value = {  
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "menuName", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "menuUrl", column = "url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "targetId", column = "target_id", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
    @SelectProvider(type=MenuSql.class, method="getListByParam")
    List<Menu> getListByParam(@Param("serviceCode")String serviceCode, @Param("roleId")Long roleId,@Param("roleIds")Long[] roleIds,@Param("parentId")Long parentId);

    /**
     * 通过角色id删除角色菜单关系表的数据
     * @param roleId 角色id
     * @param serviceCode 服务编号
     * @return 更新的记录数
     * @author 许小满  
     * @date 2017年2月16日 下午3:11:08
     */
    @Delete("delete rm.* from np_biz_role_menu rm inner join np_biz_menu m on rm.menu_id=m.id where m.service_code=#{serviceCode} and rm.role_id=#{roleId}")
    int deleteRoleMenuByRoleId(@Param("serviceCode")String serviceCode, @Param("roleId")Long roleId);
    
    /**
     * 角色-菜单关系表  批量更新
     * @param roleId 角色id
     * @param menuIds 菜单表主键id数组
     * @return 新增的记录数
     * @author 许小满  
     * @date 2017年2月16日 下午3:11:08
     */
    @InsertProvider(type=MenuSql.class, method="batchAddRoleMenu")
    int batchAddRoleMenu(@Param("roleId")Long roleId, @Param("menuIds")Long[] menuIds);
}
