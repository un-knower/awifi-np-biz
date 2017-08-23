package com.awifi.np.biz.timebuysrv.web.core.rules;

import com.awifi.np.biz.timebuysrv.util.StringUtil;


public class EmailRule extends Rule {
	
	public EmailRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isEmail(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("请输入邮箱地址");
				return false;
			}
		}
	}

}
