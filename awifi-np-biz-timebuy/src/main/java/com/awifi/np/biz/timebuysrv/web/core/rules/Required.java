package com.awifi.np.biz.timebuysrv.web.core.rules;


public class Required extends Rule {
	
	public Required(){
		
	}
	
	public boolean valid(){
		if(this.getValue()==null || this.getValue().equals("")){
			this.setMessage("不能为空");
			return false;
		}else {
			return true;
		}
	}
}
