/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月15日 下午2:07:05
* 创建作者：许小满
* 文件名称：PortalParams.java
* 版本：  v1.0
* 功能：Portal页面参数
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.server.portal.model;

import java.io.Serializable;

public class PortalParams implements Serializable {

    /** 对象序列号 */
    private static final long serialVersionUID = -8145910766213204868L;

    /** 全局日志key（日志ID） */
    private String globalKey;
    
    /** 设备id */
    private String devId;
    /** 胖AP类设备网关 （AP类认证有） */
    private String gwAddress;
    /** 胖AP类设备端口（AP类认证有） */
    private String gwPort;
    /** nas设备名称，NAS认证必填（NAS类认证时用） */
    private String nasName;
    
    /** 用户类型： NEW_USER 新用户、EXEMPT_AUTH_USER 免认证用户  */
    private String userType;
    /** 登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网  */
    private String loginType;
    /** 用户mac地址  */
    private String userMac;
    /** 用户ip  */
    private String userIp;
    /** 用户手机号  */
    private String userPhone;
    
    /** 用户浏览器输入的被拦截前的url原始地址  */
    private String url;
    
    //4.x 多余参数
    /** 省分平台-前缀  */
    private String platform;
    

    public String getGlobalKey() {
        return globalKey;
    }

    public void setGlobalKey(String globalKey) {
        this.globalKey = globalKey;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getGwAddress() {
        return gwAddress;
    }

    public void setGwAddress(String gwAddress) {
        this.gwAddress = gwAddress;
    }

    public String getGwPort() {
        return gwPort;
    }

    public void setGwPort(String gwPort) {
        this.gwPort = gwPort;
    }

    public String getNasName() {
        return nasName;
    }

    public void setNasName(String nasName) {
        this.nasName = nasName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "PortalParams [globalKey=" + globalKey + ", devId=" + devId
                + ", gwAddress=" + gwAddress + ", gwPort=" + gwPort
                + ", nasName=" + nasName + ", userType=" + userType
                + ", loginType=" + loginType + ", userMac=" + userMac
                + ", userIp=" + userIp + ", userPhone=" + userPhone + ", url="
                + url + ", platform=" + platform + "]";
    }
}
