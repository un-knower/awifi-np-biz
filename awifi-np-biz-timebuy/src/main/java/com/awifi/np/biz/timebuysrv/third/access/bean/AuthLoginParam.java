/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 下午8:06:18
* 创建作者：张智威
* 文件名称：AuthLoginParam.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.third.access.bean;

public class AuthLoginParam {
    //日志ID
    String logId;
    //设备ID
    String deviceId;
    //商户ID
    Long merchantId;
    //用户MAC
    String usermac;
    //平台名称
    String platform;
    //认证类型
    String authType;
    //用户真实IP
    String publicuserip;
    //用户真实端口
    String publicuserport;
    //手机号
    String mobilephone;
    //验证码
    String smscode;
    //用户ID
    Long userId;
    //用户名
    String username;
    //密码
    String password;
    //校验码
    String token;
    //溯源类型
    String tracetype;
    //溯源值
    String tracevalue;
    //用户IP
    String userip;
    //NAS设备名称
    String nasname;
    //热点名称
    String ssid;
    //回调函数名称
    String callback;
    public String getLogId() {
        return logId;
    }
    public void setLogId(String logId) {
        this.logId = logId;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getUsermac() {
        return usermac;
    }
    public void setUsermac(String usermac) {
        this.usermac = usermac;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getAuthType() {
        return authType;
    }
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    public String getPublicuserip() {
        return publicuserip;
    }
    public void setPublicuserip(String publicuserip) {
        this.publicuserip = publicuserip;
    }
    public String getPublicuserport() {
        return publicuserport;
    }
    public void setPublicuserport(String publicuserport) {
        this.publicuserport = publicuserport;
    }
    public String getMobilephone() {
        return mobilephone;
    }
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
    public String getSmscode() {
        return smscode;
    }
    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getTracetype() {
        return tracetype;
    }
    public void setTracetype(String tracetype) {
        this.tracetype = tracetype;
    }
    public String getTracevalue() {
        return tracevalue;
    }
    public void setTracevalue(String tracevalue) {
        this.tracevalue = tracevalue;
    }
    public String getUserip() {
        return userip;
    }
    public void setUserip(String userip) {
        this.userip = userip;
    }
    public String getNasname() {
        return nasname;
    }
    public void setNasname(String nasname) {
        this.nasname = nasname;
    }
    public String getSsid() {
        return ssid;
    }
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    public String getCallback() {
        return callback;
    }
    public void setCallback(String callback) {
        this.callback = callback;
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
}
