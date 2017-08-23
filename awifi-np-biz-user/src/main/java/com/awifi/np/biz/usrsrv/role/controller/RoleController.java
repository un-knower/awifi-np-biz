/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 下午7:22:56
* 创建作者：王冬冬
* 文件名称：RoleController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.usrsrv.role.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.admin.security.role.service.RoleService;
import com.awifi.np.biz.common.base.controller.BaseController;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月13日 下午4:49:44
* 创建作者：王冬冬
* 文件名称：Hello.java
* 版本：  v1.0
* 功能：角色管理控制层
* 修改记录：
*/
@Controller
public class RoleController extends BaseController{
	
	/**
	 * 
	 */
    @Autowired
    @Qualifier(value= "roleService")
	private RoleService roleService;
    
    /**
     * 通过角色id获取权限信息
     * @param accessToken 令牌
     * @param request 请求
     * @param roleId 角色id
     * @return map
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年4月12日 下午7:33:15
     */
    @SuppressWarnings("rawtypes")
	@ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="/usrsrv/role/{roleId}")
    public Map getPermisionByRoleId(@RequestParam(value="access_token",required=true)String accessToken,HttpServletRequest request,@PathVariable("roleId") Long roleId) throws Exception{
    	
        List list=roleService.getPermisionByRoleId(roleId);//查询权限列表
        return this.successMsg(list);
    }

}
