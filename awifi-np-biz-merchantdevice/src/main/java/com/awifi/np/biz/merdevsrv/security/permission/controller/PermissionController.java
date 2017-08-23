package com.awifi.np.biz.merdevsrv.security.permission.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.permission.service.PermissionService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午8:40:41
 * 创建作者：许小满
 * 文件名称：PermissionController.java
 * 版本：  v1.0
 * 功能：权限--控制层
 * 修改记录：
 */
@Controller
@RequestMapping("/merdevsrv")
public class PermissionController extends BaseController {
    
    /** 权限--业务层 */
    @Resource(name="permissionService")
    private PermissionService permissionService;
    
    /**
     * 权限校验接口
     * @param access_token 安全令牌
     * @param params 请求参数
     * @return json
     * @author 许小满  
     * @date 2017年2月9日 上午8:45:35
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/permission/check", method=RequestMethod.GET)
    public Map<String,Object> check(
            @RequestParam(value="access_token",required=true)String access_token,//安全令牌，不允许为空
            @RequestParam(value="params",required=true)String params//请求参数
    ){
        logger.debug("----------提示：开始执行权限接口  PermissionController.check()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);//参数从字符串转json
        /* 参数校验 */
        String code = (String)paramMap.get("interfaceCode");//接口编号
        ValidUtil.valid("权限（接口）编号[interfaceCode]", code, "required");//接口编号必填校验
        Map<String,Object> userInfoMap = (Map<String,Object>)paramMap.get("userInfo");//用户信息
        ValidUtil.valid("用户信息[userInfo]", userInfoMap, "required");//用户信息必填校验
        String roleIds = (String)userInfoMap.get("roleIds");//角色ids
        ValidUtil.valid("角色ids[roleIds]", roleIds, "required");//角色ids必填校验
        
        String[] roleIdStrArray = roleIds.split(",");//将roleIds转换成字符串数组
        ValidUtil.valid("角色ids[roleIds]", roleIdStrArray, "arrayNotBlank");//数组内部不允许存在空值(null|"")的校验
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_merchantdevice");//服务代码[外键]
        //从redis中获取
        String newRoleIds = getRoleIds(roleIdStrArray);
        String key = RedisConstants.PERMISSION + serviceCode + "_" + newRoleIds + "_" + code;
        if(!RedisUtil.exist(key)){//如果存在
            Long[] roleIdLongArray = CastUtil.toLongArray(roleIdStrArray);//字符串数组转Long数组
            boolean isPass = permissionService.check(roleIdLongArray, code, serviceCode);//权限校验
            if(!isPass){
                throw new BizException("E2000041", MessageUtil.getMessage("E2000041", new Object[]{roleIds, code}));//角色ids[{0}]无权限调用接口[{1}]!
            }
            //redis缓存
            RedisUtil.set(key, "1", RedisConstants.PERMISSION_TIME);
        }
        logger.debug("----------提示：权限接口执行成 功 PermissionController.check(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
        return this.successMsg();//返回成功信息
    }
    
    /**
     * 角色id转换 {1}{2}
     * @param roleIdStrArray 角色数组
     * @return 角色ids
     * @author 周颖  
     * @date 2017年6月26日 上午11:06:26
     */
    private String getRoleIds(String[] roleIdStrArray ){
        int maxLength = roleIdStrArray.length;
        StringBuilder roleIds = new StringBuilder();
        for(int i=0; i<maxLength; i++){
            roleIds.append("{").append(roleIdStrArray[i]).append("}");
        }
        return roleIds.toString();
    }

    /**
     * 获取某一角色关联的权限接口 --管理系统
     * @param roleId 角色id，不允许为空，数字
     * @return json
     * @author 许小满  
     * @date 2017年2月13日 下午5:01:31
     */
    @ResponseBody
    @RequestMapping(value="/am/role/{roleid}/permissions", method=RequestMethod.GET)
    public Map<String,Object> getCodesByRoleIdForAm(
            @PathVariable(value="roleid",required=true)String roleId//角色id，不允许为空，数字
    ){
        //1.参数校验
        ValidUtil.valid("角色id[roleid]", roleId, "{'required':true,'numeric':true}");//角色id，不允许为空，数字
        Long roleIdLong = Long.parseLong(roleId);//角色id 转为 long类型
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_merchantdevice");//服务编号
        List<String> codes = permissionService.getCodesByRoleId(serviceCode, roleIdLong);
        return this.successMsg(codes);//返回成功信息
    }
    
    /**
     * 权限批量更新接口 --管理系统
     * @param roleId 角色id，不允许为空，数字
     * @param codes 接口编号数组
     * @return json
     * @author 许小满  
     * @date 2017年2月15日 下午3:59:18
     */
    @ResponseBody
    @RequestMapping(value="/am/role/{roleid}/permissions", method=RequestMethod.POST)
    public Map<String,Object> batchAddRolePermissionForAm(
            @PathVariable(value="roleid", required=true)String roleId,//角色id，不允许为空，数字
            @RequestBody(required=true) String[] codes//接口编号数组
    ){
        //1.参数校验
        ValidUtil.valid("角色id[roleid]", roleId, "{'required':true,'numeric':true}");//角色id，不允许为空，数字
        ValidUtil.valid("接口编号数组[codes]", codes, "{'arrayNotBlank':true}");//接口编号数组 校验
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_merchantdevice");//服务编号
        permissionService.batchAddRolePermission(serviceCode, Long.parseLong(roleId), codes);
        return this.successMsg();//返回成功信息
    }
    
    /**
     * 推送接口注册信息
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月20日 上午10:15:08
     */
    @ResponseBody
    @RequestMapping(value="/privateapi/interfaces/push", method=RequestMethod.POST)
    public Map<String,Object> pushForAm() throws Exception{
        String serviceCode = SysConfigUtil.getParamValue("servicecode_merchantdevice");//从配置表读取服务代码
        String serviceKey = SysConfigUtil.getParamValue("servicekey_merchantdevice");//从配置表读取服务密钥
        permissionService.pushInterfaces(serviceCode, serviceKey);//获取接口信息并推送至admin
        return this.successMsg();//返回成功信息
    }
}
