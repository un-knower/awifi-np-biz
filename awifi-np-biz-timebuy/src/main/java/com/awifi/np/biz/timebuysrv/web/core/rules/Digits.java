package com.awifi.np.biz.timebuysrv.web.core.rules;

import com.awifi.np.biz.timebuysrv.util.StringUtil;


public class Digits extends Rule {
	int integer=10;
	int fraction=0;
	public Digits() {
		
	}
public Digits(int integer,int fraction) {
		this.integer=integer;
		this.fraction=fraction;
	}
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.checkFloat(this.value, integer, fraction)) {
				return true;
			}
			else {
				this.setMessage("请输入数字");
				return false;
			}
		}
	}

}
