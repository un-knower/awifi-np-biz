package com.awifi.np.biz.timebuysrv.web.core.rules;

import com.awifi.np.biz.timebuysrv.util.StringUtil;


public class CheckBox extends Rule {
	private String[] cherkArr;
	public String[] getCherkArr() {
		return cherkArr;
	}
	public void setCherkArr(String[] cherkArr) {
		this.cherkArr = cherkArr;
	}

	public CheckBox(String[] cherkArr) {
		super();
		this.cherkArr = cherkArr;
	}
	
	@Override
	public boolean valid() throws Exception{
        if (this.getValue() == null || this.getValue().equals("")) {
            return true;
        }

		boolean result = false;
		if(this.getCherkArr()!=null){
			for(String item : this.getCherkArr()){
				if(item.equalsIgnoreCase(this.getValue())){
					result = true;
					break;
				}
			}
		}
		
		if(result){
			return true;
		}else {
			this.setMessage("请选择");
			return false;
		}
	}
}
