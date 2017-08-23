package com.awifi.np.biz.timebuysrv.user.bean;

import java.io.Serializable;
import java.util.Date;

public class Captcha implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7973389548888353507L;
	/** * 手机号 */
	private String mobile;
	/** * 验证码*/
	private String captcha;
	/** * 发送时间 */
	private Date sendTime;

	public Captcha(String mobile, String captcha, Date sendTime) {
		super();
		this.mobile = mobile;
		this.captcha = captcha;
		this.sendTime = sendTime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	
}
