package com.awifi.np.biz.toe.admin.usrmgr.blackuser.model;

import java.io.Serializable;

import com.awifi.np.biz.common.util.FormatUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午8:54:21 
 * 创建作者：周颖 
 * 文件名称：BlackUser.java 
 * 版本：v1.0 
 * 功能：黑名单实体 
 * 修改记录：
 */
public class BlackUser implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1207555741095278513L;

    /** 黑名单表主键id */
    private Long id;

    /**用户名*/
    private String userName;
    
    /** 匹配规则：1代表精确、2代表模糊 */
    private Integer matchRule;

    /** 手机号 */
    private String cellphone;

    /** 真实姓名 */
    private String realName;

    /** 商户id */
    private Long merchantId;

    /** 商户名称 */
    private String merchantName;

    /** 商户层级关系 */
    private String cascadeLabel;

    /** 用户类型：1代表普通员工、2代表VIP客户、3代表终端体验区 */
    private Integer userType;

    /** 创建日期，示例：2017-01-19 09:50:27 */
    private String createDate;
    
    /**备注*/
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(Integer matchRule) {
        this.matchRule = matchRule;
    }
    
    public String getMatchRuleDsp(){
        return FormatUtil.matchRuleDsp(matchRule);
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getCascadeLabel() {
        return cascadeLabel;
    }

    public void setCascadeLabel(String cascadeLabel) {
        this.cascadeLabel = cascadeLabel;
    }

    public Integer getUserType() {
        return userType;
    }

    /**
     * 用户类型转化
     * @return 显示值
     * @author 周颖  
     * @date 2017年2月13日 上午10:31:10
     */
    public String getUserTypeDsp(){
        return FormatUtil.userTypeDsp(userType);
    }
    
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }    
}