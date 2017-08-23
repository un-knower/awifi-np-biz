package com.awifi.np.biz.toe.admin.security.org.util;

import javax.servlet.http.HttpServletRequest;

import com.awifi.np.biz.common.base.constants.OrgConstants;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午2:27:02
 * 创建作者：亢燕翔
 * 文件名称：OrgUtil.java
 * 版本：  v1.0
 * 功能：  
 * 修改记录：
 */
public class OrgUtil {

    /** 角色业务层  */
    private static ToeRoleService toeRoleService;
    
    /**
     * 获取组织ID
     * @param request 请求
     * @return org
     * @author 亢燕翔  
     * @date 2017年1月17日 下午7:44:42
     */
    public static Long getCurOrgId(HttpServletRequest request){
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);//先从SessionUtil中获取SessionUser
        Long orgId = sessionUser.getOrgId();
        if(orgId != null){//如果sessionUser中存在orgId则直接返回
            return orgId;
        }
        orgId = getRoleService ().getOrgIdByBizRoleIds(sessionUser.getRoleIds()); //不存在则通过roleIds反查orgId
        if(orgId == null){
            Object[] args = {sessionUser.getUserName(),sessionUser.getId()};
            //角色id（roleIds）[{0}]下的账号[{1}]对应的组织id（orgId）不允许为空!
            throw new BizException("E2000006", MessageUtil.getMessage("E2000006", args));
        }
        sessionUser.setOrgId(orgId);//从数据库中查到之后再set到sessionUser对象中
        request.setAttribute("sessionUser", sessionUser);//放入Attribute中以供其他查询
        return orgId;
    }
    
    /**
     * 获取RoleService bean
     * @return RoleService
     * @author 亢燕翔  
     * @date 2017年1月13日 下午3:15:21
     */
    private static ToeRoleService getRoleService() {
        if(toeRoleService == null){
            toeRoleService = (ToeRoleService) BeanUtil.getBean("toeRoleService");
        }
        return toeRoleService;
    }
    
    /**
     * 判断是否是"超级管理员"
     * @param sessionUser 
     * @return boolean
     * @author 亢燕翔  
     * @date 2017年1月16日 下午2:19:09
     */
    public static boolean isSuperAdmin(SessionUser sessionUser) {
        Long id = sessionUser.getId();//获取用户ID
        return id.equals(1L);//用户id等于1
    }
    
    /**
     * 判断用户是否为"超级管理员"
     * @param userId 用户ID
     * @return boolean
     * @author 亢燕翔  
     * @date 2017年1月16日 上午11:27:06
     */
    public static boolean isSuperAdmin(Long userId){
        return userId.equals(1L);
    }
    
    /**
     * 判断组织是否为"爱WiFi运营中心"
     * @param orgId 组织id
     * @return true 是、false 否
     * @author 许小满  
     * @date 2016年9月30日 上午9:50:21
     */
    public static boolean isAwifi(Long orgId){
        if(orgId == null){
            return false;
        }
        return orgId.equals(OrgConstants.AWIFI);
    }
    
    /**
     * 判断是否是"电信"
     * @param orgId 组织ID
     * @return boolean
     * @author 亢燕翔  
     * @date 2017年1月16日 上午11:32:21
     */
    public static boolean isTelecom(Long orgId){
        return orgId.equals(OrgConstants.TELECOM);
    }
    
    /**
     * 判断是否是"项目"
     * @param orgId 组织ID
     * @return boolean
     * @author 亢燕翔  
     * @date 2017年1月16日 上午11:33:06
     */
    public static boolean isProject(Long orgId){
        return orgId.equals(OrgConstants.PROJECT);
    }
    
    /**
     * 判断组织是否为"其它"
     * @param orgId 组织id
     * @return true 是、false 否
     * @author 许小满  
     * @date 2017年2月3日 下午4:08:34
     */
    public static boolean isOther(Long orgId){
        boolean isAwifi = isAwifi(orgId);
        boolean isTelecom = isTelecom(orgId);
        boolean isProject = isProject(orgId);
        boolean isOther = !(isAwifi || isTelecom || isProject);
        return isOther;
    }

}
