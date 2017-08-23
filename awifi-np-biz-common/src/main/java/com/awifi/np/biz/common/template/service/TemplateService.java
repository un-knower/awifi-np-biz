package com.awifi.np.biz.common.template.service;

import com.awifi.np.biz.common.template.model.Template;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 上午9:53:19
 * 创建作者：周颖
 * 文件名称：TemplateService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface TemplateService {

    /***
     * 查询模板内容
     * @param suitCode 套码
     * @param serviceCode 服务代码
     * @param templateCode page(pan)编号
     * @return 模板内容
     * @author 周颖  
     * @date 2017年1月11日 上午10:28:10
     */
    String getByCode(String suitCode,String serviceCode,String templateCode);
    /**
     * @param template 模板
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:26:01
     */
    void addTemplate(Template template);
    /**
     * @param template 模板
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:26:03
     */
    void deleteTemplate(Template template);
    /**
     * @param template 模板
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:26:06
     */
    void updateTemplate(Template template);
    /**
     * @param template 模板
     * @return Template
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:26:09
     */
    Template getTemplateByParam(Template template);
	/**
	 * @param template 模板
	 * @return boolean
	 * @author 王冬冬  
	 * @date 2017年4月10日 上午11:26:11
	 */
    boolean isExist(Template template);
	/**
	 * @param template 模板
	 * @return Long
	 * @author 王冬冬  
	 * @date 2017年4月10日 上午11:26:14
	 */
    Long getExistTemplateId(Template template);
}
