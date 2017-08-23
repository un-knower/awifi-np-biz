/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 下午5:12:10
* 创建作者：周颖
* 文件名称：SitePageComponent.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.site.model;

import java.io.Serializable;

public class SitePageComponent implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3293396247515582989L;
    
    /**主键id*/
    private Long sitePageComponentId;;
    
    /**站点页id*/
    private Long sitePageId;
    
    /**初始化参数json数据*/
    private String json;
    
    /**创建日期*/
    private String createDate;
    
    /**修改日期*/
    private String updateDate;
    
    /**组件顺序*/
    private Integer orderNo;

    /**组件id*/
    private Long componentId;
    
    /**层级关系*/
    private String cascadeLabel;

    public Long getSitePageComponentId() {
        return sitePageComponentId;
    }

    public void setSitePageComponentId(Long sitePageComponentId) {
        this.sitePageComponentId = sitePageComponentId;
    }

    public Long getSitePageId() {
        return sitePageId;
    }

    public void setSitePageId(Long sitePageId) {
        this.sitePageId = sitePageId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
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

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    public String getCascadeLabel() {
        return cascadeLabel;
    }

    public void setCascadeLabel(String cascadeLabel) {
        this.cascadeLabel = cascadeLabel;
    }
}
