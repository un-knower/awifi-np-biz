/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:21:09
* 创建作者：周颖
* 文件名称：SitePage.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.site.model;

import java.io.Serializable;
import java.util.List;

public class SitePage implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 8422122573015188322L;

    /**主键id*/
    private Long pageId;
    
    /**站点页面路径*/
    private String pagePath;
    
    /**页面类型*/
    private Integer pageType;
    
    /**页面顺序*/
    private Integer num;
    
    /**页面创建时间*/
    private String createDate;
    
    /**页面修改时间*/
    private String updateDate;
    
    /**站点id*/
    private Long siteId;
    
    /**站点页面组件*/
    private List<SitePageComponent> componets;
    
    /**预览页地址*/
    private String url;
    
    /**站点页位置*/
    private String position;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public List<SitePageComponent> getComponets() {
        return componets;
    }

    public void setComponets(List<SitePageComponent> componets) {
        this.componets = componets;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
