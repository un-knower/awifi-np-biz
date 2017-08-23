package com.awifi.np.biz.projsrv.menu.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.menu.service.MenuService;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月7日 上午10:04:48
 * 创建作者：亢燕翔
 * 文件名称：MenuController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/projsrv")
public class MenuController extends BaseController{

    /**菜单服务*/
    @Resource(name = "menuService")
    private MenuService menuService;
    
    /**
     * 菜单接口
     * @param request 请求
     * @param access_token 安全令牌
     * @return json
     * @author 亢燕翔  
     * @date 2017年2月7日 上午10:10:10
     */
    @ResponseBody
    @RequestMapping(value="/menus", method=RequestMethod.GET)
    public Map getListByParam(HttpServletRequest request,@RequestParam(value="access_token",required=true)String access_token){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_project");//从配置表读取服务代码
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        return this.successMsg(menuService.getListByParam(sessionUser,serviceCode));
    }
    
    /**
     * 获取所有菜单接口--管理系统
     * @return json
     * @author 许小满  
     * @date 2017年2月16日 下午2:49:32
     */
    @ResponseBody
    @RequestMapping(value="/am/menus", method=RequestMethod.GET)
    public Map<String,Object> getListByServiceCodeForAm(){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_project");//从配置表读取服务代码
        return this.successMsg(menuService.getListByParam(serviceCode));
    }
    
    /**
     * 获取某一角色关联的菜单接口--管理系统
     * @param roleId 角色id，不允许为空，数字
     * @return json
     * @author 许小满  
     * @date 2017年2月16日 下午2:49:32
     */
    @ResponseBody
    @RequestMapping(value="/am/role/{roleid}/menus", method=RequestMethod.GET)
    public Map<String,Object> getListByRoleIdForAm(
            @PathVariable(value="roleid",required=true)String roleId//角色id，不允许为空，数字
    ){
      //1.参数校验
        ValidUtil.valid("角色id[roleid]", roleId, "{'required':true,'numeric':true}");//角色id，不允许为空，数字
        Long roleIdLong = Long.parseLong(roleId);//角色id 转为 long类型
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_project");//从配置表读取服务代码
        return this.successMsg(menuService.getListByParam(serviceCode, roleIdLong));
    }
    
    /**
     * 菜单批量更新接口 -- 管理系统
     * @param roleId 角色id
     * @param menuIds 菜单ids
     * @return json
     * @author 许小满  
     * @date 2017年2月16日 下午3:09:36
     */
    @ResponseBody
    @RequestMapping(value="/am/role/{roleid}/menus", method=RequestMethod.POST)
    public Map<String,Object> batchAddRoleMenuForAm(
            @PathVariable(value="roleid", required=true)String roleId,//角色id，不允许为空，数字
            @RequestBody(required=true) Long[] menuIds//菜单id数组
    ){
        //1.参数校验
        ValidUtil.valid("角色id[roleid]", roleId, "{'required':true,'numeric':true}");//角色id，不允许为空，数字
        ValidUtil.valid("菜单id数组[menuIds]", menuIds, "{'arrayNotBlank':true}");//菜单id数组 校验
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_project");//服务编号
        menuService.batchAddRoleMenu(serviceCode, Long.parseLong(roleId), menuIds);
        return this.successMsg();//返回成功信息
    }
    
}
