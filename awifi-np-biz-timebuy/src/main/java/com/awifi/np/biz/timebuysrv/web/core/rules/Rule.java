package com.awifi.np.biz.timebuysrv.web.core.rules;


public abstract class Rule{
	public String value;
	public String message;
	public static final String charset = "UTF-8";
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public abstract boolean valid() throws Exception;
}