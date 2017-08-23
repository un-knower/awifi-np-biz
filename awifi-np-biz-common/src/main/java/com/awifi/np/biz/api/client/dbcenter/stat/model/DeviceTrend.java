/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月28日 上午9:09:03
* 创建作者：周颖
* 文件名称：DeviceTrend.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.stat.model;

import java.io.Serializable;

public class DeviceTrend implements Serializable {

    /**
     * 序列化uid
     */
    private static final long serialVersionUID = -3189740700844309762L;
    
    /**地区id  31-83-3229*/
    private String areaId;
    
    /**地区名称*/
    private String areaName;
    
    /**是否有下级*/
    private Boolean hasChild; 
    
    /**受理数-到达数*/
    private Integer acceptArriveNum;
    
    /**受理数-新增数*/
    private Integer acceptNewNum;
    
    /**受理数-拆机数*/
    private Integer acceptUnbindeNum;
    
    /**AP类激活绑定数-到达数*/
    private Integer apArriveNum;
    
    /**AP类激活绑定数-新增数*/
    private Integer apNewNum;
    
    /**AP类激活绑定数-解绑数*/
    private Integer apUnbindNum;

    /**AP类激活绑定数-活跃数*/
    private Integer apActiveNum;
    
    /**到达商户数*/
    private Integer arriveMerchantNum;
    
    /**活跃商户数*/
    private Integer activeMerchantNum;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Integer getAcceptArriveNum() {
        return acceptArriveNum;
    }

    public void setAcceptArriveNum(Integer acceptArriveNum) {
        this.acceptArriveNum = acceptArriveNum;
    }

    public Integer getAcceptNewNum() {
        return acceptNewNum;
    }

    public void setAcceptNewNum(Integer acceptNewNum) {
        this.acceptNewNum = acceptNewNum;
    }

    public Integer getAcceptUnbindeNum() {
        return acceptUnbindeNum;
    }

    public void setAcceptUnbindeNum(Integer acceptUnbindeNum) {
        this.acceptUnbindeNum = acceptUnbindeNum;
    }

    public Integer getApArriveNum() {
        return apArriveNum;
    }

    public void setApArriveNum(Integer apArriveNum) {
        this.apArriveNum = apArriveNum;
    }

    public Integer getApNewNum() {
        return apNewNum;
    }

    public void setApNewNum(Integer apNewNum) {
        this.apNewNum = apNewNum;
    }

    public Integer getApUnbindNum() {
        return apUnbindNum;
    }

    public void setApUnbindNum(Integer apUnbindNum) {
        this.apUnbindNum = apUnbindNum;
    }

    public Integer getApActiveNum() {
        return apActiveNum;
    }

    public void setApActiveNum(Integer apActiveNum) {
        this.apActiveNum = apActiveNum;
    }

    public Integer getArriveMerchantNum() {
        return arriveMerchantNum;
    }

    public void setArriveMerchantNum(Integer arriveMerchantNum) {
        this.arriveMerchantNum = arriveMerchantNum;
    }

    public Integer getActiveMerchantNum() {
        return activeMerchantNum;
    }

    public void setActiveMerchantNum(Integer activeMerchantNum) {
        this.activeMerchantNum = activeMerchantNum;
    }
}
