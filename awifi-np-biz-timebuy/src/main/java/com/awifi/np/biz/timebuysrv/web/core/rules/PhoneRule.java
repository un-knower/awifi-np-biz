package com.awifi.np.biz.timebuysrv.web.core.rules;

import com.awifi.np.biz.timebuysrv.util.StringUtil;

public class PhoneRule extends Rule {
	
	public PhoneRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isPhone(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("请输入正确的手机号码");
				return false;
			}
		}
	}

}
