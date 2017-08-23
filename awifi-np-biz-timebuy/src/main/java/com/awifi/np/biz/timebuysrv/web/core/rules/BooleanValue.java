package com.awifi.np.biz.timebuysrv.web.core.rules;

import com.awifi.np.biz.timebuysrv.util.StringUtil;


public class BooleanValue extends Rule {
	
	@Override
	public boolean valid() throws Exception {
        if (this.getValue() == null) {
            return true;
        }

		if (this.getValue() != null && 
				(this.getValue().equalsIgnoreCase(Boolean.TRUE.toString()) 
						|| this.getValue().equalsIgnoreCase(Boolean.FALSE.toString()))){
			return true;
		}
		else {
			this.setMessage("请选择");
			return false;			
		}
	
	}
}
