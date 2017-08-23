package com.awifi.np.biz.timebuysrv.web.core.rules;

import com.awifi.np.biz.timebuysrv.util.StringUtil;


public class IDCardRule extends Rule {
	
	public IDCardRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isID(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("请输入身份证号");
				return false;
			}
		}
	}

}
