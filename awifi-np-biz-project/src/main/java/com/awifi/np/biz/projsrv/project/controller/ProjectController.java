package com.awifi.np.biz.projsrv.project.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.project.model.Project;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 10, 2017 11:26:20 AM
 * 创建作者：亢燕翔
 * 文件名称：ProjectController.java
 * 版本：  v1.0
 * 功能：  项目管理控制层
 * 修改记录：
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Controller
@RequestMapping("/projsrv")
public class ProjectController extends BaseController{

	/** 项目管理业务层  */
    @Resource(name = "projectService")
	private ProjectService projectService;
	
    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**
     * 项目显示接口
     * @param request 请求
     * @param templatecode page(pan)编号
     * @param access_token 安全令牌
     * @return map
     * @author 亢燕翔  
     * @date 2017年1月16日 上午9:01:44
     */
    @ResponseBody
    @RequestMapping(value="/view/{templatecode}", method=RequestMethod.GET)
    public Map view(HttpServletRequest request, @PathVariable String templatecode, @RequestParam(value="access_token",required=true)String access_token){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_project");//从配置表读取服务代码
        String suitCode = SessionUtil.getCurSessionUser(request).getSuitCode();//从session中获取套码
        if(StringUtils.isBlank(suitCode)){//套码未找到抛出异常
            throw new ValidException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
        }
        String template = templateService.getByCode(suitCode,serviceCode,templatecode);//获取模板页面
        return this.successMsg(template);
    }
    
	/**
	 * 项目分页查询列表
	 * @param request 请求 
	 * @param access_token 安全令牌 
	 * @param params json数据
	 * @return map
	 * @throws Exception 异常
	 * @author 亢燕翔  
	 * @date 2017年1月16日 上午9:45:50
	 */
    @ResponseBody
    @RequestMapping(value="/projects", method=RequestMethod.GET)
    public Map list(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception {
        /*接收参数*/
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        String projectName = (String) paramsMap.get("projectName");
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));
        Integer pageNo = (Integer) paramsMap.get("pageNo");
        Integer pageSize = (Integer) paramsMap.get("pageSize");
        /*数据校验*/
        int maxSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//获取系统配置最大每页条数限制
        ValidUtil.valid("省ID[provinceId]", provinceId, "numeric");
        ValidUtil.valid("市ID[cityId]", cityId, "numeric");
        ValidUtil.valid("区县ID[areaId]", areaId, "numeric");
        ValidUtil.valid("页码[pageNo]", pageNo, "numeric");
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxSize+"}}");
        Page<Project> page = new Page<Project>();
        page.setPageNo(pageNo == null ? 1 : pageNo);
        page.setPageSize(pageSize);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        projectService.getListByParam(sessionUser,projectName,provinceId,cityId,areaId,page);
        return this.successMsg(page);
    }
   
    /**
     * 添加项目 
     * @param access_token 安全令牌 
     * @param bodyParam 请求体数据
     * @throws Exception 异常 
     * @return map 
     * @author 亢燕翔  
     * @date Jan 11, 2017 10:42:39 AM 
     */
    @ResponseBody
    @RequestMapping(value="/project", method=RequestMethod.POST, produces="application/json")
    public Map add(@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        projectService.add(bodyParam);
        return this.successMsg();
    }
    
    /**
     * 项目详情
     * @param access_token 安全令牌 
     * @param id 项目ID
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date Jan 11, 2017 11:22:39 AM
     */
    @ResponseBody
    @RequestMapping(value="/project/{id}", method=RequestMethod.GET)
    public Map getById(@RequestParam(value="access_token",required=true) String access_token,@PathVariable(name="id") Long id) throws Exception{
        return this.successMsg(projectService.getById(id));
    }
    
    /**
     * 删除项目
     * @param access_token 安全令牌 
     * @param id 项目ID
     * @return map
     * @author 亢燕翔  
     * @date Jan 11, 2017 2:11:56 PM
     */
    @ResponseBody
    @RequestMapping(value="/project/{id}", method=RequestMethod.DELETE)
    public Map delete(@RequestParam(value="access_token",required=true) String access_token,@PathVariable(name="id",required=true) Long id){
        projectService.delete(id);
        return this.successMsg();
    }
    
    /**
     * 编辑项目
     * @param access_token 安全令牌 
     * @param id 项目ID
     * @param bodyParam 请求体数据
     * @return map
     * @author 亢燕翔  
     * @date Jan 11, 2017 2:16:14 PM
     */
    @ResponseBody
    @RequestMapping(value="/project/{id}", method=RequestMethod.PUT, produces="application/json")
    public Map update(@RequestParam(value="access_token",required=true) String access_token,@PathVariable(name="id",required=true) Long id,
            @RequestBody(required=true) Map<String, Object> bodyParam){
        projectService.update(id, bodyParam);
        return this.successMsg();
    }
    
    /**
     * 导出项目列表
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月20日 上午9:20:00
     */
    @RequestMapping(value = "/projects/xls", method=RequestMethod.GET)
    public void export(HttpServletRequest request,HttpServletResponse response, @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        String projectName = (String) paramsMap.get("projectName");
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        projectService.export(sessionUser,response,projectName,provinceId,cityId,areaId,path);
    }
    
    
    
    /**
     * 根据项目id获取设备和商户数
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param projectIds 项目id
     * @throws Exception 异常
     * @return map
     * @author 王冬冬  
     * @date 2017年5月5日 下午4:24:07
     */
    @ResponseBody
    @RequestMapping(value = "/projects/projectIds", method=RequestMethod.GET)
    public Map getMerchantCountByProjectIds(HttpServletRequest request,HttpServletResponse response, @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="projectIds",required=true) String projectIds) throws Exception{
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        List<Map<String,Object>> list=projectService.getMerchantCountByProjectIds(sessionUser,projectIds);
        return this.successMsg(list);
    }
}
