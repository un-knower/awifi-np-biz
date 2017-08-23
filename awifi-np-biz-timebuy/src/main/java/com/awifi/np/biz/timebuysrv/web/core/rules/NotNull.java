package com.awifi.np.biz.timebuysrv.web.core.rules;




public class NotNull extends Rule {
	

	
	public NotNull(){
	}

	@Override
	public boolean valid() throws Exception{
		if(this.getValue() == null ){
			
				message = "不能为空";
				return false;
			}else {
				return true;
			}
		
	}
}
