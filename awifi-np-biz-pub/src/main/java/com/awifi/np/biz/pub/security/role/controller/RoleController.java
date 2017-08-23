/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 下午5:19:25
* 创建作者：周颖
* 文件名称：RoleController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.security.role.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.admin.security.role.service.RoleService;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@Controller
public class RoleController extends BaseController {
    
    /**角色服务层*/
    @Resource(name = "roleService")
    private RoleService roleService;
    
    /**
     * 根据access_token获取角色列表
     * @param accessToken access_token
     * @return 结果
     * @author 周颖  
     * @date 2017年5月8日 下午6:29:34
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.GET, value="/pubsrv/roles")
    @ResponseBody
    public Map<String,Object> getRolesByAccessToken(@RequestParam(value="access_token",required=true)String accessToken){
        String valueString = RedisAdminUtil.get(accessToken);//从redis获取access_token的值
        if(StringUtils.isBlank(valueString)){
            throw new BizException("E0000001", MessageUtil.getMessage("E0000001"));//access_token失效
        }
        Map<String,Object> value = (Map<String, Object>) JsonUtil.fromJson(valueString, HashMap.class);//转map
        Map<String,Object> userInfo = (Map<String, Object>) value.get("userInfo");//获取redis的用户信息
        String roleIds = (String) userInfo.get("roleIds");
        if(StringUtils.isBlank(roleIds)){
            throw new BizException("E2000011", MessageUtil.getMessage("E2000011",roleIds));//用户信息中的角色id[roleIds]不允许为空!
        }
        Long userId = CastUtil.toLong(userInfo.get("id"));
        List<Map<String,Object>> roleList = new ArrayList<Map<String,Object>>();
        if(userId.equals(1L)){
            roleList = roleService.getAllRole();
        }else{
            roleList = roleService.getIdsAndNamesByRoleIds(roleIds);
        }
        return this.successMsg(roleList);
    }
}
