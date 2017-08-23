package com.awifi.np.biz.toe.admin.security.user.model;

import java.io.Serializable;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月4日 下午2:03:40
 * 创建作者：周颖
 * 文件名称：toeUser.java
 * 版本：  v1.0
 * 功能：toe用户实体
 * 修改记录：
 */
public class ToeUser implements Serializable{

    /**
     * 序列化
     */
    private static final long serialVersionUID = 6440828556535049330L;

    /** 主键id */
    private Long id;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;
    
    /**省*/
    private Long provinceId;
    
    /**市*/
    private Long cityId;
   
    /**区县*/
    private Long areaId;
    
    /**联系人*/
    private String contactPerson;
    
    /**联系方式*/
    private String contactWay;
    
    /**部门*/
    private String deptName;
    
    /**备注*/
    private String remark;
    
    /**创建时间*/
    private String createDate;
    
    /**更新时间*/
    private String updateDate;
    
    /**项目id*/
    private Long projectId;
    
    /**创建者*/
    private Long createUserId;
    
    /**np角色ids*/
    private String roleIds;
    
    /**用户套码*/
    private String suitCode;
    
    /**商户id*/
    private Long merchantId;
    
    /**真实姓名*/
    private String realname;
    
    /**昵称*/
    private String nickname;
    
    /**邮箱*/
    private String email;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getSuitCode() {
        return suitCode;
    }

    public void setSuitCode(String suitCode) {
        this.suitCode = suitCode;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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
}