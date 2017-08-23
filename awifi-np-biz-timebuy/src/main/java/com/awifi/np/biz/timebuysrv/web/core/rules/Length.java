package com.awifi.np.biz.timebuysrv.web.core.rules;



public class Length extends Rule {
	private int minLength;
	private int maxLength;
	
	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}


	public Length(int maxLength){
		this.minLength = 0;
		this.maxLength = maxLength;
	}
	
	public Length(int minLength, int maxLength){
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	@Override
	public boolean valid() throws Exception{
		if(this.getValue() != null && !this.getValue().equals("")){
			if(this.getValue().length()<minLength
					|| this.getValue().length()>maxLength){
				message = "字符长度"+this.getValue().length()+" 应控制在"+minLength+"~"+maxLength+"个字符";
				return false;
			}else {
				return true;
			}
		}
		else {
			return true;
		}
	}
}
