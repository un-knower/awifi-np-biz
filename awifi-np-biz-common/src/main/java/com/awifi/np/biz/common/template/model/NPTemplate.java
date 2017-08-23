package com.awifi.np.biz.common.template.model;

import java.io.Serializable;
import java.util.Date;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:11:30
* 创建作者：王冬冬
* 文件名称：NPTemplate.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
public class NPTemplate implements Serializable{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;

	/**
	 * 模板名称
	 */
    private String templateName;

    /**
     * 模板编号
     */
    private String templateCode;

	/**
	 * 套码编号
	 */
    private String suitCode;

	/**
	 * 服务编号
	 */
    private String serviceCode;

	/**
	 * 
	 */
    private String url;

	/**
	 * 创建日期
	 */
    private Date createDate;

	/**
	 * 创建者id
	 */
    private Integer createUserId;

	/**
	 * 更新时间
	 */
    private Date updateDate;

	/**
	 * 更新者id
	 */
    private Integer updateUserId;

	/**
	 * 备注
	 */
    private String remark;

	/**
	 * 模板源代码
	 */
    private String src;
	/**
	 * 模板编译代码
	 */
    private String content;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSuitCode() {
        return suitCode;
    }

    public void setSuitCode(String suitCode) {
        this.suitCode = suitCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSrc() {
    	return src;
    }

    public void setSrc(String src) {
    	this.src = src;
    }

    public String getContent() {
    	return content;
    }

    public void setContent(String content) {
    	this.content = content;
    }
}