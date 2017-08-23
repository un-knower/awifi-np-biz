package com.awifi.np.biz.appsrv.security.permission.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/appsrv")
@SuppressWarnings("unchecked")
public class PermissionController extends BaseController {
    
    /** 权限--业务层 */
    @Resource(name="permissionService")
    private PermissionService permissionService;
    
    /**
     * 权限校验接口
     * @param accessToken 安全令牌
     * @param params 请求参数
     * @return json
     * @author 许小满  
     * @date 2017年2月9日 上午8:45:35
     */
    @ResponseBody
    @RequestMapping(value="/permission/check", method=RequestMethod.GET)
    public Map<String,Object> check(
            @RequestParam(value="access_token",required=true)String accessToken,//安全令牌，不允许为空
            @RequestParam(value="params",required=true)String params//请求参数
    ){
        logger.debug("----------提示：开始执行权限接口  PermissionController.check()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        
        logger.debug("安全令牌access_token:"+ accessToken);
        
        ValidUtil.valid("安全令牌access_token", accessToken, "required");//安全令牌必填校验
        
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);//参数从字符串转json
        String code = (String)paramMap.get("interfaceCode");//接口编号
        ValidUtil.valid("权限（接口）编号[interfaceCode]", code, "required");//接口编号必填校验
        
        Map<String,Object> infoMap = null;//返回的信息
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_app");//服务代码[外键]
        
        if(StringUtils.indexOf(accessToken, "_APP_") != -1){//AT_APP_开头 第三方请求
            infoMap = (Map<String,Object>)paramMap.get("appInfo");
            dealAPP(code,serviceCode,infoMap);
        }else{//其余 业务系统请求
            infoMap = (Map<String,Object>)paramMap.get("userInfo");
            dealData(code,serviceCode,infoMap);
        }
        logger.debug("----------提示：权限接口执行成功  PermissionController.check(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
        return this.successMsg();//返回成功信息
    }
    
    /**
     * 处理 业务系统的权限校验
     * @param code 接口编号
     * @param serviceCode 服务编号
     * @param userInfoMap 参数
     * @author 周颖  
     * @date 2017年7月19日 下午2:49:06
     */
    private void dealData(String code, String serviceCode, Map<String,Object> userInfoMap){
        /* 参数校验 */
        ValidUtil.valid("用户信息[userInfo]", userInfoMap, "required");//用户信息必填校验
        String roleIds = (String)userInfoMap.get("roleIds");//角色ids
        ValidUtil.valid("角色ids[roleIds]", roleIds, "required");//角色ids必填校验
        
        String[] roleIdStrArray = roleIds.split(",");//将roleIds转换成字符串数组
        ValidUtil.valid("角色ids[roleIds]", roleIdStrArray, "arrayNotBlank");//数组内部不允许存在空值(null|"")的校验
        
        //从redis中获取
        String newRoleIds = getRoleIds(roleIdStrArray);
        String key = RedisConstants.PERMISSION + serviceCode + "_" + newRoleIds + "_" + code;
        if(!RedisUtil.exist(key)){//如果不存在
            Long[] roleIdLongArray = CastUtil.toLongArray(roleIdStrArray);//字符串数组转Long数组
            boolean isPass = permissionService.check(roleIdLongArray, code, serviceCode);//权限校验
            if(!isPass){
                throw new BizException("E2000041", MessageUtil.getMessage("E2000041", new Object[]{roleIds, code}));//角色ids[{0}]无权限调用接口[{1}]!
            }
            //redis缓存
            RedisUtil.set(key, "1", RedisConstants.PERMISSION_TIME);
        }
    }
    
   /**
    * 处理第三方接口请求权限
    * @param code 接口编号
    * @param serviceCode 服务编号
    * @param appInfoMap 第三方应用信息
    * @author 周颖  
    * @date 2017年7月19日 下午4:29:12
    */
    private void dealAPP(String code, String serviceCode, Map<String,Object> appInfoMap){
        /* 参数校验 */
        ValidUtil.valid("第三方信息[appInfo]", appInfoMap, "required");//第三方信息必填校验
        
        Long appId = CastUtil.toLong(appInfoMap.get("id"));
        ValidUtil.valid("应用表主键id[id]", appId, "{'required':true,'numeric':true}");//应用表主键id
        
        String appName = (String) appInfoMap.get("appName");
        ValidUtil.valid("应用名称[appName]", appName, "required");//应用名称
        
        String key = RedisConstants.PERMISSION + serviceCode + "_" + appId + "_" + code;
        if(!RedisUtil.exist(key)){//如果不存在
            boolean isPass = permissionService.check(appId,code);
            if(!isPass){
                throw new BizException("E2900010", MessageUtil.getMessage("E2900010", new Object[]{appName, code}));//应用[{0}]无权限调用接口[{1}]!
            }
        }
        //redis缓存
        RedisUtil.set(key, "1", RedisConstants.PERMISSION_TIME);
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
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_app");//服务编号
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
        
        String serviceCode = SysConfigUtil.getParamValue("servicecode_app");//服务编号
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
        String serviceCode = SysConfigUtil.getParamValue("servicecode_app");//从配置表读取服务代码
        String serviceKey = SysConfigUtil.getParamValue("servicekey_app");//从配置表读取服务密钥
        permissionService.pushInterfaces(serviceCode, serviceKey);//获取接口信息并推送至admin
        return this.successMsg();//返回成功信息
    }
}
