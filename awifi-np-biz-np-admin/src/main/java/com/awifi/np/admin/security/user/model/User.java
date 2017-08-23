package com.awifi.np.admin.security.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午5:05:32
 * 创建作者：周颖
 * 文件名称：User.java
 * 版本：  v1.0
 * 功能：np用户实体类
 * 修改记录：
 */
public class User implements Serializable{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -3038258787254766228L;

    /**主键id*/
    private Long id;
    
    /**用户名*/
    private String userName;
    
    /**密码*/
    private String password;
    
    /**角色ids 1,2,3*/
    private String roleIds;
    
    /**角色名称*/
    private String roleNames;
    
    /**省id*/
    private Long provinceId;
    
    /**省*/
    private String province;
    
    /**市id*/
    private Long cityId;
    
    /**市*/
    private String city;
    
    /**区县id*/
    private Long areaId;
    
    /**区县*/
    private String area;
    
    /**地址全称*/
    private String locationFullName;
    
    /**真实姓名*/
    private String realname;
    
    /**昵称*/
    private String nickname;
    
    /**邮箱*/
    private String email;
    
    /**部门*/
    private String deptName;
    
    /**联系人*/
    private String contactPerson;
    
    /**联系方式*/
    private String contactWay;
    
    /**备注*/
    private String remark;
    
    /**状态：1 正常、9 已删除*/
    private Integer status;
    
    /**创建日期*/
    private Date createDate;
    
    /**修改日期*/
    private Date updateDate;
    
    /**商户id*/
    private Long merchantId;
    
    /**套码*/
    private String suitCode;
    
    /**创建用户id*/
    private Long createUserId;
    
    /**管理项目 1,2*/
    private String projectIds;
    
    /**管理项目名称*/
    private String projectNames;
    
    /**过滤项目 1,2*/
    private String filterProjectIds;
    
    /**过滤项目名称*/
    private String filterProjectNames;
    
    /**管理商户*/
    private String merchantIds;
    
    /**管理商户名称*/
    private String merchantNames;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getSuitCode() {
        return suitCode;
    }

    public void setSuitCode(String suitCode) {
        this.suitCode = suitCode;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocationFullName() {
        return locationFullName;
    }

    public void setLocationFullName(String locationFullName) {
        this.locationFullName = locationFullName;
    }

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }

    public String getFilterProjectIds() {
        return filterProjectIds;
    }

    public void setFilterProjectIds(String filterProjectIds) {
        this.filterProjectIds = filterProjectIds;
    }

    public String getMerchantIds() {
        return merchantIds;
    }

    public void setMerchantIds(String merchantIds) {
        this.merchantIds = merchantIds;
    }

    public String getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(String projectNames) {
        this.projectNames = projectNames;
    }

    public String getFilterProjectNames() {
        return filterProjectNames;
    }

    public void setFilterProjectNames(String filterProjectNames) {
        this.filterProjectNames = filterProjectNames;
    }

    public String getMerchantNames() {
        return merchantNames;
    }

    public void setMerchantNames(String merchantNames) {
        this.merchantNames = merchantNames;
    }   
}