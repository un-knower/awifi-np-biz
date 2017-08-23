/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月11日 下午5:03:25
* 创建作者：王冬冬
* 文件名称：WhiteUser.java
* 版本：  v1.0
* 功能：白名单实体类
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.model;

import com.awifi.np.biz.common.util.FormatUtil;

public class WhiteUser {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 商户id
     */
    private Long merchantId;
    /**
     * 手机号
     */
    private String cellPhone;
    /**
     * mac地址
     */
    private String mac;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 商户名
     */
    private String merchantName;
    /**
     * 用户类型：1代表普通员工、2代表VIP客户、3代表终端体验区
     */
    private Integer userType;
    
    /**
     * 创建日期，示例：2017-01-19 09:50:27
     */
    private String createDate;
    
    /**
     * accouctId 默认为空
     */
    private Integer accountId;
    /**
     * userId 默认为空
     */
    private Integer userId;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    public String getCellPhone() {
        return cellPhone;
    }
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public Integer getUserType() {
        return userType;
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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * 静态用户类型显示值
     * @return dsp
     * @date 2017年2月13日 下午7:17:56
     */
    public String getUserTypeDsp(){
        return FormatUtil.userTypeDsp(userType);
    }
    
    public Integer getAccountId() {
        return accountId;
    }
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
