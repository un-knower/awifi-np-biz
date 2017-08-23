/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 上午11:11:07
* 创建作者：尤小平
* 文件名称：PubMerchant.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.model;

import com.awifi.np.biz.common.util.FormatUtil;

import java.io.Serializable;

public class PubMerchant implements Serializable{
    /**
     * 序列化id
     */
    private static final long serialVersionUID = -5887297718434546066L;

    /**商户id*/
    private Long id;

    /**关系用户编号*/
    private Long userId;

    /**商户名称*/
    private String merchantName;

    /**商户类型*/
    private Integer merchantType;

    /**级联标签*/
    private String cascadeLabel;

    /**级联等级*/
    private Integer cascadeLevel;

    /**父商户id*/
    private Long parentId;

    /**父商户名称*/
    private String parentName;

    /**账号*/
    private String account;

    /**角色ids*/
    private String roleIds;

    /**角色名称*/
    private String roleNames;

    /**联系人*/
    private String contact;

    /**联系方式*/
    private String contactWay;

    /**项目id*/
    private Long projectId;

    /**项目名称*/
    private String projectName;

    /**一级行业id*/
    private String priIndustryCode;

    /**一级行业*/
    private String priIndustry;

    /**二级行业id*/
    private String secIndustryCode;

    /**二级行业*/
    private String secIndustry;

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

    /**地区全称*/
    private String locationFullName;

    /**详细地址*/
    private String address;

    /**销售点二级分类：1代表专营店、2代表自有厅、3代表其他*/
    private Integer storeType;

    /**自有厅级别：1代表1级、2代表2级、3代表3级、4代表4级、5代表5级*/
    private Integer storeLevel;

    /**专营店星级：1代表1星、2代表2星、3代表3星、4代表4星、 5代表5星*/
    private Integer storeStar;

    /**专营店类别：1代表社区店、2代表商圈店、3代表旗舰店、4代表其他*/
    private Integer storeScope;

    /**接入方式：chinanet代表Chinanet接入、fatAp代表胖AP接入、optical modem代表定制光猫接入、others代表其他*/
    private String connectType;

    /**备注*/
    private String remark;

    /**创建日期,示例：2017-01-19 09:50:27*/
    private String createDate;

    /**修改日期，示例：2017-01-19 09:50:27*/
    private String updateDate;

    /**状态 1：正常；9：注销*/
    private Integer status;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public Integer getMerchantType() {
        return merchantType;
    }
    public void setMerchantType(Integer merchantType) {
        this.merchantType = merchantType;
    }
    public String getCascadeLabel() {
        return cascadeLabel;
    }
    public void setCascadeLabel(String cascadeLabel) {
        this.cascadeLabel = cascadeLabel;
    }
    public Integer getCascadeLevel() {
        return cascadeLevel;
    }
    public void setCascadeLevel(Integer cascadeLevel) {
        this.cascadeLevel = cascadeLevel;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
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
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getContactWay() {
        return contactWay;
    }
    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getPriIndustryCode() {
        return priIndustryCode;
    }
    public void setPriIndustryCode(String priIndustryCode) {
        this.priIndustryCode = priIndustryCode;
    }
    public String getPriIndustry() {
        return priIndustry;
    }
    public void setPriIndustry(String priIndustry) {
        this.priIndustry = priIndustry;
    }
    public String getSecIndustryCode() {
        return secIndustryCode;
    }
    public void setSecIndustryCode(String secIndustryCode) {
        this.secIndustryCode = secIndustryCode;
    }
    public String getSecIndustry() {
        return secIndustry;
    }
    public void setSecIndustry(String secIndustry) {
        this.secIndustry = secIndustry;
    }
    public Long getProvinceId() {
        return provinceId;
    }
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Long getAreaId() {
        return areaId;
    }
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Integer getStoreType() {
        return storeType;
    }
    //二级分类　1专营店,2自有厅,3其他
    public String getStoreTypeDsp() {
        return FormatUtil.storeTypeDsp(storeType);
    }
    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }
    public Integer getStoreLevel() {
        return storeLevel;
    }
    //门店级别　1-5
    public String getStoreLevelDsp() {
        return FormatUtil.storeLevelDsp(storeLevel);
    }
    public void setStoreLevel(Integer storeLevel) {
        this.storeLevel = storeLevel;
    }
    public Integer getStoreStar() {
        return storeStar;
    }
    //专营店星级
    public String getStoreStarDsp() {
        return FormatUtil.storeStarDsp(storeStar);
    }
    public void setStoreStar(Integer storeStar) {
        this.storeStar = storeStar;
    }
    public Integer getStoreScope() {
        return storeScope;
    }
    //专营店类别
    public String getStoreScopeDsp(){
        return FormatUtil.storeScopeDsp(storeScope);
    }
    public void setStoreScope(Integer storeScope) {
        this.storeScope = storeScope;
    }
    public String getConnectType() {
        return connectType;
    }
    //接入方式
    public String getConnectTypeDsp() {
        return FormatUtil.connectTypeDsp(connectType);
    }
    public void setConnectType(String connectType) {
        this.connectType = connectType;
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
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
}
