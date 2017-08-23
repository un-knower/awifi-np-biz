package com.awifi.np.biz.common.template.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.template.dao.TemplateDao;
import com.awifi.np.biz.common.template.model.Template;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 上午9:55:18
 * 创建作者：周颖
 * 文件名称：TemplateServiceImpl.java
 * 版本：  v1.0
 * 功能：模板实现类
 * 修改记录：
 */
@Service("templateService")
public class TemplateServiceImpl implements TemplateService {
    
    /**模板dao*/
    @Resource(name = "templateDao")
    private TemplateDao templateDao;
    
    /***
     * 查询模板内容
     * @param suitCode 套码
     * @param serviceCode 服务代码
     * @param templateCode page(pan)编号
     * @return 模板内容
     * @author 周颖  
     * @date 2017年1月11日 上午10:28:10
     */
    public String getByCode(String suitCode,String serviceCode,String templateCode){
        String template = templateDao.getByCode(suitCode,serviceCode,templateCode);//获取模板
        if(StringUtils.isBlank(template)){//模板如果为空
            String[] error = {serviceCode,suitCode,templateCode};
            throw new BizException("E2000001",MessageUtil.getMessage("E2000001",error));//抛出异常
        }
        return template;//返回模板
    }

    /**
     * @param template 模板
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:26:01
     */
    @Override
	public void addTemplate(Template template) {
		
    	templateDao.addTemplate(template);
    	templateDao.addTemplateSuit(template.getSuitCode(), template.getId());
    }

    /**
     * @param nptemplate 模板
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:26:03
     */
    @Override
    public void deleteTemplate(Template nptemplate) {
    	Template template=templateDao.getTemplateByParam(nptemplate);
    	templateDao.deleteTemplateSuit(template.getSuitCode(),template.getId());
    	templateDao.deleteTemplate(template);
    }

    /**
     * @param template 模板
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:26:06
     */
    @Override
	public void updateTemplate(Template template) {
    	templateDao.updateTemplate(template);
    }

    /**
     * @param template 模板
     * @return Template
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:26:09
     */
    @Override
    public Template getTemplateByParam(Template template) {
    	return templateDao.getTemplateByParam(template);
    }

    /**
	 * @param template 模板
	 * @return boolean
	 * @author 王冬冬  
	 * @date 2017年4月10日 上午11:26:11
	 */
    @Override	
    public boolean isExist(Template template) {
    	if(templateDao.isTemplateExist(template.getSuitCode(), template.getServiceCode(), template.getCode())==0){
            return false;
    	}
    	return true;
    }

    /**
	 * @param template 模板
	 * @return Long
	 * @author 王冬冬  
	 * @date 2017年4月10日 上午11:26:14
	 */
    @Override
    public Long getExistTemplateId(Template template) {
    	return templateDao.getExistTemplateId(template.getSuitCode(), template.getServiceCode(), template.getCode());
    }
}