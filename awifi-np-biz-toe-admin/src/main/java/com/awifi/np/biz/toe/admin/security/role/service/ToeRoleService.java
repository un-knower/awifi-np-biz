package com.awifi.np.biz.toe.admin.security.role.service;

import java.util.List;

import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.toe.admin.security.role.model.ToeRole;



/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午2:26:30
 * 创建作者：亢燕翔
 * 文件名称：RoleService.java
 * 版本：  v1.0
 * 功能：  角色业务层
 * 修改记录：
 */
public interface ToeRoleService {

    /**
     * 通过角色ID获取组织ID
     * @return 组织ID
     * @author 亢燕翔  
     * @param roleIds 
     * @date 2017年1月13日 下午2:51:30
     */
    Long getOrgIdByBizRoleIds(String roleIds);

    /**
     * 维护账号角色关系
     * @param userId 账号id
     * @param roleIds 角色ids 以逗号拼接
     * @author 周颖  
     * @date 2017年2月4日 下午2:18:00
     */
    void addUserRole(Long userId, String roleIds);  
    
    /**
     * 通过用户id获取角色信息
     * @param userId 用户id
     * @return 角色信息
     * @author 周颖  
     * @date 2017年2月6日 上午9:38:36
     */
    List<ToeRole> getNamesByUserId(Long userId);

    /**
     * 通过登录账号和组织id获取角色列表
     * @param curOrgId 当前登陆账号id
     * @param user 当前登陆账号
     * @param orgId 组织id
     * @return 角色列表
     * @author 周颖  
     * @date 2017年2月6日 下午2:24:31
     */
    List<ToeRole> getListByOrgId(Long curOrgId, SessionUser user, Long orgId);

    /**
     * 更新账号的角色
     * @param userId 账号id
     * @param roleIds 角色
     * @author 周颖  
     * @date 2017年2月7日 下午2:18:40
     */
    void updateUserRole(Long userId, String roleIds);

    /**
     * 获取np角色列表
     * @param id 用户id
     * @return 角色列表
     * @author 周颖  
     * @date 2017年4月5日 上午9:20:52
     */
    List<Long> getIdsById(Long id);

    /**
     * 通过角色名称获取角色id
     * @param roleName 角色名称
     * @return 角色id
     * @author 周颖  
     * @date 2017年4月7日 下午4:17:53
     */
    Long getIdByName(String roleName);
    
    /**
     * 通过角色名称获取角色id
     * @param parentId 父商户id
     * @param roleName 角色名称
     * @return 角色id
     * @author 周颖  
     * @date 2017年4月7日 下午4:17:53
     */
    Long getIdByName(Long parentId,String roleName);
}