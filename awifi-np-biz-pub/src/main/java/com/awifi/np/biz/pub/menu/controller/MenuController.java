package com.awifi.np.biz.pub.menu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.admin.service.service.ServiceService;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.menu.model.Menu;
import com.awifi.np.biz.common.menu.service.MenuService;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午3:50:25
 * 创建作者：周颖
 * 文件名称：MenuController.java
 * 版本：  v1.0
 * 功能：菜单控制层
 * 修改记录：
 */
@Controller
@RequestMapping("/pubsrv")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuController extends BaseController {
    
    /**菜单服务*/
    @Resource(name = "menuService")
    private MenuService menuService;
    
    /**服务业务层*/
    @Resource(name="serviceService")
    private ServiceService serviceService;

    /**
     * 获取一级菜单
     * @param accessToken access_token
     * @return 一级菜单
     * @author 周颖  
     * @date 2017年1月12日 下午8:30:24
     */
    @RequestMapping(method=RequestMethod.GET, value="/1st/menus")
    @ResponseBody
    public Map getTopMenus(@RequestParam(value="access_token",required=true)String accessToken){
        String valueString = RedisAdminUtil.get(accessToken);//从redis获取access_token的值
        if(StringUtils.isBlank(valueString)){
            throw new BizException("E0000001", MessageUtil.getMessage("E0000001"));//access_token失效
        }
        Map<String,Object> value = (Map<String, Object>) JsonUtil.fromJson(valueString, HashMap.class);//转map
        Map<String,Object> userInfo = (Map<String, Object>) value.get("userInfo");//获取redis的用户信息
        String roleIds = (String)userInfo.get("roleIds");//获取登陆用户的角色信息
        String appId = (String) value.get("appId");//获取平台id
        List<Map<String,Object>> menusList = serviceService.getTopMenus(appId, CastUtil.toLongArray(roleIds.split(",")));//获取一级菜单列表
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        Map<String,Object> dataMap = null;
        for(Map<String,Object> map : menusList){//循环返回数据
            dataMap = new HashMap<String,Object>();
            dataMap.put("menuName", (String) map.get("menu_name"));//保存菜单名称
            dataMap.put("menuUrl", (String) map.get("menu_url"));//保存菜单url
            dataMap.put("targetId", (String) map.get("target_id"));//保存显示区域 吧
            dataMap.put("hasSubMenu",(boolean) map.get("has_submenu") ? 1 : 0);//保存是否有下级菜单
            data.add(dataMap);//保存map
        }
        return this.successMsg(data);//返回信息
    }
    
    /**
     * 菜单接口
     * @param accessToken access_token
     * @param request 请求
     * @return 服务菜单
     * @author 周颖  
     * @date 2017年2月8日 下午3:49:12
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="/menus")
    public Map getListByParam(@RequestParam(value="access_token",required=true)String accessToken,HttpServletRequest request){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_pub");//配置表获取服务编号
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        List<Menu> menuList = menuService.getListByParam(sessionUser, serviceCode);
        return this.successMsg(menuList);
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
        String serviceCode = SysConfigUtil.getParamValue("servicecode_pub");//从配置表读取服务代码
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
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_pub");//从配置表读取服务代码
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
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_pub");//服务编号
        menuService.batchAddRoleMenu(serviceCode, Long.parseLong(roleId), menuIds);
        return this.successMsg();//返回成功信息
    }
}