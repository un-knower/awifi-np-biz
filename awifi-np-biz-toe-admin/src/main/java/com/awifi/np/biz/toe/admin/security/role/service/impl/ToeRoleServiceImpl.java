package com.awifi.np.biz.toe.admin.security.role.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.toe.admin.security.org.util.OrgUtil;
import com.awifi.np.biz.toe.admin.security.role.dao.ToeRoleDao;
import com.awifi.np.biz.toe.admin.security.role.model.ToeRole;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午2:26:44
 * 创建作者：亢燕翔
 * 文件名称：RoleServiceImpl.java
 * 版本：  v1.0
 * 功能：  角色业务层实现类
 * 修改记录：
 */
@Service("toeRoleService")
public class ToeRoleServiceImpl implements ToeRoleService{

    /** 角色持久层  */
    @Resource(name = "toeRoleDao")
    private ToeRoleDao toeRoleDao;
    
    /**
     * 通过角色ID获取组织ID
     * @author 亢燕翔  
     * @date 2017年1月13日 下午4:57:39
     */
    @Override
    public Long getOrgIdByBizRoleIds(String roleIds) {
        String orgId = toeRoleDao.getOrgIdByBizRoleIds(roleIds);
        return orgId != null ? Long.parseLong(orgId) : null;
    }

    /**
     * 维护账号角色关系
     * @param userId 账号id
     * @param roleIds 角色ids 以逗号拼接
     * @author 周颖  
     * @date 2017年2月4日 下午2:18:00
     */
    public void addUserRole(Long userId, String roleIds){
        String[] roleIdsArray = roleIds.split(",");
        int maxLength = roleIdsArray.length;
        for(int i =0 ; i<maxLength ; i++){
            toeRoleDao.addUserRole(userId, Long.parseLong(roleIdsArray[i]));
        }
    } 
    
    /**
     * 通过用户id获取角色信息
     * @param userId 用户id
     * @return 角色信息
     * @author 周颖  
     * @date 2017年2月6日 上午9:38:36
     */
    public List<ToeRole> getNamesByUserId(Long userId){
        return toeRoleDao.getNamesByUserId(userId);
    }
    
    /**
     * 通过登录账号和组织id获取角色列表
     * @param curOrgId 当前登陆账号id
     * @param user 当前登陆账号
     * @param orgId 组织id
     * @return 角色列表
     * @author 周颖  
     * @date 2017年2月6日 下午2:24:31
     */
    public List<ToeRole> getListByOrgId(Long curOrgId, SessionUser user, Long orgId){
        if(OrgUtil.isAwifi(curOrgId)){//如果组织为"爱WiFi运营中心" 显示全部
            return toeRoleDao.getListByOrgId(orgId);//返回结果
        }
        if(!curOrgId.equals(orgId)){//如果登陆账号的组织id和组织参数不一致
            return toeRoleDao.getListByOrgId(orgId);//返回结果
        }else{//如果登陆账号的组织id和组织参数一致
            return toeRoleDao.getListByUserAndOrgId(user.getId(),orgId);//返回登陆账号的角色列表
        }
    }
    
    /**
     * 更新账号的角色
     * @param userId 账号id
     * @param roleIds 角色
     * @author 周颖  
     * @date 2017年2月7日 下午2:18:40
     */
    public void updateUserRole(Long userId, String roleIds){
        toeRoleDao.delete(userId);//删除原先账号角色关系
        addUserRole(userId,roleIds);//保存新的账号角色关系
    }
    
    /**
     * 获取np角色列表
     * @param id 用户id
     * @return 角色列表
     * @author 周颖  
     * @date 2017年4月5日 上午9:19:43
     */
    public List<Long> getIdsById(Long id){
        return toeRoleDao.getIdsById(id);
    }
    
    /**
     * 通过角色名称获取角色id
     * @param roleName 角色名称
     * @return 角色id
     * @author 周颖  
     * @date 2017年4月7日 下午4:17:53
     */
    public Long getIdByName(String roleName){
        return toeRoleDao.getIdByName(roleName);
    }
    
    /**
     * 通过角色名称获取角色id
     * @param parentId 父商户id
     * @param roleName 角色名称
     * @return 角色id
     * @author 周颖  
     * @date 2017年4月7日 下午4:17:53
     */
    public Long getIdByName(Long parentId,String roleName){
        return toeRoleDao.getIdByNameAndMerchantId(parentId,roleName);
    }
}