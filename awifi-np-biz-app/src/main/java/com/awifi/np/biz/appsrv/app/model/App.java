package com.awifi.np.biz.appsrv.app.model;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月10日 下午7:15:03
 * 创建作者：许尚敏
 * 文件名称：App.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class App {
    /** 主键id */
    private Long id;
    /**应用id */
    private String appId;
    /** 应用key */
    private String appKey;
    /** 应用名称 */
    private String appName;
    /** 应用参数 */
    private String appParam;
    /** 公司名称 */
    private String company;
    /** 营业执照 */
    private String businessLicense;
    /** 联系人 */
    private String contactPerson;
    /** 联系方式 */
    private String contactWay;
    /** 状态：1 启用、2 禁用 */
    private Integer status;
    /** 备注 */
    private String remark;
    /** 创建时间 */
    private String createDate;
    /** 状态：启用 禁用 */
    private String statusDsp;
    
    public Long getId(){
        return this.id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public String getAppId(){
        return this.appId;
    }
    
    public void setAppId(String appId){
        this.appId = appId;
    }
    
    public String getAppKey(){
        return this.appKey;
    }
    
    public void setAppKey(String appKey){
        this.appKey = appKey;
    }
    
    public String getAppName(){
        return this.appName;
    }
    
    public void setAppName(String appName){
        this.appName = appName;
    }
    
    public String getAppParam(){
        return this.appParam;
    }
    
    public void setAppParam(String appParam){
        this.appParam = appParam;
    }
    
    public String getCompany(){
        return this.company;
    }
    
    public void setCompany(String company){
        this.company = company;
    }
    
    public String getBusinessLicense(){
        return this.businessLicense;
    }
    
    public void setBusinessLicense(String businessLicense){
        this.businessLicense = businessLicense;
    }
    
    public String getContactPerson(){
        return this.contactPerson;
    }
    
    public void setContactPerson(String contactPerson){
        this.contactPerson = contactPerson;
    }
    
    public String getContactWay(){
        return this.contactWay;
    }
    
    public void setContactWay(String contactWay){
        this.contactWay = contactWay;
    }
    
    public Integer getStatus(){
        return this.status;
    }
    
    public void setStatus(Integer status){
        this.status = status;
    }
    
    public String getRemark(){
        return this.remark;
    }
    
    public void setRemark(String remark){
        this.remark = remark;
    }
    
    public String getCreateDate(){
        return this.createDate;
    }
    
    public void setCreateDate(String createDate){
        this.createDate = createDate;
    }
    
    public String getStatusDsp() {
        return statusDsp;
    }

    public void setStatusDsp(String statusDsp) {
        this.statusDsp = statusDsp;
    }
}
