package com.awifi.np.biz.mersrv.security.role.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.toe.admin.security.org.util.OrgUtil;
import com.awifi.np.biz.toe.admin.security.role.model.ToeRole;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午1:54:30
 * 创建作者：周颖
 * 文件名称：RoleController.java
 * 版本：  v1.0
 * 功能：角色控制层
 * 修改记录：
 */
@Controller
public class RoleController extends BaseController {

    /**toe角色服务层*/
    @Resource(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    
    /**
     * 通过组织id获取角色列表
     * @param access_token access_token
     * @param request 请求
     * @param orgId 组织id
     * @return 角色列表
     * @author 周颖  
     * @date 2017年2月6日 下午2:43:14
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="/mersrv/org/{orgid}/roles")
    public Map getListByOrgId(@RequestParam(value="access_token",required=true) String access_token,HttpServletRequest request,@PathVariable(value="orgid") Long orgId){
        SessionUser user = SessionUtil.getCurSessionUser(request);
        Long curOrgId = OrgUtil.getCurOrgId(request);
        List<ToeRole> roleList = toeRoleService.getListByOrgId(curOrgId,user,orgId);
        return this.successMsg(roleList);
    }
}