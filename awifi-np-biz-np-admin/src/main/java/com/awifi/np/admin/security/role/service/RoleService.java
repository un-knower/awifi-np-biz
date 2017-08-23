package com.awifi.np.admin.security.role.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:58:26
 * 创建作者：周颖
 * 文件名称：RoleService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface RoleService {

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return 角色
     * @author 周颖  
     * @date 2017年1月11日 下午7:22:37
     */
    List<Long> getIdsById(Long userId);

    /**
     * 获取角色名称
     * @param roleIds 角色ids
     * @return 角色名称
     * @author 周颖  
     * @date 2017年2月23日 下午2:37:11
     */
    String getNamesByIds(String roleIds);
    
    /**
     * 获取用户角色名称
     * @param userId 用户id
     * @return 角色名称
     * @author 周颖  
     * @date 2017年5月5日 下午4:03:56
     */
    Map<String,String> getIdsAndNamesByUserId(Long userId);
    
/**
     * 角色权限
     * @param roleId 角色id
     * @return 权限
     * @throws Exception 异常
     * @date 2017年5月5日 下午4:39:02
     */
    @SuppressWarnings("rawtypes")
    List getPermisionByRoleId(Long roleId) throws Exception;

    /**
     * 维护账号角色关系
     * @param userId 账号id
     * @param roleIds 角色ids
     * @author 周颖  
     * @date 2017年5月8日 下午2:17:21
     */
    void addUserRole(Long userId, String roleIds);

    /**
     * 删除账号角色
     * @param userId 账号id
     * @author 周颖  
     * @date 2017年5月8日 下午2:43:31
     */
    void deleteUserRole(Long userId);

    /**
     * 全部角色
     * @return 结果
     * @author 周颖  
     * @date 2017年5月8日 下午6:19:47
     */
    List<Map<String, Object>> getAllRole();

    /**
     * 获取角色列表
     * @param roleIds 角色ids
     * @return 结果
     * @author 周颖  
     * @date 2017年5月8日 下午6:21:52
     */
    List<Map<String, Object>> getIdsAndNamesByRoleIds(String roleIds);
}