package com.awifi.np.biz.common.security.permission.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午8:47:43
 * 创建作者：许小满
 * 文件名称：PermissionService.java
 * 版本：  v1.0
 * 功能：权限--业务层接口
 * 修改记录：
 */
public interface PermissionService {

    /**
     * 权限校验
     * @param roleIds 角色ids
     * @param code 权限(接口)编号
     * @param serviceCode 服务代码
     * @return true 有效、false 无效
     * @author 许小满  
     * @date 2017年2月9日 上午9:38:41
     */
    boolean check(Long[] roleIds, String code, String serviceCode);
    
    /**
     * 通过服务代码[外键]、角色id获取权限编号集合
     * @param serviceCode 服务代码[外键]
     * @param roleId 角色id
     * @return 权限编号集合
     * @author 许小满  
     * @date 2017年2月13日 下午4:54:45
     */
    List<String> getCodesByRoleId(String serviceCode, Long roleId);
    
    /**
     * 角色-权限关系表  批量更新
     * @param serviceCode 服务编号
     * @param roleId 角色id
     * @param codes 接口编号数组
     * @author 许小满  
     * @date 2017年2月15日 下午4:20:00
     */
    void batchAddRolePermission(String serviceCode, Long roleId, String[] codes);

    /**
     * 推送接口注册信息
     * @param serviceCode 服务编号
     * @param serviceKey 服务秘钥 
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月20日 上午10:16:45
     */
    void pushInterfaces(String serviceCode, String serviceKey) throws Exception;
    
    /**
     * 获取当前登陆账号所有权限
     * @param roleIds 角色ids
     * @return 权限
     * @author 周颖  
     * @date 2017年5月9日 上午10:03:39
     */
    List<Map<String,Object>> getPermissionsByRoleIds(Long[] roleIds);

    /**
     * 权限校验
     * @param appId 第三方应用表主键id
     * @param code 接口编号
     * @return true 存在
     * @author 周颖  
     * @date 2017年7月19日 下午4:16:24
     */
    boolean check(Long appId, String code);
}
