/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 上午10:36:20
* 创建作者：张智威
* 文件名称：PortalParam.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.model;

import java.io.Serializable;

/**
 * 用户访问portal 带上来的参数
 * @author 张智威
 * 2017年4月10日 上午11:00:54
 */
public class PortalParam implements Serializable{
    private static final long serialVersionUID =201783153823l; 
	String userType;
	/**
	 * 校验码
	 * 新用户请求发送验证码时需要，
	 * 免认证用户请求免认证服务时需要	
	 */
	String token;
	/**
	 * 用户访问的URL
	 */
	String url;
	/**
	 * 设备ID
	 */
	String deviceId;
	/**
	 * 手机号
	 */
	String mobilePhone;
	/**
	 * 用户终端MAC
	 */
	String userMac;
	/**
	 * 用户IP
	 */
	String userIP;
	/**
	 * 胖AP网关地址
	 */
	String gwAddress;
	/**
	 * 胖AP端口号
	 */
	String gwPort;
	/**
	 * NAS设备名称
	 */
	String nasName;
	/**
	 * 日志ID
	 */
	String logId;
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getUserMac() {
		return userMac;
	}
	public void setUserMac(String userMac) {
		this.userMac = userMac;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
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
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	
}
