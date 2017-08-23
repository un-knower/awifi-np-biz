package com.awifi.np.biz.timebuysrv.web.module.time.model;


import java.io.Serializable;
import java.util.Date;


/**
 * 商户下用户上网剩余时长信息
 * @author 张智威
 *
 */
public class UserCutoff implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4429625803963910661L;
	/** *  center_pub_merchant_user_cutoff_date.id */
	private Long id;
	/** *  center_pub_merchant_user_cutoff_date.merchantId 商户编号 */
	private Long merchantId;
	/** *  center_pub_merchant_user_cutoff_date.userId 用户编号 */
	private Long userId;
	/** *  center_pub_merchant_user_cutoff_date.cutoffDate  可用截至日期*/
	private Date cutoffDate;
	/** *  center_pub_merchant_user_cutoff_date.remarks 描述*/
	private String remarks;
	/** *   全流程日志关键词*/
	private String globalKey;
	/** *   全流程日志关键值*/
	private String globalValue;
	/** *   预留参数*/
	private String globalStandby;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCutoffDate() {
		return cutoffDate;
	}
	public void setCutoffDate(Date cutoffDate) {
		this.cutoffDate = cutoffDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getGlobalKey() {
		return globalKey;
	}
	public void setGlobalKey(String globalKey) {
		this.globalKey = globalKey;
	}
	public String getGlobalValue() {
		return globalValue;
	}
	public void setGlobalValue(String globalValue) {
		this.globalValue = globalValue;
	}
	public String getGlobalStandby() {
		return globalStandby;
	}
	public void setGlobalStandby(String globalStandby) {
		this.globalStandby = globalStandby;
	}
	
	
}
