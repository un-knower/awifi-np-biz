package com.awifi.np.biz.toe.admin.thirdapp.microshop.model;

import java.io.Serializable;

/**   
 * @Description:  
 * @Title: Microshop.java 
 * @Package com.awifi.toe.admin.microshop.model 
 * @author 亢燕翔 
 * @date 2016年3月25日 下午2:59:39
 * @version V1.0   
 */
public class Microshop implements Serializable{

    /** 序列化  */
    private static final long serialVersionUID = -9186093935123749105L;

    /** 主键  */
    private Long id;
    
    /** shopId */
    private String shopId;
    
    /** shopName */
    private String shopName;
    
    /** 微旺铺应用秘钥  */
    private String wifiKey;
    
    /** 应用ID  */
    private String appid;
    
    /** 微旺铺应用ID  */
    private String wwpAppid;

    /** 关联模式  */
    private Integer spreadModel;
    
    /** 客户层级  */
    private String cascadeLabel;
    
    /** 是否强制关注  */
    private Integer forceAttention;
    
    /** 是否关注  */
    private Integer attentionFlag;
    
    /** 客户ID  */
    private Long customerId;
    /** 关联客户ID  */
    private Long relateCustomerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getWifiKey() {
        return wifiKey;
    }

    public void setWifiKey(String wifiKey) {
        this.wifiKey = wifiKey;
    }

    public Integer getSpreadModel() {
        return spreadModel;
    }

    public void setSpreadModel(Integer spreadModel) {
        this.spreadModel = spreadModel;
    }

    public Long getRelateCustomerId() {
        return relateCustomerId;
    }

    public void setRelateCustomerId(Long relateCustomerId) {
        this.relateCustomerId = relateCustomerId;
    }

    public Integer getForceAttention() {
        return forceAttention;
    }

    public void setForceAttention(Integer forceAttention) {
        this.forceAttention = forceAttention;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCascadeLabel() {
        return cascadeLabel;
    }

    public void setCascadeLabel(String cascadeLabel) {
        this.cascadeLabel = cascadeLabel;
    }

    public Integer getAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(Integer attentionFlag) {
        this.attentionFlag = attentionFlag;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getWwpAppid() {
        return wwpAppid;
    }

    public void setWwpAppid(String wwpAppid) {
        this.wwpAppid = wwpAppid;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}