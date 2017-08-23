package com.awifi.np.biz.mersrv.template.controller;

import java.util.Map;

import javax.annotation.Resource;

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
import com.awifi.np.biz.common.util.ValidUtil;
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
@RequestMapping("/mersrv")
public class TemplateController extends BaseController{

    /**
     * 
     */
    @Resource
    private TemplateService templateService;
	
    /**
     * @param nptemplate 模板
     * @return json
     * @author 王冬冬  
     * @date 2017年4月10日 上午10:59:36
     */
    @ResponseBody
    @RequestMapping(value="/am/template", method= RequestMethod.POST)
    public Map<String,Object> addTemplateFromAm(@RequestBody NPTemplate nptemplate){
    	Template template=transfer(nptemplate);
    	ValidUtil.valid("套码编号[suitcode]", template.getSuitCode(), "{'required':true}");
    	ValidUtil.valid("模板编号[templatecode]", template.getCode(), "{'required':true}");
    	ValidUtil.valid("模板名称[templatename]", template.getName(), "{'required':true}");
    	ValidUtil.valid("模板源代码[src]", template.getSrc(), "{'required':true}");
    	ValidUtil.valid("模板编译代码[content]", template.getContent(), "{'required':true}");
    	ValidUtil.valid("服务编号[servicecode]", template.getServiceCode(), "{'required':true}");
    	if(templateService.isExist(template)){
    	    throw new BizException("E2000055", MessageUtil.getMessage("E2000055", new Object[]{template.getName()}));
    	}
        templateService.addTemplate(template);
        return this.successMsg();
    }

	/**
     * 模板详情
     * @param nptemplate 模板
     * @return json
     */
    @ResponseBody
    @RequestMapping(value="/am/template", method= RequestMethod.GET)
    public Map<String,Object> getTemplateToAm(NPTemplate nptemplate){
    	Template template=transfer(nptemplate);
    	template=templateService.getTemplateByParam(template);
        return this.successMsg(template);
    }
    /**
     * 模板更新
     * @param nptemplate 模板
     * @return josn
     */
    @ResponseBody
    @RequestMapping(value="/am/template", method= RequestMethod.PUT)
    public Map<String,Object> updateTemplateToAm(@RequestBody NPTemplate nptemplate){
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
     * @param nptemplate 模板
     * @return json
     */
    @ResponseBody
    @RequestMapping(value="/am/template", method= RequestMethod.DELETE)
    public Map<String,Object> deleteTemplateToAm(NPTemplate nptemplate){
    	Template template=transfer(nptemplate);
    	templateService.deleteTemplate(template);
        return this.successMsg();
    }
    
   /**
     * 管理系统实体转换成业务系统实体
     * @param nptemplate 管理系统模板实体
     * @return Template
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