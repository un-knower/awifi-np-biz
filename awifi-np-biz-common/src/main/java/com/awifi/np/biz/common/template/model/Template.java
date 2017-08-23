package com.awifi.np.biz.common.template.model;

import java.util.Date;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:11:30
* 创建作者：王冬冬
* 文件名称：Template.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
public class Template {
    /**
     * 模板id
     */
    private Long id;

    /**
     * 模板编号
     */
    private String code;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 服务编号
     */
    private String serviceCode;

    /**
     * 套码编号
     */
    private String suitCode;
    /**
     * 模板源代码
     */
    private String src;

    /**
     * 模板编译后代码
     */
    private String content;

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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getSuitCode() {
    	return suitCode;
    }

    public void setSuitCode(String suitCode) {
    	this.suitCode = suitCode;
    }
}
