/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月5日 上午9:52:54
* 创建作者：周颖
* 文件名称：UserController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.usrsrv.security.user.controller;

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

import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.admin.security.user.service.UserService;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;

@Controller
@RequestMapping("/usrsrv")
public class UserController extends BaseController {

    /**np admin 用户服务*/
    @Resource(name="userService")
    private UserService userService;
    
    /**项目*/
    @Resource(name="projectService")
    private ProjectService projectService;
    
    /**
     * 管理员账号列表
     * @param accessToken access_token
     * @param params 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月5日 上午10:00:13
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.GET, value="/users")
    @ResponseBody
    public Map<String,Object> getListByParam(@RequestParam(value="access_token",required=true)String accessToken,@RequestParam(value="params",required=true)String params,HttpServletRequest request) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = (Integer) paramsMap.get("pageSize");//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");//校验每页记录数 不能大于最大数
        Long roleId = CastUtil.toLong(paramsMap.get("roleId"));//角色id，数字，允许为空
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));//省id，数字，允许为空
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));//市id，数字，允许为空
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));//区id，数字，允许为空
        String userName = (String)paramsMap.get("userName");//账号关键字 可为空
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));//页码，数字，允许为空，默认第1页
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<User> page = new Page<User>(pageNo,pageSize);
        SessionUser user = SessionUtil.getCurSessionUser(request);
        userService.getListByParams(user,page,roleId,provinceId,cityId,areaId,userName);
        return this.successMsg(page);
    }
    
    /**
     * 添加管理员
     * @param accessToken access_token
     * @param bodyParam 请求体
     * @return 结果
     * @author 周颖  
     * @date 2017年2月9日 下午4:10:24
     */
    @RequestMapping(method=RequestMethod.POST, value="/user",produces="application/json")
    @ResponseBody
    public Map<String,Object> add(@RequestParam(value="access_token",required=true)String accessToken, @RequestBody(required=true) Map<String,Object> bodyParam){
        String userName = (String) bodyParam.get("userName");//用户名（账号），不允许为空，1-50位字符，包括字母、数字、下划线、连接符，正则：^[0-9a-zA-Z\-\_]{1,50}$
        Long provinceId= CastUtil.toLong(bodyParam.get("provinceId"));//省id，数字，允许为空
        Long cityId= CastUtil.toLong(bodyParam.get("cityId"));//市id，数字，允许为空
        Long areaId= CastUtil.toLong(bodyParam.get("areaId"));//区县id，数字，允许为空
        String roleIds = (String) bodyParam.get("roleIds");//角色ids，不允许为空，多个时用逗号拼接
        String projectIds = (String) bodyParam.get("projectIds");//包含的项目ids，允许为空，多个时用逗号拼接
        String filterProjectIds = (String) bodyParam.get("filterProjectIds");//排除的项目ids，允许为空，多个时用逗号拼接
        String merchantIds = (String) bodyParam.get("merchantIds");//管理的商户ids，允许为空，多个时用逗号拼接
        String deptName = (String) bodyParam.get("deptName");//部门，允许为空，正则校验[1-50位汉字、字母、数字]
        String contactPerson = (String) bodyParam.get("contactPerson");//联系人，允许为空，正则校验[1-50位汉字、字母]
        String contactWay = (String) bodyParam.get("contactWay");//联系方式，允许为空，正则校验[1-50位数字]
        String remark = (String) bodyParam.get("remark");//备注，允许为空，正则校验[500位任意字符]
        ValidUtil.valid("账号[userName]", userName, "{'required':true,'regex':'"+RegexConstants.USER_NAME_PATTERN+"'}");
        ValidUtil.valid("角色[roleIds]", roleIds, "required");
        //ValidUtil.valid("包含项目[projectIds]", projectIds, "required");
        //ValidUtil.valid("排除项目[filterProjectIds]", filterProjectIds, "required");
        //ValidUtil.valid("管理的商户[merchantIds]", merchantIds, "required");
        if(StringUtils.isNotBlank(deptName)){
            ValidUtil.valid("部门[deptName]", deptName, "{'required':true,'regex':'"+RegexConstants.DEPT_NAME_PATTERN+"'}");
        }
        if(StringUtils.isNotBlank(contactPerson)){
            ValidUtil.valid("联系人[contactPerson]", contactPerson, "{'required':true,'regex':'"+RegexConstants.CONTACT_PERSON_PATTERN+"'}");
        }
        if(StringUtils.isNotBlank(contactWay)){
            ValidUtil.valid("联系方式[contactWay]", contactWay, "{'required':true,'regex':'"+RegexConstants.CONTACT_WAY_PATTERN+"'}");
        }
        if(userService.isUserNameExist(userName)){//用户名唯一性判断
            throw new BizException("E2000028", MessageUtil.getMessage("E2000028",userName));
        }
        User user = new User();
        user.setUserName(userName);
        user.setProvinceId(provinceId);
        user.setCityId(cityId);
        user.setAreaId(areaId);
        user.setRoleIds(roleIds);
        user.setProjectIds(projectIds);
        user.setFilterProjectIds(filterProjectIds);
        user.setMerchantIds(merchantIds);
        user.setContactPerson(contactPerson);
        user.setContactWay(contactWay);
        user.setDeptName(deptName);
        user.setRemark(remark);
        userService.add(user);
        return this.successMsg();   
    }
   
    /**
     * 编辑管理员账号
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param id 账号主键id
     * @return 结果
     * @author 周颖  
     * @date 2017年5月8日 下午2:46:53
     */
    @RequestMapping(method=RequestMethod.PUT, value="/user/{id}",produces="application/json")
    @ResponseBody
    public Map<String,Object> update(
            @RequestParam(value="access_token",required=true)String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            @PathVariable Long id){
        Long provinceId= CastUtil.toLong(bodyParam.get("provinceId"));//省id，数字，允许为空
        Long cityId= CastUtil.toLong(bodyParam.get("cityId"));//市id，数字，允许为空
        Long areaId= CastUtil.toLong(bodyParam.get("areaId"));//区县id，数字，允许为空
        String roleIds = (String) bodyParam.get("roleIds");//角色ids，不允许为空，多个时用逗号拼接
        String projectIds = (String) bodyParam.get("projectIds");//包含的项目ids，允许为空，多个时用逗号拼接
        String filterProjectIds = (String) bodyParam.get("filterProjectIds");//排除的项目ids，允许为空，多个时用逗号拼接
        String merchantIds = (String) bodyParam.get("merchantIds");//管理的商户ids，允许为空，多个时用逗号拼接
        String deptName = (String) bodyParam.get("deptName");//部门，允许为空，正则校验[1-50位汉字、字母、数字]
        String contactPerson = (String) bodyParam.get("contactPerson");//联系人，允许为空，正则校验[1-50位汉字、字母]
        String contactWay = (String) bodyParam.get("contactWay");//联系方式，允许为空，正则校验[1-50位数字]
        String remark = (String) bodyParam.get("remark");//备注，允许为空，正则校验[500位任意字符]
        ValidUtil.valid("角色[roleIds]", roleIds, "required");
        //ValidUtil.valid("包含项目[projectIds]", projectIds, "required");
        //ValidUtil.valid("排除项目[filterProjectIds]", filterProjectIds, "required");
        //ValidUtil.valid("管理的商户[merchantIds]", merchantIds, "required");
        if(StringUtils.isNotBlank(deptName)){
            ValidUtil.valid("部门[deptName]", deptName, "{'required':true,'regex':'"+RegexConstants.DEPT_NAME_PATTERN+"'}");
        }
        if(StringUtils.isNotBlank(contactPerson)){
            ValidUtil.valid("联系人[contactPerson]", contactPerson, "{'required':true,'regex':'"+RegexConstants.CONTACT_PERSON_PATTERN+"'}");
        }
        if(StringUtils.isNotBlank(contactWay)){
            ValidUtil.valid("联系方式[contactWay]", contactWay, "{'required':true,'regex':'"+RegexConstants.CONTACT_WAY_PATTERN+"'}");
        }
        User user = new User();
        user.setId(id);
        user.setProvinceId(provinceId);
        user.setCityId(cityId);
        user.setAreaId(areaId);
        user.setRoleIds(roleIds);
        user.setProjectIds(projectIds);
        user.setFilterProjectIds(filterProjectIds);
        user.setMerchantIds(merchantIds);
        user.setContactPerson(contactPerson);
        user.setContactWay(contactWay);
        user.setDeptName(deptName);
        user.setRemark(remark);
        userService.update(user);
        return this.successMsg();
    }
    
    /**
     * 管理员账号详情
     * @param accessToken access_token
     * @param id 主键id
     * @return 详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月8日 下午3:22:33
     */
    @RequestMapping(method=RequestMethod.GET, value="/user/{id}")
    @ResponseBody
    public Map<String,Object> getById(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id) throws Exception{
        User user = userService.getUserById(id);
        String projectNames = projectService.getNamesByIds(user.getProjectIds());//补全项目信息
        user.setProjectNames(projectNames);
        String filterProjectNames = projectService.getNamesByIds(user.getFilterProjectIds());
        user.setFilterProjectNames(filterProjectNames);
        return this.successMsg(user);
    }
    
    /**
     * 逻辑删除管理员
     * @param accessToken access_token
     * @param id 主键id
     * @return 结果
     * @author 周颖  
     * @date 2017年5月8日 下午7:17:25
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/user/{id}")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id){
        userService.delete(id);
        return this.successMsg();
    }
    
    /**
     * 重置密码
     * @param access_token access_token
     * @param id 主键id
     * @return 结果
     * @author 周颖  
     * @date 2017年5月8日 下午7:23:00
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.PUT,value="/user/{id}/resetpwd")
    public Map<String,Object> resetPassword(@RequestParam(value="access_token",required=true) String access_token,@PathVariable Long id){
        userService.resetPassword(id);
        return this.successMsg();
    }
}
