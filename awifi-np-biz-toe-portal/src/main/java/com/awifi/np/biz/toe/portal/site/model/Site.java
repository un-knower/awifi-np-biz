/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:21:09
* 创建作者：周颖
* 文件名称：Site.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.site.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.FormatUtil;

public class Site implements Serializable {
    
    /***/
    private static final long serialVersionUID = 6814590754854525822L;

    /** 主键id  */
    private Long id;

    /** 站点名称  */
    private String siteName;
    
    /** 站点略缩图  */
    private String thumb;
    
    /**省id*/
    private Long provinceId;
    
    /**省*/
    private String province;
    
    /**市id*/
    private Long cityId;
    
    /**市*/
    private String city;
    
    /**地区*/
    private String locationFullName;
    
    /**一级行业编号*/
    private String priIndustryCode;
    
    /**一级行业*/
    private String priIndustry;
    
    /**二级行业编号*/
    private String secIndustryCode;
    
    /**二级行业*/
    private String secIndustry;
    
    /**商户id*/
    private Long merchantId;
    
    /**商户名称*/
    private String merchantName;
    
    /**商户层级*/
    private String cascadeLabel;
    
    /**状态 1 待审核、2 审核中、3 已审核、4 已发布、5 驳回*/
    private Integer status;

    /** 备注  */
    private String remark;
    
    /** 创建时间  */
    private String createDate;
    
    /**策略数*/
    private Integer strategyNum;
    
    /**站点类型 -1商户站点 1默认站点 2行业站点 3地区站点*/
    private Integer defaultSite;
    
    /**站点页面*/
    private List<SitePage> pages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

    public String getLocationFullName() {
        return locationFullName;
    }

    public void setLocationFullName(String locationFullName) {
        this.locationFullName = locationFullName;
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 站点状态显示值
     * @return 状态
     * @author 周颖  
     * @date 2017年4月19日 下午5:23:01
     */
    public String getStatusDsp(){
        if(status == null){
            return StringUtils.EMPTY;
        }
        return FormatUtil.statusDsp(status);
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    
    public Integer getStrategyNum() {
        return strategyNum;
    }

    public void setStrategyNum(Integer strategyNum) {
        this.strategyNum = strategyNum;
    }

    public Integer getDefaultSite() {
        return defaultSite;
    }

    public void setDefaultSite(Integer defaultSite) {
        this.defaultSite = defaultSite;
    }

    public String getCascadeLabel() {
        return cascadeLabel;
    }

    public void setCascadeLabel(String cascadeLabel) {
        this.cascadeLabel = cascadeLabel;
    }

    public List<SitePage> getPages() {
        return pages;
    }

    public void setPages(List<SitePage> pages) {
        this.pages = pages;
    }
}
