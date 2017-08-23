package com.awifi.np.biz.eagleeyesrv.template.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.template.model.NPTemplate;
import com.awifi.np.biz.common.template.model.Template;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.MessageUtil;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月7日 上午9:06:00
* 创建作者：王冬冬
* 文件名称：TemplateController1.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@Controller
@RequestMapping("/eagleeyesrv")
public class TemplateController extends BaseController{

    @Resource
    TemplateService templateService;
	
    /**
     * 新增模板
     * @param request
     * @param response
     * @param template
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/am/template", method= RequestMethod.POST)
    public Map<String,Object> addTemplateFromAm(HttpServletRequest request, HttpServletResponse response, @RequestBody NPTemplate nptemplate){
    	Template template=transfer(nptemplate);
    	
    	 if(StringUtils.isBlank(template.getSuitCode())){//套码未找到抛出异常
             throw new BizException("E0000002", MessageUtil.getMessage("E0000002",new Object[]{"suitcode"}));//套码编号不允许为空!
         }
    	 if(StringUtils.isBlank(template.getCode())){
             throw new BizException("E0000002", MessageUtil.getMessage("E0000002",new Object[]{"templateCode"}));//模板编号不允许为空!
         }
    	 if(StringUtils.isBlank(template.getName())){
             throw new BizException("E0000002", MessageUtil.getMessage("E0000002",new Object[]{"templateName"}));//模板名称不允许为空!
         }
    	 if(StringUtils.isBlank(template.getSrc())){
             throw new BizException("E0000002", MessageUtil.getMessage("E0000002",new Object[]{"src"}));//模板源代码不允许为空!
         }
    	 if(StringUtils.isBlank(template.getContent())){
             throw new BizException("E0000002", MessageUtil.getMessage("E0000002",new Object[]{"content"}));//模板编译代码不允许为空!
         }
    	if(templateService.isExist(template)){
    		throw new BizException("E2000055", MessageUtil.getMessage("E2000055", new Object[]{template.getName()}));
    	}
        templateService.addTemplate(template);
        return this.successMsg();
    }

	/**
     * 模板详情
     * @param request
     * @param response
     * @param templates
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/am/template", method= RequestMethod.GET)
    public Map<String,Object> getTemplateToAm(HttpServletRequest request, HttpServletResponse response,NPTemplate nptemplate){
    	Template template=transfer(nptemplate);
    	template=templateService.getTemplateByParam(template);
        return this.successMsg(template);
    }
    /**
     * 模板更新
     * @param request
     * @param response
     * @param template
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/am/template", method= RequestMethod.PUT)
    public Map<String,Object> updateTemplateToAm(HttpServletRequest request, HttpServletResponse response,@RequestBody NPTemplate nptemplate){
    	Template template=transfer(nptemplate);
    	Long id=templateService.getExistTemplateId(template);
    	if(id==null){
    		throw new BizException("E2000056", MessageUtil.getMessage("E2000056", new Object[]{template}));//
    	}
    	template.setId(id);
    	templateService.updateTemplate(template);
        return this.successMsg();
    }
    
    /**
     * 模板删除
     * @param request
     * @param response
     * @param templates
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/am/template", method= RequestMethod.DELETE)
    public Map<String,Object> deleteTemplateToAm(HttpServletRequest request, HttpServletResponse response,NPTemplate nptemplate){
    	Template template=transfer(nptemplate);
    	templateService.deleteTemplate(template);
        return this.successMsg();
    }
    
    /**
     * 管理系统实体转换成业务系统实体
     * @param nptemplate
     * @return
     * @author 王冬冬  
     * @date 2017年4月7日 上午11:16:34
     */
    private Template transfer(NPTemplate nptemplate) {
		Template t=new Template();
		t.setCode(nptemplate.getTemplateCode());
		t.setContent(nptemplate.getContent());
		t.setCreateDate(nptemplate.getCreateDate());
		t.setName(nptemplate.getTemplateName());
		t.setRemark(nptemplate.getRemark());
		t.setServiceCode(nptemplate.getServiceCode());
		t.setSrc(nptemplate.getSrc());
		t.setSuitCode(nptemplate.getSuitCode());
		t.setUpdateDate(nptemplate.getUpdateDate());
		return t;
	}
}

