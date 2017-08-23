package com.awifi.np.biz.common.security.user.model;

import java.io.Serializable;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 9, 2017 8:28:59 PM
 * 创建作者：亢燕翔
 * 文件名称：SessionUser.java
 * 版本：  v1.0
 * 功能：  
 * 修改记录：
 */
public class SessionUser implements Serializable {

    /** 序列化  */
    private static final long serialVersionUID = 194010773093059354L;

    /** 用户id  */
    private Long id;
    
    /** 用户名  */
    private String userName;
    
    /** 组织id  */
    private Long orgId;

    /** 角色ids(多个时用逗号拼接)，对应管理系统的角色ids  */
    private String roleIds;
    
    /** 省id  */
    private Long provinceId;
    
    /** 市id  */
    private Long cityId;
    
    /** 区id  */
    private Long areaId;
    
    /** 商户id */
    private Long merchantId;
    
    /** 商户层级关系 ，格式为：1-2-3 */
    private String cascadeLabel;
    
    /** 套码  */
    private String suitCode;
    
    /**排除管理的项目id*/
    private String filterProjectIds;
    
    /**管理的项目ids*/
    private String projectIds;
    
    /**管理的商户ids*/
    private String merchantIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getCascadeLabel() {
        return cascadeLabel;
    }

    public void setCascadeLabel(String cascadeLabel) {
        this.cascadeLabel = cascadeLabel;
    }

    public String getSuitCode() {
        return suitCode;
    }

    public void setSuitCode(String suitCode) {
        this.suitCode = suitCode;
    }

    public String getFilterProjectIds() {
        return filterProjectIds;
    }

    public void setFilterProjectIds(String filterProjectIds) {
        this.filterProjectIds = filterProjectIds;
    }

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }

    public String getMerchantIds() {
        return merchantIds;
    }

    public void setMerchantIds(String merchantIds) {
        this.merchantIds = merchantIds;
    }
    
}
