package com.awifi.np.biz.timebuysrv.web.module.time.model;

import java.io.Serializable;

/**
 * 存放session用户的地方
 * 
 * @author 张智威 2017年4月10日 上午10:03:30
 */
public class SessionUser implements  Serializable{
  
	/**
     * 
     */
    private static final long serialVersionUID = -9016383875942796219L;
    /**
	 * 角色
	 */
	String roleType;
	/**
	 * 头像
	 */
	String face;
	/**
	 * 用户id
	 */
	Long id;
	/**
	 * 地址
	 */
	String address;
	/**
	 * 性别
	 */
	int sex;// 1男 2女 0未知
	/**
	 * 生日
	 */
	Long birthday;
	/**
	 * 昵称
	 */
	String nick;
	// private boolean isVipUser;
	/**
	 * 接入认证密码
	 */
	private String authPswd;
	/**
	 * 手机号码
	 */
	String phone;
	/**
	 * 姓名
	 */
	String name;
	/*
	 * Long authId; Long pubId;
	 */

	/*
	 * public Long getAuthId() { return authId; }
	 * 
	 * public void setAuthId(Long authId) { this.authId = authId; }
	 * 
	 * public Long getPubId() { return pubId; }
	 * 
	 * public void setPubId(Long pubId) { this.pubId = pubId; }
	 */

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	/*
	 * 
	 * 
	 * public boolean isVipUser() { return isVipUser; }
	 * 
	 * public void setVipUser(boolean vipUser) { isVipUser = vipUser; }
	 */

	public String getAuthPswd() {
		return authPswd;
	}

	public void setAuthPswd(String authPswd) {
		this.authPswd = authPswd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
