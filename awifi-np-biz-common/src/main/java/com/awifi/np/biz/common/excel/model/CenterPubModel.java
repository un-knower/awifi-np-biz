package com.awifi.np.biz.common.excel.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;


/**
 *@ClassName:CenterPubModel
 *@Description:
 *@author root
 *
 */
public class CenterPubModel implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 687036197158058L;

    /**
     * 主键 id
     */
    private Long id;
    
    /**
     * 
     */
    private String modelCode;

    /**
     * 型号名字
     */
    private String modelName;

    /**
     * 型号状态 状态 1:正常；9:作废
     */
    private Integer status;
    
    /**
     * 厂商id
     */
    private Long corpId;
    
    /**
     * 厂商名字
     */
    private String corpIdText;

    /**
     * 设备类型 11 BAS,21 AC,31 FAT_AP,32 GPON,33 GPON_W,34 EPON,35 EPON_W, 36 二合一,37 三合一
     */
    private Integer entityType;
    
    /**
     * 最大集采数
     */
    private Long collectionNum;
    /**
     * 入库数量
     */
    private int importNum;
    
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 收获地厂家列表
     */
    @JSONField(name="info")
    private List<CenterPubModelContract> origins;

    public Long getId() {
    	return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
    	this.modelCode = modelCode;
    }

    public String getModelName() {
    	return modelName;
    }

    public void setModelName(String modelName) {
    	this.modelName = modelName;
    }

    public Integer getStatus() {
    	return status;
    }

    public void setStatus(Integer status) {
    	this.status = status;
    }

    public Long getCorpId() {
    	return corpId;
    }

    public void setCorpId(Long corpId) {
    	this.corpId = corpId;
    }

    public String getCorpIdText() {
    	return corpIdText;
    }

    public void setCorpIdText(String corpIdText) {
    	this.corpIdText = corpIdText;
    }

    public Integer getEntityType() {
    	return entityType;
    }

    public void setEntityType(Integer entityType) {
    	this.entityType = entityType;
    }

    public Long getCollectionNum() {
    	return collectionNum;
    }

    public void setCollectionNum(Long collectionNum) {
    	this.collectionNum = collectionNum;
    }

    public int getImportNum() {
    	return importNum;
    }

    public void setImportNum(int importNum) {
    	this.importNum = importNum;
    }

    public Date getCreateDate() {
    	return createDate;
    }

    public void setCreateDate(Date createDate) {
    	this.createDate = createDate;
    }

    public Date getModifyDate() {
    	return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
    	this.modifyDate = modifyDate;
    }

    public List<CenterPubModelContract> getOrigins() {
    	return origins;
    }

    public void setOrigins(List<CenterPubModelContract> origins) {
    	this.origins = origins;
    }
	
    public static long getSerialversionuid() {
    	return serialVersionUID;
    }

    @Override
    public String toString() {
    	return "CenterPubModel [id=" + id + ", modelCode=" + modelCode + ", modelName=" + modelName + ", status="+ status + ", corpId=" + corpId + ", corpIdText=" + corpIdText + ", entityType=" + entityType
				+ ", collectionNum=" + collectionNum + ", importNum=" + importNum + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", info=" + origins + "]";
    }
	
	
}