package com.awifi.np.biz.timebuysrv.third.access.bean;

/**
 * 设备redis缓存bean
 * @author 张智威
 * 2017年4月14日 下午2:52:26
 */
public class DevParms {
	//手机号码
	private String username;
	//接入密码
	private String password;
	//设备id
	private String deviceId;
	//用户手机mac
	private String terMac;
	//设备mac
	private String apMac;
	//放通次数
	private Integer num;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getTerMac() {
		return terMac;
	}
	public void setTerMac(String terMac) {
		this.terMac = terMac;
	}
	public String getApMac() {
		return apMac;
	}
	public void setApMac(String apMac) {
		this.apMac = apMac;
	}
}
