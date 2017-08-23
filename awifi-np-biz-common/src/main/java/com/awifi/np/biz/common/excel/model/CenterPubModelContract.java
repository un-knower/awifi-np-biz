package com.awifi.np.biz.common.excel.model;

import java.util.Date;

public class CenterPubModelContract {
	/**
	 * 主键 id
	 */
    private Long id;

	/**
	 * 合同编号
	 */
	//@JSONField(name="contractNO")
    private Long contractId;
	
	/**
	 * 本地区最大集采数量
	 */
    private Long collectionNum;
    
    /**
     * 型号编号
     */
	
    private Long modelId;
    
    /**
     * 发货地区-省
     */
    private Long province;
    
    /**
     * 发货地区-省-翻译
     */
    private String provinceText;
    
    /**
     * 关系状态 状态 1:正常；9:作废
     */
    private Long status;
    
    /**
     * 创建时间
     */
    private Date createDate;
    
    /**
     * 修改时间
     */
    private Date modifyDate;


    
    
    public Long getId() {
    	return id;
    }

    public void setId(Long id) {
    	this.id = id;
    }

    public Long getContractId() {
    	return contractId;
    }

    public void setContractId(Long contract) {
    	this.contractId = contract;
    }

    public Long getCollectionNum() {
    	return collectionNum;
    }

    public void setCollectionNum(Long collectionNum) {
    	this.collectionNum = collectionNum;
    }

    public Long getModelId() {
    	return modelId;
    }

    public void setModelId(Long modelId) {
    	this.modelId = modelId;
    }

    public Long getProvince() {
    	return province;
    }

    public void setProvince(Long province) {
    	this.province = province;
    }

    public String getProvinceText() {
    	return provinceText;
    }

    public void setProvinceText(String provinceText) {
    	this.provinceText = provinceText;
    }

    public Long getStatus() {
    	return status;
    }

    public void setStatus(Long status) {
    	this.status = status;
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
}